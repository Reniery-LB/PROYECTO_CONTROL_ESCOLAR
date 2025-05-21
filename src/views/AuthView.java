package views;

import java.awt.BorderLayout;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import aplication.ScalableUtils;
import controllers.AuthController;
import controllers.GruposController;
import models.ConnectionModel;

public class AuthView {
	
	private static final int BASE_ANCHO = 1024;
	private static final int BASE_ALTURA = 768;
	private JFrame ventana;
	private JPanel mipanel;
	private JPanel opciones_panel;
	protected String id;
	
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
		

		
		//METODO PARA AGREGAR COMPONENTES ESCALADOS
		Consumer<JComponent> addScaled = ScalableUtils.createScaler(BASE_ANCHO, BASE_ALTURA);
		
		login(addScaled);
		ventana.setVisible(true);
	}
	
	//===========================================================================================================================
	
	
	public void login(Consumer<JComponent> addScaled) {
		remover();
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(97, 72, 101, 102);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel iniciar_sesion_label = new JLabel("Iniciar sesión");
		iniciar_sesion_label.setFont(new Font("SansSerif", Font.PLAIN, 34));
		iniciar_sesion_label.setBounds(277, 173, 242, 63);
		addScaled.accept(iniciar_sesion_label);
		mipanel.add(iniciar_sesion_label);
		
		JLabel imagen = new JLabel();
		imagen.setIcon(new ImageIcon(getClass().getResource("/img/login.png")));
		imagen.setBackground(new Color(0, 255, 255));
		imagen.setBounds(775, 68, 682, 709);
		imagen.setOpaque(false);
		addScaled.accept(imagen);
		mipanel.add(imagen);
		
		JLabel usuario_label = new JLabel("Usuario");
		usuario_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		usuario_label.setBounds(277, 299, 124, 46);
		addScaled.accept(usuario_label);
		mipanel.add(usuario_label);
		
		JTextField usuario_field = new JTextField();
		usuario_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		usuario_field.setBounds(277, 344, 299, 46);
		usuario_field.setColumns(10);
		usuario_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		addScaled.accept(usuario_field);
		mipanel.add(usuario_field);
		
		JLabel contra_label = new JLabel("Contraseña");
		contra_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		contra_label.setBounds(277, 428, 124, 46);
		addScaled.accept(contra_label);
		mipanel.add(contra_label);

		JPasswordField contra_field = new JPasswordField();
		contra_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		contra_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		contra_field.setBounds(277, 473, 299, 46);
		addScaled.accept(contra_field);
		mipanel.add(contra_field);

		JButton acceder_btn = new JButton("Acceder");
		acceder_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String usuario = usuario_field.getText().trim();
				String contrasena = new String(contra_field.getPassword());

				if (usuario.isEmpty() || contrasena.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor llena todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
					return;
				}
		        String hash = hashPassword(contrasena);

		        try {
		            Connection conn = DriverManager.getConnection(
		                "jdbc:mysql://sql.freedb.tech:3306/freedb_ProyectoControl",
		                "freedb_Renie",
		                "$Cxr85wsg#sP87T"
		            );
					
					String query = "SELECT * FROM Usuario WHERE usuario = ? AND contrasena = ?";
					PreparedStatement stmt = conn.prepareStatement(query);
					stmt.setString(1, usuario);
					stmt.setString(2, hash); 
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
						AuthView.this.administrador(addScaled);
					} else {
						JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error de inicio", JOptionPane.ERROR_MESSAGE);
					}

				} catch (SQLException ex) {
				}
			}
		});

		acceder_btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		acceder_btn.setBounds(277, 591, 299, 56);
		acceder_btn.setBackground(Color.decode("#AAC4FF"));
		acceder_btn.setOpaque(true);
		acceder_btn.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		addScaled.accept(acceder_btn);
		mipanel.add(acceder_btn);
		
	
		
		JLabel subrayado = new JLabel("_________________");
		subrayado.setFont(new Font("SansSerif", Font.PLAIN, 12));
		subrayado.setBounds(371, 684, 123, 13);
		addScaled.accept(subrayado);
		mipanel.add(subrayado);
		
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
		mipanel.add(crear_cuenta_btn);
		
		JLabel fondo_login = new JLabel();
		fondo_login.setBounds(97, 72, 1350, 700);
		fondo_login.setBackground(Color.decode("#EEF1FF"));
		fondo_login.setOpaque(true);
		addScaled.accept(fondo_login);
		mipanel.add(fondo_login);
	}
	
	//===========================================================================================================================
	
	
	public void registro(Consumer<JComponent> addScaled) {
		remover();
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(97, 72, 101, 102);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel crear_cuenta_label = new JLabel("Crear cuenta");
		crear_cuenta_label.setFont(new Font("SansSerif", Font.PLAIN, 34));
		crear_cuenta_label.setBounds(277, 95, 242, 63);
		addScaled.accept(crear_cuenta_label);
		mipanel.add(crear_cuenta_label);
		
		JLabel imagen = new JLabel();
		imagen.setIcon(new ImageIcon(getClass().getResource("/img/registro.png")));
		imagen.setBackground(new Color(0, 255, 255));
		imagen.setBounds(775, 72, 682, 700);
		addScaled.accept(imagen);
		mipanel.add(imagen);
		
		JLabel usuario_label = new JLabel("Usuario:");
		usuario_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		usuario_label.setBounds(277, 180, 124, 46);
		addScaled.accept(usuario_label);
		mipanel.add(usuario_label);
		
		JTextField usuario_field = new JTextField();
		usuario_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		usuario_field.setBounds(277, 225, 299, 46);
		usuario_field.setColumns(10);
		usuario_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		addScaled.accept(usuario_field);
		mipanel.add(usuario_field);
		
		JLabel contra_label = new JLabel("Contraseña:");
		contra_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		contra_label.setBounds(277, 382, 124, 46);
		addScaled.accept(contra_label);
		mipanel.add(contra_label);
		
		JLabel correo_label = new JLabel("Correo:");
		correo_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		correo_label.setBounds(277, 281, 124, 46);
		addScaled.accept(correo_label);
		mipanel.add(correo_label);
		
		JTextField correo_field = new JTextField();
		correo_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		correo_field.setColumns(10);
		correo_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		correo_field.setBounds(277, 326, 299, 46);
		addScaled.accept(correo_field);
		mipanel.add(correo_field);
		
		JLabel confirmar_label = new JLabel("Confirmar contraseña:");
		confirmar_label.setFont(new Font("SansSerif", Font.PLAIN, 18));
		confirmar_label.setBounds(277, 483, 197, 46);
		addScaled.accept(confirmar_label);
		mipanel.add(confirmar_label);
		
		JPasswordField confirmar_field = new JPasswordField();
		confirmar_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		confirmar_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		confirmar_field.setBounds(277, 528, 299, 46);
		addScaled.accept(confirmar_field);
		mipanel.add(confirmar_field);
		
		JCheckBox terminos = new JCheckBox("Acepto términos y condiciones ");
		terminos.setFont(new Font("SansSerif", Font.PLAIN, 18));
		terminos.setBounds(277, 603, 299, 21);
		terminos.setBackground(Color.decode("#EEF1FF"));
		addScaled.accept(terminos);
		mipanel.add(terminos);
		
		JLabel fondo_registro = new JLabel();
		fondo_registro.setBounds(97, 72, 1350, 700);
		fondo_registro.setBackground(Color.decode("#EEF1FF"));
		fondo_registro.setOpaque(true);
		addScaled.accept(fondo_registro);
		mipanel.add(fondo_registro);
		
		JPasswordField contra_field = new JPasswordField();
		contra_field.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		contra_field.setFont(new Font("SansSerif", Font.PLAIN, 14));
		contra_field.setBounds(277, 427, 299, 46);
		addScaled.accept(contra_field);
		mipanel.add(contra_field);
		
		JLabel subrayado = new JLabel("_________________");
		subrayado.setFont(new Font("SansSerif", Font.PLAIN, 12));
		subrayado.setBounds(371, 743, 123, 13);
		addScaled.accept(subrayado);
		mipanel.add(subrayado);
		
		
		
		JButton acceder_btn = new JButton("Acceder");
		acceder_btn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String usuario = usuario_field.getText().trim();
		        String correo = correo_field.getText().trim();
		        String contrasena = new String(contra_field.getPassword());
		        String confirmar = new String(confirmar_field.getPassword());

		        if (usuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
		            return;
		        }

		        if (!contrasena.equals(confirmar)) {
		            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
		            return;
		        }

		        

		        if (!terminos.isSelected()) {
		            JOptionPane.showMessageDialog(null, "Debes aceptar los términos y condiciones.");
		            return;
		        }

		        String hash = hashPassword(contrasena);

		        try {
		            Connection conn = DriverManager.getConnection(
		                "jdbc:mysql://sql.freedb.tech:3306/freedb_ProyectoControl",
		                "freedb_Renie",
		                "$Cxr85wsg#sP87T"
		            );

		            PreparedStatement stmt = conn.prepareStatement(
		                "INSERT INTO Usuario (usuario, correo, contrasena) VALUES (?, ?, ?)"
		            );
		            stmt.setString(1, usuario);
		            stmt.setString(2, correo);
		            stmt.setString(3, hash);

		            stmt.executeUpdate();
		            stmt.close();
		            conn.close();

		            JOptionPane.showMessageDialog(null, "Registro exitoso.");
		            AuthView.this.login(addScaled); 

		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error al registrar: " + ex.getMessage());
		        }
		    }
		});

		acceder_btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		acceder_btn.setBounds(277, 650, 299, 56);
		acceder_btn.setBackground(Color.decode("#AAC4FF"));
		acceder_btn.setOpaque(true);
		acceder_btn.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		mipanel.add(acceder_btn);
		addScaled.accept(acceder_btn);
	
		
		
		JButton iniciar_sesion_btn = new JButton("Iniciar sesión");
		iniciar_sesion_btn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String usuario = usuario_field.getText().trim();
		        String contrasena = new String(contra_field.getPassword());

		        if (usuario.isEmpty() || contrasena.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Ingresa usuario y contraseña.");
		            return;
		        }

		        String hashedPassword = hashPassword(contrasena);

		        try {
		            Connection conn = DriverManager.getConnection(
		                "jdbc:mysql://sql.freedb.tech:3306/freedb_ProyectoControl",
		                "freedb_Renie",
		                "$Cxr85wsg#sP87T"
		            );

		            PreparedStatement stmt = conn.prepareStatement(
		                "SELECT contrasena FROM Usuario WHERE usuario = ?"
		            );
		            stmt.setString(1, usuario);
		            ResultSet rs = stmt.executeQuery();

		            if (rs.next()) {
		                String storedPassword = rs.getString("contrasena");

		                if (storedPassword.equals(hashedPassword)) {
		                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
		                    AuthView.this.administrador(addScaled);
		                } else {
		                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
		                }
		            } else {
		                JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
		            }

		            rs.close();
		            stmt.close();
		            conn.close();

		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + ex.getMessage());
		        }
		    }
		});

		iniciar_sesion_btn.setOpaque(true);
		iniciar_sesion_btn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		iniciar_sesion_btn.setBorder(BorderFactory.createLineBorder(Color.decode("#EEF1FF"),1));
		iniciar_sesion_btn.setBackground(new Color(170, 196, 255));
		iniciar_sesion_btn.setBounds(277, 716, 299, 56);
		iniciar_sesion_btn.setBackground(Color.decode("#EEF1FF"));
		mipanel.add(iniciar_sesion_btn);
		addScaled.accept(iniciar_sesion_btn);
		
		
		
		
	}
	
	private String hashPassword(String password) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hash = md.digest(password.getBytes());
	        StringBuilder hexString = new StringBuilder();

	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    }
	}

	
	//===========================================================================================================================
	
	
	public void administrador(Consumer<JComponent> addScaled) {
		remover();
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 0, 101, 102);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra = new JLabel("Administrador");
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JLabel fondo_barra_2 = new JLabel("Seleccione sus opciones");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JButton btn_asignaturas = new JButton();
		btn_asignaturas.setOpaque(true);
		btn_asignaturas.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas.png")));
		btn_asignaturas.setBackground(new Color(170, 196, 255));
		btn_asignaturas.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_asignaturas.setBounds(1137, 246, 263, 312);
		addScaled.accept(btn_asignaturas);
		mipanel.add(btn_asignaturas);
		
		JButton btn_asignaturas_label = new JButton("Asignaturas");
		btn_asignaturas_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_asignaturas_label.setBackground(Color.decode("#EEF1FF"));
		btn_asignaturas_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_asignaturas_label.setBounds(1137, 556, 263, 58);
		addScaled.accept(btn_asignaturas_label);
		mipanel.add(btn_asignaturas_label);
		
		JButton btn_docentes = new JButton();
		btn_docentes.setOpaque(true);
		btn_docentes.setIcon(new ImageIcon(getClass().getResource("/img/docentes.png")));
		btn_docentes.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_docentes.setBackground(new Color(170, 196, 255));
		btn_docentes.setBounds(800, 246, 263, 312);
		addScaled.accept(btn_docentes);
		mipanel.add(btn_docentes);
		
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
		mipanel.add(btn_docentes_label);
		
		JButton btn_alumnos = new JButton();
		btn_alumnos.setOpaque(true);
		btn_alumnos.setIcon(new ImageIcon(getClass().getResource("/img/alumnos.png")));
		btn_alumnos.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_alumnos.setBackground(new Color(170, 196, 255));
		btn_alumnos.setBounds(463, 246, 263, 312);
		addScaled.accept(btn_alumnos);
		mipanel.add(btn_alumnos);
		
		JButton btn_alumnos_label = new JButton("Alumnos");
		btn_alumnos_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_alumnos_label.setBackground(Color.decode("#EEF1FF"));
		btn_alumnos_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_alumnos_label.setBounds(463, 556, 263, 58);
		addScaled.accept(btn_alumnos_label);
		mipanel.add(btn_alumnos_label);
		
		JButton btn_grupo = new JButton();
		btn_grupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.dispose();
				GruposController gc = new GruposController();
				gc.grupos(addScaled);
			}
		});
		btn_grupo.setOpaque(true);
		btn_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_grupo.setIcon(new ImageIcon(getClass().getResource("/img/grupos.png")));
		btn_grupo.setBackground(new Color(170, 196, 255));
		btn_grupo.setBounds(127, 246, 263, 312);
		addScaled.accept(btn_grupo);
		mipanel.add(btn_grupo);
		
		JButton btn_grupo_label = new JButton("Grupos");
		btn_grupo_label.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.dispose();
				GruposController gc = new GruposController();
				gc.grupos(addScaled);
			}
		});
		btn_grupo_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_grupo_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_grupo_label.setBackground(Color.decode("#EEF1FF"));
		btn_grupo_label.setBounds(127, 556, 263, 58);
		addScaled.accept(btn_grupo_label);
		mipanel.add(btn_grupo_label);
		
		JButton btn_cerrarSesion = new JButton("Cerrar Sesión");
		btn_cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AuthView.this.login(addScaled);
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
		mipanel.add(btn_cerrarSesion);
	}
	
	public void remover() {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
	}

}
