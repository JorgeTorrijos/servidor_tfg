package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.Carreras;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_CARRERAS_BY_ID_EVENTO, BeanPropertyRowMapper.newInstance(Carreras.class),id));


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

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_CARRERA_BY_ID, BeanPropertyRowMapper.newInstance(Carreras.class),id));


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

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_CARRERAS_BY_USER, BeanPropertyRowMapper.newInstance(Carreras.class),user));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Carreras no encontradas");

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
