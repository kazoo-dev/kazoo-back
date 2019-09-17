package kazoo.controller.to;

import kazoo.model.Compas;
import kazoo.model.Partitura;

import java.util.List;

public class DetallePartitura {

    private Long partitura_id;
    private String tonalidad;
    private String numerador;
    private String denominador;
    private String nombre;
    private List<Compas> compases;

    public DetallePartitura(Partitura partitura) {
        this.partitura_id = partitura.getPartitura_id();
        this.tonalidad = partitura.getTonalidad();
        this.numerador = partitura.getNumerador();
        this.denominador = partitura.getDenominador();
        this.nombre = partitura.getNombre();
        this.compases = partitura.getCompases();
    }

    public Long getPartitura_id() {
        return partitura_id;
    }

    public void setPartitura_id(Long partitura_id) {
        this.partitura_id = partitura_id;
    }

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
}
