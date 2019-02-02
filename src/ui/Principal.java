package ui;

import java.awt.EventQueue;

import view.ViewLocacaoDetalhes;
import view.ViewLocacoes;

public class Principal {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					ViewLocacoes frame = new ViewLocacoes();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

