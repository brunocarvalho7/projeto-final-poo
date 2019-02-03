package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import interfaces.IRepositorio;
import model.Carro;

public class RepositorioCarro implements IRepositorio<Carro>{

	private static RepositorioCarro instance;
	private Map<Integer, Carro> carros = new HashMap<>();
	
	private RepositorioCarro() {
		if(Files.exists(Paths.get("database/carros.txt"))){
			lerDadosTxt();
		}
	}
	
	public static RepositorioCarro getInstance() {
		if(instance == null)
			instance = new RepositorioCarro();
		
		return instance;
	}

	@Override
	public Carro salvar(Carro obj) {
		carros.put(obj.getId(), obj);
		gravarDadosTxt();
		
		return carros.get(obj.getId());
	}
	
	@Override
	public Carro remover(int id) {
		Carro aux = null;
		
		if(carros.containsKey(id)) {
			aux = carros.get(id);
			carros.remove(id);
			gravarDadosTxt();
		}
		
		return aux;
	}

	@Override
	public Carro buscar(int id) {
		return carros.get(id);
	}
	
	@Override
	public List<Carro> buscar(String chave) {
		List<Carro> aux = new ArrayList<>();
		
		for(Carro c : carros.values()) {
			if(c.getModelo().contains(chave))
				aux.add(c);
		}
		
		return aux;
	}
	
	@Override
	public Map<Integer, Carro> buscarTodos() {
		return carros;
	}
	
	public void alugarCarro(int idCarro) {
		if(carros.containsKey(idCarro)) {
			carros.get(idCarro).alugar();
		}
		
		gravarDadosTxt();
	}
	
	public void liberarCarro(int idCarro) {
		if(carros.containsKey(idCarro)) {
			carros.get(idCarro).devolver();
		}
		
		gravarDadosTxt();
	}

	@Override
	public boolean gravarDadosTxt() {
		try {
			PrintStream out = new PrintStream(new File("database/carros.txt"));
			
			for(Carro c : carros.values()) 
				out.println(c.serializarCarro());
			
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar gravar os dados no txt: "+e.getStackTrace());
		}
		
		return true;
	}
	
	@Override
	public boolean lerDadosTxt() {
		carros.clear();
		
		try {
			Scanner dados = new Scanner(new File("database/carros.txt"));
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