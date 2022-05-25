package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosUsuarios;
import dao.modelos.Usuario;
import dao.modelos.UsuarioDTO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import utils.CreateHash;

import java.time.LocalDateTime;

@Log4j2
@Path("/registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    private final ServiciosUsuarios serviciosUsuarios;
    private final CreateHash createHash;

    @Inject
    public RestUsuarios(ServiciosUsuarios serviciosUsuarios, CreateHash createHash) {
        this.serviciosUsuarios = serviciosUsuarios;
        this.createHash = createHash;
    }

    @POST
    public Response registrarUsuario(Usuario usuario) {

        Response response = null;

        //HASEAR PASS
        String passHaseada = createHash.hashear(usuario.getPass());
        usuario.setPass(passHaseada);

        Either<String, UsuarioDTO> registrarUser = serviciosUsuarios.registrarUsuario(usuario);

        if (registrarUser.isRight()) {


            response = Response.ok(registrarUser.get()).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(registrarUser.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }


}
