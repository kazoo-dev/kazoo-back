package kazoo.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Partitura {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long partitura_id;

    private String tonalidad;
    private String numerador;
    private String denominador;
    private String nombre;
    private Boolean esPublica = false;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "partitura_id")
    @OrderBy("compas_id")
    private List<Compas> compases;

    @ManyToOne
    @JoinColumn(name="id")
    private Usuario usuario;

    public String getTonalidad() {
        return tonalidad;
    }

    public void setTonalidad(String tonalidad) {
        this.tonalidad = tonalidad;
    }

    public String getNumerador() {
        return numerador;
    }

    public void setNumerador(String numerador) {
        this.numerador = numerador;
    }

    public String getDenominador() {
        return denominador;
    }

    public void setDenominador(String denominador) {
        this.denominador = denominador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Compas> getCompases() {
        return compases;
    }

    public void setCompases(List<Compas> compases) {
        this.compases = compases;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getPartitura_id() {
        return partitura_id;
    }

    public void setPartitura_id(Long partitura_id) {
        this.partitura_id = partitura_id;
    }

    public Boolean getEsPublica() {
        return esPublica;
    }

    public void setEsPublica(Boolean esPublica) {
        this.esPublica = esPublica;
    }
}
