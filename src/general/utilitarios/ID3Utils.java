package general.utilitarios;

import general.Dado;
import general.FrequenciaValorAtributo;
import general.arvore.Branch;
import general.arvore.Node;

import java.util.*;

import static general.utilitarios.BaseDeConhecimento.NOME_CLASSE;

public class ID3Utils {

    public static double testaAcuracia(List<Dado> conjunto, Node raiz) {
        double numeroAcertos = 0;
        for (Dado dado : conjunto) {
            String respostaEsperada = dado.getAttr(NOME_CLASSE);
            if (respondeDado(dado, raiz, conjunto).equals(respostaEsperada)) {
                numeroAcertos++;
            }
        }
        return numeroAcertos / (double) conjunto.size();
    }

    private static String respondeDado(Dado novo, Node raiz, List<Dado> conjunto) {
        Node atual = raiz;
        while (!atual.ehFolha) {
            for (Branch aresta : atual.arestas) {
                if (aresta.valorCondicao.equals(novo.getAttr(atual.nomeAtributo))) {
                    atual = aresta.filho;
                }
            }
//            if (atual.arestaPai != null) {
//                return classeDeMaiorFrequencia(atual.arestaPai.conjuntoRecortado);
//            }
            if (valorNaoPresenteNasArestas(atual, novo) && atual.arestaPai != null) {
                return classeDeMaiorFrequencia(atual.arestaPai.conjuntoRecortado);
            }
            if (atual == raiz) {
                return classeDeMaiorFrequencia(conjunto);
            }

        }
        return atual.nomeAtributo;
    }

    private static boolean valorNaoPresenteNasArestas(Node atual, Dado novo) {
        for (Branch branch : atual.arestas) {
            if (branch.valorCondicao.equals(novo.getAttr(atual.nomeAtributo))) {
                return false;
            }
        }
        return true; //valor nao presente nas arestas
    }

    public static String maiorGanhoDeInformacao(String classe, List<HashMap<String, FrequenciaValorAtributo>> frequencias, int numeroDeClasses, List<Dado> conjunto) {
        //retorna qual nome do melhor atributo
        HashMap<String, Double> ganho = ganhoDeInformacao(frequencias, numeroDeClasses, conjunto);
        ganho.remove(classe); //remove o nao utilizado (Propria classe);
        double max = Collections.max(ganho.values());
        Set<String> chaves = ganho.keySet();
        for (String chave : chaves) {
            if (ganho.get(chave) == max)
                return chave;
        }
        throw new RuntimeException("Problema no maiorGanhoDeInformação");
    }

    public static String classeDeMaiorFrequencia(List<Dado> conjunto) {
        HashMap<String, Integer> mapFrequencias = analisaFrequencias(conjunto, NOME_CLASSE);
        double max = Collections.max(mapFrequencias.values());
        Set<String> chaves = mapFrequencias.keySet();
        for (String chave : chaves) {
            if (mapFrequencias.get(chave) == max)
                return chave;
        }
        throw new RuntimeException("Problema no classeDeMaiorFreq");
    }

    private static HashMap<String, Double> ganhoDeInformacao(List<HashMap<String, FrequenciaValorAtributo>> frequencias, int numero_de_classes, List<Dado> conjunto) {
        //Recebe a lista de todos os atributos.
        //calcular ganho de informacao = entropia geral - somatoria da entropia do atributo para cada valor;
        // vou devolver um hashmap de ganhho de info. key eh o nome do atributo;
        HashMap<String, Double> ganhoDeInformacao = new HashMap<>();
        double entropiaGeral;
        FrequenciaValorAtributo valorAnalisado;
        double proporcaoValor;
        double ocorrencias;
        String nomeAtributo = "";
        for (HashMap<String, FrequenciaValorAtributo> hash : frequencias) {
            entropiaGeral = entropiaConjunto(conjunto);
            Set<String> chaves = hash.keySet();
            verificaDistribuicaoPorClasse(hash, conjunto, numero_de_classes);
            calculaEntropiaAtributo(hash, numero_de_classes); //Calcula entropia para cada atributo
            for (String chave : chaves) {
                valorAnalisado = hash.get(chave);
                nomeAtributo = valorAnalisado.nomeAtributo;
                ocorrencias = (double) (valorAnalisado.numeroDeOcorrencias);
                //percorro o hashmap subtraindo as entropias
                proporcaoValor = ocorrencias / conjunto.size();
                entropiaGeral -= proporcaoValor * valorAnalisado.getEntropia();
            }
            ganhoDeInformacao.put(nomeAtributo, entropiaGeral);
        }
        return ganhoDeInformacao;
    }

    public static double entropiaConjunto(List<Dado> conjunto) {
        String classe = NOME_CLASSE;
        HashMap<String, Integer> frequenciaClasse = analisaFrequencias(conjunto, classe);//vai me retornar a distribuicao da classe
        double entropia = 0;
        Set<String> chaves = frequenciaClasse.keySet();
        double numeroDeOcorrencias;
        double proporcao;
        for (String chave : chaves) {
            numeroDeOcorrencias = frequenciaClasse.get(chave); // me retorna a frequencia
            proporcao = numeroDeOcorrencias / conjunto.size();
            entropia -= proporcao * Calculo.log2(proporcao);
        }
        return entropia;
    }

