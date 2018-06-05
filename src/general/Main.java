package general;

import general.arvore.Branch;
import general.arvore.DecisionTree;
import general.arvore.Node;
import general.utilitarios.BaseDeConhecimento;
import general.utilitarios.KFoldCrossValidation;
import general.utilitarios.Printer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        KFoldCrossValidation kFold = new KFoldCrossValidation();
        kFold.roda(10, BaseDeConhecimento.parseCSV());
        //DecisionTree decisionTree = new DecisionTree();
        // Printer p = new Printer();
        //p.printaRegras(decisionTree.criaArvore(BaseDeConhecimento.parseCSV()));
        // List<Dado> dados = BaseDeConhecimento.parseCSV();
        // Node raiz = decisionTree.criaArvore(dados, true, null);
        //p.printaArvore(raiz, raiz ,null , true); // falta o conjunto de validacao
        System.out.println();
    }
}
