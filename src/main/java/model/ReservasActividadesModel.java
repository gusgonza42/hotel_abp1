package model;

import lombok.*;
import utils.State;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Getter
@Setter
public class ReservasActividadesModel {
    private int IdReservasActividad;
    private int IdUsuario;
    private int IdActividad;
    private State estado;
}
