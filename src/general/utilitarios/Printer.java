package general.utilitarios;

import java.util.*;

import general.Regra;
import general.arvore.Branch;
import general.arvore.Node;

public class Printer {
    public Map<String, List<Regra>> regrasDeClasses;
    public Map<Regra, Double> ocorrenciasDaRegra;

    public Printer() {
        this.regrasDeClasses = new HashMap<>();
        this.ocorrenciasDaRegra = new HashMap<>();
    }

    public void limpaRegras() {
        this.regrasDeClasses.clear();
    }

    public void printaOcorrencias() {
        //Collections.sort(();
        for (Regra regra : ocorrenciasDaRegra.keySet()) {
            System.out.println("Regra: " + regra + " possui acc de " + regra.numeroDeOcorrencias);
        }
    }

    public void printaRegras(Node raiz) {

        // popula o regrasDeClasses
        montaRegrasRecursivo(raiz, new Regra());

        // imprime as regras
        for (String classe : regrasDeClasses.keySet()) {
            System.out.print("IF ");
//            for (Regra regra : regrasDeClasses.get(classe)) {
            for (int i = 0; i < regrasDeClasses.get(classe).size(); i++) {
                System.out.print("(" + regrasDeClasses.get(classe).get(i) + ") ");
                ocorrenciasDaRegra.put(regrasDeClasses.get(classe).get(i), regrasDeClasses.get(classe).get(i).numeroDeOcorrencias);
                if (i < regrasDeClasses.get(classe).size() - 1) {
                    System.out.print("|| ");
                }
            }
            System.out.print("THEN [" + classe + "]");
            System.out.println();
        }

        printaOcorrencias();

    }


    private void montaRegrasRecursivo(Node raiz, Regra regraAtual) {
        Node atual = raiz;
        if (atual.ehFolha) {
            System.out.println("O numero de registros nessa regra é: " + atual.arestaPai.conjuntoRecortado.size());
            regrasDeClasses.computeIfAbsent(atual.nomeAtributo, k -> new ArrayList<>());
            List<Regra> listaNova = new ArrayList<>(regrasDeClasses.get(atual.nomeAtributo));
            listaNova.add(regraAtual);
            regraAtual.numeroDeOcorrencias = ID3Utils.testaAcuracia(atual.arestaPai.conjuntoRecortado, atual);
            regrasDeClasses.put(atual.nomeAtributo, listaNova);
            return;
        }


        for (Branch aresta : atual.arestas) {
            Regra regraNova = regraAtual.copy();
            regraNova.add(atual.nomeAtributo, aresta.valorCondicao);
            montaRegrasRecursivo(aresta.filho, regraNova);
        }

    }

}
