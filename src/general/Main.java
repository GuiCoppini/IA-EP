package general;

import general.utilitarios.BaseDeConhecimento;
import general.utilitarios.KFoldCrossValidation;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Dado> todosOsDados = new ArrayList<>();
        KFoldCrossValidation.roda(10, BaseDeConhecimento.parseCSV());

    }

}
