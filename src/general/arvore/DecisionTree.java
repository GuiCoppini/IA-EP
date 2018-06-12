package general.arvore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import general.Dado;
import static general.utilitarios.BaseDeConhecimento.NOME_CLASSE;
import static general.utilitarios.BaseDeConhecimento.filter;
import static general.utilitarios.BaseDeConhecimento.removeAttribute;
import static general.utilitarios.ID3Utils.analisaFrequencias;
import static general.utilitarios.ID3Utils.classeDeMaiorFrequencia;
import static general.utilitarios.ID3Utils.entropiaConjunto;
import static general.utilitarios.ID3Utils.inicializaFreq;
import static general.utilitarios.ID3Utils.maiorGanhoDeInformacao;
import static general.utilitarios.ID3Utils.testaAcuracia;

public class DecisionTree {

    Node raizPrincipal = null;
    int numeroDeNos = 0;
    List<Dado> conjuntoTeste = null;
//    public static void main(String[] args) {
//        DecisionTree decisionTree = new DecisionTree();
//        List<Dado> conjunto = parseCSV();
//        Node raiz = decisionTree.criaSubArvore(conjunto, true);
//        System.out.println(ID3Utils.testaAcuracia(conjunto, raiz));
//        System.out.println();
//    }

    public Node criaArvore(List<Dado> conjunto) {
        return criaSubArvore(conjunto, true, null, false);
    }

    public Node criaArvoreComAcuracia(List<Dado> conjunto, List<Dado> conjuntoDeTeste) {
        this.conjuntoTeste = conjuntoDeTeste;
        return criaSubArvore(conjunto, true, null, true);
    }

    public Node criaSubArvore(List<Dado> conjunto, boolean principal, Branch aresta, boolean acuraciaACadaNo) {
        Node raiz = criaNode(conjunto, principal, aresta, acuraciaACadaNo);
        //raiz.arestaPai = null;
//        System.out.println("Ta criando arvore com cunjunto de tamanho: " + conjunto.size());
        if (!raiz.ehFolha) {
//            System.out.println("Criando arestas pro node: " + raiz.nomeAtributo);
            criaArestasNoNode(raiz, conjunto, acuraciaACadaNo);
        } else {
            //eh folha
            raiz.nomeAtributo = classeDeMaiorFrequencia(conjunto);
        }
        return raiz;
    }

    private void criaArestasNoNode(Node raiz, List<Dado> conjunto, boolean acuraciaACadaNo) {
        Set<String> valores = analisaFrequencias(conjunto, raiz.nomeAtributo).keySet();

        for (String valor : valores) {
            Branch aresta = new Branch(conjunto, valor, raiz);
            raiz.arestas.add(aresta);
            aresta.filho = criaSubArvore(recortaConjunto(conjunto, aresta), false, aresta, acuraciaACadaNo);
            aresta.filho.arestaPai = aresta;
        }
    }

    private Node criaNode(List<Dado> conjunto, boolean principal, Branch aresta, boolean acuraciaACadaNo) {
        Node raiz = new Node();
        if (principal) {
            raizPrincipal = raiz; //salva a raiz principal da arvore
        } else {
            aresta.filho = raiz;
            aresta.filho.arestaPai = aresta;
        }
        String nomeClasse = NOME_CLASSE;
        Set<String> atributos = new HashSet<>(conjunto.get(0).atributos.keySet());
        raiz.nomeAtributos.addAll(atributos);
        raiz.nomeAtributo = classeDeMaiorFrequencia(conjunto);

        if (conjunto.get(0).atributos.size() > 1) {
            numeroDeNos++;
            if (acuraciaACadaNo && (numeroDeNos % 10 == 0 || numeroDeNos == 1)) { //soh calcula acuracia por nó caso queira.
                //System.out.println("Acuracia com " + numeroDeNos + " Nós: " + testaAcuracia(conjuntoTeste, raizPrincipal));
                System.out.println(numeroDeNos + ";" + testaAcuracia(conjuntoTeste, raizPrincipal));
            }
            raiz.ehFolha = false;
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
        // System.out.println(numeroDeNos);
        return raiz;

    }

    private List<Dado> recortaConjunto(List<Dado> conjuntoAntigo, Branch aresta) {
        List<Dado> conjuntoComValorCondicao = filter(conjuntoAntigo, aresta.pai.nomeAtributo, aresta.valorCondicao);
        return removeAttribute(conjuntoComValorCondicao, aresta.pai.nomeAtributo, aresta.pai.nomeAtributos);
    }
}
