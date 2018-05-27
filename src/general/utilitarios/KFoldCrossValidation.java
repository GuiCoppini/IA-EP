package general.utilitarios;

import general.Dado;
import general.arvore.DecisionTree;
import general.arvore.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KFoldCrossValidation {

    public static void roda(int k, List<Dado> todosOsDados) {
        double[] acuracias = new double[k]; //Array para guardar a acuracia de cada rodada
        List<Dado> totalCopy = new ArrayList<>(todosOsDados); // nao mudar a lista original
        Collections.shuffle(totalCopy); // embaralha o conjunto
        // quebra tudo em k folds
        List<List<Dado>> foldsSeparados = divideListaEm(todosOsDados, k); // quebra a lista em K partes

        for (int i = 0; i < foldsSeparados.size(); i++) {
            // inicializa conjuntos de teste e treinamento
            List<Dado> conjuntoDeTeste = new ArrayList<>();
            List<Dado> conjuntoDeTreinamento = new ArrayList<>();
            for (int j = 0; j < foldsSeparados.size(); j++) {
                if (i == j) {
                    System.out.println("Folder de teste: Folder " + i);
                    conjuntoDeTeste.addAll(new ArrayList<>(foldsSeparados.get(i))); // new pra evitar alterar valores
                } else {
                    //System.out.println("Montando conjunto de treinamento #" + j);
                    conjuntoDeTreinamento.addAll(new ArrayList<>(foldsSeparados.get(j))); // pois sao passados por
                    // ponteiro
                }
            }
            DecisionTree decisionTree = new DecisionTree();
            System.out.println("Criando Arvore de Decisão utilizando ID3");
            Node raiz = decisionTree.criaArvore(conjuntoDeTreinamento);
            System.out.println("Testando árvore criada com o conjunto de teste");
            double acuraciaTeste = ID3Utils.testaAcuracia(conjuntoDeTeste, raiz);
            acuracias[i] = acuraciaTeste;
            System.out.println("Acuracia da arvore: " + acuracias[i]);
        }
        double[] erroVerdadeiro = taxaErroVerdadeiro(acuracias, todosOsDados.size());
        System.out.println("O erro verdadeiro do modelo, com uma confianca de 95%, estará entre: " + erroVerdadeiro[0] + " e " + erroVerdadeiro[1]);
    }

    private static double[] taxaErroVerdadeiro(double[] acuracias, int totalDeRegistros) {
        double erroModelo = calculaErroModelo(acuracias, totalDeRegistros);
        double[] confianca95 = new double[2];
        confianca95[0] = (erroMedio(acuracias, totalDeRegistros) - (1.96 * calculaErroModelo(acuracias, totalDeRegistros)));
        confianca95[1] = (erroMedio(acuracias, totalDeRegistros) + (1.96 * calculaErroModelo(acuracias, totalDeRegistros)));
        return confianca95;
    }

    private static double calculaErroModelo(double[] acuracias, int totalDeRegistros) {
        double erroMedio = erroMedio(acuracias, totalDeRegistros);
        double erroTotal = Math.sqrt((erroMedio * (1 - erroMedio)) / (double) totalDeRegistros);
        return erroTotal;
    }

    private static double erroMedio(double[] acuracias, int totalDeRegistros) {
        double erroMedio = 0.0;
        for (int i = 0; i < acuracias.length; i++) {
            erroMedio += (1 - acuracias[i]);
            erroMedio = erroMedio / acuracias.length; //media dos erros
        }
        return erroMedio;
    }

    private static List<List<Dado>> divideListaEm(List<Dado> todosOsDados, int kFolds) {
        List<List<Dado>> result = new ArrayList<>(); // onde vai ser retornado

        inicializaResult(kFolds, result); // deixa tudo cheio de new ArrayList

        int i = 0;
        while (i < todosOsDados.size()) {
            for (List<Dado> fold : result) {
                if (i == todosOsDados.size())
                    break;
                fold.add(todosOsDados.get(i));
                i++;
            }
        }

        return result;
    }

    private static void inicializaResult(int kFolds, List<List<Dado>> result) {
        for (int i = 0; i < kFolds; i++) {
            result.add(i, new ArrayList<Dado>());
        }
    }

	/*static void add(HashMap att, int i) {
		if (i % 2 == 0) {
			att.put("Sentimento", "Odio");
			att.put("Temperatura", "frio");
			att.put("Classe", "felipe");
		} else {
			att.put("Sentimento", "Terror");
			att.put("Temperatura", "quente");
			att.put("Classe", "smith");
		}

	}

	public static void populaListaTotal(List<general.Dado> todosOsDados, int tamanho) {
		for (int i = 1; i <= tamanho; i++) {
			HashMap<String, String> att = new HashMap<String, String>();
			add(att, i);
			todosOsDados.add(new general.Dado("nome", att));
		}
	}*/

}
