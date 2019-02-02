package model;

import enums.Cargo;

public class Funcionario extends Pessoa{

	private static int seqID = 0;
	
	private String nome;
	private double salario;
	private Cargo cargo;

	public Funcionario() {
		super(++Funcionario.seqID);
	}

	public Funcionario(int id) {
		super(id);
	}
	
	public Funcionario(String endereco, String telefone, String email, String nome, double salario,
			Cargo cargo) {
		super(++Funcionario.seqID, endereco, telefone, email);
		this.nome = nome;
		this.salario = salario;
		this.cargo = cargo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return getIdPessoa() + "|" + nome + "|" + salario + "|" + cargo  + "|" + getEndereco() + "|"
				+ getTelefone() + "|" + getEmail();
	}
	
	public static Funcionario desserializarFuncionario(String[] s) {
		try {
			Funcionario.seqID = Integer.parseInt(s[0]);
			
			Funcionario f = new Funcionario(Integer.parseInt(s[0]));
			
			f.setIdPessoa(Integer.parseInt(s[0]));
			f.setNome(s[1]);
			f.setSalario(Double.parseDouble(s[2]));
			f.setCargo(Cargo.valueOf(s[3]));
			f.setEndereco(s[4]);
			f.setTelefone(s[5]);
			f.setEmail(s[6]);
			
			return f;
		} catch(Exception e) {
			System.out.println("Ocorreu um problema ao desserializar o seguinte funcionario: "+s);
			return null;
		}
	}
}