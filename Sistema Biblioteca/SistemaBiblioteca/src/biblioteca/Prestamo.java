package biblioteca;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prestamo {
    private static final BigDecimal MULTA_POR_DIA = new BigDecimal("500");

    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estado;

    public Prestamo(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = fechaPrestamo.plusDays(14);
        this.estado = EstadoPrestamo.ACTIVO;
    }

    public void devolver() {
        if (estado == EstadoPrestamo.ACTIVO) {
            LocalDate hoy = LocalDate.now();
            if (hoy.isAfter(fechaDevolucion)) {
                long diasRetraso = ChronoUnit.DAYS.between(fechaDevolucion, hoy);
                BigDecimal multa = MULTA_POR_DIA.multiply(new BigDecimal(diasRetraso));
                usuario.agregarMulta(multa);
                estado = EstadoPrestamo.VENCIDO;
            } else {
                estado = EstadoPrestamo.DEVUELTO;
            }
            libro.devolver();
            usuario.devolverPrestamo(this);
        }
    }

    public boolean estaActivo() {
        return estado == EstadoPrestamo.ACTIVO;
    }

    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public EstadoPrestamo getEstado() { return estado; }

    @Override
    public String toString() {
        return "📚 " + libro.getTitulo() +
                " | Estado: " + estado +
                " | Prestado el: " + fechaPrestamo +
                " | Devolver antes de: " + fechaDevolucion;
    }
}
