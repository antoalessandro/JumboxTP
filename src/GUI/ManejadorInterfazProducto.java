package GUI;

import BLL.Cliente;
import BLL.EncargadoStock;
import BLL.GestorProductos;
import BLL.Usuario;

import javax.swing.*;
import java.sql.SQLException;

public class ManejadorInterfazProducto {
    private Usuario usuario;

    public ManejadorInterfazProducto(Usuario usuario) {
        this.usuario = usuario;
    }

    public void mostrarMenuSegunRol() throws SQLException {
        if (usuario instanceof Cliente) {
            menuCliente((Cliente) usuario);
        } else if (usuario instanceof EncargadoStock) {
            menuEncargadoStock((EncargadoStock) usuario);
        } else if (usuario instanceof GestorProductos) {
            menuGestorProductos((GestorProductos) usuario);
        } else {
            JOptionPane.showMessageDialog(null, "Rol no reconocido.");
        }
    }

    private void menuCliente(Cliente cliente) throws SQLException {
        String[] opciones = {"Ver productos", "Comprar producto", "Salir"};
        while (true) {
            String opcion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione opción:",
                    "Cliente",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (opcion == null || opcion.equals("Salir")) {
                break;
            }

            switch (opcion) {
                case "Ver productos":
                	ManejadorInterfazCliente manejador1 = new ManejadorInterfazCliente(cliente);
                	manejador1.mostrarProductos();
                    break;
                case "Comprar producto":
                	ManejadorInterfazCliente manejador2 = new ManejadorInterfazCliente(cliente);
                	manejador2.comprarProducto();
                    break;
               case "Salir":
               break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                    break;
            }
        }
    }

    private void menuEncargadoStock(EncargadoStock encargado) throws SQLException {
        String[] opciones = {"Ver productos", "Actualizar stock","Salir"};
        while (true) {
            String opcion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione opción:",
                    "Encargado de Stock",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (opcion == null || opcion.equals("Salir")) {
                break;
            }

            switch (opcion) {
                case "Ver productos":
                	ManejadorInterfazEncargadoS manejador4 = new ManejadorInterfazEncargadoS(encargado);
                    manejador4.mostrarProductos();
                    break;
                
                case "Actualizar stock":
                	ManejadorInterfazEncargadoS manejador5 = new ManejadorInterfazEncargadoS(encargado);
                    manejador5.actualizarStock();
                    break;
                case "Salir":
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }
        }
    }

    private void menuGestorProductos(GestorProductos gestor) throws SQLException {
        String[] opciones = {"Ver productos", "Agregar producto", "Modificar producto", "Eliminar producto", "Salir"};
        while (true) {
            String opcion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione opción:",
                    "Gestor de Productos",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (opcion == null || opcion.equals("Salir")) {
                break;
            }

            switch (opcion) {
                case "Ver productos":
                	ManejadorInterfazGestorP manejador6 = new ManejadorInterfazGestorP(gestor);
                	manejador6.verProductos();
                    break;
                case "Agregar producto":
                	ManejadorInterfazGestorP manejador7 = new ManejadorInterfazGestorP(gestor);
                	manejador7.agregarProducto();
                    break;
                case "Modificar producto":
                	ManejadorInterfazGestorP manejador8 = new ManejadorInterfazGestorP(gestor);
                   manejador8.modificarProducto();
                    break;
                case "Eliminar producto":
                	ManejadorInterfazGestorP manejador9 = new ManejadorInterfazGestorP(gestor);
                    manejador9.eliminarProducto();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                    break;
            }
        }
    }
}
