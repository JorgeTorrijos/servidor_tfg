package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.CarreraFavoritaInsert;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoCarrerasFavoritas {

    private final DBConnectionPool dbConnection;

    @Inject
    public DaoCarrerasFavoritas(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, String> deleteCarrera(CarreraFavoritaInsert carreraFavoritaInsert) {

        Either<String, String> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            jdbcTemplate.update(ConstantesDAO.DELETE_FROM_CARRERAS_FAVORITAS_WHERE_ID_CARRERA_AND_USUARIO, carreraFavoritaInsert.getId_carrera(), carreraFavoritaInsert.getUsuario());

            respuesta = Either.left(ConstantesDAO.CARRERA_ELIMINADA);


        } catch (DataAccessException dataAccessException) {

            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.CARRERAS_FAVORITAS_NO_ENCONTRADAS);

        }

        return respuesta;

    }

    public Either<String, CarreraFavoritaInsert> insertCarrerasFavoritas(CarreraFavoritaInsert carreraToInsert) {

        //comprobamos que no esté repetida

        Either<String, CarreraFavoritaInsert> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        List<CarreraFavoritaInsert> lista = new ArrayList<>();

        try {

            jtm.query(ConstantesDAO.SELECT_FROM_CARRERAS_FAVORITAS_WHERE_USUARIO_AND_ID_CARRERA, new RowMapper<CarreraFavoritaInsert>() {
                @Override
                public CarreraFavoritaInsert mapRow(ResultSet resultSet, int i) throws SQLException {
                    CarreraFavoritaInsert carreraFavoritaInsert = new CarreraFavoritaInsert();
                    carreraFavoritaInsert.setId(resultSet.getInt(1));
                    carreraFavoritaInsert.setUsuario(resultSet.getString(2));
                    carreraFavoritaInsert.setId_carrera(resultSet.getInt(3));

                    lista.add(carreraFavoritaInsert);

                    return carreraFavoritaInsert;

                }
            }, carreraToInsert.getUsuario(), carreraToInsert.getId_carrera());

            if (lista.isEmpty()) {

                KeyHolder keyHolder = new GeneratedKeyHolder();

                try {

                    jtm.update(connection -> {

                        PreparedStatement ps = connection.prepareStatement(ConstantesDAO.INSERT_INTO_CARRERAS_FAVORITAS_USUARIO_ID_CARRERA_VALUES, Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, carreraToInsert.getUsuario());
                        ps.setInt(2, carreraToInsert.getId_carrera());


                        return ps;
                    }, keyHolder);

                    CarreraFavoritaInsert carreraFavoritaInsert = carreraToInsert;
                    carreraFavoritaInsert.setId(keyHolder.getKey().intValue());

                    respuesta = Either.right(carreraFavoritaInsert);

                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left(ConstantesDAO.PROBLEMA_AL_INSERTAR_CARRERA_FAVORITA);

                }

            } else {

                respuesta = Either.left(ConstantesDAO.PROBLEMA_AL_INSERTAR_CARRERA_FAVORITA_ESE_USER_YA_TIENE_ESA_CARRERA);

            }

        } catch (DataAccessException dataAccessException) {

            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.CARRERAS_FAVORITAS_NO_ENCONTRADAS_);

        }


        return respuesta;

    }

}
