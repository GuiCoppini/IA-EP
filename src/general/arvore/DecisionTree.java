package general.arvore;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import general.Dado;

import static general.utilitarios.BaseDeConhecimento.NOME_CLASSE;
import static general.utilitarios.BaseDeConhecimento.filter;
import static general.utilitarios.BaseDeConhecimento.removeAttribute;
import static general.utilitarios.ID3Utils.*;

public class DecisionTree {

    public Node criaArvore(List<Dado> conjunto) {
        Node raiz = criaNode(conjunto);

        if(!raiz.ehFolha) {
            criaArestasNoNode(raiz, conjunto);
        }
        return raiz;
    }

    private String classeDeMaiorFrequencia(List<Dado> conjunto) {
        HashMap<String, Integer> mapFrequencias = analisaFrequencias(conjunto, NOME_CLASSE);
        double max = Collections.max(mapFrequencias.values());
        Set<String> chaves = mapFrequencias.keySet();
        for (String chave : chaves) {
            if (mapFrequencias.get(chave) == max)
                return chave;
        }
        throw new RuntimeException("Essa arvore nao tem swag bro");
    }

    private void criaArestasNoNode(Node raiz, List<Dado> conjunto) {
        Set<String> valores = analisaFrequencias(conjunto, raiz.nomeAtributo).keySet();

        for(String valor : valores) {
            Branch aresta = new Branch(conjunto, valor, raiz);
            raiz.arestas.add(aresta);
            aresta.filho = criaArvore(recortaConjunto(conjunto, aresta));
        }
    }

    private Node criaNode(List<Dado> conjunto) {
        Node raiz =  new Node();
        String nomeClasse = NOME_CLASSE;

        raiz.nomeAtributo = maiorGanhoDeInformacao(
                nomeClasse,
                inicializaFreq(conjunto),
                analisaFrequencias(conjunto, nomeClasse).size(),
                conjunto);

        if (conjunto.get(0).atributos.size() <= 1 || entropiaConjunto(conjunto) == 0) {
            raiz.ehFolha = true;
        }

        return raiz;
    }

    private List<Dado> recortaConjunto(List<Dado> conjuntoAntigo, Branch aresta) {
        List<Dado> conjuntoComValorCondicao = filter(conjuntoAntigo, aresta.pai.nomeAtributo, aresta.valorCondicao);
        return removeAttribute(conjuntoComValorCondicao, aresta.pai.nomeAtributo);
    }
}
