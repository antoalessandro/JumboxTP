package BLL;

import java.time.LocalDateTime;

public class TicketCompra {
    private LocalDateTime fecha;
    private double montoTotal;
    private MetodoPago metodoPago;

    public TicketCompra(double montoTotal, MetodoPago metodoPago) {
        this.fecha = LocalDateTime.now();
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return "Ticket de compra - Fecha: " + fecha + " - Monto: $" + montoTotal + " - Pago: " + metodoPago;
    }
}