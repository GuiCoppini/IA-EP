package general.utilitarios;

import general.Dado;
import general.arvore.Branch;
import general.arvore.Node;

import java.util.ArrayList;
import java.util.List;

import static general.utilitarios.ID3Utils.classeDeMaiorFrequencia;
import static general.utilitarios.ID3Utils.testaAcuracia;
//import static general.utilitarios.ID3Utils.classeDeMaiorFrequencia;

public class Poda {
    Node raiz = null;
    Node atual = null;
    List<Dado> conjuntoDeTeste =  new ArrayList<>();
    List<Dado> conjTodo = new ArrayList<>();
    double accAnterior = 0;
    double accNova = 0;
    public boolean podou = false;

    public Poda(Node raiz, Node atual, List<Dado> conjuntoDeTeste , double accAnterior , List<Dado> conjTodo){
        this.raiz = raiz;
        this.atual = atual;
        this.conjuntoDeTeste = conjuntoDeTeste;
        this.accAnterior = accAnterior;
        this.conjTodo = conjTodo;
    }

    public void run() {
        Node copia = atual.copy(); // copiamos o no pq neh
        String classeMajor  = calculaClasseMajoritaria(atual);
        //for(Branch a : atual.arestas)a.pai = null;
        atual.arestas = new ArrayList<>();
        atual.ehFolha = true;
        atual.nomeAtributo = classeMajor;
        System.out.println("Calculando Acc nova sem o node "+copia.nomeAtributo);
        double accNova = testaAcuracia(conjuntoDeTeste , raiz);
        System.out.println("Testando Poda para node "+copia.nomeAtributo+"." +
                "Classe majoritaria "+classeMajor+"." +
                "Acuracia com o node = "+accAnterior+"." +
                " Acc sem o node = "+accNova+".");
        if(accNova >= accAnterior){ // podamos o jovem
            System.out.println("Node antigo podado "+copia.nomeAtributo+" -> virou node "+atual.nomeAtributo+"." +
                    "Acuracia nova: "+accNova);
            this.podou = true;
            this.accNova = accNova;
            return;
        }
        else{
            this.podou = false;
            atual.arestas = copia.arestas;
            for(int i = 0 ; i < atual.arestas.size() ; i++)atual.arestas.get(i).pai = atual;
            atual.ehFolha = false;
            atual.nomeAtributo = copia.nomeAtributo;
            //for(Branch a : atual.arestas)a.pai = atual;
        }
    }
    public boolean isPodou(){
        return podou;
    }
    public String calculaClasseMajoritaria(Node atual){
       if(atual.arestaPai != null) return classeDeMaiorFrequencia(atual.arestaPai.conjuntoRecortado);
       return classeDeMaiorFrequencia(conjTodo); // classe major do no raiz
    }
    public double getAcc(){
        return accNova;
    }
}
