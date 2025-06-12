package BLL;

import DLL.ControladorProducto;
import java.sql.SQLException;
import java.util.List;

public class Cliente extends Usuario {
    private ControladorProducto controlador;

    public Cliente(int id, String nombre, String email, String tipo, String password, ControladorProducto controlador) {
        super(id, nombre, email, tipo, password);
        this.controlador = controlador;
    }
    
    public Cliente(int id, String nombre, String email, String tipo, String password, ControladorProducto controlador, boolean yaEncriptada) {
        super(id, nombre, email, tipo, password, yaEncriptada);
        this.controlador = controlador;
    }

    public ControladorProducto getControladorProducto() {
        return controlador;
    }

    public List<Producto> obtenerProductos() throws SQLException {
        return controlador.obtenerTodosLosProductos();
    }

    public boolean comprarProducto(String nombre, int cantidad) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty() || cantidad <= 0) {
            return false;
        }

        Producto producto = controlador.obtenerProductoPorNombre(nombre.trim());
        if (producto == null) {
            return false;
        }

        if (producto.getCantidadStock() < cantidad) {
            return false;
        }

        int nuevoStock = producto.getCantidadStock() - cantidad;
        return controlador.actualizarStock(nombre.trim(), nuevoStock);
    }
}
