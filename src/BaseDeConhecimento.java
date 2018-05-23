import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDeConhecimento {

    private static String[] nome_atributos;


    public static List<Dado> filter(List<Dado> lista, String key, String value) {
        List<Dado> copy = new ArrayList<>(lista);
        List<Dado> result = new ArrayList<>();

        for (Dado dado : copy) {
            if (value.equals(dado.getAttr(key))) {
                result.add(dado);
            }
        }
        return result;
    }

    public static List<Dado> parseCSV() {
        String csvFile = "adult.csv";
        BufferedReader br = null;
        String line;
        String splitBy = ";";
        String[] colunas = null;
        List<Map<String, String>> lista = new ArrayList<>(); // lista de hashmap tunadassa
        int count = -1;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] linha = line.split(splitBy);
                if (colunas != null) {
                    System.out.println("LINHA " + (count + 1));
//		            System.out.println("LINE LENGTH "+ linha.length);
                    count++;
                    Map<String, String> rexi = new HashMap<>();
                    for (int i = 0; i < colunas.length; i++) rexi.put(colunas[i], linha[i]);
                    lista.add(count, rexi);
                } else {
                    for (int i = 0; i < linha.length; i++) System.out.println(linha[i]);
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


        for (String coluna : colunas) System.out.print(coluna + " ");
        System.out.println();
        List<Dado> base = new ArrayList<>(lista.size());
        for (int i = 0; i < lista.size(); i++) {
            base.add(new Dado("nome", lista.get(i)));
        }
        setAtributos(colunas);
        return base;
    }

    private static void setAtributos(String[] colunas) {
        nome_atributos = new String[colunas.length];
        int i = 0;
        for (String s : colunas) {
            nome_atributos[i] = s;
            i++;
        }
    }


    public static String[] getAtributos() {
        return nome_atributos;
    }
}