package model;

import enums.TipoAutomovel;

public class Carro {

	private static int seqId = 0;
	
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
	
	public Carro() {
		this.id = ++Carro.seqId;
		this.disponivel = true;
	}
	
	public Carro(int id) {
		this.id = id;		
	}
	
	public Carro(String modelo, String chassi, String placa, int ano, boolean arCondicionado,
			boolean vidrosEletricos, boolean travasEletricas, boolean direcaoHidraulica, double valorDiaria,
			TipoAutomovel tipo) {
		this.id = ++Carro.seqId;
		this.modelo = modelo;
		this.chassi = chassi;
		this.placa = placa;
		this.ano = ano;
		this.arCondicionado = arCondicionado;
		this.vidrosEletricos = vidrosEletricos;
		this.travasEletricas = travasEletricas;
		this.direcaoHidraulica = direcaoHidraulica;
		this.valorDiaria = valorDiaria;
		this.tipo = tipo;
		this.disponivel = true;
	}
	
	public Carro(int id, String modelo, String chassi, String placa, int ano, boolean arCondicionado,
			boolean vidrosEletricos, boolean travasEletricas, boolean direcaoHidraulica, double valorDiaria,
			TipoAutomovel tipo) {
		this.id = id;
		this.modelo = modelo;
		this.chassi = chassi;
		this.placa = placa;
		this.ano = ano;
		this.arCondicionado = arCondicionado;
		this.vidrosEletricos = vidrosEletricos;
		this.travasEletricas = travasEletricas;
		this.direcaoHidraulica = direcaoHidraulica;
		this.valorDiaria = valorDiaria;
		this.tipo = tipo;
		this.disponivel = true;
	}
	
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
		return modelo;
	}

	public boolean equals(Carro obj) {
		return this.getId() == obj.getId();
	}
	
	public String serializarCarro() {
		return id + "|" + modelo + "|" + chassi + "|" + placa + "|" + ano + "|" + arCondicionado + "|" + vidrosEletricos
			+ "|" + travasEletricas + "|" + direcaoHidraulica + "|" + valorDiaria + "|" + tipo + "|" + disponivel;
	}
	
	public static Carro desserializarCarro(String[] s) {
		try {
			Carro.seqId = Integer.parseInt(s[0]);
			Carro c = new Carro(Integer.parseInt(s[0]));
			c.setModelo(s[1]);
			c.setChassi(s[2]);
			c.setPlaca(s[3]);
			c.setAno(Integer.parseInt(s[4]));
			c.setArCondicionado(Boolean.valueOf(s[5]));
			c.setVidrosEletricos(Boolean.valueOf(s[6]));
			c.setTravasEletricas(Boolean.valueOf(s[7]));
			c.setDirecaoHidraulica(Boolean.valueOf(s[8]));
			c.setValorDiaria(Double.parseDouble(s[9]));
			c.setTipo(TipoAutomovel.valueOf(s[10]));
			
			if(s[11].equals("true"))
				c.devolver();
			else
				c.alugar();
		
			return c;
		} catch(Exception e) {
			System.out.println("Ocorreu um problema ao desserializar o seguinte carro: "+s);
			return null;
		}
	}
}
