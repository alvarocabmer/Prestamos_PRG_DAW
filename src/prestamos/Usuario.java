package prestamos;

import java.time.*;

public class Usuario {
    private String nombre;
    private String email;
    private String numeroSocio;
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion;

    public Usuario(String nombre,String email,String numeroSocio,LocalDate fechaRegistro,boolean sancionado,LocalDate fechaFinSancion)
            throws UsuarioInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El nombre es obligatorio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El email es incorrecto");
        }
        if (numeroSocio == null || numeroSocio.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El numero de socio es incorrecto");
        }
        if (fechaRegistro == null || fechaRegistro.isAfter(LocalDate.now())) {
            throw new UsuarioInvalidoException("La fecha de registro no es válida");
        }
        if (sancionado == false) {
            throw new UsuarioInvalidoException("El sancionado es obligatorio");
        }
        if (fechaFinSancion == null) {
            throw new UsuarioInvalidoException("La fecha del fin de sanción no es válida")
        }

    }
}
