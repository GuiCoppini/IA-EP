package general.utilitarios;

public class Calculo {
    public static double log2(double n) {
        if (n == 0) return 0;
        return Math.log(n) / Math.log(2);
    }
}
