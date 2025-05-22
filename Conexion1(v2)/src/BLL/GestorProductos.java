package BLL;

import java.util.HashMap;

import javax.swing.JOptionPane;

import java.util.ArrayList;

public class GestorProductos {
    private static HashMap<String, Producto> inventario = new HashMap<>();

    public static void inicializarProductos() {
        agregarProducto(new Producto("Arroz", 500, 50));
        agregarProducto(new Producto("Harina", 300, 40));
        agregarProducto(new Producto("Aceite", 1500, 30));
        agregarProducto(new Producto("Yerba", 800, 25));
    }

    public static void agregarProducto(Producto producto) {
        if (inventario.containsKey(producto.getNombre())) {
            inventario.get(producto.getNombre()).incrementarStock(producto.getCantidadStock());
        } else {
            inventario.put(producto.getNombre(), producto);
        }
    }

    public static void mostrarProductos() {
        StringBuilder listaProductos = new StringBuilder("Stock disponible:\n");
        for (Producto p : inventario.values()) {
            listaProductos.append(p.getNombre()).append(" - $").append(p.getPrecio())
                          .append(" - Stock: ").append(p.getCantidadStock()).append("\n");
        }

        JOptionPane.showMessageDialog(null, listaProductos.toString(), "Stock del Supermercado", JOptionPane.INFORMATION_MESSAGE);
    }

    public static ArrayList<Producto> getListaProductos() {
        return new ArrayList<>(inventario.values());
    }

    public static Producto buscarProducto(String nombre) {
        return inventario.getOrDefault(nombre, null);
    }

    public static boolean reducirStock(String nombre, int cantidad) {
        Producto producto = inventario.get(nombre);
        if (producto != null && producto.getCantidadStock() >= cantidad) {
            producto.reducirStock(cantidad);
            return true;
        }
        return false;
    }

    // ✅ Nuevo método para eliminar producto por nombre
    public static boolean eliminarProducto(String nombre) {
        if (inventario.containsKey(nombre)) {
            inventario.remove(nombre);
            return true;
        } else {
            return false;
        }
    }
}