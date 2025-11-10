@"
# 📚 Sistema de Gestión de Biblioteca (Proyecto Final)

## 🎯 Objetivo
Este proyecto implementa un **sistema de gestión de biblioteca** en Java aplicando los conceptos de **Programación Orientada a Objetos (POO)**, manejo de **excepciones personalizadas**, **validaciones**, y **colecciones**.

---

## 🧩 Estructura del Proyecto
SistemaBiblioteca/
├── src/
│ └── biblioteca/
│ ├── BibliotecaApp.java
│ ├── Biblioteca.java
│ ├── Libro.java
│ ├── Usuario.java
│ ├── Prestamo.java
│ ├── EstadoPrestamo.java
│ ├── LibroNoDisponibleException.java
│ ├── UsuarioSinCupoException.java
│ └── InvalidDataException.java
└── README.md

---

## 🧱 Clases Principales

### **Libro**
- Atributos: ISBN, título, autor, año, ejemplares totales/disponibles.  
- Métodos: `prestar()`, `devolver()`, `estaDisponible()`.  
- Valida ISBN (13 dígitos) y año válido.  
- Lanza `LibroNoDisponibleException` si no hay ejemplares.

### **Usuario**
- Atributos: ID (autogenerado con `AtomicInteger`), nombre, email validado, libros prestados, multas.  
- Métodos: `puedePedirPrestado()`, `agregarMulta()`, `pagarMultas()`.  
- Reglas: máximo 3 libros y multas ≤ $5000.  
- Lanza `UsuarioSinCupoException`.

### **Prestamo**
- Controla el estado (`activo`, `devuelto`, `vencido`).  
- Calcula multa usando `BigDecimal` ($500 por día de retraso).  
- Maneja fechas y reglas de devolución.

### **Biblioteca**
- Repositorio de `Libro`, `Usuario`, `Prestamo`.  
- Métodos:
  - `agregarLibro()`
  - `registrarUsuario()`
  - `realizarPrestamo()`
  - `devolverLibro()`
  - `obtenerTopLibrosPrestados()`
  - `obtenerUsuariosConMultas()`
- Usa `HashMap`, `ArrayList`, `Optional` y `Streams` para eficiencia y claridad.

### **BibliotecaApp**
- Menú principal con opciones:
  1. Agregar libro  
  2. Registrar usuario  
  3. Realizar préstamo  
  4. Devolver libro  
  5. Consultar libros disponibles  
  6. Consultar préstamos de usuario  
  7. Listar usuarios con multas  
  8. Top 5 libros más prestados  
  9. Salir  

---

## ⚙️ Tecnologías Utilizadas
- **Java 8 o superior**  
- **Colecciones Java (HashMap, ArrayList)**  
- **Streams y Optional**  
- **Excepciones personalizadas**
- **BigDecimal** para cálculos monetarios  
- **AtomicInteger** para ID seguros  
- **POO (Encapsulación, Herencia, Polimorfismo)**

---

## 🚀 Cómo Ejecutarlo

1️⃣ Compilar todas las clases:
```bash
javac -d out src/biblioteca/*.java

2️⃣ Ejecutar la aplicación:

java -cp out biblioteca.BibliotecaApp

javac -d out src/biblioteca/*.java

