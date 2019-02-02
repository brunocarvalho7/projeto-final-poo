package model;

public class ClientePessoaJuridica extends Cliente {

	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;
	
	public ClientePessoaJuridica(String endereco, String telefone, String email, double rsLocado,
			String nomeFantasia, String razaoSocial, String cnpj) {
		super(endereco, telefone, email, rsLocado);
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
			   "Telefones: " + getTelefone() + "\n" +
			   "Email: " + getEmail() + "\n"; 
	}
}
