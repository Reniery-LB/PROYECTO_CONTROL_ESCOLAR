package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AuthView {
	
	private static final int BASE_ANCHO = 1024;
	private static final int BASE_ALTURA = 768;
	private JFrame ventana;
	private JPanel mipanel;
	
	public AuthView() {
		inicializar();
	}
	
	public void inicializar() {
		//VENTANA PRINCIPAL
		ventana = new JFrame("CONTROL ESCOLAR");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);

		//CONFIGURACION DEL PANEL PRINCIPAL
		mipanel = new JPanel();
		mipanel.setLayout(null);
		mipanel.setPreferredSize(new Dimension(BASE_ANCHO, BASE_ALTURA));
		
		//SCROLL
		JScrollPane scrollPane = new JScrollPane(mipanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		ventana.add(scrollPane);
		
		//CALCULO DEL ESCALADO
		double scaleX = screenSize.getWidth() / (double) BASE_ANCHO;
		double scaleY = screenSize.getHeight() / (double) BASE_ALTURA;
		double scale = Math.min(scaleX, scaleY) * 0.9;
		
		//METODO PARA AGREGAR COMPONENTES ESCALADOS
		Consumer<JComponent> addScaled = comp -> {
            Rectangle bounds = comp.getBounds();
            comp.setBounds(
                (int)(bounds.x * scale),
                (int)(bounds.y * scale), 
                (int)(bounds.width * scale),
                (int)(bounds.height * scale)
            );
            
            if (comp instanceof JLabel || comp instanceof JButton) {
                Font originalFont = comp.getFont();
                comp.setFont(new Font(
                    originalFont.getName(),
                    originalFont.getStyle(),
                    (int)(originalFont.getSize() * scale)
                ));
            }
            mipanel.add(comp);
		};
		
		
		login(addScaled);
		ventana.add(mipanel);
		ventana.setVisible(true);
	
	}
	
//	public void Panel() {
//		JPanel mipanel = new JPanel();
//		login(mipanel);
//		ventana.add(mipanel);
//	}
	
	public void login(Consumer<JComponent> addScaled) {
		JLabel titulo = new JLabel("ACCEDER");
		titulo.setSize(200, 30);
		titulo.setOpaque(true);
		titulo.setBackground(Color.decode("#D9DFC6")); 
		titulo.setLocation(165, 64);
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setFont(new Font("Britannic",Font.BOLD,24));
        // Título escalado
        JLabel titulo1 = new JLabel("ACCEDER", SwingConstants.CENTER);
        titulo1.setBounds(350, 50, 300, 60); // Posición en diseño base
        titulo1.setOpaque(true);
        titulo1.setBackground(Color.decode("#D9DFC6"));
        titulo1.setFont(new Font("Britannic", Font.BOLD, 36));
        addScaled.accept(titulo1);
        
        // Ejemplo de campo de usuario escalado
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setBounds(350, 150, 200, 30);
        usuarioLabel.setFont(new Font("Britannic", Font.BOLD, 18));
        addScaled.accept(usuarioLabel);
        
        JTextField usuarioField = new JTextField();
        usuarioField.setBounds(350, 190, 300, 40);
        addScaled.accept(usuarioField);
        
        // Ejemplo de botón escalado
        JButton loginBtn = new JButton("INGRESAR");
        loginBtn.setBounds(400, 300, 200, 50);
        loginBtn.setFont(new Font("Britannic", Font.BOLD, 20));
        loginBtn.setBackground(Color.decode("#FBFFE4"));
        addScaled.accept(loginBtn);
		
		mipanel.add(titulo1);
	}
	
	public void registro(Consumer<JComponent> addScaled) {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
		
		
	}

}
