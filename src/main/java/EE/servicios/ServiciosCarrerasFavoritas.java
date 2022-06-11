package EE.servicios;

import dao.daos.DaoCarrerasFavoritas;
import dao.modelos.CarreraFavoritaInsert;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServiciosCarrerasFavoritas {

    private final DaoCarrerasFavoritas daoCarrerasFavoritas;

    @Inject
    public ServiciosCarrerasFavoritas(DaoCarrerasFavoritas daoCarrerasFavoritas) {
        this.daoCarrerasFavoritas = daoCarrerasFavoritas;
    }

    public Either<String, CarreraFavoritaInsert> insertCarrerasFavoritas(CarreraFavoritaInsert carreraToInsert) {
        return daoCarrerasFavoritas.insertCarrerasFavoritas(carreraToInsert);
    }

    public Either<String, String> deleteCarrera(CarreraFavoritaInsert carreraFavoritaInsert) {
        return daoCarrerasFavoritas.deleteCarrera(carreraFavoritaInsert);
    }
}
