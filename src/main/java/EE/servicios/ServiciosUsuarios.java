package EE.servicios;

import dao.daos.DaoUsuarios;
import dao.modelos.UserLogin;
import dao.modelos.Usuario;
import dao.modelos.UsuarioDTO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

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

    public Either<String, UserLogin> getUserActivo(String correo) {
        return daoUsuarios.getUserActivo(correo);

    }

}
