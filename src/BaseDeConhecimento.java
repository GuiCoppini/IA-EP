import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDeConhecimento {

    private static String[] nomeAtributos;


    // mantem na lista final todos os Dados cujo dado.atributos.key tenha o valor value
    // Exemplo: filter(lista, "vento", "forte") mantem todos os dados com vento forte
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

    // devolve uma lista com todos os dados SEM o atributo key
    public static List<Dado> removeAttribute(List<Dado> lista, String atributo) {
        List<Dado> copy = new ArrayList<>(lista);
        for(Dado dado : copy) {
            dado.atributos.keySet().remove(atributo);
        }

        return copy;
    }

    public static List<Dado> parseCSV() {
        String csvFile = "adult_discretizado_v1.csv";
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
                    //  System.out.println("LINHA " + (count + 1));
//		            System.out.println("LINE LENGTH "+ linha.length);
                    count++;
                    Map<String, String> rexi = new HashMap<>();
                    for (int i = 0; i < colunas.length; i++) rexi.put(colunas[i], linha[i]);
                    lista.add(count, rexi);
                } else {
                    for (int i = 0; i < linha.length; i++) System.out.println(linha[i]);
                    //   System.out.println("END COLUNAS");
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
            base.add(new Dado(lista.get(i)));
        }
        setAtributos(colunas);
        return base;
    }

    private static void setAtributos(String[] colunas) {
        nomeAtributos = new String[colunas.length];
        int i = 0;
        for (String s : colunas) {
            nomeAtributos[i] = s;
            i++;
        }
    }


    public static String[] getAtributos() {
        return nomeAtributos;
    }

}