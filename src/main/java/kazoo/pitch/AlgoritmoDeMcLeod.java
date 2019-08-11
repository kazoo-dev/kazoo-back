package kazoo.pitch;

import org.hipparchus.complex.Complex;
import org.hipparchus.transform.DftNormalization;
import org.hipparchus.transform.FastFourierTransformer;
import org.hipparchus.transform.TransformType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoDeMcLeod {
    /**
     * Defines the relative size the chosen peak (kazoo.pitch) has. 0.93 means: choose
     * the first peak that is higher than 93% of the highest peak detected. 93%
     * is the default value used in the Tartini user interface.
     */
    private static final double TOPE_POR_DEFECTO = 0.97;
    /**
     * For performance reasons, peaks below this cutoff are not even considered.
     */
    private static final double TOPE_DE_PICO = 0.5;

    /**
     * Pitch annotations below this threshold are considered invalid, they are
     * ignored.
     */
    private static final double TOPE_MINIMO = 80.0; // Hz

    private final float frecuenciaDeMuestreo;
    private final double tope;
    private double[] bufferAudio;
    public double[] autocorrelaciones;
    private double[] componentesM;
    private NSDFArray nsdf;
    private List<Integer> posicionesDePicos;

    public AlgoritmoDeMcLeod(float frecuenciaDeMuestreo) {
        this.frecuenciaDeMuestreo = frecuenciaDeMuestreo;
        this.tope = TOPE_POR_DEFECTO;
    }

    public ResultadoDeDeteccion estimarAltura(double[] bufferAudio) {
        this.bufferAudio = bufferAudio;

        this.autocorrelacionar();
        this.calcularComponentesM();
        this.calcularNSDF();
        this.elegirPicos();

        EstimacionesYMaximaAmplitud estimacionesYMaximaAmplitud = this.obtenerEstimacionesYMaximaAmplitud();

        double altura;
        if (estimacionesYMaximaAmplitud.estimaciones.size() == 0) {
            altura = -1;
        } else
            altura = this.primerEstamactionSobreTope(estimacionesYMaximaAmplitud);

        if (altura == -1) return new ResultadoDeDeteccion(-1, -1, false);

        return new ResultadoDeDeteccion(altura, estimacionesYMaximaAmplitud.maximaAmplitud, true);
    }

    private double primerEstamactionSobreTope(EstimacionesYMaximaAmplitud estimacionesYMaximaAmplitud) {
        double topeReal = this.tope * estimacionesYMaximaAmplitud.maximaAmplitud;
        double periodo = -1;

        for (double[] estimacion: estimacionesYMaximaAmplitud.estimaciones) {
            if (estimacion[1] >= topeReal) {
                periodo = estimacion[0];
                break;
            }
        }

        double estimacionReal = this.frecuenciaDeMuestreo / periodo;
        return estimacionReal > TOPE_MINIMO ? estimacionReal : -1;
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

    private void elegirPicos() {
        Integer maximaPosicionActual = 0;
        boolean pasoPrimerCrucePositivo = false;
        posicionesDePicos = new ArrayList<>();


        for(Integer posicion=1; posicion< (this.nsdf.length - 1); posicion++) {
            if (this.nsdf.esCrucePositivo(posicion)) {
                pasoPrimerCrucePositivo = true;
                maximaPosicionActual = posicion;
            }
            if(!pasoPrimerCrucePositivo){
                continue;
            }

            if(this.nsdf.esCruceNegativo(posicion)) {
                posicionesDePicos.add(maximaPosicionActual);
                continue;
            }

            if (this.nsdf.get(posicion) < 0) {
                continue;
            }

            if (this.nsdf.esMayorQueElAnterior(posicion) && this.nsdf.esMayorOIgualQueElSiguiente(posicion)) {
                if (this.nsdf.get(posicion) > this.nsdf.get(maximaPosicionActual)) {
                    maximaPosicionActual = posicion;
                }
            }
        }
    }

    private EstimacionesYMaximaAmplitud obtenerEstimacionesYMaximaAmplitud() {
        List<double[]> estimaciones = new ArrayList<>();
        double maximaAmplitud = Float.MIN_VALUE;

        for (Integer tau: this.posicionesDePicos) {
            maximaAmplitud = Math.max(maximaAmplitud, this.nsdf.get(tau));
            if (this.nsdf.get(tau) > TOPE_DE_PICO) {
                double[] puntoDeQuiebre = this.nsdf.interpolarParabolicamente(tau);
                estimaciones.add(puntoDeQuiebre);
                maximaAmplitud = Math.max(maximaAmplitud, puntoDeQuiebre[1]);
            }
        }

        return new EstimacionesYMaximaAmplitud(estimaciones, Math.max(maximaAmplitud, -1));
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
