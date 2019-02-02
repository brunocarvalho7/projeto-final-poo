package model;

public class ClientePessoaJuridica extends Cliente {

	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;
	
	public ClientePessoaJuridica() {
	}
	
	public ClientePessoaJuridica(int idPessoa) {
		super(idPessoa);
	}
	
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
		return "pj|" + getIdPessoa() + "|" + nomeFantasia + "|" + razaoSocial + "|" + cnpj + "|" + getRsLocado() + "|" +
				getEndereco() + "|" + getTelefone() + "|" + getEmail();
	}

	@Override
	public Cliente desserializarCliente(String[] s) {
		try {
			Cliente.seqID = Integer.parseInt(s[1]);
			
			ClientePessoaJuridica c = new ClientePessoaJuridica(Integer.parseInt(s[1]));
			
			c.setNomeFantasia(s[2]);
			c.setRazaoSocial(s[3]);
			c.setCnpj(s[4]);
			c.setRsLocado(Double.parseDouble(s[5]));
			c.setEndereco(s[6]);
			c.setTelefone(s[7]);
			c.setEmail(s[8]);
			
			return c;
		} catch(Exception e) {
			System.out.println("Ocorreu um problema ao desserializar o seguinte funcionario: "+s);
			return null;
		}
	}
}