package kazoo.pitch;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class McLeodPitchMethodTests {
    private AlgoritmoDeMcLeod metodoDeteccion;

    @Before
    public void setup() {
        this.metodoDeteccion = new AlgoritmoDeMcLeod(44100);
    }

    @Test
    public void computaLaAutocorrelacionDeDatasetsDeDosMuestras() {
        this.metodoDeteccion.estimarAltura(new double[]{1, -1});
        assertThat(this.metodoDeteccion.autocorrelaciones).isEqualTo(new double[]{2, -1});
    }

    @Test
    public void cumputaLaAutocorrelacionDeDatasetsDeTresMuestras() {
        this.metodoDeteccion.estimarAltura(new double[]{1, 2, 1});
        assertThat(this.metodoDeteccion.autocorrelaciones).isEqualTo(new double[]{6, 4, 1});
    }

    @Test
    public void computaLaAutocorrelacionDeDatasetsDeCuatroMuestras() {
        this.metodoDeteccion.estimarAltura(new double[]{1, 0, 1, 0});
        assertThat(this.metodoDeteccion.autocorrelaciones).isEqualTo(new double[]{2, 0, 1, 0});
    }

    @Test
    public void computaLaAutocorrelacionDeDatasetsDeCincoMuestras() {
        this.metodoDeteccion.estimarAltura(new double[]{1, 2, 3, 4, 5});
        assertThat(this.metodoDeteccion.autocorrelaciones).isEqualTo(new double[]{55, 40, 26, 14, 5});
    }

    @Test
    public void computaLaAutocorrelacionDeDatasetsDeOchoMuestras() {
        this.metodoDeteccion.estimarAltura(new double[]{1, -1, 1, -1, 1, -1, 1, -1});
        assertCorrelates(this.metodoDeteccion.autocorrelaciones, new double[]{8, -7, 6, -5, 4, -3, 2, -1});
    }

    @Test
    public void estimaLaAlturaDeUnaOndaSinusoidalConUn1PorcientoDeErrorYUn99PorcientoDeClaridad() {
        int la4 = 440;

    }

    private void assertCorrelates(double[] correlaciones, double[] correlacionesEsperadas) {
        for(int i=0; i<correlaciones.length; i++) {
            assertThat(correlaciones[i]).isCloseTo(correlacionesEsperadas[i], offset(0.0000000000001));
        }
    }
}
