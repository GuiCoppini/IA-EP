package general;

import java.util.Map;

public class Dado {
	public Map<String, String> atributos;

	public Dado(Map<String,String> atributos) {
		this.atributos = atributos;
	}

	// da o valor do atributo cujo nome/chave eh a key
	// ex: dado.getAttr("vento") => "forte"
	public String getAttr(String key) {
		return this.atributos.get(key);
	}

	public Map getAtributos() {
		return atributos;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("{").append("atributos=").append(atributos).append("}").toString();
	}
}
