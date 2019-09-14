package kazoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Partitura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String notas;

    @NotNull
    private String nombre;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Partitura(String notas, String nombre) {
        this.notas = notas;
        this.nombre = nombre;
    }
}