package general.menu;

import general.arvore.DecisionTree;
import general.arvore.Node;
import general.utilitarios.KFoldCrossValidation;
import general.utilitarios.Printer;

import java.util.Scanner;

import static general.utilitarios.BaseDeConhecimento.parseCSV;

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
        }

        System.out.println("Deseja rodar o K-Fold Cross Validation para o conjunto "+nomeConjunto+"? [y/n]");
        char kfold = sc.nextLine().charAt(0);
        if(kfold == 'y') {
            System.out.println("Quantos folds voce quer?");
            int k = sc.nextInt();
            System.out.println("Rodando um "+k+"-Fold Cross Validation para " + nomeConjunto);
            KFoldCrossValidation.roda(k, parseCSV(nomeConjunto));
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
        Node raiz = decisionTree.criaArvoreComAcuracia(parseCSV(nomeConjunto));

        System.out.println("As regras que representam a arvore antes da poda sao:");
        Printer printaRegras = new Printer();
        printaRegras.printaRegras(raiz);
    }
}
