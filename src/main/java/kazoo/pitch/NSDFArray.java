package kazoo.pitch;

public class NSDFArray {
    private final double[] array;

    public NSDFArray(double[] array) {
        this.array = array;
    }

    public boolean esCrucePositivo(int posicion) {
        return this.array[posicion - 1] < 0 && 0 < this.array[posicion + 1];
    }

    public boolean esCruceNegativo(int posicion) {
        return this.array[posicion - 1] > 0 && 0 > this.array[posicion + 1];
    }

    public boolean esMayorQueElAnterior(int posicion) {
        return this.array[posicion] > this.array[posicion - 1];
    }

    public boolean esMayorOIgualQueElSiguiente(int posicion) {
        return this.array[posicion] >= this.array[posicion + 1];
    }

    public double[] interpolarParabolicamente(int indice) {
        double valorAnterior = this.array[indice - 1];
        double valorActual = this.array[indice];
        double valorSiguiente = this.array[indice + 1];
        double fondo = valorSiguiente + valorAnterior - 2 * valorActual;

        if (fondo == 0) {
            return new double[] {indice, valorActual};
        }

        double delta = valorAnterior - valorSiguiente;
        return new double[] { indice + delta / (2 * fondo), valorActual - Math.pow(delta, 2) / (8 * fondo) };
    }
}
