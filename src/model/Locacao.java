package model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enums.LocacaoStatus;
import repository.RepositorioCarro;
import repository.RepositorioCliente;
import repository.RepositorioFuncionario;

public class Locacao{

	private static int seqID = 0;

	private int idLocacao;
	private LocalDate dataLocacao;
	private LocalDate dataDevolucao;
	private Cliente cliente;
	private Funcionario atendente;
	private List<Carro> veiculos = new ArrayList<>();
	private double valorLocacao;
	private LocacaoStatus status;
	
	public Locacao() {
		this.idLocacao   = ++Locacao.seqID;
		this.dataLocacao = LocalDate.now();
		this.status      = LocacaoStatus.ABERTO;
	}
	
	public Locacao(int idLocacao) {
		this.idLocacao = idLocacao;
	}
	
	public Locacao(LocalDate dataDevolucao, Cliente cliente, Funcionario atendente,
			List<Carro> veiculos) {
		this.idLocacao     = ++Locacao.seqID;
		this.dataLocacao   = LocalDate.now();
		this.dataDevolucao = dataDevolucao;
		this.cliente       = cliente;
		this.atendente     = atendente;
		this.veiculos      = veiculos;
		this.status        = LocacaoStatus.ABERTO;
		
		calcularValorLocacao();
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
	
	public void calcularValorLocacao() {
		double aux = 0;
		int qtdDias = Period.between(getDataLocacao(), getDataDevolucao()).getDays();	
		
		for(Carro c : veiculos) {
			if (qtdDias == 0) {
				aux += c.getValorDiaria();
			}else {
				aux += c.getValorDiaria() * (Period.between(getDataLocacao(), getDataDevolucao()).getDays());	
			}
		}
		this.valorLocacao = aux;
	}
	
	public boolean equals(Locacao obj) {
		return this.getIdLocacao() == obj.getIdLocacao();
	}

	@Override
	public String toString() {
		return idLocacao + " "+valorLocacao;
	}

	public String serializarCliente(){
		List<Integer> idsVeiculos = new ArrayList<>();
		
		for(Carro v:veiculos) 
			idsVeiculos.add(v.getId());
		
		return idLocacao + "|" + dataLocacao + "|" + dataDevolucao + "|" + cliente.getIdPessoa() + "|" + atendente.getIdPessoa() + "|" + idsVeiculos
				+ "|" + valorLocacao + "|" + status;
	}
	
	public static Locacao desserializarLocacao(String[] s) {
		try {
			Locacao.seqID = Integer.parseInt(s[0]);
			
			Locacao l = new Locacao(Integer.parseInt(s[0]));
			
			l.setDataLocacao(LocalDate.parse(s[1]));
			l.setDataDevolucao(LocalDate.parse(s[2]));
			l.setCliente(RepositorioCliente.getInstance().buscar(Integer.parseInt(s[3])));
			l.setAtendente(RepositorioFuncionario.getInstance().buscar(Integer.parseInt(s[4])));
			
			String[] aux = s[5].replace("]", "").replace("[", "").split(",");
				
			List<Carro> veiculos = new ArrayList<>();
			for(String sAux : aux) {
				veiculos.add(RepositorioCarro.getInstance().buscar(Integer.parseInt(sAux.trim())));
			}
			
			l.setVeiculos(veiculos);
			l.setValorLocacao(Double.parseDouble(s[6]));
			l.setStatus(LocacaoStatus.valueOf(s[7]));
			
			return l;
		} catch(Exception e) {
			System.out.println("Ocorreu um problema ao desserializar a seguinte locação: "+Arrays.toString(s));
			return null;
		}
	}
}