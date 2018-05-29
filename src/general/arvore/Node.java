package general.arvore;

import general.Dado;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String nomeAtributo;
    public boolean ehFolha = false;
    public List<Branch> arestas = new ArrayList<>();
    public List<String> nomeAtributos = new ArrayList<>();
    public Branch arestaPai;
}
