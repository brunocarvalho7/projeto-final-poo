package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import enums.TipoAutomovel;
import model.Carro;
import repository.Repositorio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;

import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewCarroDetalhes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtModelo;
	private JTextField txtAno;
	private JTextField txtChassi;
	private JTextField txtPlaca;
	private JTextField txtDiaria;
	private JCheckBox chckbxArCondicionado;
	private JCheckBox chckbxDireoHidraulica;
	private JCheckBox chckbxTravasEltricas;
	private JCheckBox chckbxVidrosEltricos;
	private JComboBox<TipoAutomovel> comboTipoAutomovel;
	
	private Carro carro;

	public ViewCarroDetalhes(Carro carro) {
		this.carro = carro;
		
		initComponents();
		
		if(carro != null)
			prencherDadosFormulario();		
	}
	
	public void prencherDadosFormulario() {
		txtModelo.setText(carro.getModelo());
		txtChassi.setText(carro.getChassi());
		txtPlaca.setText(carro.getPlaca());
		txtAno.setText(String.valueOf(carro.getAno()));
		txtDiaria.setText(String.format("%.2f", carro.getValorDiaria()));
		comboTipoAutomovel.setSelectedItem(carro.getTipo());
		
		chckbxArCondicionado.setSelected(carro.isArCondicionado());
		chckbxDireoHidraulica.setSelected(carro.isDirecaoHidraulica());
		chckbxTravasEltricas.setSelected(carro.isTravasEletricas());
		chckbxVidrosEltricos.setSelected(carro.isVidrosEletricos());
	}
	
	public void salvarInformacoes() {
		if(validarCampos()) {
			System.out.println("Antes:" + carro);
			
			if(carro == null)
				carro = new Carro();
			
			carro.setModelo(txtModelo.getText());
			carro.setChassi(txtChassi.getText());
			carro.setPlaca(txtPlaca.getText());
			carro.setAno(Integer.parseInt(txtAno.getText()));
			carro.setValorDiaria(Double.parseDouble(txtDiaria.getText().replace(',', '.')));
			carro.setTipo((TipoAutomovel) comboTipoAutomovel.getSelectedItem());
			
			carro.setArCondicionado(chckbxArCondicionado.isSelected());
			carro.setDirecaoHidraulica(chckbxDireoHidraulica.isSelected());
			carro.setTravasEletricas(chckbxTravasEltricas.isSelected());
			carro.setVidrosEletricos(chckbxVidrosEltricos.isSelected());
			
			System.out.println("Depois: " + carro);
			
			Carro aux = Repositorio.getInstance().salvarCarro(carro);
			
			if(aux != null) {
				JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
				dispose(); //Fechar tela
			}else 
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar salvar os dados!");	
		}
	}
	
	private boolean validarCampos() {
		if(txtModelo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe um modelo válido!!");
			txtModelo.requestFocus();
			return false;
		}else if(txtChassi.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Informe um chassi válido!!");
			txtChassi.requestFocus();
			return false;
		}else if(txtPlaca.getText().trim().equals("") || txtPlaca.getText().trim().length() > 8) {
			JOptionPane.showMessageDialog(null, "Informe uma placa válida!!");
			txtPlaca.requestFocus();
			return false;
		}else if(txtAno.getText().trim().equals("") || txtAno.getText().trim().length() != 4)  {
			JOptionPane.showMessageDialog(null, "Informe um ano válido!!");
			txtAno.requestFocus();
			return false;	
		}else if(txtDiaria.getText().trim().equals(""))  {
			JOptionPane.showMessageDialog(null, "Informe um valor válido!!");
			txtDiaria.requestFocus();
			return false;	
		}else {
			//Garantir que o ano é um valor numerico
			try {
				Integer.parseInt(txtAno.getText());
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Informe um ano válido!!");
				txtAno.requestFocus();
				return false;	
			}
			
			//Garantir que o valor é um valor válido
			try {
				Double.parseDouble(txtDiaria.getText().replace(',', '.'));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Informe um valor válido!!");
				txtDiaria.requestFocus();
				return false;	
			}
		}
		
		return true;
	}

	public void initComponents() {
		setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 513, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{92, 26, 124, 124, 0};
		gbl_contentPane.rowHeights = new int[]{20, 20, 20, 20, 23, 0, 0, 22, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblCadastroDeVeculos = new JLabel("CADASTRO DE VE\u00CDCULOS");
		lblCadastroDeVeculos.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 27));
		GridBagConstraints gbc_lblCadastroDeVeculos = new GridBagConstraints();
		gbc_lblCadastroDeVeculos.gridwidth = 4;
		gbc_lblCadastroDeVeculos.insets = new Insets(0, 0, 0, 5);
		gbc_lblCadastroDeVeculos.gridx = 0;
		gbc_lblCadastroDeVeculos.gridy = 0;
		panel.add(lblCadastroDeVeculos, gbc_lblCadastroDeVeculos);
		
		JLabel lblModelo= new JLabel("Modelo: ");
		lblModelo.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblModelo = new GridBagConstraints();
		gbc_lblModelo.anchor = GridBagConstraints.EAST;
		gbc_lblModelo.fill = GridBagConstraints.VERTICAL;
		gbc_lblModelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblModelo.gridx = 0;
		gbc_lblModelo.gridy = 1;
		contentPane.add(lblModelo, gbc_lblModelo);
		
		txtModelo = new JTextField();
		GridBagConstraints gbc_txtModelo = new GridBagConstraints();
		gbc_txtModelo.gridwidth = 3;
		gbc_txtModelo.insets = new Insets(0, 0, 5, 0);
		gbc_txtModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtModelo.gridx = 1;
		gbc_txtModelo.gridy = 1;
		contentPane.add(txtModelo, gbc_txtModelo);
		txtModelo.setColumns(10);
		lblModelo.setLabelFor(txtModelo);
		
		JLabel lblChassi= new JLabel("Chassi: ");
		lblChassi.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblChassi = new GridBagConstraints();
		gbc_lblChassi.anchor = GridBagConstraints.EAST;
		gbc_lblChassi.fill = GridBagConstraints.VERTICAL;
		gbc_lblChassi.insets = new Insets(0, 0, 5, 5);
		gbc_lblChassi.gridx = 0;
		gbc_lblChassi.gridy = 2;
		contentPane.add(lblChassi, gbc_lblChassi);
		
		txtChassi = new JTextField();
		txtChassi.setColumns(10);
		GridBagConstraints gbc_txtChassi = new GridBagConstraints();
		gbc_txtChassi.insets = new Insets(0, 0, 5, 5);
		gbc_txtChassi.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtChassi.gridx = 1;
		gbc_txtChassi.gridy = 2;
		contentPane.add(txtChassi, gbc_txtChassi);
		
		JLabel lblPlaca= new JLabel("Placa:");
		lblPlaca.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblPlaca = new GridBagConstraints();
		gbc_lblPlaca.anchor = GridBagConstraints.EAST;
		gbc_lblPlaca.fill = GridBagConstraints.VERTICAL;
		gbc_lblPlaca.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaca.gridx = 2;
		gbc_lblPlaca.gridy = 2;
		contentPane.add(lblPlaca, gbc_lblPlaca);
		
		txtPlaca = new JTextField();
		txtPlaca.setColumns(10);
		GridBagConstraints gbc_txtPlaca = new GridBagConstraints();
		gbc_txtPlaca.anchor = GridBagConstraints.NORTH;
		gbc_txtPlaca.insets = new Insets(0, 0, 5, 0);
		gbc_txtPlaca.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPlaca.gridx = 3;
		gbc_txtPlaca.gridy = 2;
		contentPane.add(txtPlaca, gbc_txtPlaca);
		
		JLabel lblAno= new JLabel("Ano: ");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblAno = new GridBagConstraints();
		gbc_lblAno.anchor = GridBagConstraints.EAST;
		gbc_lblAno.insets = new Insets(0, 0, 5, 5);
		gbc_lblAno.gridx = 0;
		gbc_lblAno.gridy = 3;
		contentPane.add(lblAno, gbc_lblAno);
		
		txtAno = new JTextField();
		txtAno.setColumns(10);
		GridBagConstraints gbc_txtAno = new GridBagConstraints();
		gbc_txtAno.insets = new Insets(0, 0, 5, 5);
		gbc_txtAno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAno.gridx = 1;
		gbc_txtAno.gridy = 3;
		contentPane.add(txtAno, gbc_txtAno);
		
		JLabel lblValorDiria = new JLabel("Valor Di\u00E1ria:");
		lblValorDiria.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblValorDiria = new GridBagConstraints();
		gbc_lblValorDiria.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblValorDiria.insets = new Insets(0, 0, 5, 5);
		gbc_lblValorDiria.gridx = 2;
		gbc_lblValorDiria.gridy = 3;
		contentPane.add(lblValorDiria, gbc_lblValorDiria);
		
		txtDiaria = new JTextField();
		txtDiaria.setColumns(10);
		GridBagConstraints gbc_txtDiaria = new GridBagConstraints();
		gbc_txtDiaria.insets = new Insets(0, 0, 5, 0);
		gbc_txtDiaria.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDiaria.gridx = 3;
		gbc_txtDiaria.gridy = 3;
		contentPane.add(txtDiaria, gbc_txtDiaria);
		
		JLabel lblTipoAutomvel = new JLabel("Tipo Autom\u00F3vel:");
		lblTipoAutomvel.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		GridBagConstraints gbc_lblTipoAutomvel = new GridBagConstraints();
		gbc_lblTipoAutomvel.anchor = GridBagConstraints.EAST;
		gbc_lblTipoAutomvel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoAutomvel.gridx = 0;
		gbc_lblTipoAutomvel.gridy = 4;
		contentPane.add(lblTipoAutomvel, gbc_lblTipoAutomvel);
		
		comboTipoAutomovel = new JComboBox<>();
		comboTipoAutomovel.setModel(new DefaultComboBoxModel<TipoAutomovel>(TipoAutomovel.values()));
		GridBagConstraints gbc_comboTipoAutomovel = new GridBagConstraints();
		gbc_comboTipoAutomovel.insets = new Insets(0, 0, 5, 0);
		gbc_comboTipoAutomovel.fill = GridBagConstraints.BOTH;
		gbc_comboTipoAutomovel.gridwidth = 3;
		gbc_comboTipoAutomovel.gridx = 1;
		gbc_comboTipoAutomovel.gridy = 4;
		contentPane.add(comboTipoAutomovel, gbc_comboTipoAutomovel);
		
		JLabel lblOpcionais = new JLabel("Opcionais");
		lblOpcionais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOpcionais.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 15));
		GridBagConstraints gbc_lblOpcionais = new GridBagConstraints();
		gbc_lblOpcionais.gridwidth = 2;
		gbc_lblOpcionais.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpcionais.gridx = 1;
		gbc_lblOpcionais.gridy = 5;
		contentPane.add(lblOpcionais, gbc_lblOpcionais);
		
		chckbxArCondicionado = new JCheckBox("Ar Condicionado");
		chckbxArCondicionado.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxArCondicionado = new GridBagConstraints();
		gbc_chckbxArCondicionado.anchor = GridBagConstraints.NORTH;
		gbc_chckbxArCondicionado.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxArCondicionado.gridx = 0;
		gbc_chckbxArCondicionado.gridy = 6;
		contentPane.add(chckbxArCondicionado, gbc_chckbxArCondicionado);
		
		chckbxDireoHidraulica = new JCheckBox("Dire\u00E7\u00E3o Hidraulica");
		chckbxDireoHidraulica.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxDireoHidraulica = new GridBagConstraints();
		gbc_chckbxDireoHidraulica.anchor = GridBagConstraints.NORTH;
		gbc_chckbxDireoHidraulica.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxDireoHidraulica.gridx = 1;
		gbc_chckbxDireoHidraulica.gridy = 6;
		contentPane.add(chckbxDireoHidraulica, gbc_chckbxDireoHidraulica);
		
		chckbxVidrosEltricos = new JCheckBox("Vidros El\u00E9tricos");
		chckbxVidrosEltricos.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxVidrosEltricos = new GridBagConstraints();
		gbc_chckbxVidrosEltricos.anchor = GridBagConstraints.NORTH;
		gbc_chckbxVidrosEltricos.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVidrosEltricos.gridx = 2;
		gbc_chckbxVidrosEltricos.gridy = 6;
		contentPane.add(chckbxVidrosEltricos, gbc_chckbxVidrosEltricos);
		
		chckbxTravasEltricas = new JCheckBox("Travas El\u00E9tricas");
		chckbxTravasEltricas.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxTravasEltricas = new GridBagConstraints();
		gbc_chckbxTravasEltricas.anchor = GridBagConstraints.NORTH;
		gbc_chckbxTravasEltricas.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxTravasEltricas.gridx = 3;
		gbc_chckbxTravasEltricas.gridy = 6;
		contentPane.add(chckbxTravasEltricas, gbc_chckbxTravasEltricas);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarInformacoes();
			}
		});
		btnSalvar.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		GridBagConstraints gbc_btnNovo = new GridBagConstraints();
		gbc_btnNovo.insets = new Insets(0, 0, 0, 5);
		gbc_btnNovo.gridx = 1;
		gbc_btnNovo.gridy = 8;
		contentPane.add(btnSalvar, gbc_btnNovo);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		GridBagConstraints gbc_btnAtualizar = new GridBagConstraints();
		gbc_btnAtualizar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAtualizar.gridx = 2;
		gbc_btnAtualizar.gridy = 8;
		contentPane.add(btnCancelar, gbc_btnAtualizar);
	}
}
