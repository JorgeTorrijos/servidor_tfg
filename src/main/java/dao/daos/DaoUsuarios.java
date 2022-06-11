package dao.daos;

import dao.bbdd.DBConnectionPool;
import dao.modelos.UserLogin;
import dao.modelos.Usuario;
import dao.modelos.UsuarioDTO;
import dao.modelos.UsuarioDTOlist;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoUsuarios {

    private final DBConnectionPool dbConnection;

    @Inject
    public DaoUsuarios(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<String, UsuarioDTO> registrarAdministrador(Usuario usuario) {
        Either<String, UsuarioDTO> resultado;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());

        usuario.setTipo_user(1); //todos los que se registran por aqui son administradores

        if (usuario.getUsername() != null && usuario.getPass() != null && usuario.getPeso() != 0) {

            try {
                jdbcTemplate.update(connection -> {

                    PreparedStatement ps = connection.prepareStatement(
                            ConstantesDAO.INSERT_INTO_USUARIOS_USERNAME_PASS_PESO_TIPO_USER_VALUES);

                    ps.setString(1, usuario.getUsername());
                    ps.setString(2, usuario.getPass());
                    ps.setDouble(3, usuario.getPeso());
                    ps.setInt(4, usuario.getTipo_user());

                    return ps;
                });

                resultado = Either.right(new UsuarioDTO(usuario.getUsername(), usuario.getPeso()));

            } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
                log.error(emptyResultDataAccessException.getMessage());
                resultado = Either.left(ConstantesDAO.FALTAN_PARAMETROS);

            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                log.error(dataIntegrityViolationException.getMessage());
                resultado = Either.left(ConstantesDAO.EL_USUARIO_YA_EXISTE);

            }


        } else {

            resultado = Either.left(ConstantesDAO.FALTAN_PARAMETROS);

        }

        return resultado;
    }

    public Either<String, UsuarioDTO> registrarUsuario(Usuario usuario) {

        Either<String, UsuarioDTO> resultado;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());

        usuario.setTipo_user(2); //todos los que se registran por aqui son normales

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
                resultado = Either.left(ConstantesDAO.FALTAN_PARAMETROS);

            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                log.error(dataIntegrityViolationException.getMessage());
                resultado = Either.left(ConstantesDAO.EL_USUARIO_YA_EXISTE);

            }


        } else {

            resultado = Either.left(ConstantesDAO.FALTAN_PARAMETROS);

        }

        return resultado;

    }

    public Either<String, UserLogin> getUserActivo(String correo) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());

        Either<String, UserLogin> respuesta = null;

        try {

            Usuario usuario = jdbcTemplate.queryForObject(ConstantesDAO.SELECT_FROM_USUARIOS_WHERE_USERNAME, BeanPropertyRowMapper.newInstance(Usuario.class), correo);

            if (usuario != null) {

                UserLogin userLogin = new UserLogin();
                userLogin.setUsername(usuario.getUsername());
                userLogin.setTipo_user(usuario.getTipo_user());
                userLogin.setPass(usuario.getPass());

                respuesta = Either.right(userLogin);

            } else {

                respuesta = Either.left(ConstantesDAO.NO_HAY_USUARIO);


            }

        } catch (DataAccessException dataAccessException) {

            respuesta = Either.left(ConstantesDAO.USUARIO_INCORRECTO);
            log.error(dataAccessException.getMessage());

        }

        return respuesta;

    }

    public Either<String, List<UsuarioDTOlist>> getAllUsuarios() {

        Either<String, List<UsuarioDTOlist>> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            respuesta = Either.right(jtm.query(ConstantesDAO.SELECT_USERNAME_PESO_TIPO_FROM_USUARIOS_JOIN_TIPO_USUARIO_TU_ON_TU_ID_USUARIOS_TIPO_USER, BeanPropertyRowMapper.newInstance(UsuarioDTOlist.class)));


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.USUARIOS_NO_ENCONTRADOS);

        }

        return respuesta;

    }

    public Either<String, String> deleteUsuario(String username) {

        Either<String, String> respuesta = null;

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        try {

            //comprobamos que primero tenga carreras favoritas -> cannot delete

            int numeroCarreras = tieneCarrerasFavoritas(username);

            if (numeroCarreras != 0) {

                //eliminamos los registros de las carreras

                //EMPEZAR TRANSICTION

                TransactionDefinition txDef = new DefaultTransactionDefinition();
                DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
                TransactionStatus txStatus = transactionManager.getTransaction(txDef);

                boolean sol = false;
                int salida = 0;

                try {
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(
                            Objects.requireNonNull(transactionManager.getDataSource())
                    );

                    //borramos de carreras favoritas
                    jdbcTemplate.update(ConstantesDAO.DELETE_FROM_CARRERAS_FAVORITAS_WHERE_USUARIO, username);
                    //borramos usuario
                    salida = jtm.update(ConstantesDAO.DELETE_FROM_USUARIOS_WHERE_USERNAME, username);

                    transactionManager.commit(txStatus);
                    sol = true;
                } catch (Exception e) {
                    sol = false;
                    transactionManager.rollback(txStatus);
                }

                if (sol == true) {
                    //Esto en caso de que exista o no
                    if (salida == 1) {
                        respuesta = Either.right(ConstantesDAO.USUARIO_CON_USERNAME + username + ConstantesDAO.ELIMINADO);
                    } else {
                        respuesta = Either.left(ConstantesDAO.USUARIO_CON_USERNAME + username + ConstantesDAO.NO_EXISTE);
                    }
                }

            } else {
                int salida = 0;
                salida = jtm.update("Delete from usuarios where username = ?", username);

                if (salida == 1) {
                    respuesta = Either.right(ConstantesDAO.USUARIO_CON_USERNAME + username + ConstantesDAO.ELIMINADO);
                } else {
                    respuesta = Either.left(ConstantesDAO.USUARIO_CON_USERNAME + username + ConstantesDAO.NO_EXISTE);
                }

            }


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            respuesta = Either.left(ConstantesDAO.USERNAME_NO_ENCONTRADO);

        }

        return respuesta;

    }

    public int tieneCarrerasFavoritas(String username) {

        JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());

        int numero = 0;

        try {

            numero = jtm.queryForObject(ConstantesDAO.SELECT_COUNT_FROM_CARRERAS_FAVORITAS_WHERE_USUARIO, Integer.class, username);


        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
        }

        return numero;

    }

}
