package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Carro;

public class Repositorio {

	private static Repositorio instance;
	private Map<Integer, Carro> carros = new HashMap<>();
	
	private Repositorio() {
		lerDadosTxt();
	}
	
	public static Repositorio getInstance() {
		if(instance == null)
			instance = new Repositorio();
		
		return instance;
	}

	public Carro salvarCarro(Carro carro) {
		carros.put(carro.getId(), carro);
		gravarDadosTxt();
		
		return carros.get(carro.getId());
	}
	
	public Carro removerCarro(int idCarro) {
		Carro aux = null;
		
		if(carros.containsKey(idCarro)) {
			aux = carros.get(idCarro);
			carros.remove(idCarro);
			gravarDadosTxt();
		}
		
		return aux;
	}

	public List<Carro> buscarCarro(String modelo) {
		List<Carro> aux = new ArrayList<>();
		
		for(Carro c : carros.values()) {
			if(c.getModelo().contains(modelo))
				aux.add(c);
		}
		
		return aux;
	}
	
	public Map<Integer, Carro> buscarCarros(){
		return carros;
	}

	public boolean gravarDadosTxt() {
		try {
			PrintStream out = new PrintStream(new File("carros.txt"));
			
			for(Carro c : carros.values()) 
				out.println(c);
			
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar gravar os dados no txt: "+e.getStackTrace());
		}
		
		return true;
	}
	
	public boolean lerDadosTxt() {
		carros.clear();
		
		try {
			Scanner dados = new Scanner(new File("carros.txt"));
			while(dados.hasNext()) {
				String[] s = dados.nextLine().split("\\|");
				
				Carro c = Carro.desserializarCarro(s);
				if(c != null)
					carros.put(c.getId(), c);
			}
			
			dados.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar ler os dados do txt: "+e.getStackTrace());
			return false;
		} 
		
		return true;
	}
}