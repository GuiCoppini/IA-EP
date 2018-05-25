import java.util.ArrayList;
import java.util.List;

public class Node {
    String nomeAtributo;
    boolean folha;
    List<Branch> arestas = new ArrayList<>();

    public Node(String nomeAtributo, int entropia) {
        this.nomeAtributo = nomeAtributo;
        if (entropia == 0) folha = true;
    }

}
