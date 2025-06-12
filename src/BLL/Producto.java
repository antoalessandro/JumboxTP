package BLL;

public class Producto {
    private String nombre;
    private double precio;
    private int cantidadStock;
    
    public Producto(String nombre, double precio, int cantidadStock) {
        this.nombre = validarNombre(nombre);
        this.precio = validarPrecio(precio);
        this.cantidadStock = validarStock(cantidadStock);
       
    }
    private String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre del producto debe tener al menos 2 caracteres");
        }
        if (nombre.trim().length() > 100) {
            throw new IllegalArgumentException("El nombre del producto no puede superar los 100 caracteres");
        }
        return nombre.trim();
    }
    private double validarPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (precio > 999999.99) {
            throw new IllegalArgumentException("El precio excede el límite máximo");
        }
        return Math.round(precio * 100.0) / 100.0;
    }

    private int validarStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (stock > 100000) {
            throw new IllegalArgumentException("El stock excede el límite máximo");
        }
        return stock;
    }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidadStock() { return cantidadStock; }
   
 
    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }

    public void setPrecio(double precio) {
        this.precio = validarPrecio(precio);
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = validarStock(cantidadStock);
    }
    public boolean tieneStockSuficiente(int cantidadRequerida) {
        return this.cantidadStock >= cantidadRequerida && cantidadRequerida > 0;
    }

    public void incrementarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser positiva");
        }
        this.cantidadStock = validarStock(this.cantidadStock + cantidad);
    }

    public boolean reducirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reducir debe ser positiva");
        }
        if (!tieneStockSuficiente(cantidad)) {
            return false;
        }
        this.cantidadStock -= cantidad;
        return true;
    }
    public static void validarDatosProducto(String nombre, double precio, int stock) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (nombre.trim().length() < 2 || nombre.trim().length() > 100) {
            throw new IllegalArgumentException("El nombre debe tener entre 2 y 100 caracteres");
        }
        if (precio < 0 || precio > 999999.99) {
            throw new IllegalArgumentException("Precio inválido");
        }
        if (stock < 0 || stock > 100000) {
            throw new IllegalArgumentException("Stock inválido");
        }
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " - Stock: " + cantidadStock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Producto) {
            return this.nombre.equals(((Producto) obj).nombre);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

}