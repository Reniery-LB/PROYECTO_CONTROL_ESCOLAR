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
	private JPanel opciones_panel;
	
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
		
		opciones_panel = new JPanel();
		opciones_panel.setLayout(null);
		opciones_panel.setVisible(false);
		
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
		remover();
		
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
		acceder_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthView.this.administrador(addScaled);
			}
		});
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
		remover();
		
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
		acceder_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthView.this.administrador(addScaled);
			}
		});
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
	
	public void administrador(Consumer<JComponent> addScaled) {
		remover();
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 0, 101, 102);
		addScaled.accept(logo);
		
		JLabel fondo_barra = new JLabel("Administrador");
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		
		JLabel fondo_barra_2 = new JLabel("Seleccione sus opciones");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		
		JButton btn_asignaturas = new JButton();
		btn_asignaturas.setOpaque(true);
		btn_asignaturas.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas.png")));
		btn_asignaturas.setBackground(new Color(170, 196, 255));
		btn_asignaturas.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_asignaturas.setBounds(1137, 246, 263, 312);
		addScaled.accept(btn_asignaturas);
		
		JButton btn_asignaturas_label = new JButton("Asignaturas");
		btn_asignaturas_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_asignaturas_label.setBackground(Color.decode("#EEF1FF"));
		btn_asignaturas_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_asignaturas_label.setBounds(1137, 556, 263, 58);
		addScaled.accept(btn_asignaturas_label);
		
		JButton btn_docentes = new JButton();
		btn_docentes.setOpaque(true);
		btn_docentes.setIcon(new ImageIcon(getClass().getResource("/img/docentes.png")));
		btn_docentes.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_docentes.setBackground(new Color(170, 196, 255));
		btn_docentes.setBounds(800, 246, 263, 312);
		addScaled.accept(btn_docentes);
		
		JButton btn_docentes_label = new JButton("Docentes");
		btn_docentes_label.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_docentes_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_docentes_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_docentes_label.setBackground(Color.decode("#EEF1FF"));
		btn_docentes_label.setBounds(800, 556, 263, 58);
		addScaled.accept(btn_docentes_label);
		
		JButton btn_alumnos = new JButton();
		btn_alumnos.setOpaque(true);
		btn_alumnos.setIcon(new ImageIcon(getClass().getResource("/img/alumnos.png")));
		btn_alumnos.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_alumnos.setBackground(new Color(170, 196, 255));
		btn_alumnos.setBounds(463, 246, 263, 312);
		addScaled.accept(btn_alumnos);
		
		JButton btn_alumnos_label = new JButton("Alumnos");
		btn_alumnos_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_alumnos_label.setBackground(Color.decode("#EEF1FF"));
		btn_alumnos_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_alumnos_label.setBounds(463, 556, 263, 58);
		addScaled.accept(btn_alumnos_label);
		
		JButton btn_grupo = new JButton();
		btn_grupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuthView.this.grupos(addScaled);
			}
		});
		btn_grupo.setOpaque(true);
		btn_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_grupo.setIcon(new ImageIcon(getClass().getResource("/img/grupos.png")));
		btn_grupo.setBackground(new Color(170, 196, 255));
		btn_grupo.setBounds(127, 246, 263, 312);
		addScaled.accept(btn_grupo);
		
		JButton btn_grupo_label = new JButton("Grupos");
		btn_grupo_label.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuthView.this.grupos(addScaled);
			}
		});
		btn_grupo_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_grupo_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_grupo_label.setBackground(Color.decode("#EEF1FF"));
		btn_grupo_label.setBounds(127, 556, 263, 58);
		addScaled.accept(btn_grupo_label);
	}
	
	public void grupos(Consumer<JComponent> addScaled) {
		remover();
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		
		JButton btn_editar = new JButton();
		btn_editar.setOpaque(true);
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setIcon(new ImageIcon(getClass().getResource("/img/editar.png")));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(1137, 246, 263, 312);
		addScaled.accept(btn_editar);
		
		JButton btn_editar_label = new JButton("Editar\r\n");
		btn_editar_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_editar_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar_label.setBackground(Color.decode("#EEF1FF"));
		btn_editar_label.setBounds(1137, 556, 263, 58);
		addScaled.accept(btn_editar_label);
		
		JButton btn_crear = new JButton();
		btn_crear.setOpaque(true);
		btn_crear.setIcon(new ImageIcon(getClass().getResource("/img/crear.png")));
		btn_crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_crear.setBackground(new Color(170, 196, 255));
		btn_crear.setBounds(800, 246, 263, 312);
		addScaled.accept(btn_crear);
		
		JButton btn_crear_label = new JButton("Crear");
		btn_crear_label.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_crear_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_crear_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_crear_label.setBackground(Color.decode("#EEF1FF"));
		btn_crear_label.setBounds(800, 556, 263, 58);
		addScaled.accept(btn_crear_label);
		
		JButton btn_detalles = new JButton();
		btn_detalles.setOpaque(true);
		btn_detalles.setIcon(new ImageIcon(getClass().getResource("/img/detalles.png")));
		btn_detalles.setBackground(new Color(170, 196, 255));
		btn_detalles.setHorizontalAlignment(JLabel.CENTER);
		btn_detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_detalles.setBounds(463, 246, 263, 312);
		addScaled.accept(btn_detalles);
		
		JButton btn_detalles_label = new JButton("Detalles de \r\ngrupos");
		btn_detalles_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_detalles_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_detalles_label.setBackground(Color.decode("#EEF1FF"));
		btn_detalles_label.setBounds(463, 556, 263, 58);
		addScaled.accept(btn_detalles_label);
		
		JButton btn_registros = new JButton();
		btn_registros.setOpaque(true);
		btn_registros.setIcon(new ImageIcon(getClass().getResource("/img/registros.png")));
		btn_registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_registros.setBackground(new Color(170, 196, 255));
		btn_registros.setBounds(127, 246, 263, 312);
		addScaled.accept(btn_registros);
		
		JButton btn_registros_label = new JButton("Registros");
		btn_registros_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_registros_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_registros_label.setBackground(Color.decode("#EEF1FF"));
		btn_registros_label.setBounds(127, 556, 263, 58);
		addScaled.accept(btn_registros_label);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				opciones_panel.setSize(263, 200);
				opciones_panel.setLocation(372, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("registro de grupos");
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 263, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 263, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 263, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 263, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
		});
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		
		JButton alumnos_barraLabel = new JButton("    Alumnos");
		alumnos_barraLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(633, 101, 263, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("registro de alumnos");
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 263, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 263, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 263, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 263, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
			
		});
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(894, 101, 263, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("registro de alumnos");
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 263, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 263, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 263, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 263, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
		});
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1154, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("registro de alumnos");
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 386, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
//		asignaturas_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setBackground(new Color(238, 241, 255));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		addScaled.accept(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		
		JButton btn_cerrarSesion = new JButton("Cerrar Sesión");
		btn_cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuthView.this.administrador(addScaled);
			}
		});
		btn_cerrarSesion.setIcon(new ImageIcon(getClass().getResource("/img/cerrar_sesion.png")));
		btn_cerrarSesion.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_cerrarSesion.setHorizontalAlignment(JLabel.LEFT);
		btn_cerrarSesion.setBorder(BorderFactory.createLineBorder(Color.decode("#27548A"),3));
		btn_cerrarSesion.setForeground(Color.WHITE);
		btn_cerrarSesion.setBackground(new Color(238, 241, 255));
		btn_cerrarSesion.setBounds(10, 716, 263, 58);
		btn_cerrarSesion.setOpaque(false);
		addScaled.accept(btn_cerrarSesion);
	
	}
	
	public void grupos_frame1(Consumer<JComponent> addScaled) {
		remover();
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		
		JLabel fondo_barra_2 = new JLabel("Grupos escolares");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				opciones_panel.setSize(263, 200);
				opciones_panel.setLocation(372, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 263, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 263, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 263, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 263, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
		});
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		
		JButton alumnos_barraLabel = new JButton("    Alumnos");
		alumnos_barraLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(633, 101, 263, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("registro de alumnos");
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 263, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 263, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 263, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 263, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
			
		});
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(894, 101, 263, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("registro de alumnos");
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 263, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 263, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 263, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 263, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
		});
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1154, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("registro de alumnos");
							
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 386, 50);
					opciones_panel.add(editar);
					
					opciones_panel.revalidate();
					opciones_panel.repaint();
				}
				
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
//		asignaturas_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setBackground(new Color(238, 241, 255));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthView.this.grupos(addScaled);
			}
			
		});
		btn_volver.setIcon(new ImageIcon(getClass().getResource("/img/cerrar_sesion.png")));
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_volver.setHorizontalAlignment(JLabel.LEFT);
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.decode("#27548A"),3));
		btn_volver.setForeground(Color.WHITE);
		btn_volver.setBackground(new Color(238, 241, 255));
		btn_volver.setBounds(10, 716, 263, 58);
		btn_volver.setOpaque(false);
		mipanel.add(btn_volver);
		
		JButton btn_itc = new JButton("ITC");
		btn_itc.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_itc.setBounds(93, 274, 1348, 117);
		btn_itc.setBackground(Color.decode("#AAC4FF"));
		btn_itc.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		mipanel.add(btn_itc);
		
		JButton btn_ids = new JButton("IDS");
		btn_ids.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_ids.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_ids.setBackground(new Color(170, 196, 255));
		btn_ids.setBounds(93, 387, 1348, 117);
		mipanel.add(btn_ids);
		
		JButton btn_ciber = new JButton("Ciberseguridad");
		btn_ciber.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_ciber.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_ciber.setBackground(new Color(170, 196, 255));
		btn_ciber.setBounds(93, 502, 1348, 117);
		mipanel.add(btn_ciber);
	}
	
	public void remover() {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
	}

}
