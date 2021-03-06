package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosEventos;
import dao.modelos.Eventos;
import dao.modelos.EventosToInsert;
import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@RolesAllowed(ConstantesREST.USER_ROL)
@Path(ConstantesREST.EVENTOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEventos {

    private final ServiciosEventos serviciosEventos;

    @Inject
    public RestEventos(ServiciosEventos serviciosEventos) {
        this.serviciosEventos = serviciosEventos;
    }

    @GET
    public Response getAllEventos(@QueryParam(ConstantesREST.PROVINCIA) String provincia, @QueryParam(ConstantesREST.TIPO) String tipo) {

        Response response = null;

        Either<String, List<Eventos>> getAllEventos = serviciosEventos.getAllEventos(provincia, tipo);

        if (getAllEventos.isRight()) {

            if(getAllEventos.get().isEmpty()){

                response = Response.status(Response.Status.NO_CONTENT).entity(new ApiError(ConstantesREST.NO_HAY_EVENTOS_DISPONIBLES, LocalDateTime.now())).build();

            } else {

                response = Response.ok(getAllEventos.get()).build();

            }


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(getAllEventos.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @GET()
    @Path(ConstantesREST.ID_PATH)
    public Response getEventoById(@QueryParam(ConstantesREST.ID_EVENTO_) int id_evento) {

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

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @POST
    public Response insertPartido(EventosToInsert eventos) {

        Response response = null;

        Either<String, EventosToInsert> insertPartido = serviciosEventos.insertarEvento(eventos);

        if (insertPartido.isRight()) {

            response = Response.ok(insertPartido.get()).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(insertPartido.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

    @RolesAllowed(ConstantesREST.ADMIN_ROL)
    @DELETE
    public Response deleteEvento(@QueryParam(ConstantesREST.ID_EVENTO_) String id_evento) {

        Response response = null;

        Either<String, String> deleteEvento = serviciosEventos.deleteEvento(id_evento);

        if (deleteEvento.isRight()) {

            response = Response.ok(new ApiError(deleteEvento.get(), LocalDateTime.now())).build();


        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(deleteEvento.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

}
