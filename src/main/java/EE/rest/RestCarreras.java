package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosCarreras;
import dao.modelos.CarreraSimpleMostrar;
import dao.modelos.CarreraToInsert;
import dao.modelos.Carreras;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/carreras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCarreras {

    private final ServiciosCarreras serviciosCarreras;

    @Inject
    public RestCarreras(ServiciosCarreras serviciosCarreras) {
        this.serviciosCarreras = serviciosCarreras;
    }

    @GET
    public Response getAllCarrerasByIdEvento(@QueryParam("id_evento") int id_evento) {

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
    @Path("/id")
    public Response getEventoById(@QueryParam("id_carrera") int id_carrera) {

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
    @Path("/user")
    public Response getEventoById(@QueryParam("user") String user) {

        Response response = null;

        Either<String, List<Carreras>> getCarreras = serviciosCarreras.getCarrerasByUser(user);

        if (getCarreras.isRight()) {

            response = Response.ok(getCarreras.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getCarreras.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

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
    @Path("/all")
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


    /*

    @GET
    public Response getAllCarreras() {

        Response response = null;

        Either<String, List<Carreras>> getAllCarreras = serviciosCarreras.getAllCarreras();

        if (getAllCarreras.isRight()) {

            response = Response.ok(getAllCarreras.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getAllCarreras.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET
    @Path("/ultimas")
    public Response getUltimasCarrerasAdded() {

        Response response = null;

        Either<String, List<Carreras>> getAllCarreras = serviciosCarreras.getUltimasCarrerasAdded();

        if (getAllCarreras.isRight()) {

            response = Response.ok(getAllCarreras.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getAllCarreras.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET
    @Path("/filtro-provincia")
    public Response filtradasPorProvincia(@QueryParam("provincia") String provincia) {

        Response response = null;

        Either<String, List<Carreras>> filtradasPorProvincia = serviciosCarreras.filtradasPorProvincia(provincia);

        if (filtradasPorProvincia.isRight()) {

            response = Response.ok(filtradasPorProvincia.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(filtradasPorProvincia.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET
    @Path("/filtro-tipo")
    public Response filtradasPorTipo(@QueryParam("tipo") String tipo) {

        Response response = null;

        Either<String, List<Carreras>> filtradasPorTipo = serviciosCarreras.filtradasPorTipo(tipo);

        if (filtradasPorTipo.isRight()) {

            response = Response.ok(filtradasPorTipo.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(filtradasPorTipo.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

     */

}
