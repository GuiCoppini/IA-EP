package general.arvore;

import general.Dado;
import general.utilitarios.ID3Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static general.utilitarios.BaseDeConhecimento.*;
import static general.utilitarios.ID3Utils.*;

public class DecisionTree {

    public static void main(String[] args) {
        DecisionTree decisionTree = new DecisionTree();
        List<Dado> conjunto = parseCSV();
        Node raiz = decisionTree.criaArvore(conjunto);
        System.out.println(ID3Utils.testaAcuracia(conjunto, raiz));
        System.out.println();
    }

    public Node criaArvore(List<Dado> conjunto) {
        Node raiz = criaNode(conjunto);
        System.out.println("Ta criando arvore com cunjunto de tamanho: " + conjunto.size());
        if (!raiz.ehFolha) {
            System.out.println("Criando arestas pro node: " + raiz.nomeAtributo);
            criaArestasNoNode(raiz, conjunto);
        } else {
            //eh folha
            System.out.println("Achou uma folha!");
            raiz.nomeAtributo = classeDeMaiorFrequencia(conjunto);
//            System.out.println(raiz.nomeAtributo);
        }
        return raiz;
    }

    private void criaArestasNoNode(Node raiz, List<Dado> conjunto) {
        Set<String> valores = analisaFrequencias(conjunto, raiz.nomeAtributo).keySet();

        for (String valor : valores) {
            Branch aresta = new Branch(conjunto, valor, raiz);
            raiz.arestas.add(aresta);
            aresta.filho = criaArvore(recortaConjunto(conjunto, aresta));
        }
    }

    private Node criaNode(List<Dado> conjunto) {
        Node raiz = new Node();
        String nomeClasse = NOME_CLASSE;
        Set<String> atributos = new HashSet<>(conjunto.get(0).atributos.keySet());
        raiz.nomeAtributos.addAll(atributos);

        if(atributos.size() > 1) {
            raiz.nomeAtributo = maiorGanhoDeInformacao(
                    nomeClasse,
                    inicializaFreq(conjunto, raiz.nomeAtributos),
                    analisaFrequencias(conjunto, nomeClasse).size(),
                    conjunto);
        }

        if (conjunto.get(0).atributos.size() <= 1 || entropiaConjunto(conjunto) == 0) {
            raiz.ehFolha = true;
            //System.out.println(raiz.nomeAtributo);
        }

        return raiz;
    }

    private List<Dado> recortaConjunto(List<Dado> conjuntoAntigo, Branch aresta) {
        List<Dado> conjuntoComValorCondicao = filter(conjuntoAntigo, aresta.pai.nomeAtributo, aresta.valorCondicao);
        return removeAttribute(conjuntoComValorCondicao, aresta.pai.nomeAtributo, aresta.pai.nomeAtributos);
    }
}
