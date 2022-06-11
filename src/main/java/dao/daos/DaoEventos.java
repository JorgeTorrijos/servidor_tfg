package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.Eventos;
import dao.modelos.EventosToInsert;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

        if (provincia == null && tipo == null) {

            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

            try {

                respuesta = Either.right(jtm.query(ConstantesDAO.GET_ALL_EVENTOS, BeanPropertyRowMapper.newInstance(Eventos.class)));


            } catch (DataAccessException dataAccessException) {
                log.error(dataAccessException.getMessage());
                respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

            }

        } else if (provincia != null && tipo != null) {

            if (!provincia.isEmpty() && !tipo.isEmpty()) {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_PROVINCIA_y_TIPO, BeanPropertyRowMapper.newInstance(Eventos.class), provincia, tipo));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

                }

            } else if (!provincia.isEmpty() && tipo.isEmpty()) {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_PROVINCIA, BeanPropertyRowMapper.newInstance(Eventos.class), provincia));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

                }

            } else if (!tipo.isEmpty() && provincia.isEmpty()) {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_TIPO_CARRERA, BeanPropertyRowMapper.newInstance(Eventos.class), tipo));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

                }

            } else {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.GET_ALL_EVENTOS, BeanPropertyRowMapper.newInstance(Eventos.class)));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

                }

            }

        } else {

            if (provincia != null && !provincia.isEmpty()) {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_PROVINCIA, BeanPropertyRowMapper.newInstance(Eventos.class), provincia));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

                }

            } else if (tipo != null && !tipo.isEmpty()) {

                JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

                try {

                    respuesta = Either.right(jtm.query(ConstantesDAO.EVENTOS_FILTRADOS_POR_TIPO_CARRERA, BeanPropertyRowMapper.newInstance(Eventos.class), tipo));


                } catch (DataAccessException dataAccessException) {
                    log.error(dataAccessException.getMessage());
                    respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

                }

            }

        }

        return respuesta;

    }

    public Either<String, List<Eventos>> getEventoById(int id) {

        Either<String, List<Eventos>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.GET_EVENTO_BY_ID, BeanPropertyRowMapper.newInstance(Eventos.class), id));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADOS);

        }

        return respuesta;

    }

    public Either<String, EventosToInsert> insertarEvento(EventosToInsert eventos) {

        Either<String, EventosToInsert> respuesta = null;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {

            jdbcTemplate.update(connection -> {

                PreparedStatement ps = connection.prepareStatement(
                        ConstantesDAO.INSERT_EVENTOS, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, eventos.getTitulo());
                ps.setString(2, eventos.getDescripcion());
                ps.setString(3, eventos.getImg());
                ps.setInt(4, eventos.getTipo());
                ps.setInt(5, eventos.getProvincia());
                ps.setString(6, eventos.getPag_oficial_evento());
                ps.setDate(7, Date.valueOf(eventos.getFecha()));


                return ps;
            }, keyHolder);

            EventosToInsert eventosToInsertNew = eventos;
            eventosToInsertNew.setId(String.valueOf(keyHolder.getKey().intValue()));

            respuesta = Either.right(eventosToInsertNew);

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.PROBLEMA_AL_INSERTAR_EVENTO);

        }

        return respuesta;

    }

    public Either<String, String> deleteEvento(String id) {

        Either<String, String> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            jtm.update(ConstantesDAO.DELETE_FROM_EVENTOS_WHERE_ID, id);
            respuesta = Either.right(ConstantesDAO.EVENTO_CON_ID + id + ConstantesDAO.ELIMINADO_);

        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.EVENTOS_NO_ENCONTRADO);

        }

        return respuesta;

    }

}