    private static void calculaEntropiaAtributo(HashMap<String, FrequenciaValorAtributo> frequencias, int numeroDeClasses) {
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
            //navega pelo Hash atributos.
            valorAnalisado = frequencias.get(chave);
            for (int i = 0; i < numeroDeClasses; i++) {
                distribuicao = (double) valorAnalisado.getDistribuicao()[i];
                total = (double) (valorAnalisado.numeroDeOcorrencias);
                proporcao = (distribuicao / total);
                entropia -= (proporcao) * Calculo.log2(proporcao);
            }
            valorAnalisado.setEntropia(entropia); //guarda a entropia dele na classe ;
            //  System.out.println("");
        }
    }


    private static void verificaDistribuicaoPorClasse
            (HashMap<String, FrequenciaValorAtributo> frequencias, List<Dado> conjunto, int numeroDeClasses) {
        // Analisar a distribuicao de classes para cada valor do atributo; colocar o resultado num vetor dentro do hash frequencia desse valor;
        Set<String> chaves = frequencias.keySet();
        for (String chave : chaves) { // iterando sobre o hash

            //System.out.println("Valor atributo: " + chave + "Total: " + frequencias.get(chave).numeroDeOcorrencias);
            List<Dado> filtro = BaseDeConhecimento.filter(conjunto, frequencias.get(chave).nomeAtributo, chave);
            //filtrei apenas para linhas que possuem aquele valor;
            HashMap<String, Integer> hashfrequenciaClasse = analisaFrequencias(filtro, NOME_CLASSE);
            //chamar o analisa Frequencia para ver a frequencia apenas da última coluna (classe) ;
            Set<String> chavesClasse = hashfrequenciaClasse.keySet();
            int i = 0;
            int[] frequenciaPorClasse = new int[numeroDeClasses]; //quantidade de classes que temos
            for (String chaveClasse : chavesClasse) {
                frequenciaPorClasse[i] = hashfrequenciaClasse.get(chaveClasse);
                // coloca no vetor o get(key) da frequencia por classe, onde contem o valor de cada classe;
                // cada posicao é um valor diferente;
                //System.out.println("Valor da classe : " + chaveClasse + "Valor: " + frequenciaPorClasse[i]); // >50k 70
                i++;
            }
            //Preencher com 0, para caso tenha somente 1 distribuicao.
            if (i <= numeroDeClasses - 1) {
                while (i <= numeroDeClasses - 1) {
                    frequenciaPorClasse[i] = 0;
                    i++;
                }
            }
            frequencias.get(chave).setDistribuicao(frequenciaPorClasse);

        }
    }

    public static List<HashMap<String, FrequenciaValorAtributo>> inicializaFreq(List<Dado> conjunto, List<String> nomeAtributos) {
        List<HashMap<String, FrequenciaValorAtributo>> listaDeFreq = new ArrayList<>();
        HashMap<String, FrequenciaValorAtributo> hashValor;
        // criar a classe general.FrequenciaValorAtributo para cada valor que podemos ter.
        // ela guardará o valor do atributo (nome), o numero total de ocorrencias, o valor de cada classe distribuida.
        // vou criar a partir da lista de frequencias.
        List<Map<String, Integer>> mapaDeFrequencia = analisaValores(conjunto, nomeAtributos);
        int i = 0;
        for (Map<String, Integer> hash : mapaDeFrequencia) {
            hashValor = new HashMap<>();
            // Percorre mapa de frequencias e sai imprimindo
            Set<String> chaves = hash.keySet();
            for (String chave : chaves)
                if (chave != null) {
                    //to dentro do hashMap.
                    FrequenciaValorAtributo freq = new FrequenciaValorAtributo(chave, nomeAtributos.get(i), hash.get(chave));
                    hashValor.put(chave, freq); // atribui ao Hash a chave (valor) e esse hash vai retornar a classe desse valor;
                    //System.out.println(freq.nomeAtributo + " Valor: " + freq.valor + " Numero: " + freq.numeroDeOcorrencias);
                }
            listaDeFreq.add(hashValor); //separa os hashs por atributo.
            i++;
        }
        return listaDeFreq;
    }


    private static List<Map<String, Integer>> analisaValores(List<Dado> conjunto, List<String> nomeAtributos) {
        List<Map<String, Integer>> myMap = new ArrayList<>();
        for (int atributoAnalisado = 0; atributoAnalisado < nomeAtributos.size(); atributoAnalisado++) {
            // Constroi um hashmap de frequencia para cada valor possível de cada atributo
            // e salva numa lista de Hashmaps;
            Map<String, Integer> frequencia = analisaFrequencias(conjunto, nomeAtributos.get(atributoAnalisado));
            myMap.add(frequencia);
        }
        return myMap;
    }

    public static HashMap<String, Integer> analisaFrequencias(List<Dado> conjunto, String nomeAtributo) {
        HashMap<String, Integer> frequencia = new HashMap<>();
        // guardar o numero de aparicoes de cada valor.
        for (int i = 0; i < conjunto.size(); i++) {
            Map<String, String> att = new HashMap<>(conjunto.get(i).getAtributos());
            // iterando sobre os atributos, da linha;
            Set set = att.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                if (mentry.getKey().equals(nomeAtributo)) {
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
