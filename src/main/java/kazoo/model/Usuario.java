package kazoo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

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
}

