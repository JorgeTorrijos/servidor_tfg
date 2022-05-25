package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.UserLogin;
import dao.modelos.Usuario;
import dao.modelos.UsuarioDTO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;

@Log4j2
public class DaoUsuarios {

    private final DBConnectionPool dbConnection;

    @Inject
    public DaoUsuarios(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, UsuarioDTO> registrarUsuario(Usuario usuario) {

        Either<String, UsuarioDTO> resultado;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());

        if (usuario.getUsername() != null && usuario.getPass() != null && usuario.getPeso() != 0) {

            try {
                jdbcTemplate.update(connection -> {

                    PreparedStatement ps = connection.prepareStatement(
                            "insert into usuarios (username, pass, peso, tipo_user) VALUES (?,?,?,?)");

                    ps.setString(1, usuario.getUsername());
                    ps.setString(2, usuario.getPass());
                    ps.setDouble(3, usuario.getPeso());
                    ps.setInt(4, usuario.getTipo_user());

                    return ps;
                });

                resultado = Either.right(new UsuarioDTO(usuario.getUsername(), usuario.getPeso()));

            } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
                log.error(emptyResultDataAccessException.getMessage());
                resultado = Either.left("FALTAN PARAMETROS");

            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                log.error(dataIntegrityViolationException.getMessage());
                resultado = Either.left("EL USUARIO YA EXISTE");

            }


        } else {

            resultado = Either.left("FALTAN PARAMETROS");

        }

        return resultado;

    }

    public Either<String, UserLogin> getUserActivo(String correo) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());

        Either<String, UserLogin> respuesta = null;

        try {

            Usuario usuario = jdbcTemplate.queryForObject("select * from usuarios where username = ?", BeanPropertyRowMapper.newInstance(Usuario.class), correo);

            if (usuario != null) {

                UserLogin userLogin = new UserLogin();
                userLogin.setUsername(usuario.getUsername());
                userLogin.setTipo_user(usuario.getTipo_user());
                userLogin.setPass(usuario.getPass());

                respuesta = Either.right(userLogin);

            } else {

                respuesta = Either.left("No hay usuario");


            }

        } catch (DataAccessException dataAccessException) {

            respuesta = Either.left("Usuario incorrecto");
            log.error(dataAccessException.getMessage());

        }


        return respuesta;

    }

}
