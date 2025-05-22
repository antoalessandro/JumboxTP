package BLL;

public class Cliente extends Usuario {
    private CarritoCompra carrito;

    public Cliente(int id, String nombre, String email, String tipo, String password) {
        super(id, nombre, email, tipo, password);
        this.carrito = new CarritoCompra();
    }

    public void agregarAlCarrito(Producto producto, int cantidad) {
        if (carrito.agregarProducto(producto, cantidad)) {
            System.out.println("Producto agregado al carrito.");
        } else {
            System.out.println("Stock insuficiente.");
        }
    }

    public void eliminarDelCarrito(Producto producto) {
        carrito.eliminarProducto(producto);
    }

    public TicketCompra pagarCompra(MetodoPago metodoPago) {
        double total = carrito.calcularTotal();
        return new TicketCompra(total, metodoPago);
    }

    public CarritoCompra getCarrito() {
        return carrito;
    }
}