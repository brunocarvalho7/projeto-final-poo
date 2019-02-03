package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Cliente;
import model.ClientePessoaFisica;
import model.ClientePessoaJuridica;
import repository.RepositorioCliente;

public class ViewClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Map<Integer, Cliente> clientes;

	public ViewClientes() {
		setResizable(false);
		initComponents();
		prencherDadosTabela();
	}
 
	public void prencherDadosTabela() {
		clientes = RepositorioCliente.getInstance().buscarTodos();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setNumRows(0); //Limpar dados atuais da tabela
		
		for(Cliente c:clientes.values()) {
			Vector<String> data = new Vector<>();
			
			data.add(String.valueOf(c.getIdPessoa()));
			data.add(c.getEndereco());
			data.add(c.getTelefone());
			data.add(c.getEmail());
			
			if(c instanceof ClientePessoaFisica) {
				data.add(1, ((ClientePessoaFisica) c).getNome());
				data.add(3, ((ClientePessoaFisica) c).getCpf());
			}else {
				data.add(1, ((ClientePessoaJuridica) c).getNomeFantasia());
				data.add(3, ((ClientePessoaJuridica) c).getCnpj());
			}
			
			model.addRow(data);
		}
	}

	private void removerClienteSelecionado(Cliente c) {
		if(c != null) {
			int x = JOptionPane.showConfirmDialog(null, "Deseja realmente remover o cliente "+c.getIdPessoa()+" ?", "REMOVER CLIENTE", 0);
		
			//Se o usuario tiver realmente confirmado...
			if(x == 0) {
				RepositorioCliente.getInstance().remover(c.getIdPessoa());
				prencherDadosTabela();
			}
		}
	}
	
	public void openViewClienteDetalhes(Cliente c) {
    	ViewClienteDetalhes viewClienteDetalhes = new ViewClienteDetalhes(c);
    	viewClienteDetalhes.setLocationRelativeTo(null); //Centralizar a tela
    	viewClienteDetalhes.setVisible(true);
    	
    	viewClienteDetalhes.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				prencherDadosTabela(); //Caso tenha ocorrido alguma mudança dos dados, isso vai atualizar os dados da tela
			}
		});
	}

	public Cliente getClienteSelecionado() {
		if(table.getSelectedRow() > -1) {
			Integer idCliente = Integer.parseInt((String) table.getModel().getValueAt(table.getSelectedRow(), 0));
    	
    		return clientes.get(idCliente);
		}
		return null;
	}
	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 655, 329);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 649, 261);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//Quando for ativado o clique duplo
				if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    
                    openViewClienteDetalhes(getClienteSelecionado());
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nome/Nome Fantasia", "Endere\u00E7o", "CPF/ CNPJ", "Telefone", "Email"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(95);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		scrollPane.setViewportView(table);
		
		JButton btnNovoCliente = new JButton("Cadastrar Cliente");
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewClienteDetalhes(null);
			}
		});
		btnNovoCliente.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnNovoCliente.setBounds(387, 266, 137, 23);
		getContentPane().add(btnNovoCliente);
		
		JButton btnRemoverClienteSelecionado = new JButton("Remover cliente");
		btnRemoverClienteSelecionado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerClienteSelecionado(getClienteSelecionado());
			}
		});
		btnRemoverClienteSelecionado.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnRemoverClienteSelecionado.setBounds(534, 266, 115, 23);
		getContentPane().add(btnRemoverClienteSelecionado);
	}
}
