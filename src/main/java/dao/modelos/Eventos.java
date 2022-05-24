package dao.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eventos {

    private String id;
    private String titulo;
    private String descripcion;
    private String img;
    private String tipo;
    private String provincia;
    private String pag_oficial_evento;
    private LocalDate fecha;

}
