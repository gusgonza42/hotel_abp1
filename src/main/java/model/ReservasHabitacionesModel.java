
package model;

import lombok.*;
import utils.State;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Getter
@Setter
public class ReservasHabitacionesModel {
    private int IdReserva;
    private int IdUsuario;
    private int IdHabitacion;
    private State estado;
    private String FechaEntrada;
    private String FechaSalida;
}
