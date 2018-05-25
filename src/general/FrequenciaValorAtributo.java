package general;

public class FrequenciaValorAtributo {
    public String valor = "";
    public String nomeAtributo = "";

    public int numeroDeOcorrencias = 0;
    public int[] distribuicaoPorClasse;
    public double entropia = 0;


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
