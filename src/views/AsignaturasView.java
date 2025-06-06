package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import aplication.ScalableUtils;
import controllers.AlumnosController;
import controllers.AuthController;
import controllers.DocentesController;
import controllers.GruposController;

public class AsignaturasView {

	private static final int BASE_ANCHO = 1024;
	private static final int BASE_ALTURA = 768;
	private JFrame ventana;
	private JPanel mipanel;
	private JPanel opciones_panel;
	
	public AsignaturasView() {
		inicializar();
	}
	
	public void inicializar() {
		//VENTANA PRINCIPAL
		ventana = new JFrame("CONTROL ESCOLAR - ASIGNATURAS");
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
		
		asignaturas(addScaled);
		ventana.setVisible(true);
	}
	
	
	//===========================================================================================================================
	
	
	public void panel_asignaturas(Consumer<JComponent> addScaled) {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JButton btn_editar = new JButton();
		btn_editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.editar_asignatura(addScaled);
			}
		});
		btn_editar.setOpaque(true);
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setIcon(new ImageIcon(getClass().getResource("/img/editar.png")));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(1137, 246, 263, 312);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JButton btn_editar_label = new JButton("Editar\r\n");
		btn_editar_label.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.editar_asignatura(addScaled);
			}
		});
		btn_editar_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_editar_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar_label.setBackground(Color.decode("#EEF1FF"));
		btn_editar_label.setBounds(1137, 556, 263, 58);
		addScaled.accept(btn_editar_label);
		mipanel.add(btn_editar_label);
		
		JButton btn_crear = new JButton();
		btn_crear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.añadir_asignatura(addScaled);	
			}
		});
		btn_crear.setOpaque(true);
		btn_crear.setIcon(new ImageIcon(getClass().getResource("/img/crear.png")));
		btn_crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_crear.setBackground(new Color(170, 196, 255));
		btn_crear.setBounds(800, 246, 263, 312);
		addScaled.accept(btn_crear);
		mipanel.add(btn_crear);
		
		JButton btn_crear_label = new JButton("Crear");
		btn_crear_label.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.añadir_asignatura(addScaled);	
			}
		});
		btn_crear_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_crear_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_crear_label.setBackground(Color.decode("#EEF1FF"));
		btn_crear_label.setBounds(800, 556, 263, 58);
		addScaled.accept(btn_crear_label);
		mipanel.add(btn_crear_label);
		
		JButton btn_detalles = new JButton();
		btn_detalles.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.detalles(addScaled);
			}
		});
		btn_detalles.setOpaque(true);
		btn_detalles.setIcon(new ImageIcon(getClass().getResource("/img/detalles.png")));
		btn_detalles.setBackground(new Color(170, 196, 255));
		btn_detalles.setHorizontalAlignment(JLabel.CENTER);
		btn_detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_detalles.setBounds(463, 246, 263, 312);
		addScaled.accept(btn_detalles);
		mipanel.add(btn_detalles);
		
		JButton btn_detalles_label = new JButton("Detalles de \r\nasignaturas");
		btn_detalles_label.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.detalles(addScaled);			}
		});
		btn_detalles_label.setFont(new Font("SansSerif", Font.PLAIN, 24));
		btn_detalles_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_detalles_label.setBackground(Color.decode("#EEF1FF"));
		btn_detalles_label.setBounds(463, 556, 263, 58);
		addScaled.accept(btn_detalles_label);
		mipanel.add(btn_detalles_label);
		
		JButton btn_registros = new JButton();
		btn_registros.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.asignaturas(addScaled);	
			}
		});
		btn_registros.setOpaque(true);
		btn_registros.setIcon(new ImageIcon(getClass().getResource("/img/registros.png")));
		btn_registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_registros.setBackground(new Color(170, 196, 255));
		btn_registros.setBounds(127, 246, 263, 312);
		addScaled.accept(btn_registros);
		mipanel.add(btn_registros);
		
		JButton btn_registros_label = new JButton("Registros");
		btn_registros_label.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.asignaturas(addScaled);
			}
		});
		btn_registros_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_registros_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_registros_label.setBackground(Color.decode("#EEF1FF"));
		btn_registros_label.setBounds(127, 556, 263, 58);
		addScaled.accept(btn_registros_label);
		mipanel.add(btn_registros_label);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrGrupos(addScaled);
			}
		});
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		mipanel.add(grupos_barraLabel);
		
		JButton alumnos_barraLabel = new JButton("    Alumnos");
		alumnos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrAlumnos(addScaled);
			}
		});
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrDocentes(addScaled);	
			}
		});
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1168, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.asignaturas(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.detalles(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.añadir_asignatura(addScaled);
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.editar_asignatura(addScaled);
						}
					});
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
		asignaturas_barraLabel.setBackground(Color.decode("#AAC4FF"));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		addScaled.accept(asignaturas_barraLabel);
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.dispose();
				AuthController ac = new AuthController();
				ac.administrador(addScaled);
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
		addScaled.accept(btn_volver);
		mipanel.add(btn_volver);
	}
	
	
	//===========================================================================================================================
	
	
	public void asignaturas(Consumer<JComponent> addScaled) {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel(" Asignaturas");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(-10, 105, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrGrupos(addScaled);
			}
		});
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		mipanel.add(grupos_barraLabel);
		
		JButton alumnos_barraLabel = new JButton("    Alumnos");
		alumnos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrAlumnos(addScaled);
			}
		});
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrDocentes(addScaled);	
			}
		});
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1168, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.asignaturas(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.detalles(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.añadir_asignatura(addScaled);
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.editar_asignatura(addScaled);
						}
					});
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
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		asignaturas_barraLabel.setBackground(Color.decode("#AAC4FF"));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		addScaled.accept(asignaturas_barraLabel);
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.panel_asignaturas(addScaled);
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
		addScaled.accept(btn_volver);
		mipanel.add(btn_volver);
		
		JButton btn_prog = new JButton("Programación");
		btn_prog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.detalles(addScaled);
			}
		});
		btn_prog.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_prog.setBounds(93, 274, 1348, 117);
		btn_prog.setBackground(Color.decode("#AAC4FF"));
		btn_prog.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		addScaled.accept(btn_prog);
		mipanel.add(btn_prog);
		
		JButton btn_ingl = new JButton("Inglés");
		btn_ingl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.detalles_ingles(addScaled);
			}
		});
		btn_ingl.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_ingl.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_ingl.setBackground(new Color(170, 196, 255));
		btn_ingl.setBounds(93, 387, 1348, 117);
		addScaled.accept(btn_ingl);
		mipanel.add(btn_ingl);
		
		JButton btn_mate = new JButton("Matemáticas");
		btn_mate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.detalles_mate(addScaled);
			}
		});
		btn_mate.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_mate.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_mate.setBackground(new Color(170, 196, 255));
		btn_mate.setBounds(93, 502, 1348, 117);
		addScaled.accept(btn_mate);
		mipanel.add(btn_mate);
	}
	
	
	//===========================================================================================================================
	
	
	public void detalles(Consumer<JComponent> addScaled) {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Detalles de la asignatura");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrGrupos(addScaled);
			}
		});
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		mipanel.add(grupos_barraLabel);
		
		JButton alumnos_barraLabel = new JButton("    Alumnos");
		alumnos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrAlumnos(addScaled);
			}
		});
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrDocentes(addScaled);	
			}
		});
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1168, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.asignaturas(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.detalles(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.añadir_asignatura(addScaled);
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.editar_asignatura(addScaled);
						}
					});
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
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		asignaturas_barraLabel.setBackground(Color.decode("#AAC4FF"));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		addScaled.accept(asignaturas_barraLabel);
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.panel_asignaturas(addScaled);
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
		addScaled.accept(btn_volver);
		mipanel.add(btn_volver);
		
		JLabel nombre_materia = new JLabel("Nombre de la materia:     Programación");
		nombre_materia.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_materia.setBounds(111, 243, 400, 29);
		addScaled.accept(nombre_materia);
		mipanel.add(nombre_materia);
		
		JButton btn_editar = new JButton();
		btn_editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.editar_asignatura(addScaled);
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JLabel docente_cargo = new JLabel("Docente a cargo:     Jonathan Giovanni Soto");
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 460, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
		JLabel id_docente = new JLabel("ID del docente:     12");
		id_docente.setFont(new Font("SansSerif", Font.PLAIN, 22));
		id_docente.setBounds(179, 363, 460, 29);
		addScaled.accept(id_docente);
		mipanel.add(id_docente);
		
		JTextArea descripcion = new JTextArea();
		descripcion.setText("Descripción:    Programación es la disciplina que enseña a diseñar,\r\n                        escribir y depurar instrucciones en un lenguaje que\r\n                        una computadora puede entender para resolver\r\n                        problemas o automatizar tareas.");
		descripcion.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descripcion.setBounds(203, 423, 695, 120);
		addScaled.accept(descripcion);
		mipanel.add(descripcion);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================	
	
	
	public void añadir_asignatura(Consumer<JComponent> addScaled) {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Añadir asignatura");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrGrupos(addScaled);
			}
		});
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		mipanel.add(grupos_barraLabel);
		
		JButton alumnos_barraLabel = new JButton("    Alumnos");
		alumnos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrAlumnos(addScaled);
			}
		});
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrDocentes(addScaled);	
			}
		});
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1168, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.asignaturas(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.detalles(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.añadir_asignatura(addScaled);
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.editar_asignatura(addScaled);
						}
					});
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
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		asignaturas_barraLabel.setBackground(Color.decode("#AAC4FF"));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		addScaled.accept(asignaturas_barraLabel);
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.panel_asignaturas(addScaled);
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
		addScaled.accept(btn_volver);
		mipanel.add(btn_volver);
		
		JLabel nombre_materia = new JLabel("Nombre de la materia:");
		nombre_materia.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_materia.setBounds(111, 243, 220, 29);
		addScaled.accept(nombre_materia);
		mipanel.add(nombre_materia);
		
		JButton btn_crear = new JButton();
		btn_crear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.confirmar_asignaturaCreada(addScaled);
			}
		});
		btn_crear.setText("Crear");
		btn_crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_crear.setBackground(new Color(170, 196, 255));
		btn_crear.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_crear);
		mipanel.add(btn_crear);
		
		JLabel docente_cargo = new JLabel("Docente a cargo:");
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 169, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
		JLabel id_docente = new JLabel("ID del docente:");
		id_docente.setFont(new Font("SansSerif", Font.PLAIN, 22));
		id_docente.setBounds(179, 363, 152, 29);
		addScaled.accept(id_docente);
		mipanel.add(id_docente);
		
		JTextArea descripcion = new JTextArea();
		descripcion.setText("Descripción:");
		descripcion.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descripcion.setBounds(203, 423, 127, 40);
		addScaled.accept(descripcion);
		mipanel.add(descripcion);
		
		JTextField materiaField = new JTextField();
		materiaField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		materiaField.setColumns(10);
		materiaField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		materiaField.setBackground(new Color(217, 217, 217));
		materiaField.setBounds(341, 239, 510, 40);
		addScaled.accept(materiaField);
		mipanel.add(materiaField);
		
		JTextField docenteField = new JTextField();
		docenteField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		docenteField.setColumns(10);
		docenteField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docenteField.setBackground(new Color(217, 217, 217));
		docenteField.setBounds(341, 292, 510, 40);
		addScaled.accept(docenteField);
		mipanel.add(docenteField);
		
		JTextField idField = new JTextField();
		idField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		idField.setColumns(10);
		idField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		idField.setBackground(new Color(217, 217, 217));
		idField.setBounds(341, 352, 510, 40);
		addScaled.accept(idField);
		mipanel.add(idField);
		
		JTextArea descField = new JTextArea();
		descField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		descField.setColumns(10);
		descField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		descField.setBackground(new Color(217, 217, 217));
		descField.setBounds(340, 423, 700, 200);
		addScaled.accept(descField);
		mipanel.add(descField);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================	
	
	
	public void editar_asignatura(Consumer<JComponent> addScaled) {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Editar asignatura");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrGrupos(addScaled);
			}
		});
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		mipanel.add(grupos_barraLabel);
		
		JButton alumnos_barraLabel = new JButton("    Alumnos");
		alumnos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrAlumnos(addScaled);
			}
		});
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_IrDocentes(addScaled);	
			}
		});
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1168, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.asignaturas(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.detalles(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.añadir_asignatura(addScaled);
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.editar_asignatura(addScaled);
						}
					});
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
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		asignaturas_barraLabel.setBackground(Color.decode("#AAC4FF"));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		addScaled.accept(asignaturas_barraLabel);
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.panel_asignaturas(addScaled);
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
		addScaled.accept(btn_volver);
		mipanel.add(btn_volver);
		
		JLabel nombre_materia = new JLabel("Nombre de la materia:");
		nombre_materia.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_materia.setBounds(111, 243, 220, 29);
		addScaled.accept(nombre_materia);
		mipanel.add(nombre_materia);
		
		JButton btn_guardar = new JButton();
		btn_guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.confirmar_asignaturaEditada(addScaled);
			}
		});
		btn_guardar.setText("Guardar");
		btn_guardar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_guardar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_guardar.setBackground(new Color(170, 196, 255));
		btn_guardar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_guardar);
		mipanel.add(btn_guardar);
		
		JLabel docente_cargo = new JLabel("Docente a cargo:");
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 169, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
		JLabel id_docente = new JLabel("ID del docente:");
		id_docente.setFont(new Font("SansSerif", Font.PLAIN, 22));
		id_docente.setBounds(179, 363, 152, 29);
		addScaled.accept(id_docente);
		mipanel.add(id_docente);
		
		JTextArea descripcion = new JTextArea();
		descripcion.setText("Descripción:");
		descripcion.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descripcion.setBounds(203, 423, 127, 40);
		addScaled.accept(descripcion);
		mipanel.add(descripcion);
		
		JTextField materiaField = new JTextField();
		materiaField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		materiaField.setColumns(10);
		materiaField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		materiaField.setBackground(new Color(217, 217, 217));
		materiaField.setBounds(341, 239, 510, 40);
		addScaled.accept(materiaField);
		mipanel.add(materiaField);
		
		JTextField docenteField = new JTextField();
		docenteField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		docenteField.setColumns(10);
		docenteField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docenteField.setBackground(new Color(217, 217, 217));
		docenteField.setBounds(341, 292, 510, 40);
		addScaled.accept(docenteField);
		mipanel.add(docenteField);
		
		JTextField idField = new JTextField();
		idField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		idField.setColumns(10);
		idField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		idField.setBackground(new Color(217, 217, 217));
		idField.setBounds(341, 352, 510, 40);
		addScaled.accept(idField);
		mipanel.add(idField);
		
		JTextArea descField = new JTextArea();
		descField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		descField.setColumns(10);
		descField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		descField.setBackground(new Color(217, 217, 217));
		descField.setBounds(340, 423, 700, 200);
		addScaled.accept(descField);
		mipanel.add(descField);
		
		JButton btn_basura = new JButton();
		btn_basura.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.alerta_eliminar(addScaled);
			}
		});
		btn_basura.setOpaque(false);
		btn_basura.setIcon(new ImageIcon(getClass().getResource("/img/basura.png")));
		btn_basura.setHorizontalAlignment(SwingConstants.LEFT);
		btn_basura.setForeground(Color.WHITE);
		btn_basura.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_basura.setBorder(BorderFactory.createLineBorder(Color.decode("#27548A"),3));
		btn_basura.setBackground(new Color(238, 241, 255));
		btn_basura.setBounds(1447, 700, 65, 74);
		addScaled.accept(btn_basura);
		mipanel.add(btn_basura);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================	
	
	
	public void detalles_ingles(Consumer<JComponent> addScaled) {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Detalles de la asignatura");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JLabel grupos_barraLabel = new JLabel("    Grupos");
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		mipanel.add(grupos_barraLabel);
		
		JLabel alumnos_barraLabel = new JLabel("    Alumnos");
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JLabel docentes_barraLabel = new JLabel(" Docentes");
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1168, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.asignaturas(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.detalles(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.añadir_asignatura(addScaled);
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.editar_asignatura(addScaled);
						}
					});
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
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.asignaturas(addScaled);
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
		addScaled.accept(btn_volver);
		mipanel.add(btn_volver);
		
		JLabel nombre_materia = new JLabel("Nombre de la materia:     Inglés");
		nombre_materia.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_materia.setBounds(111, 243, 400, 29);
		addScaled.accept(nombre_materia);
		mipanel.add(nombre_materia);
		
		JButton btn_editar = new JButton();
		btn_editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.editar_asignatura(addScaled);
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JLabel docente_cargo = new JLabel("Docente a cargo:     Veronica Carrillo y Carrillo");
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 460, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
		JLabel id_docente = new JLabel("ID del docente:     16");
		id_docente.setFont(new Font("SansSerif", Font.PLAIN, 22));
		id_docente.setBounds(179, 363, 460, 29);
		addScaled.accept(id_docente);
		mipanel.add(id_docente);
		
		JTextArea descripcion = new JTextArea();
		descripcion.setText("Descripción:     Inglés se refiere al estudio de la lengua inglesa, \r\n                         incluyendo gramática, vocabulario, pronunciación,\r\n                         y habilidades de comunicación oral y escrita.");
		descripcion.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descripcion.setBounds(203, 423, 695, 120);
		addScaled.accept(descripcion);
		mipanel.add(descripcion);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	

	//===========================================================================================================================
	
	
	public void detalles_mate(Consumer<JComponent> addScaled) {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Detalles de la asignatura");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JLabel grupos_barraLabel = new JLabel("    Grupos");
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		grupos_barraLabel.setBounds(372, 0, 263, 102);
		addScaled.accept(grupos_barraLabel);
		mipanel.add(grupos_barraLabel);
		
		JLabel alumnos_barraLabel = new JLabel("    Alumnos");
		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JLabel docentes_barraLabel = new JLabel(" Docentes");
		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docentes_barraLabel.setBackground(new Color(238, 241, 255));
		docentes_barraLabel.setBounds(894, 0, 263, 102);
		addScaled.accept(docentes_barraLabel);
		mipanel.add(docentes_barraLabel);
		
		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
		asignaturas_barraLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(1168, 101, 386, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.asignaturas(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 386, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.detalles(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 386, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.añadir_asignatura(addScaled);
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 386, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AsignaturasView.this.editar_asignatura(addScaled);
						}
					});
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
		mipanel.add(asignaturas_barraLabel);
		
		JLabel fondo_barra = new JLabel();
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#EEF1FF"));
		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra.setBounds(0, 0, 1540, 102);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.asignaturas(addScaled);
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
		addScaled.accept(btn_volver);
		mipanel.add(btn_volver);
		
		JLabel nombre_materia = new JLabel("Nombre de la materia:     Matemáticas");
		nombre_materia.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_materia.setBounds(111, 243, 400, 29);
		addScaled.accept(nombre_materia);
		mipanel.add(nombre_materia);
		
		JButton btn_editar = new JButton();
		btn_editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.editar_asignatura(addScaled);
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JLabel docente_cargo = new JLabel("Docente a cargo:     Arturo Fimbres Villegas");
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 460, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
		JLabel id_docente = new JLabel("ID del docente:     20");
		id_docente.setFont(new Font("SansSerif", Font.PLAIN, 22));
		id_docente.setBounds(179, 363, 460, 29);
		addScaled.accept(id_docente);
		mipanel.add(id_docente);
		
		JTextArea descripcion = new JTextArea();
		descripcion.setText("Descripción:     Matemáticas se definen como la ciencia que\r\n                         estudia las relaciones entre cantidades, magnitudes\r\n                         y propiedades abstractas, utilizando la lógica para\r\n                         deducir relaciones desconocidas.");
		descripcion.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descripcion.setBounds(203, 423, 695, 120);
		addScaled.accept(descripcion);
		mipanel.add(descripcion);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================
	
	
	public void alerta_eliminar(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Alerta", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¿Desea eliminar esta asignatura?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(227, 10, 320, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_si = new JButton();
		btn_si.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();	
				AsignaturasView.this.confirmar_eliminarDocente(addScaled);
			}
		});
		btn_si.setForeground(new Color(255, 255, 255));
		btn_si.setText("Si");
		btn_si.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_si.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_si.setBackground(Color.decode("#02A115"));
		btn_si.setBounds(410, 250, 123, 40);
		alerta_panel.add(btn_si);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/alerta.png")));
		alerta_img.setBounds(339, 97, 95, 84);
		alerta_panel.add(alerta_img);
		
		JButton btn_no = new JButton();
		btn_no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();	
			}
		});
		btn_no.setText("No");
		btn_no.setForeground(Color.WHITE);
		btn_no.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_no.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_no.setBackground(new Color(255, 0, 0));
		btn_no.setBounds(215, 250, 123, 40);
		alerta_panel.add(btn_no);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	
	//===========================================================================================================================
	
	
	public void confirmar_eliminarDocente(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Confirmar", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Asignatura editada con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(225, 56, 294, 51);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				ventana.dispose();
				AuthController ac = new AuthController();
				ac.administrador(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de administrador");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		JLabel mensajeLabel_1 = new JLabel("¿Qué opción desea hacer?");
		mensajeLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 18));
		mensajeLabel_1.setBounds(256, 63, 230, 65);
		alerta_panel.add(mensajeLabel_1);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	
	//===========================================================================================================================
	
	
	public void confirmar_asignaturaEditada(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Confirmar", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Asignatura editada con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(225, 56, 294, 51);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				AuthController ac = new AuthController();
				ac.administrador(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de administrador");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(333, 133, 70, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	
	//===========================================================================================================================
	
	
	public void confirmar_asignaturaCreada(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Confirmar", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Asignatura creada con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(225, 56, 294, 51);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				AuthController ac = new AuthController();
				ac.administrador(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de administrador");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(333, 133, 70, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	
	//===========================================================================================================================
	
	
	public void alerta_IrGrupos(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Confirmar", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¿Desea dirigirse a la sección \"Grupos\"?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(170, 10, 414, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_aceptar = new JButton();
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				GruposController ac = new GruposController();
				ac.grupos(addScaled);
			}
		});
		btn_aceptar.setText("Aceptar");
		btn_aceptar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_aceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_aceptar.setBackground(new Color(170, 196, 255));
		btn_aceptar.setBounds(435, 250, 192, 40);
		alerta_panel.add(btn_aceptar);
		
		JButton btn_no = new JButton();
		btn_no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
		btn_no.setText("No, volver");
		btn_no.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_no.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_no.setBackground(new Color(170, 196, 255));
		btn_no.setBounds(125, 250, 192, 40);
		alerta_panel.add(btn_no);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		alerta_img.setBounds(339, 97, 95, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	
	//===========================================================================================================================

	
	public void alerta_IrAlumnos(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Confirmar", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¿Desea dirigirse a la sección \"Alumnos\"?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(167, 10, 414, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_aceptar = new JButton();
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				AlumnosController ac = new AlumnosController();
				ac.alumnos(addScaled);
			}
		});
		btn_aceptar.setText("Aceptar");
		btn_aceptar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_aceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_aceptar.setBackground(new Color(170, 196, 255));
		btn_aceptar.setBounds(435, 250, 192, 40);
		alerta_panel.add(btn_aceptar);
		
		JButton btn_no = new JButton();
		btn_no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
		btn_no.setText("No, volver");
		btn_no.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_no.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_no.setBackground(new Color(170, 196, 255));
		btn_no.setBounds(125, 250, 192, 40);
		alerta_panel.add(btn_no);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
		alerta_img.setBounds(339, 97, 95, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	//===========================================================================================================================

	
	public void alerta_IrDocentes(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Confirmar", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¿Desea dirigirse a la sección \"Docentes\"?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(167, 10, 414, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_aceptar = new JButton();
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				DocentesController dc = new DocentesController();
				dc.docentes(addScaled);
			}
		});
		btn_aceptar.setText("Aceptar");
		btn_aceptar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_aceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_aceptar.setBackground(new Color(170, 196, 255));
		btn_aceptar.setBounds(435, 250, 192, 40);
		alerta_panel.add(btn_aceptar);
		
		JButton btn_no = new JButton();
		btn_no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
		btn_no.setText("No, volver");
		btn_no.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_no.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_no.setBackground(new Color(170, 196, 255));
		btn_no.setBounds(125, 250, 192, 40);
		alerta_panel.add(btn_no);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
		alerta_img.setBounds(339, 97, 95, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	
	
	
	//===========================================================================================================================
	
	
	public void remover() {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
	}
}
