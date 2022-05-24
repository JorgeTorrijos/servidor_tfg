package EE.servicios;

import dao.daos.DaoTipoCarrera;
import dao.modelos.TipoCarrera;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosTipoCarrera {

    private final DaoTipoCarrera daoTipoCarrera;

    @Inject
    public ServiciosTipoCarrera(DaoTipoCarrera daoTipoCarrera) {
        this.daoTipoCarrera = daoTipoCarrera;
    }

    public Either<String, List<TipoCarrera>> filtrosTipoCarrera() {
        return daoTipoCarrera.filtrosTipoCarrera();
    }

}
