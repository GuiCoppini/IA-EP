import java.util.Map;

public class Dado {
	public String nome;
	public Map<String, String> map;

	public Dado(String nome, Map<String,String> atributos) {
		this.nome = nome;
		this.map = atributos;
	}

	// da o valor do atributo cujo nome/chave eh a key
	// ex: dado.getAttr("vento") => "forte"
	public String getAttr(String key) {
		return this.map.get(key);
	}
	
	public String getName() {
		return nome;
	}
	
	public Map getMap() {
		return map;
	}
}
