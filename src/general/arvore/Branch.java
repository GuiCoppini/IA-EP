package general.arvore;

import java.util.List;

import general.Dado;
import general.utilitarios.BaseDeConhecimento;

public class Branch {
    // classe aresta;
    String valorCondicao = "";
    Node pai;
    Node filho;
    List<Dado> conjuntoRecortado;

    public Branch(List<Dado> conjuntoAntigo, String valorCondicao, Node pai) {
        this.valorCondicao = valorCondicao;
        this.pai = pai;
        conjuntoRecortado = BaseDeConhecimento.filter(conjuntoAntigo, pai.nomeAtributo, valorCondicao);
    }

}
