package model;

import java.util.Arrays;

public class ClientePessoaFisica extends Cliente {

	private String nome;
	private String cpf;
	
	public ClientePessoaFisica(String endereco, String[] telefones, String email, double rsLocado, String nome,
			String cpf) {
		super(endereco, telefones, email, rsLocado);
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int getMaxLocacoesSimultaneas() {
		return 3;
	}
	
	@Override
	public String toString() {
		return "Cód.: " + getIdPessoa() + "\n"+
			   "Nome:" + getNome() + "\n" +
			   "CPF:" + getCpf() + "\n" +
			   "Endereço: " + getEndereco() + "\n" +
			   "Telefones: " + Arrays.toString(getTelefones()) + "\n" +
			   "Email: " + getEmail() + "\n"; 
	}
}
