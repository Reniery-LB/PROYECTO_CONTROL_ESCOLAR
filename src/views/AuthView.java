package views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AuthView {
	
	private JFrame ventana;
	
	public AuthView() {
		inicializar();
	}
	
	public void inicializar() {
		ventana = new JFrame("CONTROL ESCOLAR");
		ventana.setSize(700, 700);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);

		Panel();
	}
	
	public void Panel() {
		JPanel mipanel = new JPanel();
		login(mipanel);
		ventana.add(mipanel);
	}
	
	public void login(JPanel mipanel) {
		JLabel titulo = new JLabel("ACCEDER");
		titulo.setSize(200, 30);
		titulo.setOpaque(true);
		titulo.setBackground(Color.decode("#D9DFC6")); 
		titulo.setLocation(165, 64);
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setFont(new Font("Britannic",Font.BOLD,24));
		
		mipanel.add(titulo);
	}

}
