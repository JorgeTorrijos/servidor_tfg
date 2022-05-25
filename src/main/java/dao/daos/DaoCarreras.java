package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.CarreraSimpleMostrar;
import dao.modelos.CarreraToInsert;
import dao.modelos.Carreras;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Log4j2
public class DaoCarreras {

    private final DBConnectionPool dbConnection;

    @Inject
    public DaoCarreras(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, List<Carreras>> getAllCarrerasByIdEvento(int id) {

        Either<String, List<Carreras>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());


        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_CARRERAS_BY_ID_EVENTO, BeanPropertyRowMapper.newInstance(Carreras.class), id));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas");

        }

        return respuesta;


    }

    public Either<String, List<Carreras>> getCarrerasByID(int id) {

        Either<String, List<Carreras>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_CARRERA_BY_ID, BeanPropertyRowMapper.newInstance(Carreras.class), id));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas");

        }

        return respuesta;

    }

    public Either<String, List<Carreras>> getCarrerasByUser(String user) {

        Either<String, List<Carreras>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_CARRERAS_BY_USER, BeanPropertyRowMapper.newInstance(Carreras.class), user));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas");

        }

        return respuesta;

    }

    public Either<String, List<CarreraSimpleMostrar>> getAllCarreras() {

        Either<String, List<CarreraSimpleMostrar>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_ALL_CARRERAS, BeanPropertyRowMapper.newInstance(CarreraSimpleMostrar.class)));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas");

        }

        return respuesta;

    }

    public Either<String, CarreraToInsert> insertarCarrera(CarreraToInsert carreraToInsert) {

        Either<String, CarreraToInsert> respuesta = null;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {

            jdbcTemplate.update(connection -> {

                PreparedStatement ps = connection.prepareStatement(
                        ConstantesDAO.INSERTAR_CARRERAS, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, carreraToInsert.getTitulo());
                ps.setString(2, carreraToInsert.getDescripcion());
                ps.setString(3, carreraToInsert.getImg());
                ps.setDouble(4, carreraToInsert.getKm());
                ps.setString(5, carreraToInsert.getCiudad());
                ps.setDouble(6, carreraToInsert.getPrecio());
                ps.setString(7, carreraToInsert.getEnlace_compra());
                ps.setInt(8, carreraToInsert.getId_evento());


                return ps;
            }, keyHolder);

            CarreraToInsert carreraToInsert1 = carreraToInsert;
            carreraToInsert1.setId(String.valueOf(keyHolder.getKey().intValue()));

            respuesta = Either.right(carreraToInsert1);

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("PROBLEMA AL INSERTAR CARRERA");

        }

        return respuesta;

    }

    /*

    public Either<String, List<Carreras>> getAllCarreras() {

        Either<String, List<Carreras>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_ALL_CARRERAS, BeanPropertyRowMapper.newInstance(Carreras.class)));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas");

        }

        return respuesta;

    }

    public Either<String, List<Carreras>> getUltimasCarrerasAdded() {


        Either<String, List<Carreras>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_ULTIMAS_CARRERAS_ADD_5, BeanPropertyRowMapper.newInstance(Carreras.class)));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas");

        }

        return respuesta;

    }

    public Either<String, List<Carreras>> filtradasPorProvincia(String provincia) {


        Either<String, List<Carreras>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.FILTRADAS_POR_PROVINCIA, BeanPropertyRowMapper.newInstance(Carreras.class),provincia));

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas en la provincia de " + provincia);

        }

        return respuesta;

    }

    public Either<String, List<Carreras>> filtradasPorTipo(String tipo) {



        Either<String, List<Carreras>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.FILTRADAS_POR_TIPO_CARRERA, BeanPropertyRowMapper.newInstance(Carreras.class),tipo));

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras de "+tipo+" no encontradas");

        }

        return respuesta;

    }


     */
}
