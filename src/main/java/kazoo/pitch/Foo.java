package kazoo.pitch;

import org.hipparchus.complex.Complex;
import org.hipparchus.transform.DftNormalization;
import org.hipparchus.transform.FastFourierTransformer;
import org.hipparchus.transform.TransformType;
import org.hipparchus.transform.TransformUtils;

import java.util.Arrays;

public class Foo {
    /**
     * Defines the relative size the chosen peak (kazoo.pitch) has. 0.93 means: choose
     * the first peak that is higher than 93% of the highest peak detected. 93%
     * is the default value used in the Tartini user interface.
     */
    private static final double TOPE_POR_DEFECTO = 0.97;
    /**
     * For performance reasons, peaks below this cutoff are not even considered.
     */
    private static final double SMALL_CUTOFF = 0.5;

    /**
     * Pitch annotations below this threshold are considered invalid, they are
     * ignored.
     */
    private static final double LOWER_PITCH_CUTOFF = 80.0; // Hz

    private final float frecuenciaDeMuestreo;
    private final double tope;
    private double[] bufferAudio;
    public double[] autocorrelaciones;
    private double[] componentesM;
    private NSDFArray nsdf;

    public Foo(float frecuenciaDeMuestreo) {
        this.frecuenciaDeMuestreo = frecuenciaDeMuestreo;
        this.tope = TOPE_POR_DEFECTO;
    }

    public PitchDetectionResult estimarAltura(double[] bufferAudio) {
        this.bufferAudio = bufferAudio;

        this.autocorrelacionar();
        // this.calcularComponentesM();
        // this.calcularNSDF();
        // this.elegirPicos();

        return null;
    }

    private void autocorrelacionar() {
        double[] bufferDeTrabajo = Arrays.copyOf(this.bufferAudio, this.proximaPotenciaDeDosPara(2 * this.bufferAudio.length));
        FastFourierTransformer transformador = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transformados = transformador.transform(bufferDeTrabajo, TransformType.FORWARD);
        this.multiplicarCadaUnoPorSuConjugado(transformados);
        Complex[] inversos = transformador.transform(this.extraerParteReal(transformados), TransformType.INVERSE);
        this.autocorrelaciones = Arrays.copyOf(this.extraerParteReal(inversos), this.bufferAudio.length);
    }

    private void calcularComponentesM() {
        double valorInicial = 2 * this.autocorrelaciones[0];
        int longitudBuferAudio = this.bufferAudio.length;
        this.componentesM = new double[longitudBuferAudio];
        this.componentesM[0] = valorInicial;
        double valorAnterior = valorInicial;
        int proximoIndice;

        for (int i=0; i < longitudBuferAudio; i++) {
            this.componentesM[i] = valorAnterior;
            proximoIndice = longitudBuferAudio - i - 1;
            valorAnterior = Math.pow(this.bufferAudio[i], 2) + Math.pow(this.bufferAudio[proximoIndice], 2);
        }
    }

    private void calcularNSDF() {
        double[] diferenciasNormalizadas = new double[this.autocorrelaciones.length];
        for (int i=0; i < this.autocorrelaciones.length; i++) {
            diferenciasNormalizadas[i] = 2 * this.autocorrelaciones[i] / this.componentesM[i];
        }
        this.nsdf = new NSDFArray(diferenciasNormalizadas);
    }

    private void multiplicarCadaUnoPorSuConjugado(Complex[] transformados) {
        for (int i=0; i < transformados.length; i++) {
          transformados[i] = transformados[i].multiply(transformados[i].conjugate());
        }
    }

    private double[] extraerParteReal(Complex[] complejos) {
        double[] reales = new double[complejos.length];
        for (int i=0; i < complejos.length; i++) {
            reales[i] = complejos[i].getReal();
        }
        return reales;
    }

    private int proximaPotenciaDeDosPara(int unNumero) {
        return (int) Math.pow(2, Math.ceil(Math.log(unNumero)/Math.log(2)));
    }

}
