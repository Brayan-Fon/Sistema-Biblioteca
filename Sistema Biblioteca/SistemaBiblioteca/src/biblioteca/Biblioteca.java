package biblioteca;

import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

public class Biblioteca {
    private Map<String, Libro> libros;
    private Map<Integer, Usuario> usuarios;
    private List<Prestamo> prestamos;

    public Biblioteca() {
        this.libros = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.prestamos = new ArrayList<>();
    }

    // ---- Gestión de Libros ----
    public void agregarLibro(Libro libro) throws InvalidDataException {
        if (libros.containsKey(libro.getIsbn())) {
            throw new InvalidDataException("Ya existe un libro con ese ISBN.");
        }
        libros.put(libro.getIsbn(), libro);
    }

    public Optional<Libro> buscarLibroPorIsbn(String isbn) {
        return Optional.ofNullable(libros.get(isbn));
    }

    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        return libros.values().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public List<Libro> obtenerLibrosDisponibles() {
        return libros.values().stream()
                .filter(Libro::estaDisponible)
                .collect(Collectors.toList());
    }

    // ---- Gestión de Usuarios ----
    public Usuario registrarUsuario(String nombre, String email) throws InvalidDataException {
        Usuario usuario = new Usuario(nombre, email);
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    // ---- Sistema de Préstamos ----
    public void realizarPrestamo(int idUsuario, String isbn)
            throws LibroNoDisponibleException, UsuarioSinCupoException, InvalidDataException {

        Usuario usuario = usuarios.get(idUsuario);
        Libro libro = libros.get(isbn);

        if (usuario == null) throw new InvalidDataException("Usuario no encontrado.");
        if (libro == null) throw new InvalidDataException("Libro no encontrado.");

        libro.prestar();
        Prestamo prestamo = new Prestamo(libro, usuario);
        usuario.agregarPrestamo(prestamo);
        prestamos.add(prestamo);
    }

    public void devolverLibro(int idUsuario, String isbn) throws InvalidDataException {
        Usuario usuario = usuarios.get(idUsuario);
        if (usuario == null) throw new InvalidDataException("Usuario no encontrado.");

        Prestamo prestamo = prestamos.stream()
                .filter(p -> p.getUsuario().equals(usuario) && p.getLibro().getIsbn().equals(isbn) && p.estaActivo())
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("No se encontró préstamo activo para ese libro."));

        prestamo.devolver();
    }

    // ---- Reportes ----
    public List<Usuario> obtenerUsuariosConMultas() {
        return usuarios.values().stream()
                .filter(u -> u.getMultas().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
    }

    public List<Libro> obtenerTopLibrosPrestados() {
        return libros.values().stream()
                .sorted((a, b) -> Integer.compare(b.getVecesPrestado(), a.getVecesPrestado()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
