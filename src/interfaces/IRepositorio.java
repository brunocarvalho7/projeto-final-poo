package interfaces;

import java.util.List;
import java.util.Map;

public interface IRepositorio <T> {

	public T salvar(T obj);
	public T remover(int id);
	public List<T> buscar(String chave); 
	public Map<Integer, T> buscarTodos();
	public boolean gravarDadosTxt();
	public boolean lerDadosTxt();
	
}
