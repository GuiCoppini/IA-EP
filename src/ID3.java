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
        //Uma List onde: cada hashmap representa os valores que a coluna (Atributo) tem. Os hashs guardam a classe de frequencias daquele valor;
        // inicializa N classes, uma para cada valor que os atributos podem ter. (faz para TODOS);
        int numero_de_classes = 2; // ta estatico mas pode mudar dps.
        HashMap<String, Double> ganho = ganhoDeInformacao(frequencias, numero_de_classes, conjuntoDeTreinamento);
        //passa a lista toda de atributos, e ele me retorna um hash com o nome do atributo como key, e qual o seu ganho de informacao;
        //Ganho de informacao faz tudo (calcula distribuicao por classe, entropia de todos os atributos e depois seu ganho de info.
        System.out.println("Printando entropias e distribuicao");
        for (HashMap<String, FrequenciaValorAtributo> hash : frequencias) {
            // para cada Hash, a gente verifica a distribuicao de classes dele. Adiciona essa distribuicao na classe;
            Set<String> chaves = hash.keySet();
            for (String chave : chaves) {
                System.out.println(hash.get(chave).nome_atributo);
                System.out.println(" Valor: " + hash.get(chave).valor + " Numero de ocorrencias: " + hash.get(chave).numero_de_ocorrencias + "( "
                        + hash.get(chave).getDistribuicao()[0] + ", " + hash.get(chave).getDistribuicao()[1] + ")");
                System.out.println("A entropia é: " + hash.get(chave).getEntropia());
            }
        }
        System.out.println();
        System.out.println("Printando ganhos de informacao");
        Set<String> chavesGanho = ganho.keySet();
        for (String chaveDoGanho : chavesGanho) {
            System.out.println("Ganho de informacao para o atributo: " + chaveDoGanho + " É : " + ganho.get(chaveDoGanho));
        }

    }

    private HashMap<String, Double> ganhoDeInformacao(List<HashMap<String, FrequenciaValorAtributo>> frequencias, int numero_de_classes, List<Dado> conjunto) {
        //Recebe a lista de todos os atributos.
        //calcular ganho de informacao = entropia geral - somatoria da entropia do atributo para cada valor;
        // vou devolver um hashmap de ganhho de info. key eh o nome do atributo;
        HashMap<String, Double> ganhoDeInformacao = new HashMap<String, Double>();
        double entropiaGeral;
        FrequenciaValorAtributo valorAnalisado;
        double entropiaTotalDoAtributo = 0;
        double proporcaoValor = 0;
        double ganho = 0;
        double ocorrencias = 0;
        String nomeAtributo = "";
        for (HashMap<String, FrequenciaValorAtributo> hash : frequencias) {
            entropiaGeral = calculaEntropiaGeral(conjunto);
            Set<String> chaves = hash.keySet();
            verificaDistribuicaoPorClasse(hash, conjunto, numero_de_classes);
            calculaEntropiaAtributo(hash, numero_de_classes); //Calcula entropia para cada atributo
            for (String chave : chaves) {
                valorAnalisado = hash.get(chave);
                nomeAtributo = valorAnalisado.nome_atributo;
                ocorrencias = (double) (valorAnalisado.numero_de_ocorrencias);
                //percorro o hashmap subtraindo as entropias
                proporcaoValor = ocorrencias / conjunto.size();
                entropiaGeral -= proporcaoValor * valorAnalisado.getEntropia();
            }
            ganhoDeInformacao.put(nomeAtributo, entropiaGeral);
        }
        return ganhoDeInformacao;
    }

    private double calculaEntropiaGeral(List<Dado> conjunto) {
        int classe = nome_atributos.length - 1;
        HashMap<String, Integer> frequenciaClasse = analisaFrequencias(conjunto, classe);//vai me retornar a distribuicao da classe
        double entropia = 0;
        Set<String> chaves = frequenciaClasse.keySet();
        double numeroDeOcorrencias = 0;
        double proporcao = 0;
        double distribuicao = 0;
        for (String chave : chaves) {
            numeroDeOcorrencias = frequenciaClasse.get(chave); // me retorna a frequencia
            proporcao = numeroDeOcorrencias / conjunto.size();
            entropia -= proporcao * Calculo.log2(proporcao);
        }
        return entropia;
    }

    private void calculaEntropiaAtributo(HashMap<String, FrequenciaValorAtributo> frequencias, int numero_de_classe) {
        //Calcular a entropia do atributo
        double entropia = 0;
        double proporcao = 0;
        double distribuicao;
        double total = 0;
        FrequenciaValorAtributo valorAnalisado;
        Set<String> chaves = frequencias.keySet();
        for (String chave :
                chaves) {
            entropia = 0;
            //navega pelo Hash map.
            valorAnalisado = frequencias.get(chave);
            for (int i = 0; i < numero_de_classe; i++) {
                distribuicao = (double) valorAnalisado.getDistribuicao()[i];
                total = (double) (valorAnalisado.numero_de_ocorrencias);
                proporcao = (distribuicao / total);
                entropia -= (proporcao) * Calculo.log2(proporcao);
            }
            valorAnalisado.setEntropia(entropia); //guarda a entropia dele na classe ;
            //  System.out.println("");
        }
    }


    private void verificaDistribuicaoPorClasse
            (HashMap<String, FrequenciaValorAtributo> frequencias, List<Dado> conjunto, int numero_de_classes) {
        // Analisar a distribuicao de classes para cada valor do atributo; colocar o resultado num vetor dentro do hash frequencia desse valor;
        Set<String> chaves = frequencias.keySet();
        String nomeClasse = nome_atributos[nome_atributos.length - 1];
        for (String chave : chaves) { // iterando sobre o hash

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

    private HashMap<String, Integer> analisaFrequencias(List<Dado> conjunto, int numero_atributo) {
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
