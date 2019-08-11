package kazoo.pitch;

import java.util.ArrayList;
import java.util.List;

public class EstimacionesYMaximaAmplitud {
    public List<double[]> estimaciones;
    public double maximaAmplitud;

    public EstimacionesYMaximaAmplitud(List<double[]> estimaciones, double maximaAmplitud) {
        this.estimaciones = estimaciones;
        this.maximaAmplitud = maximaAmplitud;
    }
}
