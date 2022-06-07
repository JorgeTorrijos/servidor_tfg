package dao.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCarrera {

    //TODO ANTES NO HABIA ID
    private int id;
    private String nombre;

}
