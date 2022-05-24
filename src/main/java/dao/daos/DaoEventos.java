package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.Carreras;
import dao.modelos.Eventos;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Log4j2
public class DaoEventos {

    private final DBConnectionPool dbConnection;

    @Inject
    public DaoEventos(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, List<Eventos>> getAllEventos(String provincia, String tipo) {

        Either<String, List<Eventos>> respuesta = null;

        if(provincia == null && tipo == null ){

            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

            try {

                respuesta = Either.right(jtm.query(ConstantesDAO.GET_ALL_EVENTOS, BeanPropertyRowMapper.newInstance(Eventos.class)));


            } catch (DataAccessException dataAccessException) {
                log.error(dataAccessException.getMessage());
                respuesta = Either.left("Eventos no encontrados");

            }

        } else if (provincia != null && tipo != null){

            if(!provincia.isEmpty() && !tipo.isEmpty()) {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_PROVINCIA_y_TIPO, BeanPropertyRowMapper.newInstance(Eventos.class), provincia, tipo));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left("Eventos no encontrados");

                }

            }else if(!provincia.isEmpty() && tipo.isEmpty()) {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_PROVINCIA, BeanPropertyRowMapper.newInstance(Eventos.class),provincia));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left("Eventos no encontrados");

                }

            } else if (!tipo.isEmpty() && provincia.isEmpty()){

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_TIPO_CARRERA, BeanPropertyRowMapper.newInstance(Eventos.class),tipo));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left("Eventos no encontrados");

                }

            } else {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.GET_ALL_EVENTOS, BeanPropertyRowMapper.newInstance(Eventos.class)));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left("Eventos no encontrados");

                }

            }

        } else {

            if(provincia != null && !provincia.isEmpty()){

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_PROVINCIA, BeanPropertyRowMapper.newInstance(Eventos.class),provincia));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left("Eventos no encontrados");

                }

            } else if (tipo != null && !tipo.isEmpty()){

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_TIPO_CARRERA, BeanPropertyRowMapper.newInstance(Eventos.class),tipo));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left("Eventos no encontrados");

                }

            }

        }

        return respuesta;

    }

    public Either<String, List<Eventos>> getEventoById(int id) {

        Either<String, List<Eventos>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_EVENTO_BY_ID, BeanPropertyRowMapper.newInstance(Eventos.class),id));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Eventos no encontrados");

        }

        return respuesta;

    }

}
