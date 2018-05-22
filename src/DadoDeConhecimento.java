import java.util.HashMap;
import java.util.Map;

public class DadoDeConhecimento {
	public String nome;
	public Map<String, String> att;

	public DadoDeConhecimento(String nome, Map<String,String> atributos) {
		this.nome = nome;
		this.att = atributos;
	}

	
	public String getName() {
		return nome;
	}
	
	public Map getAtt() {
		return att;
	}
}
