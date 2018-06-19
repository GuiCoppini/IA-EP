package general.utilitarios;

import java.util.*;

import com.sun.xml.internal.rngom.parse.host.Base;
import general.Dado;
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

    public Map<Regra, List<Dado>> analisaBase(List<Dado> conjunto) {
        Map<Regra, List<Dado>> conjuntosParaRegras = new HashMap<>();
        List<Dado> temp = null;
        //Collections.sort(();
        for (Regra regra : ocorrenciasDaRegra.keySet()) {
            temp = conjunto;
            for (String chave : regra.valores.keySet()) {
                temp = BaseDeConhecimento.filter(temp, chave, regra.valores.get(chave));
            }
            conjuntosParaRegras.put(regra, temp);
            //         System.out.println("Regra: " + regra + " possui acc de " + regra.numeroDeOcorrencias);
        }
        return conjuntosParaRegras;
    }

    public void ordenaEPrinta(List<Dado> conjunto, Node raiz) {
        Map<Regra, List<Dado>> base = analisaBase(conjunto);
        for (Regra analisado : base.keySet()) {
            System.out.println("IF " + analisado + " THEN " + pegaClasse(analisado) + ";" + ID3Utils.testaAcuracia(base.get(analisado), raiz) + ";" + base.get(analisado).size());
        }
    }

    private String pegaClasse(Regra analisado) {
        for (String classe : regrasDeClasses.keySet()) {
            for (int i = 0; i < regrasDeClasses.get(classe).size(); i++) {
                if (regrasDeClasses.get(classe).get(i).equals(analisado))
                    return classe;
            }
        }
        return "nao encontrado";
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

    }


    private void montaRegrasRecursivo(Node raiz, Regra regraAtual) {
        Node atual = raiz;
        if (atual.ehFolha) {
            // System.out.println("O numero de registros nessa regra é: " + atual.arestaPai.conjuntoRecortado.size());
            regrasDeClasses.computeIfAbsent(atual.nomeAtributo, k -> new ArrayList<>());
            List<Regra> listaNova = new ArrayList<>(regrasDeClasses.get(atual.nomeAtributo));
            listaNova.add(regraAtual);
            regraAtual.numeroDeOcorrencias = atual.arestaPai.conjuntoRecortado.size();
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
