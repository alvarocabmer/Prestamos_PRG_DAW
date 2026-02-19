package prestamos;

import java.time.LocalDate;

public class GestorBiblioteca {

    private static final int MAX_USUARIOS = 50;
    private static final int MAX_PRESTAMOS = 200;
    private Usuario[] usuarios;
    private Prestamo[] prestamos;
    private int numeroUsuarios;
    private int numeroPrestamos;

    public GestorBiblioteca() {
        usuarios = new Usuario[MAX_USUARIOS];
        prestamos = new Prestamo[MAX_PRESTAMOS];
        numeroUsuarios = 0;
        numeroPrestamos = 0;
    }

    public void registrarUsuario(Usuario u) throws UsuarioRepetidoException {
        if (u == null) return;

        for (int i = 0; i < numeroUsuarios; i++) {
            if (usuarios[i] != null && usuarios[i].getNumeroSocio().equals(u.getNumeroSocio())) {
                throw new UsuarioRepetidoException("Usuario repetido: " + u.getNumeroSocio());
            }
        }

        if (numeroUsuarios >= MAX_USUARIOS) return;
        usuarios[numeroUsuarios] = u;
        numeroUsuarios++;
    }

    public Prestamo realizarPrestamo(String codigoLibro, String titulo, LocalDate fechaPrestamo, Usuario u)
            throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException {

        if (u == null) throw new PrestamoInvalidoException("Usuario null.");

        if (u.estaSancionado()) {
            throw new UsuarioSancionadoException("Usuario sancionado hasta: " + u.getFechaFinSancion());
        }

        for (int i = 0; i < numeroPrestamos; i++) {
            Prestamo p = prestamos[i];
            if (p != null && p.getCodigoLibro().equals(codigoLibro) && !p.estaDevuelto()) {
                throw new LibroNoDisponibleException("Libro no disponible: " + codigoLibro);
            }
        }

        if (numeroPrestamos >= MAX_PRESTAMOS) {
            throw new PrestamoInvalidoException("No caben más préstamos.");
        }

        Prestamo nuevo = new Prestamo(codigoLibro, titulo, u, fechaPrestamo, fechaPrestamo.plusDays(14));

        prestamos[numeroPrestamos] = nuevo;
        numeroPrestamos++;
        return nuevo;
    }

    public boolean devolverLibro(String codigoLibro, LocalDate fechaDevolucion) throws PrestamoInvalidoException {
        for (int i = 0; i < numeroPrestamos; i++) {
            Prestamo p = prestamos[i];

            if (p != null && p.getCodigoLibro().equals(codigoLibro) && !p.estaDevuelto()) {

                p.devolver(fechaDevolucion);

                int retraso = p.calcularDiasRetraso();
                if (retraso > 0) {
                    p.getSocio().sancionar(retraso, fechaDevolucion);
                }
                return true;
            }
        }
        return false;
    }

    public Usuario buscarUsuario(String codigoSocio) {
        for (int i = 0; i < numeroUsuarios; i++) {
            if (usuarios[i] != null && usuarios[i].getNumeroSocio().equals(codigoSocio)) {
                return usuarios[i];
            }
        }
        return null;
    }

    public Prestamo[] getPrestamos() {
        return prestamos;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public void actualizarSanciones() {
        LocalDate hoy = LocalDate.now();

        for (int i = 0; i < numeroUsuarios; i++) {
            Usuario u = usuarios[i];

            if (u != null && u.getFechaFinSancion() != null
                    && u.getFechaFinSancion().isBefore(hoy)) {

                u.levantarSancion();
            }
        }
    }


    @Override
    public String toString() {
        String s = "USUARIOS:\n";
        for (int i = 0; i < numeroUsuarios; i++) s += usuarios[i] + "\n";

        s += "\nPRESTAMOS:\n";
        for (int i = 0; i < numeroPrestamos; i++) s += prestamos[i] + "\n";

        return s;
    }
}
