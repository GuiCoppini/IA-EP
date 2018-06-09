package general;

public class FrequenciaValorAtributo {
    public String valor = ""; //SOL
    public String nomeAtributo = ""; //Temperatura

    public int numeroDeOcorrencias = 0; //5
    public int[] distribuicaoPorClasse; // 3 Sim | 2 Nao
    public double entropia = 0; //0.60


    public FrequenciaValorAtributo(String valor, String att, int numeroDeOcorrencias) {
        this.valor = valor;
        this.nomeAtributo = att;
        this.numeroDeOcorrencias = numeroDeOcorrencias;
    }

    public void setDistribuicao(int[] distribuicaoPorClasse) {
        this.distribuicaoPorClasse = distribuicaoPorClasse;
    }

    public int[] getDistribuicao() {
        return distribuicaoPorClasse;
    }

    public void setEntropia(double entropia) {
        this.entropia = entropia;
    }

    public double getEntropia() {
        return this.entropia;
    }
}
