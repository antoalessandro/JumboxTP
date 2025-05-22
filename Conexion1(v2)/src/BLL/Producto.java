package BLL;

public class Producto {
    private String nombre;
    private double precio;
    private int cantidadStock;

    public Producto(String nombre, double precio, int cantidadStock) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    public void reducirStock(int cantidadVendida) {
        if (cantidadStock >= cantidadVendida) {
            cantidadStock -= cantidadVendida;
        }
    }

    public void incrementarStock(int cantidad) {
        this.cantidadStock += cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " - Stock: " + cantidadStock;
    }
}