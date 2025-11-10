package biblioteca;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anio;
    private int ejemplaresTotales;
    private int ejemplaresDisponibles;
    private int vecesPrestado;

    public Libro(String isbn, String titulo, String autor, int anio, int ejemplaresTotales) throws InvalidDataException {
        if (!isbn.matches("\\d{13}")) {
            throw new InvalidDataException("El ISBN debe tener 13 dígitos.");
        }
        if (anio < 1000 || anio > 2025) {
            throw new InvalidDataException("El año no es válido.");
        }
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.ejemplaresTotales = ejemplaresTotales;
        this.ejemplaresDisponibles = ejemplaresTotales;
        this.vecesPrestado = 0;
    }

    public boolean estaDisponible() {
        return ejemplaresDisponibles > 0;
    }

    public void prestar() throws LibroNoDisponibleException {
        if (!estaDisponible()) {
            throw new LibroNoDisponibleException("No hay ejemplares disponibles para prestar.");
        }
        ejemplaresDisponibles--;
        vecesPrestado++;
    }

    public void devolver() {
        if (ejemplaresDisponibles < ejemplaresTotales) {
            ejemplaresDisponibles++;
        }
    }

    public String getTitulo() { return titulo; }
    public String getIsbn() { return isbn; }
    public int getVecesPrestado() { return vecesPrestado; }

    @Override
    public String toString() {
        return titulo + " (" + autor + ") - Año: " + anio +
                " | Disponibles: " + ejemplaresDisponibles + "/" + ejemplaresTotales;
    }
}
