package general;

import general.arvore.DecisionTree;
import general.arvore.Node;
import general.utilitarios.BaseDeConhecimento;
import general.utilitarios.Printer;

public class Main {
    public static void main(String[] args) {
//        KFoldCrossValidation.roda(10, BaseDeConhecimento.parseCSV());

        DecisionTree decisionTree = new DecisionTree();
        Printer p = new Printer();
        //p.printaRegras(decisionTree.criaArvore(BaseDeConhecimento.parseCSV()));
        Node raiz = decisionTree.criaArvore(BaseDeConhecimento.parseCSV());
        p.printaArvore(raiz, raiz ,null , true); // falta o conjunto de validacao
        System.out.println();
    }
}
