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

        double acuraciaTeste = ID3Utils.testaAcuracia(conjuntoDeTeste, raiz);
        List<Double> erroVerdadeiro = taxaErroVerdadeiro(acuracias, todosOsDados.size(), acuraciaTeste);
        System.out.println("O erro verdadeiro do modelo, com uma confianca de 95%, estará entre: " + erroVerdadeiro.get(0) + " e " + erroVerdadeiro.get(1));
 
    }
    
    private static List<Double> taxaErroVerdadeiro(List<Double> acuracias, int totalDeRegistros , double erroMedio) {
        double erroModelo = calculaErroModelo(acuracias, totalDeRegistros, erroMedio);
        List<Double> confianca95 = new ArrayList<>();
        //double erroMedio = erroMedio(acuracias, totalDeRegistros);
        confianca95.add(erroMedio - (1.96 * erroModelo));
        confianca95.add(erroMedio + (1.96 * erroModelo));
        return confianca95;
    }

    private static double calculaErroModelo(List<Double> acuracias, int totalDeRegistros , double erroMedio) {
        //double erroMedio = erroMedio(acuracias, totalDeRegistros);
        double erroTotal = Math.sqrt((erroMedio * (1 - erroMedio)) / (double) totalDeRegistros);
        System.out.println("Erro Medio = " + erroMedio);
        System.out.println("Erro total = " + erroTotal);
        return erroTotal;
    }
    
}
