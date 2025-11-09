package ar.edu.ubp.das.ristorinobackend.dto;

// Este DTO recibirá los 3 campos de la clave compuesta de la promoción
// que fue clickeada.
public class ClickRequestDTO {
    private Integer nroRestaurante;
    private Integer nroIdioma;
    private Integer nroContenido;

    public Integer getNroRestaurante() {
        return nroRestaurante;
    }

    public void setNroRestaurante(Integer nroRestaurante) {
        this.nroRestaurante = nroRestaurante;
    }

    public Integer getNroIdioma() {
        return nroIdioma;
    }

    public void setNroIdioma(Integer nroIdioma) {
        this.nroIdioma = nroIdioma;
    }

    public Integer getNroContenido() {
        return nroContenido;
    }

    public void setNroContenido(Integer nroContenido) {
        this.nroContenido = nroContenido;
    }
    // 💡 Genera Getters y Setters para estos 3 campos (Alt+Insert)
    // ...
}