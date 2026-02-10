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

    }
}
