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

    //select * from carreras_favoritas where usuario=? and id_carrera=?;

    //insert into carreras_favoritas (usuario, id_carrera) VALUES (?,?);

    public Either<String, String> deleteCarrera(CarreraFavoritaInsert carreraFavoritaInsert) {

        Either<String, String> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            jdbcTemplate.update("delete from carreras_favoritas where id_carrera = ? and usuario = ?", carreraFavoritaInsert.getId_carrera(), carreraFavoritaInsert.getUsuario());

            respuesta = Either.left("Carrera eliminada");


        } catch (DataAccessException dataAccessException) {

            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras Favoritas no encontradas");

        }

        return respuesta;

    }

    public Either<String, CarreraFavoritaInsert> insertCarrerasFavoritas(CarreraFavoritaInsert carreraToInsert) {

        //comprobamos que no esté repetida

        Either<String, CarreraFavoritaInsert> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        List<CarreraFavoritaInsert> lista = new ArrayList<>();

        try {

            jtm.query("select * from carreras_favoritas where usuario=? and id_carrera=?", new RowMapper<CarreraFavoritaInsert>() {
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

                        PreparedStatement ps = connection.prepareStatement("insert into carreras_favoritas (usuario, id_carrera) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, carreraToInsert.getUsuario());
                        ps.setInt(2, carreraToInsert.getId_carrera());


                        return ps;
                    }, keyHolder);

                    CarreraFavoritaInsert carreraFavoritaInsert = carreraToInsert;
                    carreraFavoritaInsert.setId(keyHolder.getKey().intValue());

                    respuesta = Either.right(carreraFavoritaInsert);

                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left("PROBLEMA AL INSERTAR CARRERA FAVORITA");

                }

            } else {

                respuesta = Either.left("PROBLEMA AL INSERTAR CARRERA FAVORITA / ESE USER YA TIENE ESA CARRERA");

            }

        } catch (DataAccessException dataAccessException) {

            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras Favoritas no encontradas");

        }


        return respuesta;

    }

}
