package GUI;

import BLL.Cliente;
import BLL.Producto;
import BLL.MetodoPago;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class ManejadorInterfazCliente {
    private Cliente cliente;

    public ManejadorInterfazCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void mostrarProductos() {
        try {
            List<Producto> productos = cliente.obtenerProductos();
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay productos disponibles en el sistema");
                return;
            }
            StringBuilder sb = new StringBuilder("Productos disponibles:\n\n");
            for (Producto p : productos) {
                sb.append(String.format("Nombre: %s\nPrecio: $%.2f\nStock: %d\n\n",
                        p.getNombre(), p.getPrecio(), p.getCantidadStock()));
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage());
        }
    }

    public void comprarProducto() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto a comprar:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido");
                return;
            }

            String cantidadStr = JOptionPane.showInputDialog("Ingrese la cantidad a comprar:");
            if (cantidadStr == null) return;

            int cantidad = Integer.parseInt(cantidadStr);

            Producto producto = cliente.getControladorProducto().obtenerProductoPorNombre(nombre.trim());
            if (producto == null) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precioTotal = producto.getPrecio() * cantidad;

            MetodoPago metodoPago = seleccionarMetodoPago();
            if (metodoPago == null) {
                JOptionPane.showMessageDialog(null, "Compra cancelada - No se seleccionó método de pago");
                return;
            }

            String mensajeConfirmacion = String.format(
                "¿Confirma la compra?\n\n" +
                "Producto: %s\n" +
                "Cantidad: %d unidad/es\n" +
                "Total a pagar: $%.2f\n" +
                "Método de pago: %s",
                nombre.trim(), cantidad, precioTotal,
                obtenerNombreMetodoPago(metodoPago)
            );

            int confirmar = JOptionPane.showConfirmDialog(null,
                    mensajeConfirmacion,
                    "Confirmar Compra",
                    JOptionPane.YES_NO_OPTION);

            if (confirmar != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Compra cancelada");
                return;
            }

            boolean exito = cliente.comprarProducto(nombre.trim(), cantidad);

            if (exito) {
                mostrarTicketCompra(nombre.trim(), cantidad, precioTotal, metodoPago);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Error en la compra:\n" +
                    "• Producto no encontrado\n" +
                    "• Stock insuficiente\n" +
                    "• Cantidad inválida\n\n" +
                    "Verifique los datos e intente nuevamente",
                    "Error en Compra",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida. Debe ser un número entero",
                    "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private MetodoPago seleccionarMetodoPago() {
        String[] opciones = {"Efectivo", "Tarjeta", "Transferencia"};

        int seleccion = JOptionPane.showOptionDialog(
            null,
            "Seleccione el método de pago:",
            "Método de Pago",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        switch (seleccion) {
            case 0: return MetodoPago.EFECTIVO;
            case 1: return MetodoPago.TARJETA;
            case 2: return MetodoPago.TRANSFERENCIA;
            default: return null;
        }
    }

    private String obtenerNombreMetodoPago(MetodoPago metodo) {
        switch (metodo) {
            case EFECTIVO: return "Efectivo";
            case TARJETA: return "Tarjeta";
            case TRANSFERENCIA: return "Transferencia";
            default: return "No especificado";
        }
    }

    private void mostrarTicketCompra(String nombreProducto, int cantidad,
                                   double total, MetodoPago metodoPago) {
        String ticket = String.format(
            "TICKET DE COMPRA\n\\n" +
            "Producto: %s\n" +
            "Cantidad: %d unidad/es\n\\n" +
            "TOTAL: $%.2f\n" +
            "Método de pago: %s\n" +
            "Fecha: %s\n\n" +
            "¡Gracias por su compra!",
            nombreProducto, cantidad, total,
            obtenerNombreMetodoPago(metodoPago),
            java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            )
        );

        JOptionPane.showMessageDialog(null, ticket, "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
}
