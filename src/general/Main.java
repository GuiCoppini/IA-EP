package general;

import general.utilitarios.BaseDeConhecimento;
import general.utilitarios.KFoldCrossValidation;

public class Main {
    public static void main(String[] args) {
        KFoldCrossValidation.roda(10, BaseDeConhecimento.parseCSV());
    }

}
