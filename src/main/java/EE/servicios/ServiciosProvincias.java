package EE.servicios;

import dao.daos.DaoProvincia;
import dao.modelos.Provincia;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosProvincias {

    private final DaoProvincia daoProvincia;

    @Inject
    public ServiciosProvincias(DaoProvincia daoProvincia) {
        this.daoProvincia = daoProvincia;
    }

    public Either<String, List<Provincia>> filtrosProvincias() {
        return daoProvincia.filtrosProvincias();
    }

}