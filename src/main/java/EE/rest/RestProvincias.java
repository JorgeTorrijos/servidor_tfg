package EE.rest;

import EE.api.ApiError;
import EE.servicios.ServiciosProvincias;
import dao.modelos.Carreras;
import dao.modelos.Provincia;
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

@Path("/provincias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestProvincias {

    private final ServiciosProvincias serviciosProvincias;

    @Inject
    public RestProvincias(ServiciosProvincias serviciosProvincias) {
        this.serviciosProvincias = serviciosProvincias;
    }

    @GET
    public Response filtrosProvincias() {

        Response response = null;

        Either<String, List<Provincia>> filtrosProvincias = serviciosProvincias.filtrosProvincias();

        if (filtrosProvincias.isRight()) {

            response = Response.ok(filtrosProvincias.get()).build();

        } else {

            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(filtrosProvincias.getLeft(), LocalDateTime.now())).build();

        }

        return response;

    }

}
