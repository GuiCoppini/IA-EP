package general.arvore;

import general.Dado;
import general.utilitarios.BaseDeConhecimento;

import java.util.List;

public class Branch {
    // classe aresta;
    public String valorCondicao = "";
    public Node pai;
    public Node filho;
    public List<Dado> conjuntoRecortado;

    public Branch(List<Dado> conjuntoAntigo, String valorCondicao, Node pai) {
        this.valorCondicao = valorCondicao;
        this.pai = pai;
        conjuntoRecortado = BaseDeConhecimento.filter(conjuntoAntigo, pai.nomeAtributo, valorCondicao);
    }

}
