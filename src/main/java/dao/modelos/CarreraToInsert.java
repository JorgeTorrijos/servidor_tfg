package dao.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraToInsert {

    private String id;
    private String titulo;
    private String descripcion;
    private String img;
    private double km;
    private String ciudad;
    private double precio;
    private String enlace_compra;
    private int id_evento;

}
