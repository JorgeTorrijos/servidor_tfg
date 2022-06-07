package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosUsuarios;
import dao.modelos.UserLogin;
import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import utils.CreateHash;

import java.time.LocalDateTime;
import java.util.Base64;

@Path(ConstantesREST.LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {

    private final ServiciosUsuarios serviciosUsuarios;
    private final CreateHash createHash;
    private final HttpServletRequest httpServletRequest;


    @Inject
    public RestLogin(ServiciosUsuarios serviciosUsuarios, CreateHash createHash, HttpServletRequest httpServletRequest) {
        this.serviciosUsuarios = serviciosUsuarios;
        this.createHash = createHash;
        this.httpServletRequest = httpServletRequest;
    }

    @GET
    @PermitAll
    public Response doLogin() {

        Response response = null;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        String[] valores = header.split(ConstantesREST.ESPACIO);
        String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
        String[] userPassSeparado = userPass.split(ConstantesREST.DOS_PUNTOS);

        String user = userPassSeparado[0];
        String pass = userPassSeparado[1];

        Either<String, UserLogin> getUserActivo = serviciosUsuarios.getUserActivo(user);

        if (getUserActivo.isRight()) {

            UserLogin userLogin = getUserActivo.get();

            UserLogin userLogin1 = new UserLogin();
            userLogin1.setUsername(userLogin.getUsername());
            userLogin1.setTipo_user(userLogin.getTipo_user());

            //COMPROBAMOS CONTRASEÑA
            if (createHash.verify(pass, userLogin.getPass())) {

                if (getUserActivo.isRight()) {

                    response = Response.ok(userLogin1).build();

                } else {

                    response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getUserActivo.getLeft(), LocalDateTime.now())).build();

                }

            } else {

                response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(ConstantesREST.USUARIO_O_CONTRASEÑA_INCORRECTOS, LocalDateTime.now())).build();

            }

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getUserActivo.getLeft(), LocalDateTime.now())).build();


        }

        return response;

    }

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @GET
    @Path("/administradores")
    public Response doLoginAdministradores() {
        Response response = null;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        String[] valores = header.split(ConstantesREST.ESPACIO);
        String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
        String[] userPassSeparado = userPass.split(ConstantesREST.DOS_PUNTOS);

        String user = userPassSeparado[0];
        String pass = userPassSeparado[1];

        Either<String, UserLogin> getUserActivo = serviciosUsuarios.getUserActivo(user);

        if (getUserActivo.isRight()) {

            UserLogin userLogin = getUserActivo.get();

            UserLogin userLogin1 = new UserLogin();
            userLogin1.setUsername(userLogin.getUsername());
            userLogin1.setTipo_user(userLogin.getTipo_user());

            //COMPROBAMOS CONTRASEÑA
            if (createHash.verify(pass, userLogin.getPass()) && userLogin.getTipo_user() == 1) {

                if (getUserActivo.isRight()) {

                    response = Response.ok(userLogin1).build();

                } else {

                    response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getUserActivo.getLeft(), LocalDateTime.now())).build();

                }

            } else {

                response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(ConstantesREST.USUARIO_O_CONTRASEÑA_INCORRECTOS, LocalDateTime.now())).build();

            }

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getUserActivo.getLeft(), LocalDateTime.now())).build();


        }

        return response;
    }

}
