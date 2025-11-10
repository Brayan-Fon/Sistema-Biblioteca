package biblioteca;

import java.util.Scanner;
import java.util.List;

public class BibliotecaApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> agregarLibro();
                case 2 -> registrarUsuario();
                case 3 -> realizarPrestamo();
                case 4 -> devolverLibro();
                case 5 -> consultarLibrosDisponibles();
                case 6 -> consultarPrestamosUsuario();
                case 7 -> listarUsuariosConMultas();
                case 8 -> topLibrosMasPrestados();
                case 9 -> System.out.println("👋 Saliendo del sistema...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
            System.out.println();
        } while (opcion != 9);
    }

    private static void mostrarMenu() {
        System.out.println("======= 📚 SISTEMA DE BIBLIOTECA =======");
        System.out.println("1. Agregar libro");
        System.out.println("2. Registrar usuario");
        System.out.println("3. Realizar préstamo");
        System.out.println("4. Devolver libro");
        System.out.println("5. Consultar libros disponibles");
        System.out.println("6. Consultar préstamos de usuario");
        System.out.println("7. Listar usuarios con multas");
        System.out.println("8. Top 5 libros más prestados");
        System.out.println("9. Salir");
        System.out.println("=======================================");
    }

    private static void agregarLibro() {
        try {
            System.out.print("ISBN (13 dígitos): ");
            String isbn = sc.nextLine();
            System.out.print("Título: ");
            String titulo = sc.nextLine();
            System.out.print("Autor: ");
            String autor = sc.nextLine();
            System.out.print("Año: ");
            int anio = leerEntero();
            System.out.print("Cantidad de ejemplares: ");
            int cantidad = leerEntero();

            Libro libro = new Libro(isbn, titulo, autor, anio, cantidad);
            biblioteca.agregarLibro(libro);
            System.out.println("✅ Libro agregado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void registrarUsuario() {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();

            Usuario usuario = biblioteca.registrarUsuario(nombre, email);
            System.out.println("✅ Usuario registrado con ID: " + usuario.getId());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void realizarPrestamo() {
        try {
            System.out.print("ID de usuario: ");
            int idUsuario = leerEntero();
            System.out.print("ISBN del libro: ");
            String isbn = sc.nextLine();

            biblioteca.realizarPrestamo(idUsuario, isbn);
            System.out.println("✅ Préstamo realizado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void devolverLibro() {
        try {
            System.out.print("ID de usuario: ");
            int idUsuario = leerEntero();
            System.out.print("ISBN del libro: ");
            String isbn = sc.nextLine();

            biblioteca.devolverLibro(idUsuario, isbn);
            System.out.println("✅ Libro devuelto correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void consultarLibrosDisponibles() {
        List<Libro> disponibles = biblioteca.obtenerLibrosDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("📕 No hay libros disponibles actualmente.");
        } else {
            System.out.println("📘 Libros disponibles:");
            disponibles.forEach(System.out::println);
        }
    }

    private static void consultarPrestamosUsuario() {
        System.out.print("ID de usuario: ");
        int idUsuario = leerEntero();
        biblioteca.buscarUsuarioPorId(idUsuario)
                .ifPresentOrElse(
                        u -> {
                            if (u.getPrestamosActivos().isEmpty()) {
                                System.out.println("📭 El usuario no tiene préstamos activos.");
                            } else {
                                System.out.println("📚 Préstamos activos del usuario:");
                                u.getPrestamosActivos().forEach(System.out::println);
                            }
                        },
                        () -> System.out.println("❌ Usuario no encontrado.")
                );
    }

    private static void listarUsuariosConMultas() {
        List<Usuario> usuarios = biblioteca.obtenerUsuariosConMultas();
        if (usuarios.isEmpty()) {
            System.out.println("✅ No hay usuarios con multas pendientes.");
        } else {
            System.out.println("⚠️ Usuarios con multas:");
            usuarios.forEach(System.out::println);
        }
    }

    private static void topLibrosMasPrestados() {
        List<Libro> top = biblioteca.obtenerTopLibrosPrestados();
        if (top.isEmpty()) {
            System.out.println("📚 No hay registros de préstamos aún.");
        } else {
            System.out.println("🏆 Top 5 libros más prestados:");
            top.forEach(System.out::println);
        }
    }

    private static int leerEntero() {
        while (true) {
            try {
                String input = sc.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
