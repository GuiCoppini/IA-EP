package general.arvore;

import java.util.List;

import general.Dado;
import general.utilitarios.BaseDeConhecimento;

public class Branch {
    // classe aresta;
    String valor = "";
    Node pai;
    Node filho;
    String atributoPai;
    List<Dado> conjuntoRecortado;

    public Branch(List<Dado> conjuntoAntigo, String valor, String atributoPai, Node pai) {
        this.valor = valor;
        this.atributoPai = atributoPai;
        this.pai = pai;
        conjuntoRecortado = BaseDeConhecimento.filter(conjuntoAntigo, atributoPai, valor);
    }
}
