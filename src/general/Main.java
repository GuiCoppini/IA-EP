package general;

import general.arvore.DecisionTree;
import general.utilitarios.BaseDeConhecimento;
import general.utilitarios.Printer;

public class Main {
    public static void main(String[] args) {
//        KFoldCrossValidation.roda(10, BaseDeConhecimento.parseCSV());

        DecisionTree decisionTree = new DecisionTree();
        Printer p = new Printer();
        p.printaRegras(decisionTree.criaArvore(BaseDeConhecimento.parseCSV()));
        System.out.println();
    }
}
