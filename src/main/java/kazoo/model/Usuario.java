package kazoo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
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

    @OneToMany(mappedBy = "usuario")
    private List<Partitura> partituras;

    public void agregarPartitura(Partitura partitura) {
        this.partituras.add(partitura);
    }

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

    public List<Partitura> getPartituras() {
        return partituras;
    }
}

