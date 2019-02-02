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
import model.Funcionario;
import repository.RepositorioFuncionario;

public class ViewFuncionarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Map<Integer, Funcionario> funcionarios;

	public ViewFuncionarios() {
		setResizable(false);
		initComponents();
		prencherDadosTabela();
	}
 
	public void prencherDadosTabela() {
		funcionarios = RepositorioFuncionario.getInstance().buscarTodos();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setNumRows(0); //Limpar dados atuais da tabela
		
		for(Funcionario f:funcionarios.values()) {
			Vector<String> data = new Vector<>();
			
			data.add(String.valueOf(f.getIdPessoa()));
			data.add(f.getNome());
			data.add(f.getCargo().name());
			data.add(f.getEndereco());
			data.add(f.getTelefone());
			data.add(f.getEmail());
			
			model.addRow(data);
		}
	}

	private void removerFuncionarioSelecionado(Funcionario f) {
		if(f != null) {
			int x = JOptionPane.showConfirmDialog(null, "Deseja realmente remover o funcionario "+f.getNome()+" ?", "REMOVER FUNCIONARIO", 0);
		
			//Se o usuario tiver realmente confirmado...
			if(x == 0) {
				RepositorioFuncionario.getInstance().remover(f.getIdPessoa());
				prencherDadosTabela();
			}
		}
	}
	
	public void openViewFuncionarioDetalhes(Funcionario f) {
		ViewFuncionarioDetalhes viewFuncionarioDetalhes = new ViewFuncionarioDetalhes(f);
		viewFuncionarioDetalhes.setLocationRelativeTo(null); //Centralizar a tela
		viewFuncionarioDetalhes.setVisible(true);
    	
		viewFuncionarioDetalhes.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				prencherDadosTabela(); //Caso tenha ocorrido alguma mudança dos dados, isso vai atualizar os dados da tela
			}
		});
	}

	public Funcionario getFuncionarioSelecionado() {
		if(table.getSelectedRow() > -1) {
			Integer idFuncionario = Integer.parseInt((String) table.getModel().getValueAt(table.getSelectedRow(), 0));
    	
    		return funcionarios.get(idFuncionario);
		}
		return null;
	}
	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 329);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 679, 261);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//Quando for ativado o clique duplo
				if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    
                    openViewFuncionarioDetalhes(getFuncionarioSelecionado());
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nome", "Cargo", "Endere\u00E7o", "Telefone", "Email"
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
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(85);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		scrollPane.setViewportView(table);
		
		JButton btnNovoFuncionario = new JButton("Cadastrar novo funcionário");
		btnNovoFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewFuncionarioDetalhes(null);
			}
		});
		btnNovoFuncionario.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnNovoFuncionario.setBounds(308, 266, 186, 23);
		getContentPane().add(btnNovoFuncionario);
		
		JButton btnRemoverFuncionarioSelecionado = new JButton("Remover funcionário");
		btnRemoverFuncionarioSelecionado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerFuncionarioSelecionado(getFuncionarioSelecionado());
			}
		});
		btnRemoverFuncionarioSelecionado.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnRemoverFuncionarioSelecionado.setBounds(519, 266, 160, 23);
		getContentPane().add(btnRemoverFuncionarioSelecionado);
	}
}
