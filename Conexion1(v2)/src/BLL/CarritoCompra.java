package BLL;

import java.util.ArrayList;

public class CarritoCompra {
    private ArrayList<Producto> productos = new ArrayList<>();

    public boolean agregarProducto(Producto producto, int cantidad) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(producto.getNombre())) {
                p.incrementarStock(cantidad); 
                return true;
            }
        }

       
        Producto nuevoProducto = new Producto(producto.getNombre(), producto.getPrecio(), cantidad);
        productos.add(nuevoProducto);
        return true;
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio() * p.getCantidadStock();
        }
        return total;
    }

    public void mostrarCarrito() {
        StringBuilder detalleCarrito = new StringBuilder("Carrito de Compras:\n");
        for (Producto p : productos) {
            detalleCarrito.append(p.getNombre()).append(" - $").append(p.getPrecio())
                          .append(" - Cantidad: ").append(p.getCantidadStock()).append("\n");
        }
        System.out.println(detalleCarrito.toString());  // Mostrar en consola
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}