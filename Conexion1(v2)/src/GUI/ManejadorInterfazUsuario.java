package GUI;

import javax.swing.JOptionPane;
import DLL.ControladorUsuario;
import BLL.Usuario;
import BLL.Cliente;
import BLL.EncargadoStock;
import BLL.Producto;
import BLL.MetodoPago;
import BLL.TicketCompra;
import BLL.GestorProductos; // Nueva importación para gestionar productos
import java.util.ArrayList;

public class ManejadorInterfazUsuario {
    private ControladorUsuario controlador;

    public ManejadorInterfazUsuario(ControladorUsuario controlador) {
        this.controlador = controlador;
    }

    public Usuario iniciarSesion() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese nombre");
            if (nombre == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. Volviendo al menú...");
                return null;
            }

            String contrasenia = JOptionPane.showInputDialog("Ingrese contraseña");
            if (contrasenia == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. Volviendo al menú...");
                return null;
            }

            Usuario usuario = controlador.login(nombre, contrasenia);
            if (usuario != null) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
                return usuario; // Ahora retornamos el usuario correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado. Volviendo al menú...");
            e.printStackTrace();
            return null;
        }
    }

    public void registrarUsuario() {
        try {
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese nombre de usuario");
            if (nuevoNombre == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. Volviendo al menú...");
                return;
            }

            String nuevoEmail = JOptionPane.showInputDialog("Ingrese correo electrónico (debe contener @jumbox.super.ar)");
            if (nuevoEmail == null || !nuevoEmail.contains("@jumbox.super.ar")) {
                JOptionPane.showMessageDialog(null, "El correo debe contener '@jumbox.super.ar'. Volviendo al menú...");
                return;
            }

            String nuevaContrasenia = JOptionPane.showInputDialog("Ingrese contraseña");
            if (nuevaContrasenia == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. Volviendo al menú...");
                return;
            }

            // Verificamos si el usuario ya existe antes de registrar
            if (controlador.existeUsuario(nuevoNombre)) {
                JOptionPane.showMessageDialog(null, "El usuario ya está registrado. Intente con otro nombre.");
                return;
            }

            Cliente nuevoCliente = new Cliente(0, nuevoNombre, nuevoEmail, "Cliente", nuevaContrasenia);
            controlador.agregarUsuario(nuevoCliente);
            JOptionPane.showMessageDialog(null, "Registro exitoso. Bienvenido " + nuevoNombre + "! Nos alegra que estés aquí.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado. Volviendo al menú...");
            e.printStackTrace();
        }
    }

    public void manejarCarrito(Cliente cliente) {
        String[] opciones = { "Agregar Producto", "Eliminar Producto", "Finalizar Compra", "Salir" };
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(null, "Carrito de Compras", null, 0, 0, null, opciones, opciones[0]);

            switch (opcion) {
                case 0: // Agregar producto
                    ArrayList<Producto> productosDisponibles = GestorProductos.getListaProductos();
                    if (productosDisponibles.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay productos disponibles.");
                        break;
                    }

                    String[] nombresProductos = new String[productosDisponibles.size()];
                    for (int i = 0; i < productosDisponibles.size(); i++) {
                        nombresProductos[i] = productosDisponibles.get(i).getNombre() + 
                                              " - $" + productosDisponibles.get(i).getPrecio() +
                                              " - Stock: " + productosDisponibles.get(i).getCantidadStock();
                    }

                    String seleccion = (String) JOptionPane.showInputDialog(
                            null, "Seleccione un producto:", "Productos",
                            JOptionPane.QUESTION_MESSAGE, null, nombresProductos, nombresProductos[0]);

                    if (seleccion == null) {
                        JOptionPane.showMessageDialog(null, "Operación cancelada. Volviendo al carrito...");
                        break;
                    }

                    String nombreProductoSeleccionado = seleccion.split(" - ")[0]; 
                    Producto productoSeleccionado = GestorProductos.buscarProducto(nombreProductoSeleccionado);

                    if (productoSeleccionado != null) {
                        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad a comprar:"));
                        if (cliente.getCarrito().agregarProducto(productoSeleccionado, cantidad)) {
                            JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Stock insuficiente.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    }
                    break;

                case 1: // Eliminar producto
                    ArrayList<Producto> productosCarrito = cliente.getCarrito().getProductos();
                    if (productosCarrito.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El carrito está vacío.");
                        break;
                    }

                    String[] nombresEnCarrito = new String[productosCarrito.size()];
                    for (int i = 0; i < productosCarrito.size(); i++) {
                        nombresEnCarrito[i] = productosCarrito.get(i).getNombre() + 
                                              " - $" + productosCarrito.get(i).getPrecio() + 
                                              " - Cantidad: " + productosCarrito.get(i).getCantidadStock();
                    }

                    String seleccionEliminar = (String) JOptionPane.showInputDialog(
                            null, "Seleccione un producto a eliminar:", "Carrito",
                            JOptionPane.QUESTION_MESSAGE, null, nombresEnCarrito, nombresEnCarrito[0]);

                    if (seleccionEliminar == null) {
                        JOptionPane.showMessageDialog(null, "Operación cancelada. Volviendo al carrito...");
                        break;
                    }

                    String nombreEliminar = seleccionEliminar.split(" - ")[0]; 
                    Producto eliminar = null;

                    for (Producto p : productosCarrito) {
                        if (p.getNombre().equalsIgnoreCase(nombreEliminar)) {
                            eliminar = p;
                            break;
                        }
                    }

                    if (eliminar != null) {
                        int cantidadEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad a eliminar:"));

                        if (cantidadEliminar <= eliminar.getCantidadStock()) {
                            eliminar.reducirStock(cantidadEliminar);
                            JOptionPane.showMessageDialog(null, "Se eliminaron " + cantidadEliminar + " unidades de " + eliminar.getNombre());
                        } else {
                            JOptionPane.showMessageDialog(null, "No puedes eliminar más de lo que tienes en el carrito.");
                        }

                        if (eliminar.getCantidadStock() == 0) {
                            cliente.getCarrito().eliminarProducto(eliminar);
                            JOptionPane.showMessageDialog(null, "Producto eliminado completamente del carrito.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado en el carrito.");
                    }
                    break;

                case 2: // Finalizar compra
                    String[] metodosPago = { "EFECTIVO", "TARJETA", "TRANSFERENCIA" };
                    int seleccionPago = JOptionPane.showOptionDialog(
                            null, "Seleccione su método de pago:", "Método de Pago",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, metodosPago, metodosPago[0]);

                    if (seleccionPago == -1) { // Si el usuario cancela
                        JOptionPane.showMessageDialog(null, "Operación cancelada. Volviendo al carrito...");
                        break;
                    }

                    MetodoPago pago = MetodoPago.valueOf(metodosPago[seleccionPago]); // Convertimos la selección a `MetodoPago`
                    TicketCompra ticket = cliente.pagarCompra(pago);
                    JOptionPane.showMessageDialog(null, ticket);
                    JOptionPane.showMessageDialog(null, "¡Gracias por comprar con nosotros, " + cliente.getNombre() + "! 🎉 ¡Esperamos verte pronto!");
                    break;
            }
        } while (opcion != 3);
    }
    

    public void gestionarStock(EncargadoStock encargado) {
        String[] opciones = { "Agregar Producto", "Eliminar Producto", "Mostrar Stock", "Salir" };
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(null, "Gestión de Stock", null, 0, 0, null, opciones, opciones[0]);

            switch (opcion) {
                case 0:
                    String nombreProducto = JOptionPane.showInputDialog("Nombre del producto:");
                    if (nombreProducto == null) return;

                    double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));

                    Producto nuevoProducto = new Producto(nombreProducto, precio, cantidad);
                    GestorProductos.agregarProducto(nuevoProducto);
                    JOptionPane.showMessageDialog(null, "Producto agregado al inventario.");
                    break;

                case 1:
                    String eliminarProducto = JOptionPane.showInputDialog("Ingrese nombre del producto a eliminar:");
                    if (eliminarProducto == null) return;

                    if (GestorProductos.eliminarProducto(eliminarProducto)) {
                        JOptionPane.showMessageDialog(null, "Producto eliminado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    }
                    break;

                case 2:
                    GestorProductos.mostrarProductos(); // ✅ Ahora se mostrará el stock correctamente
                    break;
            }
        } while (opcion != 3);
    }
}