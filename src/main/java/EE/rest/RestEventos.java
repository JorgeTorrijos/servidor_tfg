package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosEventos;
import dao.modelos.Eventos;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/eventos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEventos {

    private final ServiciosEventos serviciosEventos;

    @Inject
    public RestEventos(ServiciosEventos serviciosEventos) {
        this.serviciosEventos = serviciosEventos;
    }

    @GET
    public Response getAllEventos(@QueryParam("provincia") String provincia, @QueryParam("tipo") String tipo) {

        Response response = null;

        Either<String, List<Eventos>> getAllEventos = serviciosEventos.getAllEventos(provincia,tipo);

        if (getAllEventos.isRight()) {

            response = Response.ok(getAllEventos.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getAllEventos.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET()
    @Path("/id")
    public Response getEventoById(@QueryParam("id_evento") int id_evento) {

        Response response = null;

        Either<String, List<Eventos>> getAllEventos = serviciosEventos.getEventoById(id_evento);

        if (getAllEventos.isRight()) {

            //no pasamos lista
            response = Response.ok(getAllEventos.get().get(0)).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getAllEventos.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

}
