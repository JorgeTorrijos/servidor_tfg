package dao.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraFavoritaInsert {

    private int id;
    private String usuario;
    private int id_carrera;

}
