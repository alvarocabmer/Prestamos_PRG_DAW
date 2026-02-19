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
            throw new UsuarioInvalidoException("El email es obligatorio");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new UsuarioInvalidoException("El email debe contener '@' y '.'");
        }
        if (numeroSocio == null || numeroSocio.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El numero de socio es obligatorio");
        }
        if (!numeroSocio.contains("SOC")) {
            throw new UsuarioInvalidoException("El numero de socio debe contener 'SOC'");
        }
        if (fechaRegistro == null || fechaRegistro.isAfter(LocalDate.now())) {
            throw new UsuarioInvalidoException("La fecha de registro no es válida");
        }
        this.nombre = nombre;
        this.email = email;
        this.numeroSocio = numeroSocio;
        this.fechaRegistro = fechaRegistro;
        this.sancionado = false;
        this.fechaFinSancion = null;
    }
    public void sancionar(int dias, LocalDate fechaDevolucion){
        this.sancionado = true;
        this.fechaRegistro = LocalDate.now().plusDays(dias);
    }
    public void levantarSancionar(){
        this.sancionado = false;
        this.fechaRegistro = null;
    }
    public boolean estaSancionado(){
        return sancionado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", numeroSocio='" + numeroSocio + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", sancionado=" + sancionado +
                ", fechaFinSancion=" + fechaFinSancion +
                '}';
    }

    public String getNumeroSocio() {
        return numeroSocio;
    }
    public String getNombre() { return nombre; }
    public LocalDate getFechaFinSancion() {
        return fechaFinSancion;
    }

    public void levantarSancion() {
        sancionado = false;
        fechaFinSancion = null;
    }
}
