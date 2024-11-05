package model;

import lombok.*;
import utils.EstadoHabitacion;
import utils.TipoHabitacion;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class HabitacionesModel {
    private int id_habitacion;
    private TipoHabitacion tipo_habitacion;
    private byte[] imagen_habitacion;
    private double precio_habitacion;
    private EstadoHabitacion estado_habitacion;
}
