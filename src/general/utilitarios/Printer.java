package general.utilitarios;

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
        printaRecursivo(raiz, new Regra());
    }

    private void printaRecursivo(Node raiz, Regra regraAtual) {
        Node atual = raiz;
        if(atual.ehFolha) {
            regrasDeClasses.computeIfAbsent(atual.nomeAtributo, k -> new ArrayList<>());
            List<Regra> listaNova = new ArrayList<>(regrasDeClasses.get(atual.nomeAtributo));
            listaNova.add(regraAtual);
            regrasDeClasses.put(atual.nomeAtributo, listaNova);
            return;
        }

        regraAtual = regraAtual.copy();
        if(!atual.ehFolha) {
            for(Branch aresta : atual.arestas) {
                regraAtual.add(atual.nomeAtributo, aresta.valorCondicao);
                printaRecursivo(aresta.filho, regraAtual);
            }
        }
        // ta bugadao eh nois bro usa debug
    }

}
