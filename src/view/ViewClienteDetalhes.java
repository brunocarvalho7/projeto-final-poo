package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import model.Cliente;
import model.ClientePessoaFisica;
import model.ClientePessoaJuridica;
import repository.RepositorioCliente;
import javax.swing.JRadioButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ViewClienteDetalhes extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNome;
	private JLabel lblRazaoSocial;
	private JLabel lblCpf_CNPJ;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtValorLocado;
	
	private Cliente cliente;
	private JTextField txtCPF_CNPJ;
	private JTextField txtRazaoSocial;
	private JRadioButton rdbtnPessoaFsica;
	private JRadioButton rdbtnPessoaJurdics;
	private RadioButtonListener handler;
	private ButtonGroup groupTipoCliente;
	
	public ViewClienteDetalhes(Cliente cliente) {
		this.cliente = cliente;
		
		initComponents();
		
		if(cliente != null) {
			prencherDadosFormulario();		
			rdbtnPessoaFsica.setEnabled(false);
			rdbtnPessoaJurdics.setEnabled(false);
		}else
			txtValorLocado.setText("0.00");
	}
	
	public void prencherDadosFormulario() {
		if(cliente instanceof ClientePessoaFisica) {
			txtNome.setText(((ClientePessoaFisica)cliente).getNome());
			txtCPF_CNPJ.setText(((ClientePessoaFisica)cliente).getCpf());
			
			rdbtnPessoaFsica.setSelected(true);
		}else {
			txtNome.setText(((ClientePessoaJuridica)cliente).getNomeFantasia());
			txtRazaoSocial.setText(((ClientePessoaJuridica)cliente).getRazaoSocial());
			txtCPF_CNPJ.setText(((ClientePessoaJuridica)cliente).getCnpj());
			
			rdbtnPessoaJurdics.setSelected(true);
		}
		
		txtEndereco.setText(cliente.getEndereco());
		txtTelefone.setText(cliente.getTelefone());
		txtEmail.setText(cliente.getEmail());
		txtValorLocado.setText(String.format("%.2f", cliente.getRsLocado()));		
	}
	
	public void salvarInformacoes() {
		if(validarCampos()) {
			if(this.cliente == null) {
				if(rdbtnPessoaFsica.isSelected())
					cliente = new ClientePessoaFisica();
				else 
					cliente = new ClientePessoaJuridica();
			}
			
			if(cliente instanceof ClientePessoaFisica){
				((ClientePessoaFisica) cliente).setNome(txtNome.getText());
				((ClientePessoaFisica) cliente).setCpf(txtCPF_CNPJ.getText());
			}else {
				((ClientePessoaJuridica) cliente).setNomeFantasia(txtNome.getText());
				((ClientePessoaJuridica) cliente).setRazaoSocial(txtRazaoSocial.getText());
				((ClientePessoaJuridica) cliente).setCnpj(txtCPF_CNPJ.getText());
			}
			
			cliente.setEndereco(txtEndereco.getText());
			cliente.setTelefone(txtTelefone.getText());
			cliente.setEmail(txtEmail.getText());		
			cliente.setRsLocado(Double.parseDouble(txtValorLocado.getText().replace(',', '.')));
			
			Cliente aux = RepositorioCliente.getInstance().salvar(cliente);
			
			if(aux != null) {
				JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
				dispose(); //Fechar tela
			}else 
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar salvar os dados!");	
		}
	}
	
	private boolean validarCampos() {
		if(txtNome.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe um nome válido!!");
			txtNome.requestFocus();
			return false;
		}else if(rdbtnPessoaJurdics.isSelected() && txtRazaoSocial.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe uma razão social válida!!");
			txtRazaoSocial.requestFocus();
			return false;
		}else if(txtEndereco.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe um endereço válido!!");
			txtEndereco.requestFocus();
			return false;
		}else if(txtCPF_CNPJ.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe um valor válido!!");
			txtCPF_CNPJ.requestFocus();
			return false;
		}else if(txtTelefone.getText().trim().equals("") || txtTelefone.getText().trim().length() < 8) {
			JOptionPane.showMessageDialog(null, "Informe um telefone válido!!");
			txtTelefone.requestFocus();
			return false;
		}else if(txtEmail.getText().trim().equals(""))  {
			JOptionPane.showMessageDialog(null, "Informe um email válido!!");
			txtEmail.requestFocus();
			return false;	
		}
		
		return true;
	}

	public void initComponents() {
		setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 655, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{92, 215, 92, 152, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 20, 20, 20, 23, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{92, 26, 124, 124, 0};
		gbl_panel.rowHeights = new int[]{20, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCadastroDeVeculos = new JLabel("CADASTRO DE CLIENTES");
		lblCadastroDeVeculos.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 27));
		GridBagConstraints gbc_lblCadastroDeVeculos = new GridBagConstraints();
		gbc_lblCadastroDeVeculos.gridwidth = 4;
		gbc_lblCadastroDeVeculos.insets = new Insets(0, 0, 0, 5);
		gbc_lblCadastroDeVeculos.gridx = 0;
		gbc_lblCadastroDeVeculos.gridy = 0;
		panel.add(lblCadastroDeVeculos, gbc_lblCadastroDeVeculos);
		
		lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.fill = GridBagConstraints.VERTICAL;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 2;
		contentPane.add(lblNome, gbc_lblNome);
		
		txtNome = new JTextField();
		GridBagConstraints gbc_txtModelo = new GridBagConstraints();
		gbc_txtModelo.insets = new Insets(0, 0, 5, 5);
		gbc_txtModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtModelo.gridx = 1;
		gbc_txtModelo.gridy = 2;
		contentPane.add(txtNome, gbc_txtModelo);
		txtNome.setColumns(10);
		lblNome.setLabelFor(txtNome);
	
		lblRazaoSocial = new JLabel("Raz\u00E3o Social");
		lblRazaoSocial.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblRazaoSocial = new GridBagConstraints();
		gbc_lblRazaoSocial.anchor = GridBagConstraints.EAST;
		gbc_lblRazaoSocial.insets = new Insets(0, 0, 5, 5);
		gbc_lblRazaoSocial.gridx = 2;
		gbc_lblRazaoSocial.gridy = 2;
		contentPane.add(lblRazaoSocial, gbc_lblRazaoSocial);
		
		txtRazaoSocial = new JTextField();
		txtRazaoSocial.setColumns(10);
		GridBagConstraints gbc_txtRazaoSocial = new GridBagConstraints();
		gbc_txtRazaoSocial.insets = new Insets(0, 0, 5, 0);
		gbc_txtRazaoSocial.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRazaoSocial.gridx = 3;
		gbc_txtRazaoSocial.gridy = 2;
		contentPane.add(txtRazaoSocial, gbc_txtRazaoSocial);
	
		JLabel lblEndereco= new JLabel("Endere\u00E7o:");
		lblEndereco.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblEndereco = new GridBagConstraints();
		gbc_lblEndereco.anchor = GridBagConstraints.EAST;
		gbc_lblEndereco.fill = GridBagConstraints.VERTICAL;
		gbc_lblEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndereco.gridx = 0;
		gbc_lblEndereco.gridy = 3;
		contentPane.add(lblEndereco, gbc_lblEndereco);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		GridBagConstraints gbc_txtEndereco = new GridBagConstraints();
		gbc_txtEndereco.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_txtEndereco.gridx = 1;
		gbc_txtEndereco.gridy = 3;
		contentPane.add(txtEndereco, gbc_txtEndereco);
		
		lblCpf_CNPJ = new JLabel("CPF:");
		lblCpf_CNPJ.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblCpf_CNPJ = new GridBagConstraints();
		gbc_lblCpf_CNPJ.anchor = GridBagConstraints.EAST;
		gbc_lblCpf_CNPJ.insets = new Insets(0, 0, 5, 5);
		gbc_lblCpf_CNPJ.gridx = 2;
		gbc_lblCpf_CNPJ.gridy = 3;
		contentPane.add(lblCpf_CNPJ, gbc_lblCpf_CNPJ);
		
		txtCPF_CNPJ = new JTextField();
		txtCPF_CNPJ.setColumns(10);
		GridBagConstraints gbc_txtCPF_CNPJ = new GridBagConstraints();
		gbc_txtCPF_CNPJ.insets = new Insets(0, 0, 5, 0);
		gbc_txtCPF_CNPJ.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCPF_CNPJ.gridx = 3;
		gbc_txtCPF_CNPJ.gridy = 3;
		contentPane.add(txtCPF_CNPJ, gbc_txtCPF_CNPJ);
		
		JLabel lblTelefone= new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblTelefone = new GridBagConstraints();
		gbc_lblTelefone.anchor = GridBagConstraints.EAST;
		gbc_lblTelefone.fill = GridBagConstraints.VERTICAL;
		gbc_lblTelefone.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefone.gridx = 0;
		gbc_lblTelefone.gridy = 4;
		contentPane.add(lblTelefone, gbc_lblTelefone);
		
		txtTelefone = new JTextField();
		txtTelefone.setColumns(10);
		GridBagConstraints gbc_txtTelefone = new GridBagConstraints();
		gbc_txtTelefone.anchor = GridBagConstraints.NORTH;
		gbc_txtTelefone.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefone.gridx = 1;
		gbc_txtTelefone.gridy = 4;
		contentPane.add(txtTelefone, gbc_txtTelefone);
		
		JLabel lblEmail= new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 4;
		contentPane.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 3;
		gbc_txtEmail.gridy = 4;
		contentPane.add(txtEmail, gbc_txtEmail);
		
		JLabel lblSalario = new JLabel("R$ Locado:");
		lblSalario.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblSalario = new GridBagConstraints();
		gbc_lblSalario.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSalario.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalario.gridx = 0;
		gbc_lblSalario.gridy = 5;
		contentPane.add(lblSalario, gbc_lblSalario);
		
		txtValorLocado = new JTextField();
		txtValorLocado.setEditable(false);
		txtValorLocado.setColumns(10);
		GridBagConstraints gbc_txtValorLocado = new GridBagConstraints();
		gbc_txtValorLocado.insets = new Insets(0, 0, 5, 5);
		gbc_txtValorLocado.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorLocado.gridx = 1;
		gbc_txtValorLocado.gridy = 5;
		contentPane.add(txtValorLocado, gbc_txtValorLocado);

		handler = new RadioButtonListener();
		
		rdbtnPessoaFsica = new JRadioButton("Pessoa F\u00EDsica");
		rdbtnPessoaFsica.addItemListener(handler);
		rdbtnPessoaFsica.setSelected(true);
		GridBagConstraints gbc_rdbtnPessoaFsica = new GridBagConstraints();
		gbc_rdbtnPessoaFsica.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnPessoaFsica.gridx = 1;
		gbc_rdbtnPessoaFsica.gridy = 1;
		contentPane.add(rdbtnPessoaFsica, gbc_rdbtnPessoaFsica);
		
		rdbtnPessoaJurdics = new JRadioButton("Pessoa Jur\u00EDdica");
		rdbtnPessoaFsica.addItemListener(handler);
		GridBagConstraints gbc_rdbtnPessoaJurdics = new GridBagConstraints();
		gbc_rdbtnPessoaJurdics.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnPessoaJurdics.gridx = 2;
		gbc_rdbtnPessoaJurdics.gridy = 1;
		contentPane.add(rdbtnPessoaJurdics, gbc_rdbtnPessoaJurdics);

		groupTipoCliente = new ButtonGroup();
		groupTipoCliente.add(rdbtnPessoaFsica);
		groupTipoCliente.add(rdbtnPessoaJurdics);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setPreferredSize(new Dimension(95, 23));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarInformacoes();
			}
		});
		
		btnSalvar.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		GridBagConstraints gbc_btnNovo = new GridBagConstraints();
		gbc_btnNovo.anchor = GridBagConstraints.EAST;
		gbc_btnNovo.fill = GridBagConstraints.VERTICAL;
		gbc_btnNovo.insets = new Insets(0, 0, 0, 5);
		gbc_btnNovo.gridx = 1;
		gbc_btnNovo.gridy = 7;
		contentPane.add(btnSalvar, gbc_btnNovo);
		btnCancelar.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		GridBagConstraints gbc_btnAtualizar = new GridBagConstraints();
		gbc_btnAtualizar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAtualizar.gridx = 2;
		gbc_btnAtualizar.gridy = 7;
		contentPane.add(btnCancelar, gbc_btnAtualizar);
	}

	private class RadioButtonListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(rdbtnPessoaFsica.isSelected()) {
				txtNome.setBounds(txtNome.getX(), txtNome.getY(), 537, txtNome.getHeight());
				txtRazaoSocial.setEnabled(false);
				lblNome.setText("Nome:");
				lblCpf_CNPJ.setText("CPF:");
			}else {
				txtNome.setBounds(txtNome.getX(), txtNome.getY(), 210, txtNome.getHeight());
				txtRazaoSocial.setEnabled(true);
				lblNome.setText("Nome Fantasia:");
				lblCpf_CNPJ.setText("CNPJ:");				
			}		
		}
	}
}
