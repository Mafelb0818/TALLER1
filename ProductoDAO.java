import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos(codigo, nombre, descripcion, precio, cantidad) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getCodigo());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getDescripcion());
            pstmt.setDouble(4, p.getPrecio());
            pstmt.setInt(5, p.getCantidad());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar producto (¿El código ya existe?): " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar productos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(String codigo, String descripcion, double precio, int cantidad) {
        String sql = "UPDATE productos SET descripcion = ?, precio = ?, cantidad = ? WHERE codigo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, descripcion);
            pstmt.setDouble(2, precio);
            pstmt.setInt(3, cantidad);
            pstmt.setString(4, codigo);
            int filas = pstmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM productos WHERE codigo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            int filas = pstmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}