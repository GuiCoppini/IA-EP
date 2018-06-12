package general.menu;

import general.Dado;
import general.arvore.DecisionTree;
import general.arvore.Node;
import general.utilitarios.KFoldCrossValidation;
import general.utilitarios.Poda;
import general.utilitarios.Podador;
import general.utilitarios.Printer;

import java.util.*;

import static general.utilitarios.BaseDeConhecimento.parseCSV;
import static general.utilitarios.ID3Utils.testaAcuracia;
import static java.lang.Thread.sleep;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ola, bem vindo ao algoritmo ID3 de " +
                "Guilherme Coppini Pavani, " +
                "Lucas Bordinhon Capalbo, " +
                "Matheus Statkevicius Silva e " +
                "Silvio Novaes Lira Junior!");
        System.out.println("Para escolher as opcoes, digite a letra correspondente e de enter.");
        System.out.println("Deseja construir a ID3 para qual dataset?");
        System.out.println("a) adult_discretizado_v1.csv");
        System.out.println("b) adult_discretizado_menor.csv");
        System.out.println("c) adult_sem_fnlwgt.csv");
        System.out.println("d) PlayTennis.csv");
        System.out.println("e) OUTRO");
        char choice = sc.nextLine().charAt(0);
        String nomeConjunto = "";
        switch(choice) {
            case 'a':
                nomeConjunto = "adult_discretizado_v1.csv";
                break;
            case 'b':
                nomeConjunto = "adult_discretizado_menor.csv";
                break;
            case 'c':
                nomeConjunto = "adult_sem_fnlwgt.csv";
                break;
            case 'd':
                nomeConjunto = "PlayTennis.csv";
                break;
            case 'e':
                System.out.println("Qual o nome do arquivo?");
                nomeConjunto = sc.nextLine();
        }

        List<Dado> conjunto;
        conjunto = parseCSV(nomeConjunto);

        System.out.println("Deseja rodar o K-Fold Cross Validation para o conjunto "+nomeConjunto+"? [y/n]");
        char kfold = sc.nextLine().charAt(0);
        if(kfold == 'y') {
            System.out.println("Quantos folds voce quer?");
            int k = sc.nextInt();
            System.out.println("Rodando um "+k+"-Fold Cross Validation para " + nomeConjunto);
            KFoldCrossValidation.roda(k, conjunto);
            return;
        }

        System.out.println("Otimo, agora vamos comecar a montar a arvore para o conjunto "+nomeConjunto+"!");

        DecisionTree decisionTree = new DecisionTree();

//        System.out.println("Deseja printar a acuracia a cada no? [y/n] (Isso ira interferir no tempo de execucao do algoritmo.)");
//        char bool = sc.nextLine().charAt(0);
//        boolean acuraciaACadaNo = bool == 'y';
//        if(acuraciaACadaNo) {
        System.out.println("Montando a arvore para o conjunto "+ nomeConjunto +".");
//        }
        Node raiz = decisionTree.criaArvoreComAcuracia(conjunto);

        System.out.println("As regras que representam a arvore antes da poda sao:");
        Printer printaRegras = new Printer();
        printaRegras.printaRegras(raiz);
        System.out.println("Deseja podar a arvore? [y/n]");
        if('y' == sc.nextLine().charAt(0)) {
            podaEPrinta(nomeConjunto, raiz, printaRegras);
        }
    }

    private static void podaEPrinta(String nomeConjunto, Node raiz, Printer printaRegras) {
        List<Dado> conjTotal = parseCSV(nomeConjunto);
        Podador phodador = new Podador();
        List<Dado> conjuntodeTeste = phodador.getConjValidacao(conjTotal); // chama teste mas eh o de validacao
        List<Dado> conjuntodeTesteReal = phodador.getConjValidacao(conjTotal); // esse eh o de teste msm
        boolean fazDnv = true;
        double accFinal = testaAcuracia(conjuntodeTesteReal, raiz);
        double accTeste = accFinal;
        int nosRemovidos = 0;
        List<Node> ListaDePais = new ArrayList<>(); // hashmap de pais
        List<Node> ListaDePaisSecundaria = new ArrayList<>();
        phodador.getListaPais(ListaDePais, raiz); // devolve todos os pais dos nos folhas sem repeticao
        System.out.println("Accuracia Inicial: " + accFinal);
         while(fazDnv) {
            for (int i = ListaDePais.size()-1 ; i >= 0 ; i--) {
                Node atual = ListaDePais.get(i);
                System.out.println(" Pai  = " + atual.nomeAtributo);
                Poda nova = new Poda(raiz, atual, conjuntodeTeste, accFinal, conjTotal);
                nova.run();
                if (nova.isPodou()) {
                    accFinal = nova.getAcc();
                    nosRemovidos++;
                    fazDnv = true;
                    if (atual.arestaPai != null)
                        ListaDePaisSecundaria.add(atual.arestaPai.pai);
                }
            }

            ListaDePais = ListaDePaisSecundaria;
            ListaDePaisSecundaria = new ArrayList<>();
            if(ListaDePais.isEmpty()) fazDnv = false;
        }
        System.out.println("As regras que representam a NOVA arvore depois da poda sao:");
        printaRegras.printaRegras(raiz);

        System.out.println("A acuracia final da arvore eh: " + testaAcuracia(conjuntodeTesteReal , raiz)+" A incial era "+accTeste+" Nos removidos "+nosRemovidos);
    }
}
