package dao.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carreras {

    private int id;
    private String titulo;
    private String descripcion;
    private String img;
    private String tipo;
    private double km;
    private String provincia;
    private String ciudad;
    private double precio;
    private String enlace_compra;
    private LocalDate fecha;
    private int id_evento;

}
