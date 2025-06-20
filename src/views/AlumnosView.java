package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;

import PDF.PDFGenerator;
import aplication.LetterDocumentFilter;
import aplication.NumericDocumentFilter;
import aplication.ScalableUtils;
import aplication.TextDocumentFilter;
import controllers.AlumnosController;
import controllers.AsignaturasController;
import controllers.AuthController;
import controllers.DocentesController;
import controllers.GruposController;
import models.Alumno;
import models.AlumnoModel;
import models.Asignatura;
import models.AuthModel;
import models.Carrera;
import models.CarreraModel;
import models.ConnectionModel;
import models.Grupo;
import models.GruposModel;

public class AlumnosView {
	
	private JTextField amField;
	private String origen;
	private static final int BASE_ANCHO = 1024;
	private static final int BASE_ALTURA = 768;
	private JFrame ventana;
	private JPanel mipanel;
	private JPanel opciones_panel;
	
	public AlumnosView() {
		origen = "añadir";
		
		inicializar();
	}
	
	
	
	
	public void inicializar() {
		//VENTANA PRINCIPAL
		ventana = new JFrame("CONTROL ESCOLAR - ALUMNOS");
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
		
		alumnos(addScaled);
		ventana.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void panel_alumno(Consumer<JComponent> addScaled) {
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
				origen = "editar";
				opciones_panel.setVisible(false);
				AlumnosView.this.numero_control(addScaled);
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
				origen = "editar";
				opciones_panel.setVisible(false);
				AlumnosView.this.numero_control(addScaled);
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
				try {
					AlumnosView.this.añadir_alumno(addScaled);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
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
				try {
					AlumnosView.this.añadir_alumno(addScaled);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
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
				origen = "detalles";
				opciones_panel.setVisible(false);
				AlumnosView.this.numero_control(addScaled);
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
		
		JButton btn_detalles_label = new JButton("Detalles de \r\nalumnos");
		btn_detalles_label.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				origen = "detalles";
				opciones_panel.setVisible(false);
				AlumnosView.this.numero_control(addScaled);
			}
		});
		btn_detalles_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
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
				AlumnosView.this.alumnos(addScaled);	
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
				AlumnosView.this.alumnos(addScaled);
			}
		});
		btn_registros_label.setFont(new Font("SansSerif", Font.PLAIN, 26));
		btn_registros_label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_registros_label.setBackground(Color.decode("#EEF1FF"));
		btn_registros_label.setBounds(127, 556, 263, 58);
		addScaled.accept(btn_registros_label);
		mipanel.add(btn_registros_label);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		
		
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
	
	
	public void alumnos(Consumer<JComponent> addScaled) {
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
		
		JLabel fondo_barra_2 = new JLabel("Registro alumnos");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		asignaturas_barraLabel.setBackground(new Color(238, 241, 255));
		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
		addScaled.accept(asignaturas_barraLabel);
		mipanel.add(asignaturas_barraLabel);
		
		JButton btn_detalles = new JButton();
		btn_detalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.numero_control(addScaled);
			}
		});
		btn_detalles.setText("Detalles");
		btn_detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_detalles.setBackground(new Color(170, 196, 255));
		btn_detalles.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_detalles);
		mipanel.add(btn_detalles);
		
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
				AlumnosView.this.panel_alumno(addScaled);
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
		
		List<Alumno> alumnos = new AlumnoModel().getAll();

		String[] titles = {"No. de control", "Apellido paterno", "Apellido materno", "Nombres"};
		String[][] data = new String[alumnos.size()][4];

		for (int i = 0; i < alumnos.size(); i++) {
		    Alumno a = alumnos.get(i);
		    data[i][1] = a.getPrimer_apellido();
		    data[i][2] = a.getSegundo_apellido();
		    data[i][3] = a.getNombre();
		    data[i][0] = String.valueOf(a.getNo_control());
		}

		JTable table = new JTable(data, titles) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};

		
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.WHITE); 
		header.setForeground(Color.BLACK);
		header.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		header.setFont(new Font("SansSerif", Font.PLAIN, 18));
		header.setPreferredSize(new Dimension(header.getWidth(), 80));
		
		table.setFont(new Font("SansSerif", Font.PLAIN, 18));
		table.setRowHeight(80);
		table.setShowGrid(true);
		table.setBackground(Color.WHITE);
		table.setGridColor(Color.BLACK);
		table.setFillsViewportHeight(true);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
		    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setFont(new Font("SansSerif", Font.PLAIN, 24));
		scroll.setBounds(60, 204, 1430, 480);
		addScaled.accept(scroll);
		mipanel.add(scroll);
		

		
		JButton detalles = new JButton("Detalles");
		detalles.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	opciones_panel.setVisible(false);
				AlumnosView.this.numero_control(addScaled);
		}});
		detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
		detalles.setBackground(Color.decode("#D9D9D9"));
		detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		detalles.setBounds(60, 550, 1386, 50);
		addScaled.accept(detalles);
		opciones_panel.add(detalles);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setFont(new Font("SansSerif", Font.PLAIN, 18));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(58, 146, 1435, 537);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo); 
	}
	
	
	//===========================================================================================================================
	
	
	public void numero_control(Consumer<JComponent> addScaled) {
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
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
				AlumnosView.this.panel_alumno(addScaled);
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
		
		JLabel titulo = new JLabel("Número Control");
		titulo.setFont(new Font("SansSerif", Font.PLAIN, 28));
		titulo.setBounds(688, 243, 208, 58);
		addScaled.accept(titulo);
		mipanel.add(titulo);
		
		
		JLabel numero_control = new JLabel("Ingrese el número de control");
		numero_control.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_control.setBounds(642, 399, 284, 29);
		addScaled.accept(numero_control);
		mipanel.add(numero_control);
		
		JTextField ncField = new JTextField();
		ncField.setBackground(Color.decode("#D9D9D9"));
		ncField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		ncField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		ncField.setBounds(458, 450, 635, 40);
		ncField.setHorizontalAlignment(JLabel.CENTER);
		ncField.setColumns(10);
		((AbstractDocument) ncField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		addScaled.accept(ncField);
		mipanel.add(ncField);
		
		JButton btn_acceder = new JButton();
		btn_acceder.addActionListener(new ActionListener() {
			
			 public void actionPerformed(ActionEvent e) {
				 opciones_panel.setVisible(false);
			        try {
			        	int noControl = Integer.parseInt(ncField.getText());
			            Alumno alumno = AlumnoModel.busca_alumno(noControl); 
			            

			            if (alumno != null) {
			            	
			            	if(origen.equals("editar")) {
			            		editar_alumno(alumno,addScaled);
			            	} else {
			            		informacion_alumno(alumno, addScaled); 			            		
			            	}
			            } else {
			                JOptionPane.showMessageDialog(null, "Alumno no encontrado.");
			            }
			        } catch (NumberFormatException ex) {
			            JOptionPane.showMessageDialog(null, "Número de control inválido.");
			        } catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
		});
		btn_acceder.setText("Acceder");
		btn_acceder.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_acceder.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_acceder.setBackground(new Color(170, 196, 255));
		btn_acceder.setBounds(688, 601, 192, 40);
		addScaled.accept(btn_acceder);
		mipanel.add(btn_acceder);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(332, 210, 890, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	

	
	//===========================================================================================================================
	
	public void informacion_alumno(Alumno alumno, Consumer<JComponent> addScaled) {
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
		
		JLabel fondo_barra_2 = new JLabel("Información del Alumno");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.informacion_alumno(alumno, addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.editar_alumno(alumno, addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
				AlumnosView.this.numero_control(addScaled);
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
		
		JLabel numero_control = new JLabel("Número de control: " + alumno.getNo_control());
		numero_control.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_control.setBounds(111, 243, 700, 29);
		addScaled.accept(numero_control);
		mipanel.add(numero_control);
		
		JLabel apellido_paterno = new JLabel("Apellido paterno: " +alumno.getPrimer_apellido());
		apellido_paterno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		apellido_paterno.setBounds(133, 282, 700, 29);
		addScaled.accept(apellido_paterno);
		mipanel.add(apellido_paterno);
		
		JLabel nombres = new JLabel("Nombres: " + alumno.getNombre());
		nombres.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombres.setBounds(200, 362, 263, 29);
		addScaled.accept(nombres);
		mipanel.add(nombres);
		
		JLabel correo_electronico = new JLabel("Correo electrónico: "+ alumno.getCorreo_electronico());
		correo_electronico.setFont(new Font("SansSerif", Font.PLAIN, 22));
		correo_electronico.setBounds(111, 440, 700, 29);
		addScaled.accept(correo_electronico);
		mipanel.add(correo_electronico);
		
		JButton btn_credencial = new JButton();
		btn_credencial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.credencial_alumno(alumno, addScaled);
			}
		});
		btn_credencial.setText("Credencial");
		btn_credencial.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_credencial.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_credencial.setBackground(new Color(170, 196, 255));
		btn_credencial.setBounds(1220, 534, 192, 40);
		addScaled.accept(btn_credencial);
		mipanel.add(btn_credencial);
		
		JButton btn_pdf = new JButton("Información PDF");
		btn_pdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setDialogTitle("Guardar credencial como PDF");
		        
		        String defaultFileName = "Información Alumno" + alumno.getNo_control() + ".pdf";
		        fileChooser.setSelectedFile(new File(defaultFileName));
		        
		        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
		            @Override
		            public boolean accept(File f) {
		                return f.getName().toLowerCase().endsWith(".pdf") || f.isDirectory();
		            }
		            
		            @Override
		            public String getDescription() {
		                return "Archivos PDF (*.pdf)";
		            }
		        });
		        
		        int userSelection = fileChooser.showSaveDialog(ventana);
		        
		        if (userSelection == JFileChooser.APPROVE_OPTION) {
		            File fileToSave = fileChooser.getSelectedFile();
		            String filePath = fileToSave.getAbsolutePath();
		           
		            if (!filePath.toLowerCase().endsWith(".pdf")) {
		                filePath += ".pdf";
		                fileToSave = new File(filePath);
		            }
		            
		            if (fileToSave.exists()) {
		                int confirm = JOptionPane.showConfirmDialog(ventana,
		                    "El archivo ya existe. ¿Desea reemplazarlo?",
		                    "Confirmar sobrescritura",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.WARNING_MESSAGE);
		                
		                if (confirm != JOptionPane.YES_OPTION) {
		                    return;
		                }
		            }
		            
		            PDFGenerator pdfGenerator = new PDFGenerator();
		            pdfGenerator.generarInformacionPDF(alumno, filePath);
		            		
		        }
			}
		

				

		});
		btn_pdf.setText("Información PDF");
		btn_pdf.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_pdf.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_pdf.setBackground(new Color(170, 196, 255));
		btn_pdf.setBounds(1220, 595, 192, 40);
		addScaled.accept(btn_pdf);
		mipanel.add(btn_pdf);
		
		JLabel fecha_nacimiento = new JLabel("Fecha de nacimiento: "+ alumno.getFecha_nacimiento());
		fecha_nacimiento.setFont(new Font("SansSerif", Font.PLAIN, 22));
		fecha_nacimiento.setBounds(90, 401, 700, 29);
		addScaled.accept(fecha_nacimiento);
		mipanel.add(fecha_nacimiento);
		
		JButton btn_editar = new JButton();
		btn_editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				origen = "editar";
				opciones_panel.setVisible(false);
				try {
					AlumnosView.this.editar_alumno(alumno,addScaled);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JLabel apellido_materno = new JLabel("Apellido materno: "+ alumno.getSegundo_apellido());
		apellido_materno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		apellido_materno.setBounds(127, 323, 700, 29);
		addScaled.accept(apellido_materno);
		mipanel.add(apellido_materno);
		
		JLabel carrera = new JLabel("Grupo: "+ alumno.getCarrera());
		carrera.setFont(new Font("SansSerif", Font.PLAIN, 22));
		carrera.setBounds(216, 475, 700, 29);
		addScaled.accept(carrera);
		mipanel.add(carrera);
		
		JLabel grado_alumno = new JLabel("Grado del alumno: " + alumno.getGrado_alumno());
		grado_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grado_alumno.setBounds(119, 507, 700, 29);
		addScaled.accept(grado_alumno);
		mipanel.add(grado_alumno);
		
		JLabel numero_telefono = new JLabel("Número de teléfono: "+ alumno.getNo_telefono());
		numero_telefono.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_telefono.setBounds(111, 545, 700, 29);
		addScaled.accept(numero_telefono);
		mipanel.add(numero_telefono);
		
		JLabel perfil_alumno = new JLabel("Perfil Alumno");
		perfil_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		perfil_alumno.setBounds(1247, 495, 143, 29);
		addScaled.accept(perfil_alumno);
		mipanel.add(perfil_alumno);
		
		JLabel img_alumno = new JLabel();
		img_alumno.setOpaque(true);
		img_alumno.setBackground(new Color(192, 192, 192));
		img_alumno.setIcon(new ImageIcon(getClass().getResource("/img/img_credencial.png")));
		img_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		img_alumno.setHorizontalAlignment(JLabel.CENTER);
		img_alumno.setBounds(1236, 292, 157, 177);
		addScaled.accept(img_alumno);
		mipanel.add(img_alumno);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	


	
	
	//===========================================================================================================================
	
	
	public void añadir_alumno(Consumer<JComponent> addScaled) throws SQLException {
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
		
		JLabel fondo_barra_2 = new JLabel("Añadir Alumno");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
				AlumnosView.this.panel_alumno(addScaled);
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
		
		JLabel numero_control = new JLabel("Número de control: \r\n");
		numero_control.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_control.setBounds(111, 243, 800, 29);
		addScaled.accept(numero_control);
		mipanel.add(numero_control);
		
		JLabel apellido_paterno = new JLabel("Apellido paterno: ");
		apellido_paterno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		apellido_paterno.setBounds(133, 290, 800, 29);
		addScaled.accept(apellido_paterno);
		mipanel.add(apellido_paterno);
		
		JLabel nombres = new JLabel("Nombres: ");
		nombres.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombres.setBounds(200, 386, 800, 29);
		addScaled.accept(nombres);
		mipanel.add(nombres);
		
		JLabel correo_electronico = new JLabel("Correo electrónico: ");
		correo_electronico.setFont(new Font("SansSerif", Font.PLAIN, 22));
		correo_electronico.setBounds(111, 482, 800, 29);
		addScaled.accept(correo_electronico);
		mipanel.add(correo_electronico);
		

		JLabel fecha_nacimiento = new JLabel("Fecha de nacimiento: ");
		fecha_nacimiento.setFont(new Font("SansSerif", Font.PLAIN, 22));
		fecha_nacimiento.setBounds(90, 434, 800, 29);
		addScaled.accept(fecha_nacimiento);
		mipanel.add(fecha_nacimiento);
		
		
		JLabel apellido_materno = new JLabel("Apellido materno: \r\n");
		apellido_materno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		apellido_materno.setBounds(127, 338, 800, 29);
		addScaled.accept(apellido_materno);
		mipanel.add(apellido_materno);
		
		JLabel carrera = new JLabel("Grupo: ");
		carrera.setFont(new Font("SansSerif", Font.PLAIN, 22));
		carrera.setBounds(216, 530, 800, 29);
		addScaled.accept(carrera);
		mipanel.add(carrera);
		
		JLabel grado_alumno = new JLabel("Grado del alumno: \r\n");
		grado_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grado_alumno.setBounds(119, 578, 800, 29);
		addScaled.accept(grado_alumno);
		mipanel.add(grado_alumno);
		
		JLabel numero_telefono = new JLabel("Número de teléfono: ");
		numero_telefono.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_telefono.setBounds(99, 626, 800, 29);
		addScaled.accept(numero_telefono);
		mipanel.add(numero_telefono);
		
		JLabel perfil_alumno = new JLabel("Perfil Alumno");
		perfil_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		perfil_alumno.setBounds(1247, 495, 143, 29);
		addScaled.accept(perfil_alumno);
		mipanel.add(perfil_alumno);
		

		
		JLabel img_alumno = new JLabel();
		img_alumno.setOpaque(true);
		img_alumno.setBackground(new Color(192, 192, 192));
		img_alumno.setIcon(new ImageIcon(getClass().getResource("/img/img_credencial.png")));
		img_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		img_alumno.setHorizontalAlignment(JLabel.CENTER);
		img_alumno.setBounds(1236, 292, 157, 177);
		addScaled.accept(img_alumno);
		mipanel.add(img_alumno);
		
		JTextField numero_controlField = new JTextField();
		numero_controlField.setBackground(Color.decode("#D9D9D9"));
		numero_controlField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		numero_controlField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		numero_controlField.setBounds(311, 239, 453, 40);
		numero_controlField.setColumns(10);
		((AbstractDocument) numero_controlField.getDocument()).setDocumentFilter(new NumericDocumentFilter(10));
		addScaled.accept(numero_controlField);
		mipanel.add(numero_controlField);
		
		JTextField apField = new JTextField();
		apField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		apField.setColumns(10);
		apField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		apField.setBackground(new Color(217, 217, 217));
		apField.setBounds(311, 287, 453, 40);
		((AbstractDocument) apField.getDocument()).setDocumentFilter(new LetterDocumentFilter(30));
		addScaled.accept(apField);
		mipanel.add(apField);
		
		amField = new JTextField();
		amField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		amField.setColumns(10);
		amField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		amField.setBackground(new Color(217, 217, 217));
		amField.setBounds(311, 335, 453, 40);
		((AbstractDocument) amField.getDocument()).setDocumentFilter(new LetterDocumentFilter(30));
		addScaled.accept(amField);
		mipanel.add(amField);
		
		JTextField nombresField = new JTextField();
		nombresField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		nombresField.setColumns(10);
		nombresField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		nombresField.setBackground(new Color(217, 217, 217));
		nombresField.setBounds(311, 383, 453, 40);
		((AbstractDocument) nombresField.getDocument()).setDocumentFilter(new LetterDocumentFilter(40));
		addScaled.accept(nombresField);
		mipanel.add(nombresField);
		
		JComboBox<String> carreraCursar = new JComboBox<>();
		carreraCursar.setFont(new Font("SansSerif", Font.PLAIN, 18));
		carreraCursar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		carreraCursar.setBackground(new Color(217, 217, 217));
		carreraCursar.setBounds(311, 527, 453, 40);
		
   	  Connection conn = new ConnectionModel().getConnection();

		
		CarreraModel grupoModel = new CarreraModel(conn);
		List<Carrera> grupo = grupoModel.getAll();
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
		for (Carrera asignatura1 : grupo) {
		    comboModel.addElement(asignatura1.getIdCarrera()+ " - " + asignatura1.getNombre());
		}
		carreraCursar.setModel(comboModel);
		
		if (grupo.isEmpty()) {
			carreraCursar.addItem("No hay docentes disponibles");
		}
		
		addScaled.accept(carreraCursar);
		mipanel.add(carreraCursar);

		
		
		JTextField correoField = new JTextField();
		correoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		correoField.setColumns(10);
		correoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		correoField.setBackground(new Color(217, 217, 217));
		correoField.setBounds(311, 479, 453, 40);
		((AbstractDocument) correoField.getDocument()).setDocumentFilter(new TextDocumentFilter(40));
		addScaled.accept(correoField);
		mipanel.add(correoField);
		
		JComboBox dia = new JComboBox(new Object[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"});
		dia.setFont(new Font("SansSerif", Font.PLAIN, 18));
		dia.setBounds(311, 434, 59, 32);
		addScaled.accept(dia);
		mipanel.add(dia);
		
		JComboBox mes = new JComboBox(new Object[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
		mes.setFont(new Font("SansSerif", Font.PLAIN, 18));
		mes.setBounds(380, 434, 59, 32);
		addScaled.accept(mes);
		mipanel.add(mes);
		
		JComboBox<String> año = new JComboBox<>();
		for (int i = LocalDate.now().getYear(); i >= 1900; i--) {
		    año.addItem(String.valueOf(i));
		}
		año.setFont(new Font("SansSerif", Font.PLAIN, 18));
		año.setBounds(449, 434, 85, 32);
		addScaled.accept(año);
		mipanel.add(año);
		
		String diaSeleccionado = dia.getSelectedItem().toString();
		String mesSeleccionado = mes.getSelectedItem().toString();
		String añoSeleccionado = año.getSelectedItem().toString();

		String fechaNacimientoStr = añoSeleccionado + "-" + mesSeleccionado + "-" + diaSeleccionado;

		Date fechaNacimiento1 = Date.valueOf(fechaNacimientoStr);
		

		
		JTextField gradoField = new JTextField();
		gradoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		gradoField.setColumns(10);
		gradoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		gradoField.setBackground(new Color(217, 217, 217));
		gradoField.setBounds(311, 575, 453, 40);
		((AbstractDocument) gradoField.getDocument()).setDocumentFilter(new NumericDocumentFilter(1));
		addScaled.accept(gradoField);
		mipanel.add(gradoField);
		
		JTextField telefonoField = new JTextField();
		telefonoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		telefonoField.setColumns(10);
		telefonoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		telefonoField.setBackground(new Color(217, 217, 217));
		telefonoField.setBounds(311, 623, 453, 40);
		((AbstractDocument) telefonoField.getDocument()).setDocumentFilter(new NumericDocumentFilter(10));
		addScaled.accept(telefonoField);
		mipanel.add(telefonoField);
		

		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
		
		JButton btn_crear = new JButton();
		btn_crear.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        opciones_panel.setVisible(false);
		        
		        int diaSeleccionado = Integer.parseInt((String) dia.getSelectedItem());
		        int mesSeleccionado = Integer.parseInt((String) mes.getSelectedItem());
		        int añoSeleccionado = Integer.parseInt((String) año.getSelectedItem());
		        
		        
		        String grupoSeleccionado = (String) carreraCursar.getSelectedItem();

		        if (!validarFecha(diaSeleccionado, mesSeleccionado, añoSeleccionado)) {
		            JOptionPane.showMessageDialog(ventana, "La fecha seleccionada no es válida", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		       
		        if (!validarCampos(numero_controlField, apField, nombresField, correoField, gradoField, telefonoField)) {
		            JOptionPane.showMessageDialog(ventana, "Todos los campos son obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
		        
		        if (!validarCorreo(correoField.getText())) {
		            correoField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		            JOptionPane.showMessageDialog(ventana, "Correo electrónico inválido", "Error", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
		        
		        String fechaNacimientoStr = String.format("%04d-%02d-%02d", añoSeleccionado, mesSeleccionado, diaSeleccionado);
		        Date fechaNacimiento = Date.valueOf(fechaNacimientoStr);
		        
		        AlumnoModel am = new AlumnoModel();
		        boolean exito = am.insert(
				    numero_controlField.getText(),
				    nombresField.getText(),   
				    apField.getText(),           
				    amField.getText(),           
				    fechaNacimiento,
				    correoField.getText(),
				    grupoSeleccionado,
				    gradoField.getText(),
				    telefonoField.getText()
				);
				
				if (exito) {
				    AlumnosView.this.confirmar_alumnoCreado(addScaled);
				} else {
				    JOptionPane.showMessageDialog(ventana, "Numero de control ya existente", "Error", JOptionPane.ERROR_MESSAGE);
				    return;
				}
		    }
		});
		btn_crear.setText("Crear");
		btn_crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_crear.setBackground(new Color(170, 196, 255));
		btn_crear.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_crear);
		mipanel.add(btn_crear);
		
	}
	
	
	//===========================================================================================================================
	
	
	public void confirmar_alumnoCreado(Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¡Alumno registrado con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(225, 56, 294, 51);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				AlumnosView.this.panel_alumno(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Alumnos");
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
	
	
	public void alerta_DescargarCredencial(Alumno alumno, Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¿¡Confirma que desea descargar esta credencial!?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(138, 10, 505, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_aceptar = new JButton();
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setDialogTitle("Guardar credencial como PDF");
		        
		        String defaultFileName = "Credencial_" + alumno.getNo_control() + ".pdf";
		        fileChooser.setSelectedFile(new File(defaultFileName));
		        
		        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
		            @Override
		            public boolean accept(File f) {
		                return f.getName().toLowerCase().endsWith(".pdf") || f.isDirectory();
		            }
		            
		            @Override
		            public String getDescription() {
		                return "Archivos PDF (*.pdf)";
		            }
		        });
		        
		        int userSelection = fileChooser.showSaveDialog(ventana);
		        
		        if (userSelection == JFileChooser.APPROVE_OPTION) {
		            File fileToSave = fileChooser.getSelectedFile();
		            String filePath = fileToSave.getAbsolutePath();
		           
		            if (!filePath.toLowerCase().endsWith(".pdf")) {
		                filePath += ".pdf";
		                fileToSave = new File(filePath);
		            }
		            
		            if (fileToSave.exists()) {
		                int confirm = JOptionPane.showConfirmDialog(ventana,
		                    "El archivo ya existe. ¿Desea reemplazarlo?",
		                    "Confirmar sobrescritura",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.WARNING_MESSAGE);
		                
		                if (confirm != JOptionPane.YES_OPTION) {
		                    return;
		                }
		            }
		            
		            PDFGenerator pdfGenerator = new PDFGenerator();
		            pdfGenerator.generarCredencialPDF(alumno, filePath);
		            		
		            AlumnosView.this.confirmar_CredencialDescargada(addScaled);
		        }
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
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/alerta.png")));
		alerta_img.setBounds(339, 97, 95, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void confirmar_CredencialDescargada(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Éxito", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Credencial descargada correctamente!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(188, 10, 389, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
			}
		});
		btn_volver.setText("Volver");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(new Color(170, 196, 255));
		btn_volver.setBounds(281, 250, 192, 40);
		alerta_panel.add(btn_volver);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(339, 97, 70, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void selecciona_img(Consumer<JComponent> addScaled) {
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
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
				try {
					AlumnosView.this.editar_alumno(null, addScaled);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		

		
		JLabel mensaje = new JLabel("¡Seleccione una imagen!");
		mensaje.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensaje.setBounds(654, 602, 245, 29);
		addScaled.accept(mensaje);
		mipanel.add(mensaje);
		
		JButton img = new JButton();
		img.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.confirmar_imgSeleccionada(addScaled, "/img/img1.png");
			}
		});
		img.setBackground(Color.LIGHT_GRAY);
		img.setIcon(new ImageIcon(getClass().getResource("/img/icono_imagen.png")));
		img.setBounds(505, 295, 181, 233);
		addScaled.accept(img);
		mipanel.add(img);
		
		JButton img2 = new JButton();
		img2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.confirmar_imgSeleccionada(addScaled, "/img/img2.png");
			}
		});
		img2.setBackground(Color.LIGHT_GRAY);
		img2.setIcon(new ImageIcon(getClass().getResource("/img/icono_imagen2.png")));
		img2.setBounds(828, 295, 181, 233);
		addScaled.accept(img2);
		mipanel.add(img2);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}

	
	//===========================================================================================================================
	
	
	public void confirmar_imgSeleccionada(Consumer<JComponent> addScaled, String ruta) {
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
		
		JLabel mensajeLabel = new JLabel("¡Por favor, confirme su selección!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(213, 10, 333, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_seleccionar = new JButton();
		btn_seleccionar.setText("Seleccionar otra");
		btn_seleccionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
		btn_seleccionar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_seleccionar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_seleccionar.setBackground(new Color(170, 196, 255));
		btn_seleccionar.setBounds(435, 250, 192, 40);
		alerta_panel.add(btn_seleccionar);
		
		JButton btn_confirmar = new JButton();
		btn_confirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				AlumnosView.this.confirmar_imgColocada(addScaled);
			}
		});
		btn_confirmar.setText("Confirmar");
		btn_confirmar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_confirmar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_confirmar.setBackground(new Color(170, 196, 255));
		btn_confirmar.setBounds(125, 250, 192, 40);
		alerta_panel.add(btn_confirmar);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource(ruta)));
		alerta_img.setBounds(323, 97, 111, 110);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void confirmar_imgColocada(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Éxito", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Imagen colocada con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(240, 10, 277, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				AlumnosView.this.selecciona_img(addScaled);
			}
		});
		btn_volver.setText("Volver");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(new Color(170, 196, 255));
		btn_volver.setBounds(281, 250, 192, 40);
		alerta_panel.add(btn_volver);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(339, 97, 70, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void confirmar_credencialAñadida(Consumer<JComponent> addScaled) {
	    JDialog dialogo = new JDialog(ventana, "Éxito", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Credencial añadida con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(240, 10, 300, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
			}
		});
		btn_volver.setText("Volver");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(new Color(170, 196, 255));
		btn_volver.setBounds(281, 250, 192, 40);
		alerta_panel.add(btn_volver);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(339, 97, 70, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void credencial_alumno(Alumno alumno, Consumer<JComponent> addScaled) {
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
		
		JLabel fondo_barra_2 = new JLabel("Credencial del Alumno");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.informacion_alumno(alumno, addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.editar_alumno(alumno, addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
		        if(origen.equals("editar")) {
		            try {
						AlumnosView.this.editar_alumno(alumno, addScaled);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		        } else {
		            AlumnosView.this.informacion_alumno(alumno, addScaled);
		        }
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
		
		JLabel credencial = new JLabel("CREDENCIAL ESTUDIANTIL");
		credencial.setFont(new Font("SansSerif", Font.PLAIN, 22));
		credencial.setBounds(635, 222, 296, 29);
		addScaled.accept(credencial);
		mipanel.add(credencial);
		
		JLabel alumnoLabel = new JLabel("Alumno: " + alumno.getNombre() + " " + alumno.getPrimer_apellido() + " " + alumno.getSegundo_apellido());
		alumnoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumnoLabel.setBounds(812, 323, 396, 29);
		addScaled.accept(alumnoLabel);
		mipanel.add(alumnoLabel);
		
		JButton btn_credencial = new JButton();
		btn_credencial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_DescargarCredencial(alumno, addScaled);
			}
		});
		btn_credencial.setText("Descargar credencial");
		btn_credencial.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_credencial.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_credencial.setBackground(new Color(170, 196, 255));
		btn_credencial.setBounds(1230, 641, 238, 40);
		addScaled.accept(btn_credencial);
		mipanel.add(btn_credencial);
		

		JLabel numero_control = new JLabel("Número de control: " + alumno.getNo_control());
		numero_control.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_control.setBounds(705, 370, 800, 29);
		addScaled.accept(numero_control);
		mipanel.add(numero_control);
		
		JLabel carrera = new JLabel("Carrera: "+ alumno.getCarrera());
		carrera.setFont(new Font("SansSerif", Font.PLAIN, 22));
		carrera.setBounds(810, 471, 800, 29);
		addScaled.accept(carrera);
		mipanel.add(carrera);
		
		JLabel grado_alumno = new JLabel("Grado del alumno:  " + alumno.getGrado_alumno() + " Semestre");
		grado_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grado_alumno.setBounds(714, 421, 800, 29);
		addScaled.accept(grado_alumno);
		mipanel.add(grado_alumno);
		
		JLabel numero_telefono = new JLabel("Número de teléfono:  " + alumno.getNo_telefono());
		numero_telefono.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_telefono.setBounds(693, 530, 800, 29);
		addScaled.accept(numero_telefono);
		mipanel.add(numero_telefono);
		
		JLabel oficio = new JLabel("Alumno");
		oficio.setFont(new Font("SansSerif", Font.PLAIN, 22));
		oficio.setBounds(152, 510, 101, 29);
		addScaled.accept(oficio);
		mipanel.add(oficio);
		
		JLabel img_alumno = new JLabel();
		img_alumno.setOpaque(true);
		img_alumno.setBackground(new Color(192, 192, 192));
		img_alumno.setIcon(new ImageIcon(getClass().getResource("/img/img_credencial.png")));
		img_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		img_alumno.setHorizontalAlignment(JLabel.CENTER);
		img_alumno.setBounds(116, 323, 157, 177);
		addScaled.accept(img_alumno);
		mipanel.add(img_alumno);
		
		JLabel logo_credencial = new JLabel();
		logo_credencial.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo_credencial.setBounds(1390, 199, 91, 115);
		addScaled.accept(logo_credencial);
		mipanel.add(logo_credencial);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
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

	
	public void alerta_IrAsignaturas(Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¿Desea dirigirse a la sección \"Asignaturas\"?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(167, 10, 440, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_aceptar = new JButton();
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				AsignaturasController ac = new AsignaturasController();
				ac.asignaturas(addScaled);
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
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		alerta_img.setBounds(339, 97, 95, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void añadir_credencial(Consumer<JComponent> addScaled) {
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
		
		JLabel fondo_barra_2 = new JLabel("Credencial del Alumno");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							AlumnosView.this.numero_control(addScaled);
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
				if(origen.equals("añadir")) {
					try {
						AlumnosView.this.añadir_alumno(addScaled);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
				} else {
					try {
						AlumnosView.this.editar_alumno(null, addScaled);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
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
		
		JLabel credencial = new JLabel("CREDENCIAL ESTUDIANTIL");
		credencial.setFont(new Font("SansSerif", Font.PLAIN, 22));
		credencial.setBounds(635, 222, 296, 29);
		addScaled.accept(credencial);
		mipanel.add(credencial);
		
		JLabel alumno = new JLabel("Alumno:");
		alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		alumno.setBounds(812, 323, 83, 29);
		addScaled.accept(alumno);
		mipanel.add(alumno);
		
		JButton btn_imagen = new JButton();
		btn_imagen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.selecciona_img(addScaled);
			}
		});
		btn_imagen.setText("Imagen");
		btn_imagen.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_imagen.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_imagen.setBackground(new Color(170, 196, 255));
		btn_imagen.setBounds(116, 273, 157, 40);
		addScaled.accept(btn_imagen);
		mipanel.add(btn_imagen);
		
		JButton btn_añadir = new JButton();
		btn_añadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.confirmar_credencialAñadida(addScaled);
			}
		});
		btn_añadir.setText("Añadir");
		btn_añadir.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir.setBackground(new Color(170, 196, 255));
		btn_añadir.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_añadir);
		mipanel.add(btn_añadir);
		
		JLabel numero_control = new JLabel("Número de control:");
		numero_control.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_control.setBounds(705, 370, 192, 29);
		addScaled.accept(numero_control);
		mipanel.add(numero_control);
		
		JLabel carrera = new JLabel("Carrera:");
		carrera.setFont(new Font("SansSerif", Font.PLAIN, 22));
		carrera.setBounds(810, 471, 86, 29);
		addScaled.accept(carrera);
		mipanel.add(carrera);
		
		JLabel grado_alumno = new JLabel("Grado del alumno:");
		grado_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grado_alumno.setBounds(714, 421, 184, 29);
		addScaled.accept(grado_alumno);
		mipanel.add(grado_alumno);
		
		JLabel numero_telefono = new JLabel("Número de teléfono:");
		numero_telefono.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_telefono.setBounds(693, 530, 201, 29);
		addScaled.accept(numero_telefono);
		mipanel.add(numero_telefono);
		
		JLabel profesion = new JLabel("Profesión:");
		profesion.setFont(new Font("SansSerif", Font.PLAIN, 22));
		profesion.setBounds(152, 510, 101, 29);
		addScaled.accept(profesion);
		mipanel.add(profesion);
		
		JLabel img_alumno = new JLabel();
		img_alumno.setOpaque(true);
		img_alumno.setBackground(new Color(192, 192, 192));
		img_alumno.setIcon(new ImageIcon(getClass().getResource("/img/img_credencial.png")));
		img_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		img_alumno.setHorizontalAlignment(JLabel.CENTER);
		img_alumno.setBounds(116, 323, 157, 177);
		addScaled.accept(img_alumno);
		mipanel.add(img_alumno);
		
		JLabel logo_credencial = new JLabel();
		logo_credencial.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo_credencial.setBounds(1390, 199, 91, 115);
		addScaled.accept(logo_credencial);
		mipanel.add(logo_credencial);
		
		JTextField profesionField = new JTextField();
		profesionField.setBackground(Color.decode("#D9D9D9"));
		profesionField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		profesionField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		profesionField.setBounds(116, 549, 157, 40);
		profesionField.setColumns(10);
		((AbstractDocument) profesionField.getDocument()).setDocumentFilter(new LetterDocumentFilter());
		addScaled.accept(profesionField);
		mipanel.add(profesionField);
		
		JTextField alumnoField = new JTextField();
		alumnoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		alumnoField.setColumns(10);
		alumnoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		alumnoField.setBackground(new Color(217, 217, 217));
		alumnoField.setBounds(905, 319, 417, 40);
		((AbstractDocument) alumnoField.getDocument()).setDocumentFilter(new LetterDocumentFilter());
		addScaled.accept(alumnoField);
		mipanel.add(alumnoField);
		
		JTextField n_controlField = new JTextField();
		n_controlField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		n_controlField.setColumns(10);
		n_controlField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		n_controlField.setBackground(new Color(217, 217, 217));
		n_controlField.setBounds(905, 366, 417, 40);
		((AbstractDocument) n_controlField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		addScaled.accept(n_controlField);
		mipanel.add(n_controlField);
		
		JTextField gradoField = new JTextField();
		gradoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		gradoField.setColumns(10);
		gradoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		gradoField.setBackground(new Color(217, 217, 217));
		gradoField.setBounds(905, 417, 417, 40);
		((AbstractDocument) gradoField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		addScaled.accept(gradoField);
		mipanel.add(gradoField);
		
		JTextField carreraField = new JTextField();
		carreraField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		carreraField.setColumns(10);
		carreraField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		carreraField.setBackground(new Color(217, 217, 217));
		carreraField.setBounds(905, 467, 417, 40);
		addScaled.accept(carreraField);
		mipanel.add(carreraField);
		
		JTextField n_telefonoField = new JTextField();
		n_telefonoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		n_telefonoField.setColumns(10);
		n_telefonoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		n_telefonoField.setBackground(new Color(217, 217, 217));
		n_telefonoField.setBounds(905, 519, 417, 40);
		((AbstractDocument) n_telefonoField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		addScaled.accept(n_telefonoField);
		mipanel.add(n_telefonoField);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}

	
	//===========================================================================================================================
	
	
	public void alerta_añadirAlumno(Consumer<JComponent> addScaled) {
	   JDialog dialogo = new JDialog(ventana, "Éxito", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Alumno añadido con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(232, 10, 272, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				AlumnosView.this.panel_alumno(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Alumnos");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(334, 113, 70, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void editar_alumno(Alumno alumno, Consumer<JComponent> addScaled) throws SQLException {
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
		
		JLabel fondo_barra_2 = new JLabel("Editar Alumno");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setBackground((Color.decode("#EEF1FF")));
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				alerta_IrControl(addScaled);			
				}
		});
		addScaled.accept(c_escolar_barraLabel);
		mipanel.add(c_escolar_barraLabel);
		
		JButton grupos_barraLabel = new JButton("    Grupos");
		grupos_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrGrupos(addScaled);
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
				opciones_panel.removeAll();
				opciones_panel.revalidate();
				opciones_panel.repaint();
				opciones_panel.setBounds(640, 101, 266, 200);		
				boolean visible = !opciones_panel.isVisible();
		
				opciones_panel.setVisible(visible);
				
				if(visible) {
					
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.alumnos(addScaled);
						}
						
					});
					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
					registros.setBackground(Color.decode("#EEF1FF"));
					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					registros.setBounds(0, 0, 266, 50);
					opciones_panel.add(registros);
					
					JButton detalles = new JButton("Detalles");
					detalles.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							AlumnosView.this.informacion_alumno(alumno, addScaled);
						}
					});
					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
					detalles.setBackground(Color.decode("#EEF1FF"));
					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					detalles.setBounds(0, 50, 266, 50);
					opciones_panel.add(detalles);
					
					JButton crear = new JButton("Crear");
					crear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.añadir_alumno(addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
					crear.setBackground(Color.decode("#EEF1FF"));
					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					crear.setBounds(0, 100, 266, 50);
					opciones_panel.add(crear);
					
					JButton editar = new JButton("Editar");
					editar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							origen = "editar";
							opciones_panel.setVisible(false);
							try {
								AlumnosView.this.editar_alumno(alumno, addScaled);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
					editar.setBackground(Color.decode("#EEF1FF"));
					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					editar.setBounds(0, 150, 266, 50);
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
		alumnos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		alumnos_barraLabel.setBounds(633, 0, 263, 102);
		addScaled.accept(alumnos_barraLabel);
		mipanel.add(alumnos_barraLabel);
		
		JButton docentes_barraLabel = new JButton(" Docentes");
		docentes_barraLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrDocentes(addScaled);
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.alerta_IrAsignaturas(addScaled);
			}
		});
		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		asignaturas_barraLabel.setHorizontalAlignment(JLabel.CENTER);
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
				AlumnosView.this.panel_alumno(addScaled);
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
		
		JLabel numero_control = new JLabel("Número de control: ");
		numero_control.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_control.setBounds(111, 243, 218, 29);
		addScaled.accept(numero_control);
		mipanel.add(numero_control);
		
		JLabel apellido_paterno = new JLabel("Apellido paterno: ");
		apellido_paterno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		apellido_paterno.setBounds(133, 290, 196, 29);
		addScaled.accept(apellido_paterno);
		mipanel.add(apellido_paterno);
		
		JLabel nombres = new JLabel("Nombres: " );
		nombres.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombres.setBounds(200, 386, 129, 29);
		addScaled.accept(nombres);
		mipanel.add(nombres);
		
		JLabel correo_electronico = new JLabel("Correo electrónico: " );
		correo_electronico.setFont(new Font("SansSerif", Font.PLAIN, 22));
		correo_electronico.setBounds(111, 482, 218, 29);
		addScaled.accept(correo_electronico);
		mipanel.add(correo_electronico);
		

		
		JLabel fecha_nacimiento = new JLabel("Fecha de nacimiento: " );
		fecha_nacimiento.setFont(new Font("SansSerif", Font.PLAIN, 22));
		fecha_nacimiento.setBounds(90, 434, 239, 29);
		addScaled.accept(fecha_nacimiento);
		mipanel.add(fecha_nacimiento);
		
		
		JLabel apellido_materno = new JLabel("Apellido materno: " );
		apellido_materno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		apellido_materno.setBounds(127, 338, 202, 29);
		addScaled.accept(apellido_materno);
		mipanel.add(apellido_materno);
		
		JLabel carrera = new JLabel("Carrera: " );
		carrera.setFont(new Font("SansSerif", Font.PLAIN, 22));
		carrera.setBounds(216, 530, 113, 29);
		addScaled.accept(carrera);
		mipanel.add(carrera);
		
		JLabel grado_alumno = new JLabel("Grado del alumno: ");
		grado_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		grado_alumno.setBounds(119, 578, 210, 29);
		addScaled.accept(grado_alumno);
		mipanel.add(grado_alumno);
		
		JLabel numero_telefono = new JLabel("Número de teléfono: ");
		numero_telefono.setFont(new Font("SansSerif", Font.PLAIN, 22));
		numero_telefono.setBounds(99, 626, 218, 29);
		addScaled.accept(numero_telefono);
		mipanel.add(numero_telefono);
		
		JLabel perfil_alumno = new JLabel("Perfil Alumno");
		perfil_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		perfil_alumno.setBounds(1247, 495, 143, 29);
		addScaled.accept(perfil_alumno);
		mipanel.add(perfil_alumno);
		

		
		JLabel img_alumno = new JLabel();
		img_alumno.setOpaque(true);
		img_alumno.setBackground(new Color(192, 192, 192));
