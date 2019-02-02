package view;

import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import model.Carro;
import repository.RepositorioCarro;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewCarros extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Map<Integer, Carro> carros;

	public ViewCarros() {
		setResizable(false);
		initComponents();
		prencherDadosTabela();
	}
 
	public void prencherDadosTabela() {
		carros = RepositorioCarro.getInstance().buscarTodos();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setNumRows(0); //Limpar dados atuais da tabela
		
		for(Carro c:carros.values()) {
			Vector<String> data = new Vector<>();
			
			data.add(String.valueOf(c.getId()));
			data.add(c.getModelo());
			data.add(String.valueOf(c.getAno()));
			data.add(c.getPlaca());
			data.add(c.getTipo().name());
			if(c.isDisponivel())
				data.add("Sim");
			else
				data.add("Não");
			
			model.addRow(data);
		}
	}

	private void removerCarroSelecionado(Carro c) {
		if(c != null) {
			int x = JOptionPane.showConfirmDialog(null, "Deseja realmente remover o carro "+c.getModelo()+" ?", "REMOVER CARRO", 0);
		
			//Se o usuario tiver realmente confirmado...
			if(x == 0) {
				RepositorioCarro.getInstance().remover(c.getId());
				prencherDadosTabela();
			}
		}
	}
	
	public void openViewCarroDetalhes(Carro c) {
    	ViewCarroDetalhes viewCarroDetalhes = new ViewCarroDetalhes(c);
    	viewCarroDetalhes.setLocationRelativeTo(null); //Centralizar a tela
    	viewCarroDetalhes.setVisible(true);
    	
    	viewCarroDetalhes.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				prencherDadosTabela(); //Caso tenha ocorrido alguma mudança dos dados, isso vai atualizar os dados da tela
			}
		});
	}

	public Carro getCarroSelecionado() {
		if(table.getSelectedRow() > -1) {
        Integer idCarro = Integer.parseInt((String) table.getModel().getValueAt(table.getSelectedRow(), 0));
    	
    		return carros.get(idCarro);
		}
		return null;
	}
	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 329);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 607, 261);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//Quando for ativado o clique duplo
				if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    
                    openViewCarroDetalhes(getCarroSelecionado());
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Modelo", "Ano", "Placa", "Tipo", "Disponivel"
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
		table.getColumnModel().getColumn(1).setPreferredWidth(243);
		scrollPane.setViewportView(table);
		
		JButton btnNovoCarro = new JButton("Cadastrar novo carro");
		btnNovoCarro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewCarroDetalhes(null);
			}
		});
		btnNovoCarro.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnNovoCarro.setBounds(335, 266, 137, 23);
		getContentPane().add(btnNovoCarro);
		
		JButton btnRemoverCarroSelecionado = new JButton("Remover carro");
		btnRemoverCarroSelecionado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerCarroSelecionado(getCarroSelecionado());
			}
		});
		btnRemoverCarroSelecionado.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnRemoverCarroSelecionado.setBounds(482, 266, 115, 23);
		getContentPane().add(btnRemoverCarroSelecionado);
	}

}