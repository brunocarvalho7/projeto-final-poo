package model;

import java.util.Arrays;

public class ClientePessoaJuridica extends Cliente {

	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;
	
	public ClientePessoaJuridica(String endereco, String[] telefones, String email, double rsLocado,
			String nomeFantasia, String razaoSocial, String cnpj) {
		super(endereco, telefones, email, rsLocado);
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public int getMaxLocacoesSimultaneas() {
		return 5;
	}
	
	@Override
	public String toString() {
		return "Cód.: " + getIdPessoa() + "\n"+
			   "Razão Social:" + getRazaoSocial() + "\n" +
			   "Nome Fantasia:" + getNomeFantasia() + "\n" +				
			   "CNPJ:" + getCnpj() + "\n" +
			   "Endereço: " + getEndereco() + "\n" +
			   "Telefones: " + Arrays.toString(getTelefones()) + "\n" +
			   "Email: " + getEmail() + "\n"; 
	}
}
