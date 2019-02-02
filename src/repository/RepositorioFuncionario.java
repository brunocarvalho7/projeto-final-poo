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
import model.Funcionario;

public class RepositorioFuncionario implements IRepositorio<Funcionario>{

	private static RepositorioFuncionario instance;
	private Map<Integer, Funcionario> funcionarios = new HashMap<>();
	
	private RepositorioFuncionario() {
		lerDadosTxt();
	}
	
	public static RepositorioFuncionario getInstance() {
		if(instance == null)
			instance = new RepositorioFuncionario();
		
		return instance;
	}
	
	@Override
	public Funcionario salvar(Funcionario obj) {
		funcionarios.put(obj.getIdPessoa(), obj);
		gravarDadosTxt();
		
		return funcionarios.get(obj.getIdPessoa());
	}

	@Override
	public Funcionario remover(int id) {
		Funcionario aux = null;
		
		if(funcionarios.containsKey(id)) {
			aux = funcionarios.get(id);
			funcionarios.remove(id);
			gravarDadosTxt();
		}
		
		return aux;
	}
	
	@Override
	public Funcionario buscar(int id) {
		return funcionarios.get(id);
	}

	@Override
	public List<Funcionario> buscar(String chave) {
		List<Funcionario> aux = new ArrayList<>();
		
		for(Funcionario f : funcionarios.values()) {
			if(f.getNome().contains(chave))
				aux.add(f);
		}
		
		return aux;
	}

	@Override
	public Map<Integer, Funcionario> buscarTodos() {
		return funcionarios;
	}

	@Override
	public boolean gravarDadosTxt() {
		try {
			PrintStream out = new PrintStream(new File("funcionarios.txt"));
			
			for(Funcionario f : funcionarios.values()) 
				out.println(f.serializarCliente());
			
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar gravar os dados no txt: "+e.getStackTrace());
		}
		
		return true;
	}

	@Override
	public boolean lerDadosTxt() {
		funcionarios.clear();
		
		try {
			Scanner dados = new Scanner(new File("funcionarios.txt"));
			while(dados.hasNext()) {
				String[] s = dados.nextLine().split("\\|");
				
				Funcionario f = Funcionario.desserializarFuncionario(s);
				if(f != null)
					funcionarios.put(f.getIdPessoa(), f);
			}
			
			dados.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar ler os dados do txt: "+e.getStackTrace());
			return false;
		} 
		
		return true;
	}
}