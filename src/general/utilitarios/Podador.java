package general.utilitarios;

import general.Dado;
import general.arvore.Branch;
import general.arvore.Node;

import java.util.*;

public class Podador {
    public HashMap<Integer ,Integer> sorteados = new HashMap<Integer , Integer>();
    public boolean checkaSePodou(ArrayList<Phoda> tredis){
        for (Phoda atual: tredis) {
           if(atual.isPodou() == true) return true;
        }
        return false;
    }
    public void getListaPais(Map<String, Node> ListaDePais , Node atual) {
        if (atual.ehFolha) {
            if(atual.arestaPai != null) { // qnd n for o raiz
                if (ListaDePais.get(atual.arestaPai.pai.nomeAtributo) == null)
                    ListaDePais.put(atual.arestaPai.pai.nomeAtributo, atual.arestaPai.pai); // nao adcionar duas vezes o mesmo no
            }
        }
        else if(atual != null){
            for(int i = 0; i < atual.arestas.size(); i++) getListaPais(ListaDePais , atual.arestas.get(i).filho);
        }
    }
    public List<Dado> getConjValidacao(List<Dado> conjTodo){
        List<Dado> ret = new ArrayList<>();
        int tamanhoTodo = conjTodo.size();
        int tamanhoValidacao = tamanhoTodo / 3 ;
        for(int i = 0 ; i < tamanhoValidacao ; i++){
            ret.add(conjTodo.get(SorteiaNovo(tamanhoTodo-1)));
        }
        return ret;
    }
    public int SorteiaNovo(int tamanhoTodo){
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((tamanhoTodo - 0) + 1) + 0;
        while(sorteados.containsKey(randomNum) == true)randomNum = rand.nextInt(((tamanhoTodo-1) - 0) + 1) + 0;
        sorteados.put(randomNum ,randomNum);
        return randomNum;
    }
    public void Imprime(Node raiz){
        if(raiz.ehFolha == true){
            //System.out.println("Pai do folha "+raiz.arestaPai.pai.nomeAtributo);
            for(Branch hue : raiz.arestas)System.out.println(" Lista atributos "+hue.valorCondicao);
            return;
        }
        else for(Branch aresta : raiz.arestas)Imprime(aresta.filho);
    }
}
