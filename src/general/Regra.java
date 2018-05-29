package general;

import java.util.HashMap;
import java.util.Map;

public class Regra {
    protected Map<String, String> valores;

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
}
