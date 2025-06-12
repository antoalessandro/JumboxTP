package DLL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import BLL.Producto;

public class ControladorProducto {
    private static final Connection con = Conexion.getInstance().getConnection();

    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT nombre, precio, cantidad_stock FROM producto ORDER BY nombre";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto(
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad_stock")
                );
                productos.add(producto);
            }
        }
        return productos;
    }

    public Producto obtenerProductoPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        String sql = "SELECT nombre, precio, cantidad_stock FROM producto WHERE nombre = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombre.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad_stock")
                    );
                }
            }
        }
        return null;
    }
    public boolean agregarProducto(Producto producto) throws SQLException {
        if (producto == null) throw new IllegalArgumentException("Producto no puede ser null");

        String sql = "INSERT INTO producto (nombre, precio, cantidad_stock) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getCantidadStock());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean actualizarProducto(Producto producto) throws SQLException {
        if (producto == null) throw new IllegalArgumentException("Producto no puede ser null");

        String sql = "UPDATE producto SET precio = ?, cantidad_stock = ? WHERE nombre = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, producto.getPrecio());
            stmt.setInt(2, producto.getCantidadStock());
            stmt.setString(3, producto.getNombre());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean actualizarStock(String nombre, int nuevoStock) throws SQLException {
        String sql = "UPDATE producto SET cantidad_stock = ? WHERE nombre = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nuevoStock);
            stmt.setString(2, nombre.trim());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean eliminarProducto(String nombre) throws SQLException {
        Producto.validarDatosProducto(nombre, 0, 0);

        String sql = "DELETE FROM producto WHERE nombre = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombre.trim());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean existeProducto(String nombre) throws SQLException {
        return obtenerProductoPorNombre(nombre) != null;
    }
}
