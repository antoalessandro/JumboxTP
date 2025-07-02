package BLL;

import DLL.ControladorProducto;
import java.sql.SQLException;
import java.util.List;

public class GestorProductos extends Usuario {
    private ControladorProducto controlador;

    public GestorProductos(int id, String nombre, String email, String tipo, String password, ControladorProducto controlador) {
        super(id, nombre, email, tipo, password);
        this.controlador = controlador;
    }
    public GestorProductos(int id, String nombre, String email, String tipo, String password, ControladorProducto controlador, boolean yaEncriptada) {
        super(id, nombre, email, tipo, password, yaEncriptada);
        this.controlador = controlador;
    }
    public ControladorProducto getControladorProducto() {
        return controlador;
    }
    public List<Producto> obtenerProductos() throws SQLException {
        return controlador.obtenerTodosLosProductos();
    }

    public boolean agregarProducto(String nombre, double precio, int stock) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (controlador.existeProducto(nombre.trim())) return false;

        Producto producto = new Producto(nombre.trim(), precio, stock);
        return controlador.agregarProducto(producto);
    }

    public boolean modificarProducto(String nombre, double nuevoPrecio, int nuevoStock) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (!controlador.existeProducto(nombre.trim())) return false;

        Producto producto = new Producto(nombre.trim(), nuevoPrecio, nuevoStock);
        return controlador.actualizarProducto(producto);
    }
    public boolean eliminarProducto(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (!controlador.existeProducto(nombre.trim())) return false;

        return controlador.eliminarProducto(nombre.trim());
    }
}
