import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {



    public static void main(String[] args) {
        List<DadoDeConhecimento> todosOsDados = new ArrayList<>();
        populaListaTotal(todosOsDados, 150); // enche o total com valores de teste

        // roda o cross fold e chama id3 la dentro
        kFoldCrossValidation(10, todosOsDados);
    }

    private static void kFoldCrossValidation(int k, List<DadoDeConhecimento> todosOsDados) {
        List<DadoDeConhecimento> totalCopy = new ArrayList<>(todosOsDados); // nao mudar a lista original
        Collections.shuffle(totalCopy); // embaralha o conjunto
        // quebra tudo em k folds
        List<List<DadoDeConhecimento>> foldsSeparados = divideListaEm(todosOsDados, k); // quebra a lista em K partes

        for(int i = 0; i<foldsSeparados.size(); i++) {
            // inicializa conjuntos de teste e treinamento
            List<DadoDeConhecimento> conjuntoDeTeste = new ArrayList<>();
            List<DadoDeConhecimento> conjuntoDeTreinamento = new ArrayList<>();
            for(int j=0; j<foldsSeparados.size(); j++) {
                if(i == j) conjuntoDeTeste.addAll(new ArrayList<>(foldsSeparados.get(i))); // new pra evitar alterar valores
                else conjuntoDeTreinamento.addAll(new ArrayList<>(foldsSeparados.get(j))); // pois sao passados por ponteiro
            }
            // VE COMO RODA NA ID3
            runId3(conjuntoDeTreinamento, conjuntoDeTeste);
        }
    }

    // TODO implementar ID3 aqui
    private static void runId3(List<DadoDeConhecimento> conjuntoDeTreinamento, List<DadoDeConhecimento> conjuntoDeTeste) {
        System.out.println("Treinando com o conjunto "+conjuntoDeTeste);
        System.out.println("Testando com o conjunto "+conjuntoDeTreinamento);
        System.out.println();
    }

    private static List<List<DadoDeConhecimento>> divideListaEm(List<DadoDeConhecimento> todosOsDados, int kFolds) {
        List<List<DadoDeConhecimento>> result = new ArrayList<>(); // onde vai ser retornado

        inicializaResult(kFolds, result); // deixa tudo cheio de new ArrayList

        int i = 0;
        while(i < todosOsDados.size()) {
            for (List<DadoDeConhecimento> fold : result) {
                if(i == todosOsDados.size()) break;
                fold.add(todosOsDados.get(i));
                i++;
            }
        }

        return result;
    }

    private static void inicializaResult(int kFolds, List<List<DadoDeConhecimento>> result) {
        for(int i = 0; i < kFolds; i++) {
            result.add(i, new ArrayList<DadoDeConhecimento>());
        }
    }


    private static void populaListaTotal(List<DadoDeConhecimento> todosOsDados, int tamanho) {
        for(int i =1; i<=tamanho; i++)
            todosOsDados.add(new DadoDeConhecimento("nome " + i));
    }
}
