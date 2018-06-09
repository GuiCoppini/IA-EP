package general;

import general.utilitarios.BaseDeConhecimento;
import general.utilitarios.KFoldCrossValidation;

public class Main {
    public static void main(String[] args) {
        KFoldCrossValidation kFold = new KFoldCrossValidation();
        kFold.roda(10, BaseDeConhecimento.parseCSV("adult_discretizado_menor.csv"));
        //DecisionTree decisionTree = new DecisionTree();
        // Printer p = new Printer();
        //p.printaRegras(decisionTree.criaSubArvore(BaseDeConhecimento.parseCSV()));
        // List<Dado> dados = BaseDeConhecimento.parseCSV();
        // Node raiz = decisionTree.criaSubArvore(dados, true, null);
        //p.printaArvore(raiz, raiz ,null , true); // falta o conjunto de validacao
        System.out.println();
    }
}
