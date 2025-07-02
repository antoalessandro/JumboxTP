package GUI;

import BLL.GestorProductos;
import BLL.Producto;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ManejadorInterfazGestorP {
    private GestorProductos gestor;

    public ManejadorInterfazGestorP(GestorProductos gestor) {
        this.gestor = gestor;
    }

    public void verProductos() {
        try {
            List<Producto> productos = gestor.obtenerProductos();
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay productos disponibles");
                return;
            }

            StringBuilder sb = new StringBuilder("Listado completo de productos:\n\n");
            for (Producto p : productos) {
                sb.append(String.format("Nombre: %s\nPrecio: $%.2f\nStock: %d\n\n",
                        p.getNombre(), p.getPrecio(), p.getCantidadStock()));
            }
            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage());
        }
    }

    public void agregarProducto() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre del nuevo producto:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido");
                return;
            }

            double precio = solicitarDouble("Ingrese precio:");
            int stock = solicitarEntero("Ingrese cantidad de stock:");

            boolean agregado = gestor.agregarProducto(nombre, precio, stock);
            if (agregado) {
                JOptionPane.showMessageDialog(null, "Producto agregado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el producto (puede que ya exista)");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar producto: " + e.getMessage());
        }
    }

    public void modificarProducto() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre del producto a modificar:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido");
                return;
            }

            double nuevoPrecio = solicitarDouble("Ingrese nuevo precio:");
            int nuevoStock = solicitarEntero("Ingrese nuevo stock:");

            boolean modificado = gestor.modificarProducto(nombre, nuevoPrecio, nuevoStock);
            if (modificado) {
                JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar producto (no existe)");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar producto: " + e.getMessage());
        }
    }

    public void eliminarProducto() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre del producto a eliminar:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido");
                return;
            }

            int confirmar = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de eliminar el producto " + nombre + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                boolean eliminado = gestor.eliminarProducto(nombre);
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No existe ese producto");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
        }
    }

    private double solicitarDouble(String mensaje) throws NumberFormatException {
        String input = JOptionPane.showInputDialog(mensaje);
        if (input == null) throw new NumberFormatException("Entrada cancelada");
        return Double.parseDouble(input.trim());
    }

    private int solicitarEntero(String mensaje) throws NumberFormatException {
        String input = JOptionPane.showInputDialog(mensaje);
        if (input == null) throw new NumberFormatException("Entrada cancelada");
        return Integer.parseInt(input.trim());
    }
}
