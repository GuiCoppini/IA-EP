import java.util.HashMap;

public class No {
    String nome;
    boolean folha;
    HashMap<String, No> filhos;


    void criarNo(String nome, int entropia) {
        this.nome = nome;
        if (entropia == 0) folha = true;

    }

}
