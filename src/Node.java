import java.util.ArrayList;
import java.util.List;

public class Node {
    String nomeAtributo;
    boolean folha;
    List<Branch> arestas = new ArrayList<>();

    public Node(String nomeAtributo) {
        this.nomeAtributo = nomeAtributo;
    }

    void criaNo() {
    }

    void criaFilho(List<Dado> conjunto) {
        //ele vai ter que recortar o conjunto
        //vai ter sua propria entropia
    }
}
