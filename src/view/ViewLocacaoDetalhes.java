package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import enums.LocacaoStatus;
import model.Carro;
import model.Cliente;
import model.Funcionario;
import model.Locacao;
import repository.RepositorioCarro;
import repository.RepositorioCliente;
import repository.RepositorioFuncionario;
import repository.RepositorioLocacao;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ViewLocacaoDetalhes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblSituacao;
	private JPanel contentPane;
	private JTextField txtTotal;
	private JComboBox<Cliente> comboCliente;
	private JComboBox<Funcionario> comboAtendente;
	private JComboBox<Carro> comboVeiculo;
	private JTextField txtDataLocacao;
	private JFormattedTextField txtDataDevolucao;
	private JButton btnInserir;
	private JButton btnRemover;
	private JButton btnSalvarLocacao;
	private JTable tableVeiculo;
	
	private List<Carro> veiculos = new ArrayList<>();
	private Locacao locacao;
	
	public ViewLocacaoDetalhes(Locacao locacao) {
		this.locacao = locacao;
		
		initComponents();
		
		for(Cliente c : RepositorioCliente.getInstance().buscarTodos().values()) 
			comboCliente.addItem(c);
		
		for(Funcionario f : RepositorioFuncionario.getInstance().buscarTodos().values())
			comboAtendente.addItem(f);
		
		for(Carro carro : RepositorioCarro.getInstance().buscarTodos().values())
			if(carro.isDisponivel())
				comboVeiculo.addItem(carro);
		
		if(locacao != null) {
			verificarPermissaoEdicao();
			prencherDadosFormulario();
		}else {
			txtDataLocacao.setText(LocalDate.now().toString());
			txtDataDevolucao.setText(LocalDate.now().toString());			
		}
	}
	
	public void adicionarVeiculo() {
		//Verificar se algum item está selecionado
		if(comboVeiculo.getSelectedIndex() >= 0) {
			//Pegar o objeto selecionado no combobox
			Carro carroSelecionado = (Carro) comboVeiculo.getSelectedItem();
			
			//****** Adicionar o carro selecionado a lista de veiculos da locação atual ******
			DefaultTableModel model = (DefaultTableModel) tableVeiculo.getModel();
			
			Vector<String> data = new Vector<>();
			data.add(String.valueOf(carroSelecionado.getId()));
			data.add(carroSelecionado.getModelo());
			data.add(String.format("%.2f", carroSelecionado.getValorDiaria()));

			model.addRow(data);
			veiculos.add(carroSelecionado);
			//=============================================================================
			
			//*********** Remover item adicionado do combobox de veiculos
			comboVeiculo.removeItemAt(comboVeiculo.getSelectedIndex());
			//===========================================================
	
			calcularValorLocacao();
			
			JOptionPane.showMessageDialog(null, "Veiculo adicionado com sucesso");
		}	
	}
	
	public void removerVeiculo() {
		//Verificar se alguma linha da tabela está selecionada
		if(tableVeiculo.getSelectedRow() > -1) {
			Integer idVeiculo = Integer.parseInt((String) tableVeiculo.getModel().getValueAt(tableVeiculo.getSelectedRow(), 0));
			
			veiculos.remove(RepositorioCarro.getInstance().buscar(idVeiculo));

			comboVeiculo.addItem(RepositorioCarro.getInstance().buscar(idVeiculo));
			((DefaultTableModel) tableVeiculo.getModel()).removeRow(tableVeiculo.getSelectedRow());
	
			RepositorioCarro.getInstance().liberarCarro(idVeiculo);
			
			calcularValorLocacao();
			
			JOptionPane.showMessageDialog(null, "Veiculo removido com sucesso!!");
		}
	}
	
	public void salvarInformacoes() {
		if(validarCampos()) {
			if(locacao == null)
				locacao = new Locacao();
			
			locacao.setDataLocacao(LocalDate.parse(txtDataLocacao.getText()));
			locacao.setDataDevolucao(LocalDate.parse(txtDataDevolucao.getText()));
			locacao.setCliente((Cliente) comboCliente.getSelectedItem());
			locacao.setAtendente((Funcionario) comboAtendente.getSelectedItem());
			locacao.setVeiculos(veiculos);
			locacao.setValorLocacao(Double.parseDouble(txtTotal.getText().replace(",", ".")));
			
			for(Carro c : veiculos)
				RepositorioCarro.getInstance().alugarCarro(c.getId());
			
			Locacao aux = RepositorioLocacao.getInstance().salvar(locacao);
			
			if(aux != null) {
				JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
				dispose(); //Fechar tela
			}else 
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar salvar os dados!");	
		}
	}
	
	public void prencherDadosFormulario() {
		lblSituacao.setText(locacao.getStatus().name());
		txtDataLocacao.setText(locacao.getDataLocacao().toString());
		txtDataDevolucao.setText(locacao.getDataDevolucao().toString());
		comboCliente.setSelectedItem(locacao.getCliente());
		comboAtendente.setSelectedItem(locacao.getAtendente());

		veiculos = locacao.getVeiculos();
		
		for(Carro c : locacao.getVeiculos()) {
			DefaultTableModel model = (DefaultTableModel) tableVeiculo.getModel();
			
			Vector<String> data = new Vector<>();
			data.add(String.valueOf(c.getId()));
			data.add(c.getModelo());
			data.add(String.format("%.2f", c.getValorDiaria()));

			model.addRow(data);

		}
		txtTotal.setText(String.format("%.2f", locacao.getValorLocacao()));
	}
	
	private boolean validarCampos() {
		if(validarDataDevolucao() == false) {
			JOptionPane.showMessageDialog(null, "Informe uma data de devolução válida!!");
			txtDataDevolucao.requestFocus();
			return false;
		}else if(comboCliente.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Informe um cliente válido!!");
			comboCliente.requestFocus();
			return false;			
		}else if(comboAtendente.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Informe um atendente válido!!");
			comboAtendente.requestFocus();
			return false;
		}else if(tableVeiculo.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "Informe um veiculo a ser locado!!");
			comboVeiculo	.requestFocus();
			return false;
		}
		
		return true;
	}

	public void initComponents() {
		setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 641, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{92, 186, 92, 99, 0};
		gbl_contentPane.rowHeights = new int[]{20, 8, 20, 20, 0, 20, 23, 34, 18, 40, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridwidth = 5;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{92, 26, 124, 124, 0};
		gbl_panel.rowHeights = new int[]{50, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCadastroDeVeculos = new JLabel("LOCA\u00C7\u00C3O DE VE\u00CDCULOS");
		lblCadastroDeVeculos.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 27));
		GridBagConstraints gbc_lblCadastroDeVeculos = new GridBagConstraints();
		gbc_lblCadastroDeVeculos.gridwidth = 4;
		gbc_lblCadastroDeVeculos.insets = new Insets(0, 0, 0, 5);
		gbc_lblCadastroDeVeculos.gridx = 0;
		gbc_lblCadastroDeVeculos.gridy = 0;
		panel.add(lblCadastroDeVeculos, gbc_lblCadastroDeVeculos);
		
		lblSituacao = new JLabel("ABERTA");
		lblSituacao.setBackground(new Color(0, 128, 0));
		lblSituacao.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 8));
		GridBagConstraints gbc_lblSituacao = new GridBagConstraints();
		gbc_lblSituacao.gridwidth = 5;
		gbc_lblSituacao.anchor = GridBagConstraints.EAST;
		gbc_lblSituacao.insets = new Insets(0, 0, 5, 0);
		gbc_lblSituacao.gridx = 0;
		gbc_lblSituacao.gridy = 1;
		contentPane.add(lblSituacao, gbc_lblSituacao);
		
		JLabel lblDataLocacao= new JLabel("Data Loca\u00E7\u00E3o:");
		lblDataLocacao.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblDataLocacao = new GridBagConstraints();
		gbc_lblDataLocacao.anchor = GridBagConstraints.EAST;
		gbc_lblDataLocacao.fill = GridBagConstraints.VERTICAL;
		gbc_lblDataLocacao.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataLocacao.gridx = 0;
		gbc_lblDataLocacao.gridy = 2;
		contentPane.add(lblDataLocacao, gbc_lblDataLocacao);
		
		txtDataLocacao = new JTextField();
		txtDataLocacao.setEditable(false);
		GridBagConstraints gbc_txtDataLocacao = new GridBagConstraints();
		gbc_txtDataLocacao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataLocacao.insets = new Insets(0, 0, 5, 5);
		gbc_txtDataLocacao.gridx = 1;
		gbc_txtDataLocacao.gridy = 2;
		contentPane.add(txtDataLocacao, gbc_txtDataLocacao);
		txtDataLocacao.setColumns(10);
		
		JLabel lblDataDevolucao = new JLabel("Data Devolu\u00E7\u00E3o:");
		lblDataDevolucao.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblDataDevolucao = new GridBagConstraints();
		gbc_lblDataDevolucao.anchor = GridBagConstraints.EAST;
		gbc_lblDataDevolucao.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataDevolucao.gridx = 3;
		gbc_lblDataDevolucao.gridy = 2;
		contentPane.add(lblDataDevolucao, gbc_lblDataDevolucao);
		
		txtDataDevolucao = new JFormattedTextField();
		txtDataDevolucao.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(validarDataDevolucao())
					calcularValorLocacao();
				else {
					JOptionPane.showMessageDialog(null, "Informe uma data de devolução válida");
					txtDataDevolucao.requestFocus();
				}
			}
		});

		txtDataDevolucao.setToolTipText("Digite uma data de devolu\u00E7\u00E3o no formato YYYY-MM-DD");
		GridBagConstraints gbc_txtDataDevolucao = new GridBagConstraints();
		gbc_txtDataDevolucao.insets = new Insets(0, 0, 5, 0);
		gbc_txtDataDevolucao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataDevolucao.gridx = 4;
		gbc_txtDataDevolucao.gridy = 2;
		contentPane.add(txtDataDevolucao, gbc_txtDataDevolucao);
		
		JLabel lblCliente= new JLabel("Cliente:");
		lblCliente.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblCliente = new GridBagConstraints();
		gbc_lblCliente.anchor = GridBagConstraints.EAST;
		gbc_lblCliente.fill = GridBagConstraints.VERTICAL;
		gbc_lblCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblCliente.gridx = 0;
		gbc_lblCliente.gridy = 3;
		contentPane.add(lblCliente, gbc_lblCliente);
		
		comboCliente = new JComboBox<Cliente>();
		GridBagConstraints gbc_comboCliente = new GridBagConstraints();
		gbc_comboCliente.gridwidth = 4;
		gbc_comboCliente.insets = new Insets(0, 0, 5, 0);
		gbc_comboCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboCliente.gridx = 1;
		gbc_comboCliente.gridy = 3;
		contentPane.add(comboCliente, gbc_comboCliente);
		
		JLabel lblAtendente= new JLabel("Atendente:");
		lblAtendente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAtendente.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblAtendente = new GridBagConstraints();
		gbc_lblAtendente.anchor = GridBagConstraints.EAST;
		gbc_lblAtendente.insets = new Insets(0, 0, 5, 5);
		gbc_lblAtendente.gridx = 0;
		gbc_lblAtendente.gridy = 4;
		contentPane.add(lblAtendente, gbc_lblAtendente);
		
		comboAtendente = new JComboBox<Funcionario>();
		GridBagConstraints gbc_comboAtendente = new GridBagConstraints();
		gbc_comboAtendente.gridwidth = 4;
		gbc_comboAtendente.insets = new Insets(0, 0, 5, 0);
		gbc_comboAtendente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboAtendente.gridx = 1;
		gbc_comboAtendente.gridy = 4;
		contentPane.add(comboAtendente, gbc_comboAtendente);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.anchor = GridBagConstraints.SOUTH;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 5;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 5;
		contentPane.add(separator, gbc_separator);
		
		JLabel lblVeiculosInfo = new JLabel("Ve\u00EDculos locados:");
		lblVeiculosInfo.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblVeiculosInfo = new GridBagConstraints();
		gbc_lblVeiculosInfo.gridwidth = 5;
		gbc_lblVeiculosInfo.anchor = GridBagConstraints.NORTH;
		gbc_lblVeiculosInfo.insets = new Insets(0, 0, 5, 0);
		gbc_lblVeiculosInfo.gridx = 0;
		gbc_lblVeiculosInfo.gridy = 6;
		contentPane.add(lblVeiculosInfo, gbc_lblVeiculosInfo);
		
		JLabel lblVeculo = new JLabel("Ve\u00EDculo:");
		lblVeculo.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblVeculo = new GridBagConstraints();
		gbc_lblVeculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblVeculo.anchor = GridBagConstraints.EAST;
		gbc_lblVeculo.gridx = 0;
		gbc_lblVeculo.gridy = 7;
		contentPane.add(lblVeculo, gbc_lblVeculo);
		
		comboVeiculo = new JComboBox<>();
		GridBagConstraints gbc_comboVeiculo = new GridBagConstraints();
		gbc_comboVeiculo.insets = new Insets(0, 0, 5, 0);
		gbc_comboVeiculo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboVeiculo.gridwidth = 4;
		gbc_comboVeiculo.gridx = 1;
		gbc_comboVeiculo.gridy = 7;
		contentPane.add(comboVeiculo, gbc_comboVeiculo);
		
		JLabel lblOpes = new JLabel("Op\u00E7\u00F5es");
		lblOpes.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 9));
		GridBagConstraints gbc_lblOpes = new GridBagConstraints();
		gbc_lblOpes.gridwidth = 2;
		gbc_lblOpes.insets = new Insets(0, 0, 5, 0);
		gbc_lblOpes.gridx = 3;
		gbc_lblOpes.gridy = 8;
		contentPane.add(lblOpes, gbc_lblOpes);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 9;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		tableVeiculo = new JTable();
		tableVeiculo.setCellSelectionEnabled(true);
		tableVeiculo.setFillsViewportHeight(true);
		scrollPane.setColumnHeaderView(tableVeiculo);
		tableVeiculo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3d.", "Veiculo", "Valor/Dia"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableVeiculo.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableVeiculo.getColumnModel().getColumn(1).setPreferredWidth(150);
		
		btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarVeiculo();
			}
		});
		btnInserir.setFont(new Font("Arial Narrow", Font.PLAIN, 14));
		GridBagConstraints gbc_btnInserir = new GridBagConstraints();
		gbc_btnInserir.insets = new Insets(0, 0, 5, 5);
		gbc_btnInserir.gridx = 3;
		gbc_btnInserir.gridy = 9;
		contentPane.add(btnInserir, gbc_btnInserir);
		
		btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerVeiculo();
			}
		});
		btnRemover.setFont(new Font("Arial Narrow", Font.PLAIN, 14));
		GridBagConstraints gbc_btnRemover = new GridBagConstraints();
		gbc_btnRemover.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemover.gridx = 4;
		gbc_btnRemover.gridy = 9;
		contentPane.add(btnRemover, gbc_btnRemover);
		
		MaskFormatter maskData;
		try {
			maskData = new MaskFormatter("####-##-##");
			
			maskData.install(txtDataDevolucao);
			
			JLabel lblTotal= new JLabel("R$ Loca\u00E7\u00E3o:");
			lblTotal.setFont(new Font("Arial Narrow", Font.BOLD, 14));
			GridBagConstraints gbc_lblTotal = new GridBagConstraints();
			gbc_lblTotal.gridwidth = 2;
			gbc_lblTotal.fill = GridBagConstraints.VERTICAL;
			gbc_lblTotal.insets = new Insets(0, 0, 5, 0);
			gbc_lblTotal.gridx = 3;
			gbc_lblTotal.gridy = 10;
			contentPane.add(lblTotal, gbc_lblTotal);
			
			txtTotal = new JTextField();
			txtTotal.setEditable(false);
			txtTotal.setColumns(10);
			GridBagConstraints gbc_txtTotal = new GridBagConstraints();
			gbc_txtTotal.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtTotal.gridwidth = 2;
			gbc_txtTotal.insets = new Insets(0, 0, 5, 0);
			gbc_txtTotal.anchor = GridBagConstraints.NORTH;
			gbc_txtTotal.gridx = 3;
			gbc_txtTotal.gridy = 11;
			contentPane.add(txtTotal, gbc_txtTotal);
			
			JSeparator separator_1 = new JSeparator();
			GridBagConstraints gbc_separator_1 = new GridBagConstraints();
			gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_separator_1.gridwidth = 5;
			gbc_separator_1.insets = new Insets(0, 0, 5, 5);
			gbc_separator_1.gridx = 0;
			gbc_separator_1.gridy = 12;
			contentPane.add(separator_1, gbc_separator_1);
			
			btnSalvarLocacao = new JButton("Salvar Loca\u00E7\u00E3o");
			btnSalvarLocacao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					salvarInformacoes();
				}
			});
			btnSalvarLocacao.setPreferredSize(new Dimension(95, 23));
			btnSalvarLocacao.setFont(new Font("Arial Narrow", Font.BOLD, 15));
			GridBagConstraints gbc_btnSalvarLocacao = new GridBagConstraints();
			gbc_btnSalvarLocacao.insets = new Insets(0, 0, 0, 5);
			gbc_btnSalvarLocacao.gridx = 1;
			gbc_btnSalvarLocacao.gridy = 13;
			contentPane.add(btnSalvarLocacao, gbc_btnSalvarLocacao);
			
			JButton button_1 = new JButton("Cancelar");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			button_1.setFont(new Font("Arial Narrow", Font.BOLD, 15));
			GridBagConstraints gbc_button_1 = new GridBagConstraints();
			gbc_button_1.insets = new Insets(0, 0, 0, 5);
			gbc_button_1.gridx = 2;
			gbc_button_1.gridy = 13;
			contentPane.add(button_1, gbc_button_1);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
	
	private boolean validarDataDevolucao() {
		try {
			LocalDate dataLocacao = LocalDate.parse(txtDataLocacao.getText());
			LocalDate dataDevolucao = LocalDate.parse(txtDataDevolucao.getText());

			return dataDevolucao.isAfter(dataLocacao) || dataDevolucao.isEqual(dataLocacao);
		} catch(Exception e) {
			return false;
		}
	}
	
	private void calcularValorLocacao() {
		double aux = 0;

		if(validarDataDevolucao()) {
			int qtdDias = Period.between(LocalDate.parse(txtDataLocacao.getText()),
										 LocalDate.parse(txtDataDevolucao.getText())).getDays();	
			
			for(Carro c : veiculos) {
				if (qtdDias == 0) {
					aux += c.getValorDiaria();
				}else {
					aux += c.getValorDiaria() * qtdDias;	
				}
			}
		}

		txtTotal.setText( String.format("%.2f", aux) );
	}
	
	private void verificarPermissaoEdicao() {
		if(locacao.getStatus().equals(LocacaoStatus.CONCLUIDO)) {
			txtDataDevolucao.setEnabled(false);
			comboCliente.setEnabled(false);
			comboAtendente.setEnabled(false);
			comboVeiculo.setEnabled(false);
			btnInserir.setEnabled(false);
			btnRemover.setEnabled(false);
			btnSalvarLocacao.setEnabled(false);
		}
	}
}