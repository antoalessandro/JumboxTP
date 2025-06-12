package BLL;

import DLL.ControladorProducto;
import java.sql.SQLException;
import java.util.List;

public class EncargadoStock extends Usuario {
    private ControladorProducto controlador;

    public EncargadoStock(int id, String nombre, String email, String tipo, String password, ControladorProducto controlador) {
        super(id, nombre, email, tipo, password);
        this.controlador = controlador;
    }
    public EncargadoStock(int id, String nombre, String email, String tipo, String password, ControladorProducto controlador, boolean yaEncriptada) {
        super(id, nombre, email, tipo, password, yaEncriptada);
        this.controlador = controlador;
    }

    public ControladorProducto getControladorProducto() {
        return controlador;
    }

    public List<Producto> obtenerProductos() throws SQLException {
        return controlador.obtenerTodosLosProductos();
    }

    public boolean existeProducto(String nombre) throws SQLException {
        return controlador.existeProducto(nombre);
    }

    public boolean actualizarStockProducto(String nombre, int nuevoStock) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty() || nuevoStock < 0) {
            throw new IllegalArgumentException("Datos inválidos para actualizar stock");
        }

        if (!controlador.existeProducto(nombre.trim())) {
            throw new IllegalArgumentException("El producto no existe");
        }

        return controlador.actualizarStock(nombre.trim(), nuevoStock);
    }
}

