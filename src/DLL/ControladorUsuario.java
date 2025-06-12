
package DLL;

import java.sql.*;
import BLL.Usuario;
import BLL.Cliente;
import BLL.EncargadoStock;
import BLL.GestorProductos;

public class ControladorUsuario {
    private static Connection con = Conexion.getInstance().getConnection();

    public Usuario login(String nombre, String email, String password) throws SQLException {
        Usuario.validarDatosLogin(nombre, email, password);

        String sql = "SELECT id, nombre, email, tipo, password FROM usuario WHERE nombre = ? AND email = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombre.trim());
            stmt.setString(2, email.trim().toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String passwordBD = rs.getString("password");
                    
                    boolean esTextoPlano = verificarSiEsTextoPlano(passwordBD, password);
                    
                    if (esTextoPlano) {
                        if (passwordBD.equals(password)) {
                            Usuario usuario = crearUsuarioDesdeTextoPlano(rs);
                            return usuario;
                        }
                    } else {
                        Usuario usuario = crearUsuarioSegunTipo(rs);
                        if (usuario.verificarPassword(password)) {
                            return usuario;
                        }
                    }
                }
            }
        }
        return null;
    }
    private boolean verificarSiEsTextoPlano(String passwordBD, String passwordIngresada) {
        if (passwordBD.equals(passwordIngresada)) {
            return true;
        }
        String passwordIngresadaEncriptada = new Usuario(-1, "temp", "temp@jumbox.super.ar", "Cliente", passwordIngresada) {}.encriptar(passwordIngresada);
        return !passwordBD.equals(passwordIngresadaEncriptada);
    }

    private Usuario crearUsuarioDesdeTextoPlano(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String email = rs.getString("email");
        String tipo = rs.getString("tipo");
        String password = rs.getString("password"); 
        return crearUsuarioSegunTipo(id, nombre, email, tipo, password, false);
    }
    public boolean registrarUsuario(String nombre, String email, String tipo, String password) throws SQLException {
        Usuario.validarDatosUsuario(nombre, email, tipo, password);

        if (existeUsuario(nombre) || existeEmail(email)) {
            return false;
        }

        Usuario nuevoUsuario = crearUsuarioSegunTipo(-1, nombre, email, tipo, password, false);
        return agregarUsuario(nuevoUsuario);
    }
    public boolean agregarUsuario(Usuario usuario) throws SQLException {
        if (usuario == null) return false;

        String sql = "INSERT INTO usuario (nombre, email, tipo, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTipo());
            stmt.setString(4, usuario.getPassword());

            return stmt.executeUpdate() > 0;
        }
    }
    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, email, tipo, password FROM usuario WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return crearUsuarioSegunTipo(rs);
            }
        }
        return null;
    }

    public boolean existeUsuario(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) return false;

        String sql = "SELECT COUNT(*) FROM usuario WHERE nombre = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombre.trim());
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean existeEmail(String email) throws SQLException {
        if (email == null || email.trim().isEmpty()) return false;

        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private Usuario crearUsuarioSegunTipo(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String email = rs.getString("email");
        String tipo = rs.getString("tipo");
        String password = rs.getString("password"); 
        return crearUsuarioSegunTipo(id, nombre, email, tipo, password, true);
    }

    private Usuario crearUsuarioSegunTipo(int id, String nombre, String email, String tipo, String password, boolean yaEncriptada) {
        ControladorProducto controladorProducto = new ControladorProducto();

        switch (tipo) {
            case "Cliente":
                return new Cliente(id, nombre, email, tipo, password, controladorProducto, yaEncriptada);
            case "EncargadoStock":
                return new EncargadoStock(id, nombre, email, tipo, password, controladorProducto, yaEncriptada);
            case "GestorProductos":
                return new GestorProductos(id, nombre, email, tipo, password, controladorProducto, yaEncriptada);
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido: " + tipo);
        }
    }
}