package EE.api;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String mensaje;
    private LocalDateTime fecha;

}
