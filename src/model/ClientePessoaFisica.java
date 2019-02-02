package model;

public class ClientePessoaFisica extends Cliente {

	private String nome;
	private String cpf;

	/**
	 * 
	 * @param endereco
	 * @param telefones
	 * @param email
	 * @param nome
	 * @param cpf
	 */
	public ClientePessoaFisica(String endereco, String telefone, String email, String nome,
			String cpf) {
		super(endereco, telefone, email, 0);
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
			   "Telefone: " + getTelefone() + "\n" +
			   "Email: " + getEmail() + "\n"; 
	}
}
