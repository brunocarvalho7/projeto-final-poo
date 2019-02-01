package ui;

import java.awt.EventQueue;

import view.ViewCarros;

public class Principal {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					ViewCarros frame = new ViewCarros();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

