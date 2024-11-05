package utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constantes {
    /**
     * Constantes para la conexión a la base de datos
     */
    public static final String SCHEMA_NAME = "hotel";
    public static final String DRIVER_ROUTE = "jdbc:mysql://localhost:3306/";
    public static final String JDBC_OPTIONS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=True";
    public static final String CONNECTION = DRIVER_ROUTE + SCHEMA_NAME + JDBC_OPTIONS;
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "jupiter*";
    //public static final String PASSWORD = "";
    public static final String FOR_NAME_CLASS = "com.mysql.cj.jdbc.Driver";

    /**
     * Constantes para los mensajes de error y éxito de usuario
     */
    public static final String ERROR_LOGIN_MESSAGE = "❌ Usuario y/o Contraseña incorrecta";
    public static final String USER_SUCCESSFULLY = "✅ Usuario y password Correcto";
    /**
     * Constantes para encriptar
     */
    public static final String ALGORITHM = "AES";
    public static final String KEY_FILE = "src/main/java/utils/aesKey.key";
    /**
     * Constantes para los mensajes de error de ID y Error Connexion de BBDD
     */
    public static final String CONNECTION_DDBB_INVALID = "❌ ERROR CONEXION CON BBDD";
    public static final String ERROR_ACTIVIDAD_ID = "❌ ID FUERA DE RANGO";

    /**
     * Constantes para los mensajes de error y éxito de actividades
     */
    public static final String INSERT_ACTIVITIES_SUCCESS = "✅ Actividad insertada correctamente";
    public static final String INSERT_ACTIVITIES_INVALID = "❌ INSERT DE ACTIVIDADES ERRÓNEAS (REVISA LA QUERY)";
    public static final String UPDATE_ACTIVITIES_INVALID = "❌ UPDATE DE ACTIVIDADES ERRÓNEAS (REVISA LA QUERY)";
    public static final String DELETE_ACTIVITIES_INVALID = "❌ DELETE DE ACTIVIDADES ERRÓNEAS (REVISA LA QUERY)";
    public static final String UPDATE_ACTIVITIES_CORRECT = "✅ Actividad actualizada correctamente";
    public static final String NOT_EXITS_IDS = "❌ El ID de la actividad no existe.";
    public static final String DELETE_ACTIVITY_SUCCESS = "✅ Actividad ELIMINADA correctamente";
    public static final String EMAIL_EXISTENTE = "❌ Email introducido ya existe en la base de datos";


    /**
     * DateTimeFormatter para imprimir la fecha y hora actual
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Método para imprimir mensajes por terminal con la fecha y hora actual y el nombre del método y clase
     *
     * @param message
     */
    public static void printMssg(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println("🌌 ¡Operación exitosa! 🌟  O 💥 ERROR EN LA NAVE 🚨   - \n[" + timestamp + " - " + className + "." + method + "]: \n \n" + message + "\n \n 🚀🛸  || 🚧🛠️" + "\n - - - - - - - END PRINT MESSAGE - - - - - - - \n");
    }
}