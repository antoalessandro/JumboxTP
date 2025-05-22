package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import BLL.Usuario;
import BLL.Cliente;
import BLL.EncargadoStock;

public class ControladorUsuario {
    private static Connection con = Conexion.getInstance().getConnection();

    public Usuario login(String nombre, String contrasenia) {
        Usuario usuario = null;
        try {
            PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM usuario WHERE nombre = ? AND password = ?"
            );
            stmt.setString(1, nombre);
            stmt.setString(2, contrasenia);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String tipo = rs.getString("tipo");

                if (tipo.equalsIgnoreCase("cliente")) {
                    usuario = new Cliente(id, nombre, email, tipo, contrasenia);
                } else if (tipo.equalsIgnoreCase("encargadostock")) {
                    usuario = new EncargadoStock(id, nombre, email, tipo, contrasenia);
                } else {
                    System.out.println("Tipo de usuario desconocido: " + tipo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public void agregarUsuario(Usuario usuario) {
        try {
            if (existeUsuario(usuario.getNombre())) {
                System.out.println("El usuario ya está registrado.");
                return;
            }

            PreparedStatement statement = con.prepareStatement(
                "INSERT INTO usuario (nombre, email, tipo, password) VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getTipo());
            statement.setString(4, usuario.getPassword());

            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario agregado correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Usuario> mostrarUsuarios() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String tipo = rs.getString("tipo");
                String password = rs.getString("password");

                switch (tipo.toLowerCase()) {
                    case "cliente":
                        usuarios.add(new Cliente(id, nombre, email, tipo, password));
                        break;
                    case "encargadostock":
                        usuarios.add(new EncargadoStock(id, nombre, email, tipo, password));
                        break;
                    default:
                        System.out.println("Tipo desconocido: " + tipo);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // ✅ Nuevo método para verificar si un usuario ya existe antes de registrarlo
    public boolean existeUsuario(String nombre) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM usuario WHERE nombre = ?");
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}