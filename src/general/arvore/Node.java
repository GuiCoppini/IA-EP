package general.arvore;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String nomeAtributo;
    public boolean ehFolha = true;
    public List<Branch> arestas = new ArrayList<>();
    public List<String> nomeAtributos = new ArrayList<>();
    public Branch arestaPai;

    public void PrintaMeusAtributos() {
        System.out.println("Atributos do nó " + nomeAtributo + " -------------------------");
        for (String attr : nomeAtributos) System.out.print(attr + " ");
        System.out.println();
        System.out.println("-----------------------------------------------");
    }

    public Node copy() {
        Node novo = new Node();
        novo.nomeAtributo = this.nomeAtributo;
        novo.ehFolha = this.ehFolha;
        novo.arestas = this.arestas;
        novo.nomeAtributos = this.nomeAtributos;
        novo.arestaPai = this.arestaPai;
        return novo;
    }
}
