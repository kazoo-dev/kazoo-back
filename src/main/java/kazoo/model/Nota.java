package kazoo.model;

import javax.persistence.*;

@Entity
public class Nota {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long nota_id;
    private String pitch;
    private String duracion;
    private Boolean has_tie;
    private Boolean has_dot;
    private Integer clarity;
    private Integer error;

    @ManyToOne
    @JoinColumn(name="compas_id", nullable=false)
    private Compas compas;


    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Boolean getHas_tie() {
        return has_tie;
    }

    public void setHas_tie(Boolean has_tie) {
        this.has_tie = has_tie;
    }

    public Boolean getHas_dot() {
        return has_dot;
    }

    public void setHas_dot(Boolean has_dot) {
        this.has_dot = has_dot;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Long getNota_id() {
        return nota_id;
    }

    public void setNota_id(Long nota_id) {
        this.nota_id = nota_id;
    }

    public Compas getCompas() {
        return compas;
    }

    public void setCompas(Compas compas) {
        this.compas = compas;
    }
}
