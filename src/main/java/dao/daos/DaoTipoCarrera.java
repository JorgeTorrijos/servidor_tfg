package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.TipoCarrera;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Log4j2
public class DaoTipoCarrera {

    private final DBConnectionPool dbConnection;

    @Inject
    public DaoTipoCarrera(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, List<TipoCarrera>> filtrosTipoCarrera() {

        Either<String, List<TipoCarrera>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.FILTROS_TIPO_CARRERA, BeanPropertyRowMapper.newInstance(TipoCarrera.class)));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left("Tipos de Carrera no encontrados");

        }

        return respuesta;

    }

}
