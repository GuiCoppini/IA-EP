package general.Arvore;

import java.util.List;

import general.Dado;

public class ArvoreDecisao {

    public void criaArvore(Node raiz, List<Dado> conjunto, String melhorAtributo) {
        raiz.nomeAtributo = melhorAtributo;

    }
}