//		img_alumno.setText("SIN FOTO");
		img_alumno.setIcon(new ImageIcon(getClass().getResource("/img/img_credencial.png")));
		img_alumno.setFont(new Font("SansSerif", Font.PLAIN, 22));
		img_alumno.setHorizontalAlignment(JLabel.CENTER);
		img_alumno.setBounds(1236, 292, 157, 177);
		addScaled.accept(img_alumno);
		mipanel.add(img_alumno);
		
		Long telefono = alumno.getNo_control();
		
		JTextField numero_controlField = new JTextField(String.valueOf(telefono));
		numero_controlField.setBackground(Color.GRAY);
		numero_controlField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		numero_controlField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		numero_controlField.setForeground(Color.WHITE);
		numero_controlField.setBounds(311, 239, 453, 40);
		numero_controlField.setEditable(false);
		numero_controlField.setColumns(10);
		((AbstractDocument) numero_controlField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		addScaled.accept(numero_controlField);
		mipanel.add(numero_controlField);
		
		JTextField apField = new JTextField(alumno.getPrimer_apellido());
		apField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		apField.setColumns(10);
		apField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		apField.setBackground(new Color(217, 217, 217));
		apField.setBounds(311, 287, 453, 40);
		((AbstractDocument) apField.getDocument()).setDocumentFilter(new LetterDocumentFilter(30));
		addScaled.accept(apField);
		mipanel.add(apField);
		
		amField = new JTextField(alumno.getSegundo_apellido());
		amField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		amField.setColumns(10);
		amField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		amField.setBackground(new Color(217, 217, 217));
		amField.setBounds(311, 335, 453, 40);
		((AbstractDocument) amField.getDocument()).setDocumentFilter(new LetterDocumentFilter(30));
		addScaled.accept(amField);
		mipanel.add(amField);
		
		JTextField nombresField = new JTextField(alumno.getNombre());
		nombresField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		nombresField.setColumns(10);
		nombresField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		nombresField.setBackground(new Color(217, 217, 217));
		nombresField.setBounds(311, 383, 453, 40);
		((AbstractDocument) nombresField.getDocument()).setDocumentFilter(new LetterDocumentFilter(40));
		addScaled.accept(nombresField);
		mipanel.add(nombresField);
		
		
		
	   	 Connection conn = new ConnectionModel().getConnection();

		

	   	JComboBox<String> carreraCursar = new JComboBox<>();
	   	carreraCursar.setFont(new Font("SansSerif", Font.PLAIN, 18));
	   	carreraCursar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
	   	carreraCursar.setBackground(new Color(217, 217, 217));
	   	carreraCursar.setBounds(311, 527, 453, 40);

	   	CarreraModel carreraModel = new CarreraModel(conn);
	   	List<Carrera> carreras = carreraModel.getAll();

	   	DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
	   	for (Carrera carrera1 : carreras) {
	   	    comboModel.addElement(carrera1.getIdCarrera() + " - " + carrera1.getNombre());
	   	}
	   	carreraCursar.setModel(comboModel);

	   	if (alumno != null && alumno.getCarrera() != null) {
	   	    String carreraActual = alumno.getCarrera();
	   	    for (int i = 0; i < carreraCursar.getItemCount(); i++) {
	   	        String item = carreraCursar.getItemAt(i);
	   	        String nombreCarrera = item.split(" - ")[1]; 
	   	        /*if (nombreCarrera.equalsIgnoreCase(carreraActual)) {
	   	            carreraCursar.setSelectedIndex(i);
	   	            break;
	   	        }*/
	   	     if (item.equalsIgnoreCase(carreraActual)) {
	   	      carreraCursar.setSelectedIndex(i);
	   	      break;
	   	  }
	   	    }
	   	}

	   	if (carreras.isEmpty()) {
	   	    carreraCursar.addItem("No hay carreras disponibles");
	   	}

	   	addScaled.accept(carreraCursar);
	   	mipanel.add(carreraCursar);
	   	
		JTextField correoField = new JTextField(alumno.getCorreo_electronico());
		correoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		correoField.setColumns(10);
		correoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		correoField.setBackground(new Color(217, 217, 217));
		correoField.setBounds(311, 479, 453, 40);
		((AbstractDocument) correoField.getDocument()).setDocumentFilter(new TextDocumentFilter(40));
		addScaled.accept(correoField);
		mipanel.add(correoField);
		
		JComboBox dia = new JComboBox(new Object[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"});
		dia.setFont(new Font("SansSerif", Font.PLAIN, 18));
		dia.setBounds(311, 434, 59, 32);
		addScaled.accept(dia);
		mipanel.add(dia);
		
		JComboBox mes = new JComboBox(new Object[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
		mes.setFont(new Font("SansSerif", Font.PLAIN, 18));
		mes.setBounds(380, 434, 59, 32);
		addScaled.accept(mes);
		mipanel.add(mes);
		
		JComboBox<String> año = new JComboBox<>();
		for (int i = LocalDate.now().getYear(); i >= 1900; i--) {
		    año.addItem(String.valueOf(i));
		}
		año.setFont(new Font("SansSerif", Font.PLAIN, 18));
		año.setBounds(449, 434, 85, 32);
		addScaled.accept(año);
		mipanel.add(año);
		
		if (alumno != null && alumno.getFecha_nacimiento() != null) {
		    LocalDate fechaAlumno = alumno.getFecha_nacimiento().toLocalDate();
		    dia.setSelectedItem(String.format("%02d", fechaAlumno.getDayOfMonth()));
		    mes.setSelectedItem(String.format("%02d", fechaAlumno.getMonthValue()));
		    año.setSelectedItem(String.valueOf(fechaAlumno.getYear()));
		} else {
		    dia.setSelectedItem("01");
		    mes.setSelectedItem("01");
		    año.setSelectedItem("2000");
		}
		
		String diaSeleccionado = (String) dia.getSelectedItem();
		String mesSeleccionado = (String) mes.getSelectedItem();
		String añoSeleccionado = (String) año.getSelectedItem();

		String fechaSQL = añoSeleccionado + "-" + mesSeleccionado + "-" + diaSeleccionado;
		
		JTextField gradoField = new JTextField(alumno.getGrado_alumno());
		gradoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		gradoField.setColumns(10);
		gradoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		gradoField.setBackground(new Color(217, 217, 217));
		gradoField.setBounds(311, 575, 453, 40);
		((AbstractDocument) gradoField.getDocument()).setDocumentFilter(new NumericDocumentFilter(1));
		addScaled.accept(gradoField);
		mipanel.add(gradoField);
		
		Long numero = alumno.getNo_telefono();
		
		JTextField telefonoField = new JTextField(String.valueOf(numero));
		telefonoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		telefonoField.setColumns(10);
		telefonoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		telefonoField.setBackground(new Color(217, 217, 217));
		telefonoField.setBounds(311, 623, 453, 40);
		((AbstractDocument) telefonoField.getDocument()).setDocumentFilter(new NumericDocumentFilter(10));
		addScaled.accept(telefonoField);
		mipanel.add(telefonoField);
		
		JButton btn_guardar = new JButton();
		btn_guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);

				try {
					int diaSeleccionado = Integer.parseInt((String) dia.getSelectedItem());
					int mesSeleccionado = Integer.parseInt((String) mes.getSelectedItem());
					int añoSeleccionado = Integer.parseInt((String) año.getSelectedItem());
					
			        String grupoSeleccionado = (String) carreraCursar.getSelectedItem();


					if (!validarFecha(diaSeleccionado, mesSeleccionado, añoSeleccionado)) {
						JOptionPane.showMessageDialog(ventana, "La fecha seleccionada no es válida", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (!validarCorreo(correoField.getText())) {
						correoField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
						JOptionPane.showMessageDialog(ventana, "Correo electrónico inválido", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if (!validarCampos(numero_controlField, apField, nombresField, correoField, gradoField, telefonoField)) {
						JOptionPane.showMessageDialog(ventana, "Todos los campos son obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}

					String fechaNacimientoStr = String.format("%04d-%02d-%02d", añoSeleccionado, mesSeleccionado, diaSeleccionado);
					Date fechaNacimiento = Date.valueOf(fechaNacimientoStr);

					Alumno alumno = new Alumno();
					alumno.setNo_control(Integer.parseInt(numero_controlField.getText())); // ← este sí
					alumno.setNombre(nombresField.getText());
					alumno.setPrimer_apellido(apField.getText());
					alumno.setSegundo_apellido(amField.getText());
					alumno.setFecha_nacimiento(fechaNacimiento);
					alumno.setCorreo_electronico(correoField.getText());
					alumno.setCarrera(grupoSeleccionado);
					alumno.setGrado_alumno(gradoField.getText());
					alumno.setNo_telefono(Long.parseLong(telefonoField.getText()));

					AlumnoModel am = new AlumnoModel();
					boolean exito = am.update(alumno);

					if (exito) {
						AlumnosView.this.confirmar_alumnoCreado(addScaled);
					} else {
						JOptionPane.showMessageDialog(ventana, "Error al actualizar el alumno", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(ventana, "Error: " + ex.getMessage(), "Excepción", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btn_guardar.setText("Guardar");
		btn_guardar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_guardar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_guardar.setBackground(new Color(170, 196, 255));
		btn_guardar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_guardar);
		mipanel.add(btn_guardar);
		
		

		
		JButton btn_credencial = new JButton();
		btn_credencial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AlumnosView.this.credencial_alumno(alumno ,addScaled);
			}
		});
		btn_credencial.setText("Credencial");
		btn_credencial.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_credencial.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_credencial.setBackground(new Color(170, 196, 255));
		btn_credencial.setBounds(1220, 534, 192, 40);
		addScaled.accept(btn_credencial);
		mipanel.add(btn_credencial);
		
		JButton btn_pdf = new JButton("Información PDF");
		btn_pdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setDialogTitle("Guardar credencial como PDF");
		        
		        String defaultFileName = "Información Alumno" + alumno.getNo_control() + ".pdf";
		        fileChooser.setSelectedFile(new File(defaultFileName));
		        
		        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
		            @Override
		            public boolean accept(File f) {
		                return f.getName().toLowerCase().endsWith(".pdf") || f.isDirectory();
		            }
		            
		            @Override
		            public String getDescription() {
		                return "Archivos PDF (*.pdf)";
		            }
		        });
		        
		        int userSelection = fileChooser.showSaveDialog(ventana);
		        
		        if (userSelection == JFileChooser.APPROVE_OPTION) {
		            File fileToSave = fileChooser.getSelectedFile();
		            String filePath = fileToSave.getAbsolutePath();
		           
		            if (!filePath.toLowerCase().endsWith(".pdf")) {
		                filePath += ".pdf";
		                fileToSave = new File(filePath);
		            }
		            
		            if (fileToSave.exists()) {
		                int confirm = JOptionPane.showConfirmDialog(ventana,
		                    "El archivo ya existe. ¿Desea reemplazarlo?",
		                    "Confirmar sobrescritura",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.WARNING_MESSAGE);
		                
		                if (confirm != JOptionPane.YES_OPTION) {
		                    return;
		                }
		            }
		            
		            PDFGenerator pdfGenerator = new PDFGenerator();
		            pdfGenerator.generarInformacionPDF(alumno, filePath);
		            		
		        }
			}
		

				

		});
		btn_pdf.setText("Información PDF");
		btn_pdf.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_pdf.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_pdf.setBackground(new Color(170, 196, 255));
		btn_pdf.setBounds(1220, 595, 192, 40);
		addScaled.accept(btn_pdf);
		mipanel.add(btn_pdf);
		
		JButton btn_basura = new JButton();
		btn_basura.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				 

			            AlumnosView.this.alerta_eliminar(alumno, addScaled);


			        
			    
				
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
	
	
	private boolean validarCampos(JTextField... campos) {
	    boolean todosValidos = true;
	    
	    for(JTextField campo : campos) {
	        if(campo != amField && campo.getText().trim().isEmpty()) {
	            campo.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	            todosValidos = false;
	        } else {
	            campo.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
	            amField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
	        }
	    }
	    
	    if(!todosValidos) {
	        JOptionPane.showMessageDialog(ventana, "Por favor complete todos los campos obligatorios", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
	    }
	    
	    return todosValidos;
	}
	
	//===========================================================================================================================
	
	
	public void alerta_eliminar(Alumno alumno, Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¿Desea eliminar este alumno?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(227, 10, 320, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_si = new JButton();
		btn_si.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				 
			            int idAlumno = alumno.getIdAlumno(); 



			            Alumno alumno = new Alumno(idAlumno, null, origen, origen, origen, null, origen, origen, null, origen);
			            boolean eliminado = AlumnoModel.remove(idAlumno);
						


			            if (eliminado) {
							AlumnosView.this.confirmar_eliminarAlumno(addScaled);

			            	} else {
			                JOptionPane.showMessageDialog(null, "Error al eliminar el alumno.");
			            }
			        
			    
				
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

	
	public void confirmar_eliminarAlumno(Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¡Alumno eliminado con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(231, 43, 324, 51);
		alerta_panel.add(mensajeLabel);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(334, 113, 70, 84);
		alerta_panel.add(alerta_img);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				AlumnosView.this.panel_alumno(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Alumnos");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}

	
	//===========================================================================================================================
	
	
	public void confirmar_crearAlumno(Consumer<JComponent> addScaled) {
	   JDialog dialogo = new JDialog(ventana, "Éxito", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
	    
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¡Alumno editado con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(232, 10, 272, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				AlumnosView.this.panel_alumno(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Alumnos\r\n");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(334, 113, 70, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	public void alerta_IrControl(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¿Desea regresar al administrador?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(167, 10, 414, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_aceptar = new JButton();
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				AuthController ac = new AuthController();
				ac.administrador(addScaled);
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
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		alerta_img.setBounds(339, 97, 95, 84);
		alerta_panel.add(alerta_img);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	public boolean validarFecha(int dia, int mes, int año) {
		   try {
		        if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
		            return false;
		        }

		        if (mes == 2) {
		            if (dia > 29) {
		                return false;
		            }
		            if (dia == 29) {
		                if (!(año % 4 == 0 && (año % 100 != 0 || año % 400 == 0))) {
		                    return false;
		                }
		            }
		        }
		       
		        if (mes < 1 || mes > 12) {
		            return false;
		        }
		        
		        if (dia < 1 || dia > 31) {
		            return false;
		        }
		        
		        return true;
		    } catch (NumberFormatException e) {
		        return false;
		    }
	}
	
	private boolean validarCorreo(String correo) {
	    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
	    return correo.matches(regex);
	}
	
	
	
	public void remover() {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
	}

}