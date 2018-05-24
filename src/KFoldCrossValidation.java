import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class KFoldCrossValidation {

	static ID3 id3 = new ID3();

	public static void roda(int k, List<Dado> todosOsDados) {
		List<Dado> totalCopy = new ArrayList<>(todosOsDados); // nao mudar a lista original
		Collections.shuffle(totalCopy); // embaralha o conjunto
		// quebra tudo em k folds
		List<List<Dado>> foldsSeparados = divideListaEm(todosOsDados, k); // quebra a lista em K partes

		for (int i = 0; i < foldsSeparados.size(); i++) {
			// inicializa conjuntos de teste e treinamento
			List<Dado> conjuntoDeTeste = new ArrayList<>();
			List<Dado> conjuntoDeTreinamento = new ArrayList<>();
			for (int j = 0; j < foldsSeparados.size(); j++) {
				if (i == j)
					conjuntoDeTeste.addAll(new ArrayList<>(foldsSeparados.get(i))); // new pra evitar alterar valores
				else
					conjuntoDeTreinamento.addAll(new ArrayList<>(foldsSeparados.get(j))); // pois sao passados por
																							// ponteiro
			}
			// VE COMO RODA NA ID3
			System.out.println("Teste " + i);
			id3.runId3(todosOsDados, todosOsDados);
		}
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

	public static void populaListaTotal(List<Dado> todosOsDados, int tamanho) {
		for (int i = 1; i <= tamanho; i++) {
			HashMap<String, String> att = new HashMap<String, String>();
			add(att, i);
			todosOsDados.add(new Dado("nome", att));
		}
	}*/

}
