package EE.servicios;

import dao.daos.DaoCarreras;
import dao.modelos.CarreraSimpleMostrar;
import dao.modelos.CarreraToInsert;
import dao.modelos.Carreras;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiciosCarreras {

    private final DaoCarreras daoCarreras;

    @Inject
    public ServiciosCarreras(DaoCarreras daoCarreras) {
        this.daoCarreras = daoCarreras;
    }

    public Either<String, List<Carreras>> getAllCarrerasByIdEvento(int id) {
        return daoCarreras.getAllCarrerasByIdEvento(id);
    }

    public Either<String, List<Carreras>> getCarrerasByID(int id) {
        return daoCarreras.getCarrerasByID(id);
    }

    public Either<String, List<Carreras>> getCarrerasByUser(String user) {
        return daoCarreras.getCarrerasByUser(user);
    }

    public Either<String, CarreraToInsert> insertarCarrera(CarreraToInsert carreraToInsert) {
        return daoCarreras.insertarCarrera(carreraToInsert);
    }

    public Either<String, List<CarreraSimpleMostrar>> getAllCarreras() {
        return daoCarreras.getAllCarreras();

    }

    public Either<String, String> deleteCarrera(String id) {
        return daoCarreras.deleteCarrera(id);
    }

    /*

    public Either<String, List<Carreras>> getAllCarreras() {
        return daoCarreras.getAllCarreras();
    }

    public Either<String, List<Carreras>> getUltimasCarrerasAdded() {
        return daoCarreras.getUltimasCarrerasAdded();
    }

    public Either<String, List<Carreras>> filtradasPorProvincia(String provincia) {
        return daoCarreras.filtradasPorProvincia(provincia);
    }

    public Either<String, List<Carreras>> filtradasPorTipo(String tipo) {
        return daoCarreras.filtradasPorTipo(tipo);
    }

     */

}
