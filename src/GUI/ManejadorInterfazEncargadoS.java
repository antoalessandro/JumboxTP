package GUI;

import BLL.EncargadoStock;
import BLL.Producto;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ManejadorInterfazEncargadoS{
    private EncargadoStock encargado;

    public ManejadorInterfazEncargadoS(EncargadoStock encargado) {
        this.encargado = encargado;
    }

    public void mostrarProductos() {
        try {
            List<Producto> productos = encargado.obtenerProductos();

            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay productos disponibles");
                return;
            }

            StringBuilder sb = new StringBuilder("Productos disponibles:\n");
            for (Producto p : productos) {
                sb.append(String.format("Nombre: %s\nPrecio: %.2f\nStock: %d\n\n",
                        p.getNombre(), p.getPrecio(), p.getCantidadStock()));
            }

            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage());
        }
    }

    public void actualizarStock() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto a actualizar stock:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido");
                return;
            }

            String stockStr = JOptionPane.showInputDialog("Ingrese el nuevo stock:");
            if (stockStr == null) return;

            int stock = Integer.parseInt(stockStr);
            boolean actualizado = encargado.actualizarStockProducto(nombre, stock);

            if (actualizado) {
                JOptionPane.showMessageDialog(null, "Stock actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el stock");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Stock inválido: debe ser un número entero.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + e.getMessage());
        }
    }
}
