package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosUsuarios;
import dao.modelos.Usuario;
import dao.modelos.UsuarioDTO;
import dao.modelos.UsuarioDTOlist;
import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import utils.CreateHash;

import java.time.LocalDateTime;
import java.util.List;

@PermitAll
@Log4j2
@Path(ConstantesREST.REGISTRO)
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

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @POST
    @Path(ConstantesREST.ADMINISTRADOR)
    public Response registrarAdministrador(Usuario usuario) {

        Response response = null;

        //HASEAR PASS
        String passHaseada = createHash.hashear(usuario.getPass());
        usuario.setPass(passHaseada);

        Either<String, UsuarioDTO> registrarUser = serviciosUsuarios.registrarAdmin(usuario);

        if (registrarUser.isRight()) {


            response = Response.ok(registrarUser.get()).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(registrarUser.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @DELETE
    public Response deleteUser(@QueryParam(ConstantesREST.USERNAME_) String username) {

        Response response = null;

        Either<String, String> deleteUser = serviciosUsuarios.deleteUsuario(username);

        if (deleteUser.isRight()) {

            response = Response.ok(new ApiError(deleteUser.get(), LocalDateTime.now())).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(deleteUser.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @GET
    public Response getAllUsuarios() {

        Response response = null;

        Either<String, List<UsuarioDTOlist>> getUsuarios = serviciosUsuarios.getAllUsuarios();

        if (getUsuarios.isRight()) {

            response = Response.ok(getUsuarios.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getUsuarios.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }


}
