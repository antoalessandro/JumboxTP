package GUI;

import javax.swing.JOptionPane;
import DLL.ControladorUsuario;
import BLL.Usuario;
import BLL.Cliente;
import BLL.EncargadoStock;
import BLL.GestorProductos;

public class Main {
    public static void main(String[] args) {
        // Inicializar productos antes de mostrar el menú
        GestorProductos.inicializarProductos();

        ControladorUsuario controlador = new ControladorUsuario();
        ManejadorInterfazUsuario interfaz = new ManejadorInterfazUsuario(controlador);

        String[] acciones = { "Iniciar Sesión", "Registrar Usuario", "Salir" };
        int menu;

        do {
            menu = JOptionPane.showOptionDialog(null, "Bienvenido", null, 0, 0, null, acciones, acciones[0]);

            switch (menu) {
                case 0:
                    Usuario usuario = interfaz.iniciarSesion();
                    if (usuario instanceof Cliente) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Cliente " + usuario.getNombre());
                        interfaz.manejarCarrito((Cliente) usuario);
                    } else if (usuario instanceof EncargadoStock) {
                        JOptionPane.showMessageDialog(null, "Bienvenido Encargado de Stock " + usuario.getNombre());
                        interfaz.gestionarStock((EncargadoStock) usuario);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                    break;

                case 1:
                    interfaz.registrarUsuario();
                    break;
            }
        } while (menu != 2);
    }
}