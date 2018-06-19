package general;

import java.util.HashMap;
import java.util.Map;

public class Regra {
    public Map<String, String> valores;

    public double numeroDeOcorrencias = 0;

    public Regra() {
        this.valores = new HashMap<>();
    }

    public void add(String atributo, String valor) {
        this.valores.put(atributo, valor);
    }

    public Regra copy() {
        Regra retorno = new Regra();
        retorno.valores = new HashMap<>(this.valores);
        return retorno;
    }

    public void remove(String atributo) {
        this.valores.remove(atributo);
    }

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder();
        int i = 1;
        for (String atributo : valores.keySet()) {
            retorno
                    .append(atributo)
                    .append("=")
                    .append(valores.get(atributo));
            if (i != valores.keySet().size()) {
                retorno.append(" && ");
            }
            i++;

        }
        return retorno.toString();
    }
}
