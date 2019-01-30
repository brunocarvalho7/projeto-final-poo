package model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import enums.LocacaoStatus;

public class Locacao {

	private static int ultId = 0;

	private int idLocacao;
	private LocalDate dataLocacao;
	private LocalDate dataDevolucao;
	private Cliente cliente;
	private Funcionario atendente;
	private List<Carro> veiculos;
	private double valorLocacao;
	private LocacaoStatus status;
	
	public Locacao(LocalDate dataDevolucao, Cliente cliente, Funcionario atendente,
			List<Carro> veiculos, double valorLocacao, LocacaoStatus status) {
		this.idLocacao     = Locacao.ultId++;
		this.dataLocacao   = LocalDate.now();
		this.dataDevolucao = dataDevolucao;
		this.cliente       = cliente;
		this.atendente     = atendente;
		this.veiculos      = veiculos;
		this.valorLocacao  = calcularValorLocacao();
		this.status        = LocacaoStatus.ABERTO;
	}	
	
	public int getIdLocacao() {
		return idLocacao;
	}
	
	public LocalDate getDataLocacao() {
		return dataLocacao;
	}
	
	public void setDataLocacao(LocalDate dataLocacao) {
		this.dataLocacao = dataLocacao;
	}
	
	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}
	
	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Funcionario getAtendente() {
		return atendente;
	}
	
	public void setAtendente(Funcionario atendente) {
		this.atendente = atendente;
	}
	
	public List<Carro> getVeiculos() {
		return veiculos;
	}
	
	public void setVeiculos(List<Carro> veiculos) {
		this.veiculos = veiculos;
	}
	
	public double getValorLocacao() {
		return valorLocacao;
	}
	
	public void setValorLocacao(double valorLocacao) {
		this.valorLocacao = valorLocacao;
	}
	
	public LocacaoStatus getStatus() {
		return status;
	}
	
	public void setStatus(LocacaoStatus status) {
		this.status = status;
	}
	
	private double calcularValorLocacao() {
		double aux = 0;
		
		for(Carro c : veiculos)
			aux += c.getValorDiaria() * (Duration.between(getDataLocacao(), getDataDevolucao()).toDays());
		
		return aux;
	}
}
