package model;

public abstract class Cliente extends Pessoa {
	
	public static int seqID = 0;
	
	private double rsLocado;

	public Cliente() {
		super(++Cliente.seqID);
	}
	
	public Cliente(int idPessoa) {
		super(idPessoa);
	}
		
	public Cliente(String endereco, String telefone, String email, double rsLocado) {
		super(++Cliente.seqID, endereco, telefone, email);
		this.rsLocado = rsLocado;
	}

	public double getRsLocado() {
		return rsLocado;
	}

	public void setRsLocado(double rsLocado) {
		this.rsLocado = rsLocado;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getIdPessoa() == ((Cliente)obj).getIdPessoa();
	}

	
	public abstract int getMaxLocacoesSimultaneas();
	
	public abstract String serializarCliente();
	
	public abstract Cliente desserializarCliente(String[] s);
}