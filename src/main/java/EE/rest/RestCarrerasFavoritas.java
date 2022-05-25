package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosCarrerasFavoritas;
import dao.modelos.CarreraFavoritaInsert;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

@Path("/carreras-favoritas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCarrerasFavoritas {

    private final ServiciosCarrerasFavoritas serviciosCarrerasFavoritas;

    @Inject
    public RestCarrerasFavoritas(ServiciosCarrerasFavoritas serviciosCarrerasFavoritas) {
        this.serviciosCarrerasFavoritas = serviciosCarrerasFavoritas;
    }

    @POST
    public Response insertarCarreraFavoritos(CarreraFavoritaInsert carreraFavoritaInsert) {
        Response response = null;

        Either<String, CarreraFavoritaInsert> insertPartido = serviciosCarrerasFavoritas.insertCarrerasFavoritas(carreraFavoritaInsert);

        if (insertPartido.isRight()) {

            response = Response.ok(insertPartido.get()).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(insertPartido.getLeft(), LocalDateTime.now())).build();

        }

        return response;
    }

    @DELETE
    public Response deleteCarrera(@QueryParam("id_carrera") int id_carrera ,@QueryParam("usuario") String usuario) {
        Response response = null;

        CarreraFavoritaInsert carreraFavoritaInsert = new CarreraFavoritaInsert();

        carreraFavoritaInsert.setId_carrera(id_carrera);
        carreraFavoritaInsert.setUsuario(usuario);

        Either<String, String> insertPartido = serviciosCarrerasFavoritas.deleteCarrera(carreraFavoritaInsert);

        if (insertPartido.isRight()) {

            response = Response.ok(new ApiError(insertPartido.get(),LocalDateTime.now())).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(insertPartido.getLeft(), LocalDateTime.now())).build();

        }

        return response;
    }

}
