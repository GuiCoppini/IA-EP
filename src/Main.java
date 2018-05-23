import com.sun.swing.internal.plaf.basic.resources.basic_es;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BaseDeConhecimento base = new BaseDeConhecimento();
        List<Dado> todosOsDados = new ArrayList<>();
      //  KFoldCrossValidation.populaListaTotal(todosOsDados, 150); // enche o total com valores de teste
        // roda o cross fold e chama id3 la dentro
        KFoldCrossValidation.roda(10, base.parseCSV());
    }

}
