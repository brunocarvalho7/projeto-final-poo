package model;

import java.util.Arrays;

public abstract class Pessoa {
	
	private int idPessoa;
	private String endereco;
	private String[] telefones;
	private String email;
	
	public Pessoa() {
	}	
	
	public Pessoa(int idPessoa, String endereco, String[] telefones, String email) {
		this.idPessoa  = idPessoa;
		this.endereco  = endereco;
		this.telefones = telefones;
		this.email     = email;
	}
	
	public int getIdPessoa() {
		return idPessoa;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String[] getTelefones() {
		return telefones;
	}

	public void setTelefones(String[] telefones) {
		this.telefones = telefones;
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
			   "Telefones: " + Arrays.toString(telefones) + "\n" +
	           "Email: " + email;	           
	}
}