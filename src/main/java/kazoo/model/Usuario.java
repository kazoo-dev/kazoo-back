package kazoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String nombre;
    @NotNull
    private String clave;
    @JsonIgnore
    private byte[] salt;


    @OneToMany(mappedBy="partitura_id",cascade = CascadeType.ALL)
    private List<Partitura> partituras;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClave(String clave) {

        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setSalt(byte[] generateSalt) {
        this.salt = generateSalt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Partitura> getPartituras() {
        return partituras;
    }

    public void setPartituras(List<Partitura> partituras) {
        this.partituras = partituras;
    }

    public void agregarPartitura(Partitura partitura) {
        if(partituras.isEmpty()){
            partituras = new ArrayList<>();
        }
        partituras.add(partitura);
    }
}

