package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosCarreras;
import dao.modelos.CarreraSimpleMostrar;
import dao.modelos.CarreraToInsert;
import dao.modelos.Carreras;
import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@RolesAllowed(ConstantesREST.USER_ROL)
@Path(ConstantesREST.CARRERAS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCarreras {

    private final ServiciosCarreras serviciosCarreras;

    @Inject
    public RestCarreras(ServiciosCarreras serviciosCarreras) {
        this.serviciosCarreras = serviciosCarreras;
    }

    @GET
    public Response getAllCarrerasByIdEvento(@QueryParam(ConstantesREST.ID_EVENTO) int id_evento) {

        Response response = null;

        Either<String, List<Carreras>> getAllCarreras = serviciosCarreras.getAllCarrerasByIdEvento(id_evento);

        if (getAllCarreras.isRight()) {

            response = Response.ok(getAllCarreras.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getAllCarreras.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET()
    @Path(ConstantesREST.ID)
    public Response getEventoById(@QueryParam(ConstantesREST.ID_CARRERA) int id_carrera) {

        Response response = null;

        Either<String, List<Carreras>> getCarreras = serviciosCarreras.getCarrerasByID(id_carrera);

        if (getCarreras.isRight()) {

            //no pasamos lista
            response = Response.ok(getCarreras.get().get(0)).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getCarreras.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET()
    @Path(ConstantesREST.USER)
    public Response getEventoById(@QueryParam(ConstantesREST.USER_) String user) {

        Response response = null;

        Either<String, List<Carreras>> getCarreras = serviciosCarreras.getCarrerasByUser(user);

        if (getCarreras.isRight()) {

            response = Response.ok(getCarreras.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getCarreras.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @POST
    public Response insertarCarrera(CarreraToInsert carreraToInsert) {

        Response response = null;

        Either<String, CarreraToInsert> carrera = serviciosCarreras.insertarCarrera(carreraToInsert);

        if (carrera.isRight()) {

            response = Response.ok(carrera.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(carrera.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET
    @Path(ConstantesREST.ALL)
    public Response getAllCarreras() {
        Response response = null;

        Either<String, List<CarreraSimpleMostrar>> getAllCarreras = serviciosCarreras.getAllCarreras();

        if (getAllCarreras.isRight()) {

            response = Response.ok(getAllCarreras.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getAllCarreras.getLeft(), LocalDateTime.now())).build();

        }

        return response;
    }

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @DELETE
    public Response deleteCarrera(@QueryParam(ConstantesREST.ID_) String id) {

        Response response = null;

        Either<String, String> deleteEvento = serviciosCarreras.deleteCarrera(id);

        if (deleteEvento.isRight()) {

            response = Response.ok(new ApiError(deleteEvento.get(), LocalDateTime.now())).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(deleteEvento.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

}
