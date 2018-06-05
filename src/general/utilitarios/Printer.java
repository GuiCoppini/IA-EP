package general.utilitarios;

import general.Dado;
import general.Regra;
import general.arvore.Branch;
import general.arvore.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Printer {
    public Map<String, List<Regra>> regrasDeClasses;

    public Printer() {
        this.regrasDeClasses = new HashMap<>();
    }

    public void printaRegras(Node raiz) {

        // popula o regrasDeClasses
        montaRegrasRecursivo(raiz, new Regra());

        // imprime as regras
        for (String classe : regrasDeClasses.keySet()) {
            System.out.print("IF ");
            for (Regra regra : regrasDeClasses.get(classe)) {
                System.out.print("(" + regra + ") || ");
            }
            System.out.print("THEN [" + classe + "]");
            System.out.println();
        }

    }


    private void montaRegrasRecursivo(Node raiz, Regra regraAtual) {
        Node atual = raiz;
        if (atual.ehFolha) {
            regrasDeClasses.computeIfAbsent(atual.nomeAtributo, k -> new ArrayList<>());
            List<Regra> listaNova = new ArrayList<>(regrasDeClasses.get(atual.nomeAtributo));
            listaNova.add(regraAtual);
            regrasDeClasses.put(atual.nomeAtributo, listaNova);
            return;
        }


        for (Branch aresta : atual.arestas) {
            Regra regraNova = regraAtual.copy();
            regraNova.add(atual.nomeAtributo, aresta.valorCondicao);
            montaRegrasRecursivo(aresta.filho, regraNova);
        }

    }

    public void printaArvore(Node raiz, Node atual, List<Dado> conjuntoDeTeste , boolean recursive) {
        if (atual.ehFolha) {
            double presAntiga = ID3Utils.testaAcuracia(conjuntoDeTeste , raiz);
            Node pai = atual.arestaPai.pai;
            Node krone = pai.klone();
            pai.ehFolha = true;
            pai.arestas = null;
            pai.nomeAtributo = ID3Utils.classeDeMaiorFrequencia(pai.arestaPai.conjuntoRecortado);
            double presNova = ID3Utils.testaAcuracia(conjuntoDeTeste , raiz);
            if(presNova >= presAntiga){
                printaArvore(raiz, pai , conjuntoDeTeste, false);
            }
            else pai.arestaPai.filho = krone;
            if(recursive == true)for(Branch aresta : pai.arestas) printaArvore(raiz,aresta.filho,conjuntoDeTeste , true);
            //mudamos o pai dele para ser uma folha da classe dominante do filho

            //testamos acuracia
            //se a acuracia for maior, podamos dnv
          //  ID3Utils.classeDeMaiorFrequencia();
            //        ID3Utils.testaAcuracia();
        }

    }
}