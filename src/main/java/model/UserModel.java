package model;

import lombok.*;
import utils.Estadouser;
import utils.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class UserModel {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private Role role;
    private Estadouser estadouser;
    private String date;

}
