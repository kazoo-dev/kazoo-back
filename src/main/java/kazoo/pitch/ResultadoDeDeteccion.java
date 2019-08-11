package kazoo.pitch;

public class ResultadoDeDeteccion {
    private double altura;

    private double probabilidad;

    private boolean detectado;

    public ResultadoDeDeteccion(double altura, double probabilidad, boolean detectado) {
        this.altura = altura;
        this.probabilidad = probabilidad;
        this.detectado = detectado;
    }

    public double getAltura() {
        return altura;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public boolean isDetectado() {
        return detectado;
    }
}
