public class FrequenciaValorAtributo {
    String valor = null;
    String nome_atributo = null;

    //int numero_de_classes = BaseDeConhecimento.numero_de_classes();
    int numero_de_ocorrencias = 0;
    int[] distribuicaoPorClasse;


    public FrequenciaValorAtributo(String valor, String att, int numero_de_ocorrencias) {
        this.valor = valor;
        this.nome_atributo = att;
        this.numero_de_ocorrencias = numero_de_ocorrencias;
    }

    public void setDistribuicao(int[] distribuicaoPorClasse) {
        this.distribuicaoPorClasse = distribuicaoPorClasse;
    }

    public int[] getDistribuicao() {
        return distribuicaoPorClasse;
    }
}
