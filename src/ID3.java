import com.sun.xml.internal.rngom.parse.host.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ID3 {

    String[] nome_atributos = BaseDeConhecimento.getAtributos();

    //List<Map<String, Integer>> mapaDeFrequencia = new ArrayList<Map<String, Integer>>(); // Lista de HashMap


    public void runId3(List<Dado> conjuntoDeTreinamento, List<Dado> conjuntoDeTeste) {

        List<HashMap<String, FrequenciaValorAtributo>> frequencias = inicializaFreq(conjuntoDeTreinamento);
        //HashMap<String, FrequenciaValorAtributo>
        //inicializa N classes, uma para cada valor que os atributos podem ter. (faz para TODOS);
        // retornar um HASH <String,FrequenciaValorAtributo>. e ai quando eu colocar a key <valor do atributo>, ele retorna a classe daquele valor com as analises;
        int numero_de_classes = 2;
        for (HashMap<String, FrequenciaValorAtributo> hash : frequencias) {
            verificaDistribuicaoPorClasse(hash, conjuntoDeTreinamento, numero_de_classes);
            Set<String> chaves = hash.keySet();
            for (String chave : chaves) {
                System.out.println(hash.get(chave).nome_atributo);
                System.out.println(" Valor: " + hash.get(chave).valor + " Numero de ocorrencias: " + hash.get(chave).numero_de_ocorrencias + "( "
                        + hash.get(chave).getDistribuicao()[0] + ", " + hash.get(chave).getDistribuicao()[1] + ")");

            }
        }
        System.out.println();

    }

    private void verificaDistribuicaoPorClasse(HashMap<String, FrequenciaValorAtributo> frequencias, List<Dado> conjunto, int numero_de_classes) {
        // Analisar a distribuicao de classes para cada valor do atributo; colocar o resultado num vetor dentro do hash frequencia desse valor;
        Set<String> chaves = frequencias.keySet();
        String nomeClasse = nome_atributos[nome_atributos.length - 1];
        for (String chave : chaves) { // iterando sobre o hash
            if (!chaves.equals(nomeClasse)) {
                //System.out.println("Valor atributo: " + chave + "Total: " + frequencias.get(chave).numero_de_ocorrencias);
                List<Dado> filtro = BaseDeConhecimento.filter(conjunto, frequencias.get(chave).nome_atributo, chave);
                //filtrei apenas para linhas que possuem aquele valor;
                HashMap<String, Integer> hashfrequenciaClasse = analisaFrequencias(filtro, nome_atributos.length - 1);
                //chamar o analisa Frequencia para ver a frequencia apenas da última coluna (classe) ;
                Set<String> chavesClasse = hashfrequenciaClasse.keySet();
                int i = 0;
                int[] frequenciaPorClasse = new int[numero_de_classes]; //quantidade de classes que temos
                for (String chaveClasse : chavesClasse) {
                    frequenciaPorClasse[i] = hashfrequenciaClasse.get(chaveClasse);
                    // coloca no vetor o get(key) da frequencia por classe, onde contem o valor de cada classe;
                    // cada posicao é um valor diferente;
                    //System.out.println("Valor da classe : " + chaveClasse + "Valor: " + frequenciaPorClasse[i]); // >50k 70
                    i++;
                }
                //Preencher com 0, para caso tenha somente 1 distribuicao.
                if (i <= numero_de_classes - 1) {
                    while (i <= numero_de_classes - 1) {
                        frequenciaPorClasse[i] = 0;
                        i++;
                    }
                }
                frequencias.get(chave).setDistribuicao(frequenciaPorClasse);
            }
        }
    }

    private List<HashMap<String, FrequenciaValorAtributo>> inicializaFreq(List<Dado> conjunto) {
        List<HashMap<String, FrequenciaValorAtributo>> listaDeFreq = new ArrayList<HashMap<String, FrequenciaValorAtributo>>();
        HashMap<String, FrequenciaValorAtributo> HashValor = null;
        // criar a classe FrequenciaValorAtributo para cada valor que podemos ter.
        // ela guardará o valor do atributo (nome), o numero total de ocorrencias, o valor de cada classe distribuida.
        // vou criar a partir da lista de frequencias.
        List<Map<String, Integer>> mapaDeFrequencia = analisaValores(conjunto);
        int i = 0;
        for (Map<String, Integer> hash : mapaDeFrequencia) {
            HashValor = new HashMap<String, FrequenciaValorAtributo>();
            // Percorre mapa de frequencias e sai imprimindo
            Set<String> chaves = hash.keySet();
            for (String chave : chaves)
                if (chave != null) {
                    //to dentro do hashMap.
                    FrequenciaValorAtributo freq = new FrequenciaValorAtributo(chave, nome_atributos[i], hash.get(chave));
                    HashValor.put(chave, freq); // atribui ao Hash a chave (valor) e esse hash vai retornar a classe desse valor;
                    //System.out.println(freq.nome_atributo + " Valor: " + freq.valor + " Numero: " + freq.numero_de_ocorrencias);
                }
            listaDeFreq.add(HashValor); //separa os hashs por atributo.
            i++;
        }
        return listaDeFreq;
    }


    private List<Map<String, Integer>> analisaValores(List<Dado> conjunto) {
        List<Map<String, Integer>> myMap = new ArrayList<Map<String, Integer>>();
        for (int atributo_analisado = 0; atributo_analisado < nome_atributos.length; atributo_analisado++) {
            // Constroi um hashmap de frequencia para cada valor possível de cada atributo
            // e salva numa lista de Hashmaps;
            HashMap<String, Integer> frequencia = analisaFrequencias(conjunto, atributo_analisado);
            myMap.add(frequencia);
        }
        return myMap;
    }

    private HashMap analisaFrequencias(List<Dado> conjunto, int numero_atributo) {
        HashMap<String, Integer> frequencia = new HashMap<String, Integer>();
        // guardar o numero de aparicoes de cada valor.
        for (int i = 0; i < conjunto.size(); i++) {
            Map<String, String> att = conjunto.get(i).getMap();
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
