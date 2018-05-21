import java.util.HashMap;

public class DadoDeConhecimento {
	public String nome;
	public HashMap<String, String> att = new HashMap<String, String>();

	public DadoDeConhecimento(String nome, HashMap atributos) {
		this.nome = nome;
		this.att = atributos;
	}

	
	public String getName() {
		return nome;
	}
	
	public HashMap getAtt() {
		return att;
	}
}
