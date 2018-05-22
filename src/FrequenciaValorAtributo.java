public class FrequenciaValorAtributo {
    String valor = "";
    String nomeAtributo = "";

    int numero_de_ocorrencias = 0;
    int[] distribuicaoPorClasse;
    double entropia = 0;


    public FrequenciaValorAtributo(String valor, String att, int numero_de_ocorrencias) {
        this.valor = valor;
        this.nomeAtributo = att;
        this.numero_de_ocorrencias = numero_de_ocorrencias;
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
