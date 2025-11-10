package biblioteca;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Usuario {
    private static final AtomicInteger contador = new AtomicInteger(1);
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private final int id;
    private String nombre;
    private String email;
    private List<Prestamo> prestamosActivos;
    private BigDecimal multas;

    public Usuario(String nombre, String email) throws InvalidDataException {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new InvalidDataException("El email no tiene un formato válido.");
        }

        this.id = contador.getAndIncrement();
        this.nombre = nombre;
        this.email = email;
        this.prestamosActivos = new ArrayList<>();
        this.multas = BigDecimal.ZERO;
    }

    public boolean puedePedirPrestado() {
        return prestamosActivos.size() < 3 && multas.compareTo(new BigDecimal("5000")) <= 0;
    }

    public void agregarPrestamo(Prestamo prestamo) throws UsuarioSinCupoException {
        if (!puedePedirPrestado()) {
            throw new UsuarioSinCupoException("El usuario no puede realizar más préstamos o tiene multas pendientes.");
        }
        prestamosActivos.add(prestamo);
    }

    public void agregarMulta(BigDecimal valor) {
        multas = multas.add(valor);
    }

    public void pagarMultas() {
        multas = BigDecimal.ZERO;
    }

    public void devolverPrestamo(Prestamo prestamo) {
        prestamosActivos.remove(prestamo);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public BigDecimal getMultas() { return multas; }
    public List<Prestamo> getPrestamosActivos() { return prestamosActivos; }

    @Override
    public String toString() {
        return "Usuario #" + id + " - " + nombre + " | Email: " + email + " | Multas: $" + multas;
    }
}
