import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ID3 {

	String[] nome_atributos = { "Sentimento", "Temperatura", "Classe" };
	// Tá estático para teste, precisa colocar o nome dos atributos na hora do CSV

	List<Map<String, Integer>> mapaDeFrequencia = new ArrayList<Map<String, Integer>>(); // Lista de HashMap

	public void runId3(List<DadoDeConhecimento> conjuntoDeTreinamento, List<DadoDeConhecimento> conjuntoDeTeste) {
		analisaValores(conjuntoDeTreinamento); // Calcular as frequencias de cada att.
		// Percorre a lista de hashmaps de frequencias e imprime cada um deles;
		// Cada atributo (coluna) terá seu próprio HashMap de frequencia;
		int i = 0;
		for (Map<String, Integer> hash : mapaDeFrequencia) {
			// Percorre mapa de frequencias e sai imprimindo
			Set<String> chaves = hash.keySet();
			for (String chave : chaves) {
				if (chave != null) {
					System.out.println("HashMap de " + nome_atributos[i]);
					System.out.println("Valor do atributo: " + chave + "\n Numero de aparicoes: " + hash.get(chave));
				}
			}
			System.out.println();
			i++;
		}

	}

	public void analisaValores(List<DadoDeConhecimento> conjunto) {
		List<Map<String, Integer>> myMap = new ArrayList<Map<String, Integer>>();
		for (int atributo_analisado = 0; atributo_analisado < nome_atributos.length; atributo_analisado++) {
			// Constroi um hashmap de frequencia para cada valor possível de cada atributo
			// e salva numa lista de Hashmaps;
			HashMap<String, Integer> frequencia = analisaFrequencias(conjunto, atributo_analisado);
			myMap.add(frequencia);
		}
		mapaDeFrequencia = myMap;
	}

	private HashMap analisaFrequencias(List<DadoDeConhecimento> conjunto, int numero_atributo) {
		HashMap<String, Integer> frequencia = new HashMap<String, Integer>();
		// guardar o numero de aparicoes de cada valor.
		for (int i = 0; i < conjunto.size(); i++) {
			Map<String, String> att = conjunto.get(i).getAtt();
			// iterando sobre os atributos, da linha;
			Set set = att.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();
				if (mentry.getKey().equals(nome_atributos[numero_atributo])) {
					// Entra no if para pegar a key que contem o atributo que eu to analisando
					// agora.
					// O valor do hash, vai virar chave do novo hash e o valor é o número de
					// aparicoes.
					String atributo_analisado = (String) mentry.getValue();

					if (frequencia.containsKey(atributo_analisado)) {
						frequencia.put(atributo_analisado, frequencia.get(atributo_analisado) + 1);
					} else {
						frequencia.put(atributo_analisado, 1);
					}
				}
			}
		}
		return frequencia;
	}
}
