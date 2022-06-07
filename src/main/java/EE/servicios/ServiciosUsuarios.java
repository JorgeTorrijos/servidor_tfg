package EE.servicios;

import dao.daos.DaoUsuarios;
import dao.modelos.UserLogin;
import dao.modelos.Usuario;
import dao.modelos.UsuarioDTO;
import dao.modelos.UsuarioDTOlist;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ServiciosUsuarios {

    private final DaoUsuarios daoUsuarios;

    @Inject
    public ServiciosUsuarios(DaoUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public Either<String, UsuarioDTO> registrarUsuario(Usuario usuario) {
        return daoUsuarios.registrarUsuario(usuario);
    }

    public Either<String, UsuarioDTO> registrarAdmin(Usuario usuario) {
        return daoUsuarios.registrarAdministrador(usuario);
    }

    public Either<String, UserLogin> getUserActivo(String correo) {
        return daoUsuarios.getUserActivo(correo);
    }

    public Either<String, List<UsuarioDTOlist>> getAllUsuarios() {
        return daoUsuarios.getAllUsuarios();
    }
    public Either<String, String> deleteUsuario(String username) {
        return daoUsuarios.deleteUsuario(username);
    }
}
