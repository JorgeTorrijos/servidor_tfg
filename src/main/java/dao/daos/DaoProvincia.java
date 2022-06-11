package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.Provincia;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Log4j2
public class DaoProvincia {

    private final DBConnectionPool dbConnection;

    @Inject
    public DaoProvincia(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, List<Provincia>> filtrosProvincias() {

        Either<String, List<Provincia>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.FILTROS_PROVINCIAS, BeanPropertyRowMapper.newInstance(Provincia.class)));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.PROVINCIAS_NO_ENCONTRADAS);

        }

        return respuesta;

    }

}
