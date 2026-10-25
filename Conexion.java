import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:sqlite:inventario.db";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            crearTablaSiNoExiste(conn);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }

    private static void crearTablaSiNoExiste(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS productos (\n"
                + "    codigo TEXT PRIMARY KEY,\n"
                + "    nombre TEXT NOT NULL,\n"
                + "    descripcion TEXT,\n"
                + "    precio REAL NOT NULL,\n"
                + "    cantidad INTEGER NOT NULL\n"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}