package dao.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventosToInsert {

    private String id;
    private String titulo;
    private String descripcion;
    private String img;
    private int tipo;
    private int provincia;
    private String pag_oficial_evento;
    private LocalDate fecha;

}
