package general.utilitarios;

import java.util.ArrayList;
import java.util.List;

import general.Dado;
import general.arvore.DecisionTree;
import general.arvore.Node;
import static general.utilitarios.ID3Utils.testaAcuracia;
import static general.utilitarios.KFoldCrossValidation.divideListaEm;

public class Holdout {
    public static void roda(List<Dado> todosOsDados) {
        List<List<Dado>> listaDivididaEmTres = divideListaEm(todosOsDados, 3);

        List<Dado> conjuntoDeTeste = listaDivididaEmTres.get(2); // ultimo cara

        List<Dado> conjuntoDeTreinamento = new ArrayList<>();
        conjuntoDeTreinamento.addAll(listaDivididaEmTres.get(0));
        conjuntoDeTreinamento.addAll(listaDivididaEmTres.get(1));

        DecisionTree decisionTree = new DecisionTree();

        Node raiz = decisionTree.criaArvore(conjuntoDeTreinamento);

        testaAcuracia(conjuntoDeTeste, raiz);
    }
}
