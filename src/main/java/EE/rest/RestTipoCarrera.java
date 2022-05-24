package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosTipoCarrera;
import dao.modelos.Provincia;
import dao.modelos.TipoCarrera;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/tipos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestTipoCarrera {

    private final ServiciosTipoCarrera serviciosTipoCarrera;

    @Inject
    public RestTipoCarrera(ServiciosTipoCarrera serviciosTipoCarrera) {
        this.serviciosTipoCarrera = serviciosTipoCarrera;
    }

    @GET
    public Response filtrosTipoCarrera() {

        Response response = null;

        Either<String, List<TipoCarrera>> filtrosTipoCarrera = serviciosTipoCarrera.filtrosTipoCarrera();

        if (filtrosTipoCarrera.isRight()) {

            response = Response.ok(filtrosTipoCarrera.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(filtrosTipoCarrera.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

}
