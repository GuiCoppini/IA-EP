import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDeConhecimento {

    public static void main(String[] args) {

        String csvFile = "adult.csv";
        BufferedReader br = null;
        String line;
        String splitBy = ";";
        String[] colunas = null;
        List<Map<String , String>> lista  = new ArrayList<>(); // lista de hashmap tunadassa
        int count = -1;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] linha = line.split(splitBy);
                if(colunas != null) {
                    System.out.println("LINHA "+(count+1));
//		            System.out.println("LINE LENGTH "+ linha.length);
                    count++;
                    Map<String, String> rexi = new HashMap<>();
                    for(int i = 0 ; i < colunas.length ; i++ )rexi.put(colunas[i], linha[i]);
                    lista.add(count , rexi);
                }

                else {
                    for (int i = 0 ; i < linha.length ; i++)System.out.println(linha[i]);
                    System.out.println("END COLUNAS");
                    colunas = linha;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        for(String coluna : colunas) System.out.print(coluna+" ");
        System.out.println();
        DadoDeConhecimento[] base = new DadoDeConhecimento[lista.size()];
        for(int i = 0 ; i < base.length ; i++){
            base[i] = new DadoDeConhecimento("nome" , lista.get(i));
        }
        for(int i = 0 ; i < base.length ; i++){
            for(int j = 0 ; j < base[i].getAtt().size() ; j++){
                System.out.print(base[i].getAtt().get(colunas[j]));
            }
            System.out.println();
        }
    }

}