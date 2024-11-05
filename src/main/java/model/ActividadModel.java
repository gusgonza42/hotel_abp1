package model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter

public class ActividadModel {
    private int id_actividad;
    private String nombre_actividad;
    private String descripcion;
    private byte[] imagen;
    private double precio;
    private int cupo;
    private String fecha_actividad;
}
