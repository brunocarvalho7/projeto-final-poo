package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import interfaces.IRepositorio;
import model.Locacao;

public class RepositorioLocacao implements IRepositorio<Locacao>{

	private static RepositorioLocacao instance;
	private Map<Integer, Locacao> locacoes = new HashMap<>();
	
	private RepositorioLocacao() {
		lerDadosTxt();
	}
	
	public static RepositorioLocacao getInstance() {
		if(instance == null)
			instance = new RepositorioLocacao();
		
		return instance;
	}
	
	@Override
	public Locacao salvar(Locacao obj) {
		locacoes.put(obj.getIdLocacao(), obj);
		gravarDadosTxt();
		
		return locacoes.get(obj.getIdLocacao());
	}

	@Override
	public Locacao remover(int id) {
		Locacao aux = null;
		
		if(locacoes.containsKey(id)) {
			aux = locacoes.get(id);
			locacoes.remove(id);
			gravarDadosTxt();
		}
		
		return aux;
	}

	@Override
	public Locacao buscar(int id) {
		return locacoes.get(id);
	}
	
	@Override
	public List<Locacao> buscar(String chave) {
		List<Locacao> aux = new ArrayList<>();
		
		for(Locacao l : locacoes.values()) {
			if(String.valueOf(l.getIdLocacao()).equals(chave))
				aux.add(l);
		}
		
		return aux;
	}

	@Override
	public Map<Integer, Locacao> buscarTodos() {
		return locacoes;
	}

	@Override
	public boolean gravarDadosTxt() {
		try {
			PrintStream out = new PrintStream(new File("locacoes.txt"));
			
			for(Locacao l : locacoes.values()) 
				out.println(l.serializarCliente());
			
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar gravar os dados no txt: "+e.getStackTrace());
		}
		
		return true;
	}

	@Override
	public boolean lerDadosTxt() {
		locacoes.clear();
		
		try {
			Scanner dados = new Scanner(new File("locacoes.txt"));
			while(dados.hasNext()) {
				String[] s = dados.nextLine().split("\\|");
				
				Locacao l = Locacao.desserializarLocacao(s);
				if(l != null)
					locacoes.put(l.getIdLocacao(), l);
			}
			
			dados.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar ler os dados do txt: "+e.getStackTrace());
			return false;
		} 
		
		return true;
	}
}