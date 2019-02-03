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
import model.Cliente;
import model.ClientePessoaFisica;
import model.ClientePessoaJuridica;

public class RepositorioCliente implements IRepositorio<Cliente>{

	private static RepositorioCliente instance;
	private Map<Integer, Cliente> clientes = new HashMap<>();
	
	private RepositorioCliente() {
		if(Files.exists(Paths.get("database/clientes.txt"))){
			lerDadosTxt();
		}
	}
	
	public static RepositorioCliente getInstance() {
		if(instance == null)
			instance = new RepositorioCliente();
		
		return instance;
	}
	
	public Cliente buscar(int idCliente) {
		return clientes.get(idCliente);
	}
	
	@Override
	public Cliente salvar(Cliente obj) {
		clientes.put(obj.getIdPessoa(), obj);
		gravarDadosTxt();
		
		return clientes.get(obj.getIdPessoa());
	}

	@Override
	public Cliente remover(int id) {
		Cliente aux = null;
		
		if(clientes.containsKey(id)) {
			aux = clientes.get(id);
			clientes.remove(id);
			gravarDadosTxt();
		}
		
		return aux;
	}

	@Override
	public List<Cliente> buscar(String chave) {
		List<Cliente> aux = new ArrayList<>();
		
		for(Cliente c : clientes.values()) {
			if(c instanceof ClientePessoaFisica) {
				if(((ClientePessoaFisica)c).getNome().contains(chave)){
					aux.add(c);
				}
			}else if(c instanceof ClientePessoaJuridica) {
				if(((ClientePessoaJuridica)c).getNomeFantasia().contains(chave)){
					aux.add(c);
				}
			}
		}
		return aux;
	}

	@Override
	public Map<Integer, Cliente> buscarTodos() {
		return clientes;
	}

	@Override
	public boolean gravarDadosTxt() {
		try {
			PrintStream out = new PrintStream(new File("database/clientes.txt"));
			
			for(Cliente f : clientes.values()) 
				out.println(f.serializarCliente());
			
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar gravar os dados no txt: "+e.getStackTrace());
		}
		
		return true;
	}

	@Override
	public boolean lerDadosTxt() {
		clientes.clear();
		
		try {
			Scanner dados = new Scanner(new File("database/clientes.txt"));
			while(dados.hasNext()) {
				String[] s = dados.nextLine().split("\\|");
				
				Cliente f;
				if(s[0].equals("pf")) {
					f = new ClientePessoaFisica().desserializarCliente(s);
				}else {
					f = new ClientePessoaJuridica().desserializarCliente(s);
				}
				
				if(f != null)
					clientes.put(f.getIdPessoa(), f);
			}
			
			dados.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar ler os dados do txt: "+e.getStackTrace());
			return false;
		} 
		
		return true;
	}
}