package model;

public abstract class Pessoa {
	
	private int idPessoa;
	private String endereco;
	private String telefone;
	private String email;
	
	public Pessoa() {
	}	
	
	public Pessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public Pessoa(int idPessoa, String endereco, String telefone, String email) {
		this.idPessoa  = idPessoa;
		this.endereco  = endereco;
		this.telefone = telefone;
		this.email     = email;
	}
	
	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Cód.: " + idPessoa + "\n"+
	           "Endereço: " + endereco + "\n" +
			   "Telefone: " + telefone + "\n" +
	           "Email: " + email;	           
	}
}