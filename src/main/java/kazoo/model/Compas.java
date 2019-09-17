package kazoo.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Compas {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long compas_id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "compas_id")
    @OrderBy("nota_id")
    private List<Nota> notas;

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public Long getCompas_id() {
        return compas_id;
    }

    public void setCompas_id(Long compas_id) {
        this.compas_id = compas_id;
    }



}
