package model;

public abstract class Cliente extends Pessoa {
	
	private static int seqID = 0;
	
	private double rsLocado;

	public Cliente() {
		super();
	}
		
	public Cliente(String endereco, String[] telefones, String email, double rsLocado) {
		super(++Cliente.seqID, endereco, telefones, email);
		this.rsLocado = rsLocado;
	}

	public double getRsLocado() {
		return rsLocado;
	}

	public void setRsLocado(double rsLocado) {
		this.rsLocado = rsLocado;
	}
/**
 * VER SE É MELHOR OCLOCAR INTERFACE
 * @return
 */
	public abstract int getMaxLocacoesSimultaneas();
}