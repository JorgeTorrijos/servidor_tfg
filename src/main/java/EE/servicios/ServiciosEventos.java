package EE.servicios;

import dao.daos.DaoEventos;
import dao.modelos.Eventos;
import dao.modelos.EventosToInsert;
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

    public Either<String, EventosToInsert> insertarEvento(EventosToInsert eventos) {
        return daoEventos.insertarEvento(eventos);
    }

    public Either<String, String> deleteEvento(String id){
        return daoEventos.deleteEvento(id);
    }

}
