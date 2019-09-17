package kazoo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Nota {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long nota_id;
    private String pitch;
    private String duration;
    private Boolean has_tie;
    private Boolean has_dot;
    private Integer clarity;
    private Integer error;


    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

}
