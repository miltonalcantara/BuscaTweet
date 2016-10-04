package br.com.miltonalcantara.buscatweet.modelo;

public class Tweet {
	
	private String nome;
	private String loginDoUsuario;
	private String imagemUsuario;
	private String texto;
	private String dataDoTwitte;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLoginDoUsuario() {
		return loginDoUsuario;
	}

	public void setLoginDoUsuario(String loginDoUsuario) {
		this.loginDoUsuario = loginDoUsuario;
	}

	public String getImagemUsuario() {
		return imagemUsuario;
	}

	public void setImagemUsuario(String imagemUsuario) {
		this.imagemUsuario = imagemUsuario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getDataDoTwitte() {
		return dataDoTwitte;
	}

	public void setDataDoTwitte(String dataDoTwitte) {
		this.dataDoTwitte = dataDoTwitte;
	}
}
