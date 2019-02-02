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
	
	public ClientePessoaFisica() {
		super();
	}
	
	public ClientePessoaFisica(int idPessoa) {
		super(idPessoa);
	}
	
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
		return 2;
	}
	
	@Override
	public String toString() {
		return "pf|" + getIdPessoa() + "|" +nome + "|" + cpf + "|" + getRsLocado() + "|" + getEndereco() + "|" + 
				getTelefone() + "|" + getEmail();
	}

	@Override
	public Cliente desserializarCliente(String[] s) {
		try {
			Cliente.seqID = Integer.parseInt(s[1]);
			
			ClientePessoaFisica c = new ClientePessoaFisica(Integer.parseInt(s[1]));
			
			c.setNome(s[2]);
			c.setCpf(s[3]);
			c.setRsLocado(Double.parseDouble(s[4]));
			c.setEndereco(s[5]);
			c.setTelefone(s[6]);
			c.setEmail(s[7]);
			
			return c;
		} catch(Exception e) {
			System.out.println("Ocorreu um problema ao desserializar o seguinte funcionario: "+s);
			return null;
		}
	}
}
