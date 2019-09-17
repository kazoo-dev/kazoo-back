package kazoo.controller.to;

import kazoo.model.Partitura;

public class PartituraListada {

    private Long id;
    private String nombrePartitura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePartitura() {
        return nombrePartitura;
    }

    public void setNombrePartitura(String nombrePartitura) {
        this.nombrePartitura = nombrePartitura;
    }

    public PartituraListada(Partitura partitura) {
        this.id = partitura.getPartitura_id();
        this.nombrePartitura = partitura.getNombre();
    }
}
