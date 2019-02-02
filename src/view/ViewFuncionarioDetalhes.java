package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import model.Funcionario;
import repository.RepositorioFuncionario;
import enums.Cargo;
import java.awt.Dimension;

public class ViewFuncionarioDetalhes extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtSalario;
	private JComboBox<Cargo> comboCargo;
	
	private Funcionario funcionario;

	public ViewFuncionarioDetalhes(Funcionario funcionario) {
		this.funcionario = funcionario;
		
		initComponents();
		
		if(funcionario != null)
			prencherDadosFormulario();		
	}
	
	public void prencherDadosFormulario() {
		txtNome.setText(funcionario.getNome());
		txtEndereco.setText(funcionario.getEndereco());
		txtTelefone.setText(funcionario.getTelefone());
		txtEmail.setText(funcionario.getEmail());
		txtSalario.setText(String.format("%.2f", funcionario.getSalario()));
		comboCargo.setSelectedItem(funcionario.getCargo());
	}
	
	public void salvarInformacoes() {
		if(validarCampos()) {
			if(funcionario == null)
				funcionario = new Funcionario();
			
			funcionario.setNome(txtNome.getText());
			funcionario.setEndereco(txtEndereco.getText());
			funcionario.setTelefone(txtTelefone.getText());
			funcionario.setEmail(txtEmail.getText());
			funcionario.setSalario(Double.parseDouble(txtSalario.getText().replace(',', '.')));
			funcionario.setCargo((Cargo) comboCargo.getSelectedItem());
			
			Funcionario aux = RepositorioFuncionario.getInstance().salvar(funcionario);
			
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
		}else if(txtEndereco.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe um endereço válido!!");
			txtEndereco.requestFocus();
			return false;
		}else if(txtTelefone.getText().trim().equals("") || txtTelefone.getText().trim().length() != 8) {
			JOptionPane.showMessageDialog(null, "Informe um telefone válido!!");
			txtTelefone.requestFocus();
			return false;
		}else if(txtEmail.getText().trim().equals(""))  {
			JOptionPane.showMessageDialog(null, "Informe um email válido!!");
			txtEmail.requestFocus();
			return false;	
		}else if(txtSalario.getText().trim().equals(""))  {
			JOptionPane.showMessageDialog(null, "Informe um salário válido!!");
			txtSalario.requestFocus();
			return false;	
		}else {
			//Garantir que o valor é um valor válido
			try {
				Double.parseDouble(txtSalario.getText().replace(',', '.'));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Informe um salário válido!!");
				txtSalario.requestFocus();
				return false;	
			}
		}
		
		return true;
	}

	public void initComponents() {
		setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 571, 252);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{92, 215, 92, 152, 0};
		gbl_contentPane.rowHeights = new int[]{20, 20, 20, 20, 23, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblCadastroDeVeculos = new JLabel("CADASTRO DE FUNCION\u00C1RIOS");
		lblCadastroDeVeculos.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 27));
		GridBagConstraints gbc_lblCadastroDeVeculos = new GridBagConstraints();
		gbc_lblCadastroDeVeculos.gridwidth = 4;
		gbc_lblCadastroDeVeculos.insets = new Insets(0, 0, 0, 5);
		gbc_lblCadastroDeVeculos.gridx = 0;
		gbc_lblCadastroDeVeculos.gridy = 0;
		panel.add(lblCadastroDeVeculos, gbc_lblCadastroDeVeculos);
		
		JLabel lblNome= new JLabel("Nome:");
		lblNome.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.fill = GridBagConstraints.VERTICAL;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		contentPane.add(lblNome, gbc_lblNome);
		
		txtNome = new JTextField();
		GridBagConstraints gbc_txtModelo = new GridBagConstraints();
		gbc_txtModelo.gridwidth = 3;
		gbc_txtModelo.insets = new Insets(0, 0, 5, 0);
		gbc_txtModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtModelo.gridx = 1;
		gbc_txtModelo.gridy = 1;
		contentPane.add(txtNome, gbc_txtModelo);
		txtNome.setColumns(10);
		lblNome.setLabelFor(txtNome);
		
		JLabel lblEndereco= new JLabel("Endere\u00E7o:");
		lblEndereco.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblEndereco = new GridBagConstraints();
		gbc_lblEndereco.anchor = GridBagConstraints.EAST;
		gbc_lblEndereco.fill = GridBagConstraints.VERTICAL;
		gbc_lblEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndereco.gridx = 0;
		gbc_lblEndereco.gridy = 2;
		contentPane.add(lblEndereco, gbc_lblEndereco);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		GridBagConstraints gbc_txtEndereco = new GridBagConstraints();
		gbc_txtEndereco.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_txtEndereco.gridx = 1;
		gbc_txtEndereco.gridy = 2;
		contentPane.add(txtEndereco, gbc_txtEndereco);
		
		JLabel lblTelefone= new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblTelefone = new GridBagConstraints();
		gbc_lblTelefone.anchor = GridBagConstraints.EAST;
		gbc_lblTelefone.fill = GridBagConstraints.VERTICAL;
		gbc_lblTelefone.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefone.gridx = 2;
		gbc_lblTelefone.gridy = 2;
		contentPane.add(lblTelefone, gbc_lblTelefone);
		
		txtTelefone = new JTextField();
		txtTelefone.setColumns(10);
		GridBagConstraints gbc_txtTelefone = new GridBagConstraints();
		gbc_txtTelefone.anchor = GridBagConstraints.NORTH;
		gbc_txtTelefone.insets = new Insets(0, 0, 5, 0);
		gbc_txtTelefone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefone.gridx = 3;
		gbc_txtTelefone.gridy = 2;
		contentPane.add(txtTelefone, gbc_txtTelefone);
		
		JLabel lblEmail= new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 3;
		contentPane.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 3;
		contentPane.add(txtEmail, gbc_txtEmail);
		
		JLabel lblSalario = new JLabel("Sal\u00E1rio:");
		lblSalario.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblSalario = new GridBagConstraints();
		gbc_lblSalario.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSalario.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalario.gridx = 2;
		gbc_lblSalario.gridy = 3;
		contentPane.add(lblSalario, gbc_lblSalario);
		
		txtSalario = new JTextField();
		txtSalario.setColumns(10);
		GridBagConstraints gbc_txtSalario = new GridBagConstraints();
		gbc_txtSalario.insets = new Insets(0, 0, 5, 0);
		gbc_txtSalario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSalario.gridx = 3;
		gbc_txtSalario.gridy = 3;
		contentPane.add(txtSalario, gbc_txtSalario);
		
		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblCargo = new GridBagConstraints();
		gbc_lblCargo.anchor = GridBagConstraints.EAST;
		gbc_lblCargo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCargo.gridx = 0;
		gbc_lblCargo.gridy = 4;
		contentPane.add(lblCargo, gbc_lblCargo);
		
		comboCargo = new JComboBox<>();
		comboCargo.setModel(new DefaultComboBoxModel<Cargo>(Cargo.values()));
		GridBagConstraints gbc_comboCargo = new GridBagConstraints();
		gbc_comboCargo.insets = new Insets(0, 0, 5, 0);
		gbc_comboCargo.fill = GridBagConstraints.BOTH;
		gbc_comboCargo.gridwidth = 3;
		gbc_comboCargo.gridx = 1;
		gbc_comboCargo.gridy = 4;
		contentPane.add(comboCargo, gbc_comboCargo);
		
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
		gbc_btnNovo.gridy = 6;
		contentPane.add(btnSalvar, gbc_btnNovo);
		btnCancelar.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		GridBagConstraints gbc_btnAtualizar = new GridBagConstraints();
		gbc_btnAtualizar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAtualizar.gridx = 2;
		gbc_btnAtualizar.gridy = 6;
		contentPane.add(btnCancelar, gbc_btnAtualizar);
	}
}
