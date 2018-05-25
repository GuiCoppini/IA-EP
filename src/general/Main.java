package general;

import java.util.ArrayList;
import java.util.List;

import general.Utilitarios.BaseDeConhecimento;
import general.Utilitarios.KFoldCrossValidation;

public class Main {
    public static void main(String[] args) {
        List<Dado> todosOsDados = new ArrayList<>();
        //  general.Utilitarios.KFoldCrossValidation.populaListaTotal(todosOsDados, 150); // enche o total com valores de teste
        // roda o cross fold e chama id3 la dentro
        KFoldCrossValidation.roda(10, BaseDeConhecimento.parseCSV());
    }

}
