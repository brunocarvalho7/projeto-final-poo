package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import enums.LocacaoStatus;
import model.ClientePessoaFisica;
import model.ClientePessoaJuridica;
import model.Locacao;
import repository.RepositorioLocacao;

public class ViewLocacoes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Map<Integer, Locacao> locacoes;

	public ViewLocacoes() {
		setResizable(false);
		initComponents();
		prencherDadosTabela();
	}
 
	public void prencherDadosTabela() {
		locacoes = RepositorioLocacao.getInstance().buscarTodos();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setNumRows(0); //Limpar dados atuais da tabela
		
		for(Locacao l:locacoes.values()) {
			Vector<String> data = new Vector<>();
			
			data.add(String.valueOf(l.getIdLocacao()));

			if(l.getCliente() instanceof ClientePessoaFisica)
				data.add( ((ClientePessoaFisica)l.getCliente()).getNome() );
			else if(l.getCliente() instanceof ClientePessoaFisica)
				data.add( ((ClientePessoaJuridica)l.getCliente()).getNomeFantasia() );
			else
				data.add("NÃO LOCALIZADO");
			
			data.add(l.getDataLocacao().toString());
			data.add(l.getDataDevolucao().toString());
			data.add(String.format("%.2f", l.getValorLocacao()));
			data.add(l.getStatus().name());
			
			model.addRow(data);
		}
	}

	private void removerLocacaoSelecionada(Locacao l) {
		if(l != null) {
			if(l.getStatus().equals(LocacaoStatus.ABERTO)) {
				int x = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a locacao "+l.getIdLocacao()+" ?", "REMOVER LOCAÇÃO", 0);
				
				//Se o usuario tiver realmente confirmado...
				if(x == 0) {
					RepositorioLocacao.getInstance().remover(l.getIdLocacao());
					prencherDadosTabela();
				}
						
			}else {
				JOptionPane.showMessageDialog(null, "Não é possível remover uma locação já finalizada!!");
			}
		
		}
	}
	
	public void openViewLocacaoDetalhes(Locacao l) {
    	ViewLocacaoDetalhes viewLocacaoDetalhes = new ViewLocacaoDetalhes(l);
    	viewLocacaoDetalhes.setLocationRelativeTo(null); //Centralizar a tela
    	viewLocacaoDetalhes.setVisible(true);
    	
    	viewLocacaoDetalhes.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				prencherDadosTabela(); //Caso tenha ocorrido alguma mudança dos dados, isso vai atualizar os dados da tela
			}
		});
	}

	public Locacao getLocacaoSelecionado() {
		if(table.getSelectedRow() > -1) {
        Integer idLocacao = Integer.parseInt((String) table.getModel().getValueAt(table.getSelectedRow(), 0));
    	
    		return locacoes.get(idLocacao);
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
                    
                    openViewLocacaoDetalhes(getLocacaoSelecionado());
				}
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Cliente", "Data Loca\u00E7\u00E3o", "Data Devolu\u00E7\u00E3o", "Valor", "Status"
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
		table.getColumnModel().getColumn(1).setPreferredWidth(220);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(65);
		scrollPane.setViewportView(table);
		
		JButton btnNovaLocacao = new JButton("Nova Loca\u00E7\u00E3o");
		btnNovaLocacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewLocacaoDetalhes(null);
			}
		});
		btnNovaLocacao.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnNovaLocacao.setBounds(213, 267, 137, 23);
		getContentPane().add(btnNovaLocacao);
		
		JButton btnRemoverLocacao = new JButton("Remover loca\u00E7\u00E3o");
		btnRemoverLocacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerLocacaoSelecionada(getLocacaoSelecionado());
			}
		});
		btnRemoverLocacao.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnRemoverLocacao.setBounds(360, 267, 115, 23);
		getContentPane().add(btnRemoverLocacao);
		
		JButton btnFinalizarLocao = new JButton("Finalizar Loca\u00E7\u00E3o");
		btnFinalizarLocao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locacao l = getLocacaoSelecionado();
				
				if(l.getStatus().equals(LocacaoStatus.ABERTO)) {
					l.setStatus(LocacaoStatus.CONCLUIDO);
					l.setDataDevolucao(LocalDate.now());
					l.calcularValorLocacao();
					
					RepositorioLocacao.getInstance().salvar(l);
					
					prencherDadosTabela();
					JOptionPane.showMessageDialog(null, "Locação finalizada com sucesso!!");
				}else {
					JOptionPane.showMessageDialog(null, "Locação já está finalizada!!");
				}
				
			}
		});
		btnFinalizarLocao.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnFinalizarLocao.setBounds(485, 267, 122, 23);
		getContentPane().add(btnFinalizarLocao);
	}
}