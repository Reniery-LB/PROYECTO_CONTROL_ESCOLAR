package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
		
		ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
		Image imagen = icono.getImage();
		ventana.setIconImage(imagen);
		
		//CONFIGURACION DEL PANEL PRINCIPAL
		mipanel = new JPanel();
		mipanel.setLayout(null);
		mipanel.setPreferredSize(new Dimension(BASE_ANCHO, BASE_ALTURA));
		mipanel.setBackground(Color.decode("#27548A"));
		
		//SCROLL
		JScrollPane scrollPane = new JScrollPane(mipanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		ventana.setLayout(new BorderLayout());
		ventana.add(scrollPane, BorderLayout.CENTER);
		
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
		ventana.setVisible(true);
	}

	public void login(Consumer<JComponent> addScaled) {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(97, 72, 101, 102);
		addScaled.accept(logo);
		
		JLabel iniciar_sesion_label = new JLabel("Iniciar sesión");
		iniciar_sesion_label.setFont(new Font("SansSerif", Font.PLAIN, 34));
		iniciar_sesion_label.setBounds(277, 173, 242, 63);
		addScaled.accept(iniciar_sesion_label);
		
		JLabel imagen = new JLabel();
		imagen.setIcon(new ImageIcon(getClass().getResource("/img/login.png")));
		imagen.setBackground(new Color(0, 255, 255));
		imagen.setBounds(775, 68, 682, 709);
		imagen.setOpaque(false);
		addScaled.accept(imagen);
		
		JLabel usuario_label = new JLabel("Usuario");
		usuario_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		usuario_label.setBounds(277, 299, 124, 46);
		addScaled.accept(usuario_label);
		
		JTextField usuario_field = new JTextField();
		usuario_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		usuario_field.setBounds(277, 344, 299, 46);
		usuario_field.setColumns(10);
		usuario_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		addScaled.accept(usuario_field);
		
		JLabel contra_label = new JLabel("Contraseña");
		contra_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		contra_label.setBounds(277, 428, 124, 46);
		addScaled.accept(contra_label);
		
		JButton acceder_btn = new JButton("Acceder");
		acceder_btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		acceder_btn.setBounds(277, 591, 299, 56);
		acceder_btn.setBackground(Color.decode("#AAC4FF"));
		acceder_btn.setOpaque(true);
		acceder_btn.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		addScaled.accept(acceder_btn);
		
		JPasswordField contra_field = new JPasswordField();
		contra_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		contra_field.setBounds(277, 473, 299, 46);
		addScaled.accept(contra_field);
		
		JLabel subrayado = new JLabel("_________________");
		subrayado.setFont(new Font("SansSerif", Font.PLAIN, 12));
		subrayado.setBounds(371, 684, 123, 13);
		addScaled.accept(subrayado);
		
		JButton crear_cuenta_btn = new JButton("Crear cuenta");
		crear_cuenta_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthView.this.registro(addScaled);		
			}
		});
		crear_cuenta_btn.setOpaque(true);
		crear_cuenta_btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		crear_cuenta_btn.setBorder(BorderFactory.createLineBorder(Color.decode("#EEF1FF"),1));
		crear_cuenta_btn.setBackground(new Color(170, 196, 255));
		crear_cuenta_btn.setBounds(277, 657, 299, 56);
		crear_cuenta_btn.setBackground(Color.decode("#EEF1FF"));
		addScaled.accept(crear_cuenta_btn);
		
		JLabel fondo_login = new JLabel();
		fondo_login.setBounds(97, 72, 1350, 700);
		fondo_login.setBackground(Color.decode("#EEF1FF"));
		fondo_login.setOpaque(true);
		addScaled.accept(fondo_login);
	}
	
	public void registro(Consumer<JComponent> addScaled) {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(97, 72, 101, 102);
		addScaled.accept(logo);
		
		JLabel crear_cuenta_label = new JLabel("Crear cuenta");
		crear_cuenta_label.setFont(new Font("SansSerif", Font.PLAIN, 34));
		crear_cuenta_label.setBounds(277, 95, 242, 63);
		addScaled.accept(crear_cuenta_label);
		
		JLabel imagen = new JLabel();
		imagen.setIcon(new ImageIcon(getClass().getResource("/img/registro.png")));
		imagen.setBackground(new Color(0, 255, 255));
		imagen.setBounds(775, 72, 682, 700);
		addScaled.accept(imagen);
		
		JLabel usuario_label = new JLabel("Usuario:");
		usuario_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		usuario_label.setBounds(277, 180, 124, 46);
		addScaled.accept(usuario_label);
		
		JTextField usuario_field = new JTextField();
		usuario_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		usuario_field.setBounds(277, 225, 299, 46);
		usuario_field.setColumns(10);
		usuario_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		addScaled.accept(usuario_field);
		
		JLabel contra_label = new JLabel("Contraseña:");
		contra_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		contra_label.setBounds(277, 382, 124, 46);
		addScaled.accept(contra_label);
		
		JButton acceder_btn = new JButton("Acceder");
		acceder_btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		acceder_btn.setBounds(277, 650, 299, 56);
		acceder_btn.setBackground(Color.decode("#AAC4FF"));
		acceder_btn.setOpaque(true);
		acceder_btn.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		addScaled.accept(acceder_btn);
		
		JPasswordField contra_field = new JPasswordField();
		contra_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		contra_field.setBounds(277, 427, 299, 46);
		addScaled.accept(contra_field);
		
		JLabel subrayado = new JLabel("_________________");
		subrayado.setFont(new Font("SansSerif", Font.PLAIN, 12));
		subrayado.setBounds(371, 743, 123, 13);
		addScaled.accept(subrayado);
		
		JButton iniciar_sesion_btn = new JButton("Iniciar sesión");
		iniciar_sesion_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthView.this.login(addScaled);
			}
		});
		iniciar_sesion_btn.setOpaque(true);
		iniciar_sesion_btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		iniciar_sesion_btn.setBorder(BorderFactory.createLineBorder(Color.decode("#EEF1FF"),1));
		iniciar_sesion_btn.setBackground(new Color(170, 196, 255));
		iniciar_sesion_btn.setBounds(277, 716, 299, 56);
		iniciar_sesion_btn.setBackground(Color.decode("#EEF1FF"));
		addScaled.accept(iniciar_sesion_btn);
		
		JLabel correo_label = new JLabel("Correo:");
		correo_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		correo_label.setBounds(277, 281, 124, 46);
		addScaled.accept(correo_label);
		
		JTextField correo_field = new JTextField();
		correo_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		correo_field.setColumns(10);
		correo_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		correo_field.setBounds(277, 326, 299, 46);
		addScaled.accept(correo_field);
		
		JLabel confirmar_label = new JLabel("Confirmar contraseña:");
		confirmar_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		confirmar_label.setBounds(277, 483, 197, 46);
		addScaled.accept(confirmar_label);
		
		JPasswordField confirmar_field = new JPasswordField();
		confirmar_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		confirmar_field.setBounds(277, 528, 299, 46);
		addScaled.accept(confirmar_field);
		
		JCheckBox terminos = new JCheckBox("Acepto términos y condiciones ");
		terminos.setFont(new Font("SansSerif", Font.PLAIN, 18));
		terminos.setBounds(277, 603, 299, 21);
		terminos.setBackground(Color.decode("#EEF1FF"));
		addScaled.accept(terminos);
		
		JLabel fondo_registro = new JLabel();
		fondo_registro.setBounds(97, 72, 1350, 700);
		fondo_registro.setBackground(Color.decode("#EEF1FF"));
		fondo_registro.setOpaque(true);
		addScaled.accept(fondo_registro);
		
	}

}
