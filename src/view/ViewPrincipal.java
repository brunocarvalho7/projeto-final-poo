package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ViewPrincipal() {
		setTitle("SisLoc - Sistema de Loca\u00E7\u00E3o de Ve\u00EDculos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCarro = new JMenu("Carros");
		menuBar.add(mnCarro);
		
		JMenuItem mntmCadastroCarro = new JMenuItem("Cadastro de carros");
		mntmCadastroCarro.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewCarros frame = new ViewCarros();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});

		mnCarro.add(mntmCadastroCarro);
		
		JSeparator separatorCarro = new JSeparator();
		mnCarro.add(separatorCarro);
		
		JMenuItem mntmIncluirCarro = new JMenuItem("Incluir Carro");
		mntmIncluirCarro.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewCarroDetalhes frame = new ViewCarroDetalhes(null);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});
		
		mnCarro.add(mntmIncluirCarro);		
		
		JMenu mnCliente = new JMenu("Clientes");
		menuBar.add(mnCliente);
		
		JMenuItem mntmCadastroCliente = new JMenuItem("Cadastro de clientes");
		mntmCadastroCliente.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewClientes frame = new ViewClientes();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});
		
		mnCliente.add(mntmCadastroCliente);
		
		JSeparator separatorCliente = new JSeparator();
		mnCliente.add(separatorCliente);
		
		JMenuItem mntmIncluirCliente = new JMenuItem("Incluir Cliente");
		mntmIncluirCliente.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewClienteDetalhes frame = new ViewClienteDetalhes(null);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});
		
		mnCliente.add(mntmIncluirCliente);
		
		JMenu mnFuncionario = new JMenu("Funcionários");
		menuBar.add(mnFuncionario);
		
		JMenuItem mntmCadastroFuncionario = new JMenuItem("Cadastro de funcionários");
		mntmCadastroFuncionario.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewFuncionarios frame = new ViewFuncionarios();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});
		
		mnFuncionario.add(mntmCadastroFuncionario);
		
		JSeparator separatorFuncionario = new JSeparator();
		mnFuncionario.add(separatorFuncionario);
		
		JMenuItem mntmIncluirFuncionario = new JMenuItem("Incluir Funcionário");
		mntmIncluirFuncionario.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewFuncionarioDetalhes frame = new ViewFuncionarioDetalhes(null);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});
		
		mnFuncionario.add(mntmIncluirFuncionario);

		JMenu mnLocacao = new JMenu("Locações");
		menuBar.add(mnLocacao);
		
		JMenuItem mntmLocacoes = new JMenuItem("Locações");
		mntmLocacoes.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewLocacoes frame = new ViewLocacoes();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});
		
		mnLocacao.add(mntmLocacoes);
		
		JSeparator separatorLocacao = new JSeparator();
		mnLocacao.add(separatorLocacao);
		
		JMenuItem mntmIncluirLocacao = new JMenuItem("Incluir Locação");
		mntmIncluirLocacao.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				ViewLocacaoDetalhes frame = new ViewLocacaoDetalhes(null);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		    }
		});
		
		mnLocacao.add(mntmIncluirLocacao);		

		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("SisLoc - Sistema de Loca\u00E7\u00E3o de Ve\u00EDculos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 26));
		contentPane.add(lblNewLabel, BorderLayout.CENTER);
	}

}
