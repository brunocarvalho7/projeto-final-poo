package model;

import java.util.Arrays;

import enums.Cargo;

public class Funcionario extends Pessoa{

	private static int seqID = 0;
	
	private String nome;
	private double salario;
	private Cargo cargo;

	public Funcionario() {
		super();
	}

	public Funcionario(String endereco, String[] telefones, String email, String nome, double salario,
			Cargo cargo) {
		super(++Funcionario.seqID, endereco, telefones, email);
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
		return "Cód.: " + getIdPessoa() + "\n"+
			   "Nome:" + getNome() + "\n" +
			   "Endereço: " + getEndereco() + "\n" +
			   "Telefones: " + Arrays.toString(getTelefones()) + "\n" +
			   "Email: " + getEmail() + "\n" +
			   "Cargo: " + getCargo() + "\n" + 
			   "Salário: " + String.format("%.2f", getSalario());
	}
}
