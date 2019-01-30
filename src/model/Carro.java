package model;

import enums.TipoAutomovel;

public class Carro {

	private int id;
	private String modelo;
	private String chassi;
	private String placa;
	private int ano;
	private boolean arCondicionado;
	private boolean vidrosEletricos;
	private boolean travasEletricas;
	private boolean direcaoHidraulica;
	private double valorDiaria;
	private TipoAutomovel tipo;
	private boolean disponivel;

	public int getId() {
		return id;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getChassi() {
		return chassi;
	}
	
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public boolean isArCondicionado() {
		return arCondicionado;
	}
	
	public void setArCondicionado(boolean arCondicionado) {
		this.arCondicionado = arCondicionado;
	}
	
	public boolean isVidrosEletricos() {
		return vidrosEletricos;
	}
	
	public void setVidrosEletricos(boolean vidrosEletricos) {
		this.vidrosEletricos = vidrosEletricos;
	}
	
	public boolean isTravasEletricas() {
		return travasEletricas;
	}
	
	public void setTravasEletricas(boolean travasEletricas) {
		this.travasEletricas = travasEletricas;
	}
	
	public boolean isDirecaoHidraulica() {
		return direcaoHidraulica;
	}
	
	public void setDirecaoHidraulica(boolean direcaoHidraulica) {
		this.direcaoHidraulica = direcaoHidraulica;
	}
	
	public double getValorDiaria() {
		return valorDiaria;
	}
	
	public void setValorDiaria(double valorDiaria) {
		this.valorDiaria = valorDiaria;
	}
	
	public TipoAutomovel getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoAutomovel tipo) {
		this.tipo = tipo;
	}
	
	public boolean isDisponivel() {
		return disponivel;
	}
	
	public void alugar() {
		this.disponivel = false;
	}
	
	public void devolver() {
		this.disponivel = true;
	}

	@Override
	public String toString() {
		return "Código: " + id + "\n" + 
	           "Modelo: " + modelo + "\n" +
	           "Chassi: " + chassi + "\n" +
	           "Placa: " + placa + "\n" +
	           "Ano: " + ano + "\n" + 
	           "Tipo: " + tipo + "\n" +
	           "Ar-condicionado: " + (arCondicionado ? "Sim" : "Não") + "\n" +
	           "Vidros Eletricos: " + (vidrosEletricos ? "Sim" : "Não") + "\n" +
	           "Travas Elétricas" + (travasEletricas ? "Sim" : "Não") + "\n" +
	           "Direção Hidraulica: " + (direcaoHidraulica ? "Sim" : "Não") + "\n" +
	           "Valor da Diária: " + String.format("%.2f", valorDiaria) + 
	           "Disponível: "  + (disponivel ? "Sim" : "Não");
	}
}
