package prestamos;

import java.time.*;

public class Prestamo {
    private String codigoLibro;
    private String tituloLibro;
    private Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

    public Prestamo(String codigoLibro, String tituloLibro, Usuario socio,
                    LocalDate fechaPrestamo, LocalDate fechaDevolucionPrevista)
            throws PrestamoInvalidoException {
        if (codigoLibro == null || codigoLibro.length() != 7)
            throw new PrestamoInvalidoException("Codigo de libro no valido");
        for (int i = 0; i < 3; i++)
            if (!Character.isUpperCase(codigoLibro.charAt(i)))
                throw new PrestamoInvalidoException("Las 3 primeras letras deben ser mayúsculas.");
        for (int i = 3; i < 7; i++)
            if (!Character.isDigit(codigoLibro.charAt(i)))
                throw new PrestamoInvalidoException("Los últimos 4 caracteres deben ser números.");
        if (tituloLibro == null || tituloLibro.trim().isEmpty())
            throw new PrestamoInvalidoException("Título obligatorio.");
        if (socio == null)
            throw new PrestamoInvalidoException("El usuario no puede ser null.");

        if (fechaPrestamo == null || fechaDevolucionPrevista == null)
            throw new PrestamoInvalidoException("Fechas obligatorias.");

        if (fechaDevolucionPrevista.isBefore(fechaPrestamo))
            throw new PrestamoInvalidoException("La devolución prevista no puede ser antes del préstamo.");
    }
}
