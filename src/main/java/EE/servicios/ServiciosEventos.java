package EE.servicios;

import dao.daos.DaoEventos;
import dao.modelos.Eventos;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosEventos {

    private final DaoEventos daoEventos;

    @Inject
    public ServiciosEventos(DaoEventos daoEventos) {
        this.daoEventos = daoEventos;
    }

    public Either<String, List<Eventos>> getAllEventos(String provincia, String tipo) {
        return daoEventos.getAllEventos(provincia,tipo);
    }

    public Either<String, List<Eventos>> getEventoById(int id) {
        return daoEventos.getEventoById(id);
    }

}
