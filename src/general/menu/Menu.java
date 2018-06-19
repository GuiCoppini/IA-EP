package general.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import general.Dado;
import general.arvore.DecisionTree;
import general.arvore.Node;


import static general.utilitarios.BaseDeConhecimento.parseCSV;

import general.utilitarios.*;

import static general.utilitarios.ID3Utils.testaAcuracia;

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
        System.out.println("b) PlayTennis.csv");
        System.out.println("c) OUTRO");
        char choice = sc.nextLine().charAt(0);
        String nomeConjunto = "";
        switch (choice) {
            case 'a':
                nomeConjunto = "adult_discretizado_v1.csv";
                break;
            case 'b':
                nomeConjunto = "PlayTennis.csv";
                break;
            case 'c':
                System.out.println("Qual o nome do arquivo?");
                nomeConjunto = sc.nextLine();
        }

        List<Dado> conjunto;
        conjunto = parseCSV(nomeConjunto);

        System.out.println("Deseja rodar o K-Fold Cross Validation para o conjunto " + nomeConjunto + "? [y/n]");
        char kfold = sc.nextLine().charAt(0);
        if (kfold == 'y') {
            System.out.println("Quantos folds voce quer?");
            int k = sc.nextInt();
            System.out.println("Rodando um " + k + "-Fold Cross Validation para " + nomeConjunto);
            KFoldCrossValidation.roda(k, conjunto);
            return;
        }

        System.out.println("E o Holdout? [y/n]");
        char holdout = sc.nextLine().charAt(0);
        if (holdout == 'y') {
            Holdout.roda(parseCSV(nomeConjunto));
            //return;
        }

        System.out.println("Otimo, agora vamos comecar a montar a arvore para o conjunto " + nomeConjunto + "!");

        DecisionTree decisionTree = new DecisionTree();

        System.out.println("Montando a arvore para o conjunto " + nomeConjunto + ".");
//        }

        //Dividir conjunto em 3, para próximo passo
        System.out.println("Dividindo o conjunto em 3 conjuntos de mesmo tamanho: ");
        Collections.shuffle(conjunto); //embaralha para nao ficar viciado;
        List<List<Dado>> divideEm3 = KFoldCrossValidation.divideListaEm(conjunto, 3);
        List<Dado> conjuntoDeTreinamento = divideEm3.get(0);
        List<Dado> conjuntoDeTeste = divideEm3.get(1);
        List<Dado> conjuntoDeValidacao = divideEm3.get(2);

        Node raiz = decisionTree.criaArvore(conjuntoDeTreinamento); //Playtennis, colocar o conjunto completo pois é muito pequeno.

        System.out.println("As regras que representam a arvore antes da poda sao:");
        Printer printaRegras = new Printer();
        printaRegras.printaRegras(raiz);
        //printaRegras.ordenaEPrinta(conjuntoDeValidacao, raiz);
        printaRegras.limpaRegras();

        System.out.println("Deseja ver a árvore construida por nó? [y/n]");
        if ('y' == sc.nextLine().charAt(0)) {
            DecisionTree accNO = new DecisionTree();
            System.out.println("Testando por  nó com o conjunto de treinamento");
            accNO.criaArvoreComAcuracia(conjuntoDeTreinamento, conjuntoDeTreinamento);
            System.out.println("Testando por  nó com o conjunto de teste");
            DecisionTree accNO2 = new DecisionTree();
            accNO2.criaArvoreComAcuracia(conjuntoDeTreinamento, conjuntoDeTeste);
        }

        System.out.println("Deseja podar a arvore? [y/n]");
        if ('y' == sc.nextLine().charAt(0)) {
            podaEPrinta(nomeConjunto, raiz, printaRegras, conjuntoDeValidacao);
            System.out.println("Rankeando pós poda");
            printaRegras.ordenaEPrinta(conjuntoDeValidacao, raiz);
        }


    }


    private static void podaEPrinta(String nomeConjunto, Node raiz, Printer printaRegras, List<Dado> conjuntoValidacao) {
        List<Dado> conjTotal = parseCSV(nomeConjunto);
        Podador phodador = new Podador();
        boolean fazDnv = true;
        double accFinal = testaAcuracia(conjuntoValidacao, raiz);
        double accTeste = accFinal;
        int nosRemovidos = 0;
        List<Node> ListaDePais = new ArrayList<>(); // hashmap de pais
        List<Node> ListaDePaisSecundaria = new ArrayList<>();
        phodador.getListaPais(ListaDePais, raiz); // devolve todos os pais dos nos folhas sem repeticao
        System.out.println("Accuracia Inicial: " + accFinal);
        while (fazDnv) {
            for (int i = 0; i < ListaDePais.size(); i++) {
                Node atual = ListaDePais.get(i);
                Poda nova = new Poda(raiz, atual, conjuntoValidacao, accFinal, conjTotal);
                nova.run();
                if (nova.isPodou()) {
                    accFinal = nova.getAcc();
                    nosRemovidos++;
                    System.out.println(nosRemovidos + ";" + accFinal);
                    fazDnv = true;
                }
            }
            ListaDePais = ListaDePaisSecundaria;
            ListaDePaisSecundaria = new ArrayList<>();
            if (ListaDePais.isEmpty()) fazDnv = false;
        }
        System.out.println("Regras novas da arvore:");
        printaRegras.limpaRegras();
        printaRegras.printaRegras(raiz);
    }
}
