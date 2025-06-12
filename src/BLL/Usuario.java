package BLL;

import repository.Encriptador;

public abstract class Usuario implements Encriptador{
    private int id;
    private String nombre;
    private String email;
    private String tipo;
    private String password;
    
    public Usuario(int id, String nombre, String email, String tipo, String password) {
        this.id = id;
        this.nombre = validarNombre(nombre);
        this.email = validarEmail(email);
        this.tipo = validarTipo(tipo);
        this.password = encriptar(validarPassword(password));
        
    }
    public Usuario(int id, String nombre, String email, String tipo, String password, boolean yaEncriptada) {
        this.id = id;
        this.nombre = validarNombre(nombre);
        this.email = validarEmail(email);
        this.tipo = validarTipo(tipo);
        this.password = yaEncriptada ? password : encriptar(validarPassword(password));
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTipo() { return tipo; }
    public String getPassword() { return password; }
   
    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }

    public void setEmail(String email) {
        this.email = validarEmail(email);
    }

    public void setTipo(String tipo) {
        this.tipo = validarTipo(tipo);
    }

    public void setPassword(String password) {
        this.password = encriptar(validarPassword(password));
    }

    public static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        String limpio = nombre.trim();
        if (limpio.length() < 4) {
            throw new IllegalArgumentException("El nombre debe tener al menos 4 caracteres");
        }
        if (limpio.length() > 15) {
            throw new IllegalArgumentException("El nombre no puede superar los 15 caracteres");
        }
        return limpio;
    }

    public static String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        String limpio = email.trim().toLowerCase();
        if (!limpio.contains("@")) {
            throw new IllegalArgumentException("El email debe contener '@'");
        }
        if (!limpio.endsWith("@jumbox.super.ar")) {
            throw new IllegalArgumentException("El email debe terminar con @jumbox.super.ar");
        }
        
        if (limpio.length() > 50) {
            throw new IllegalArgumentException("El email no puede superar los 50 caracteres");
        }
        return limpio;
    }

    public static String validarTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de usuario no puede estar vacío");
        }
        String limpio = tipo.trim();
        switch (limpio) {
            case "Cliente":
            case "EncargadoStock":
            case "GestorProductos":
                return limpio;
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido. Debe ser: Cliente, Encargado Stock o Gestor Productos");
        }
    }

    public static String validarPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        if (password.length() > 20) {
            throw new IllegalArgumentException("La contraseña no puede superar los 20 caracteres");
        }
        return password;
    }

    public boolean verificarPassword(String passwordIngresada) {
        return password.equals(encriptar(passwordIngresada));
    }

    public boolean cambiarPassword(String passwordActual, String passwordNuevo) {
        if (!verificarPassword(passwordActual)) {
            return false;
        }
        this.password = encriptar(validarPassword(passwordNuevo));
        return true;
    }

    public static void validarDatosUsuario(String nombre, String email, String tipo, String password) {
        validarNombre(nombre);
        validarEmail(email);
        validarTipo(tipo);
        validarPassword(password);
    }

    public static void validarDatosLogin(String nombre, String email, String password) {
        validarNombre(nombre);
        validarEmail(email);
        validarPassword(password);
    }

    @Override
    public String toString() {
        return "ID: " + id + " - " + nombre + " - Email: " + email + " - Tipo: " + tipo;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
