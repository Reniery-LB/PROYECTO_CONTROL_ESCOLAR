package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;

import PDF.PDFGenerator;
import aplication.ScalableUtils;
import aplication.TextDocumentFilter;
import controllers.AlumnosController;
import controllers.AsignaturasController;
import controllers.AuthController;
import controllers.DocentesController;
import models.Alumno;
import models.AlumnoModel;
import models.Asignatura;
import models.AsignaturasModel;
import models.ConnectionModel;
import models.Docente;
import models.DocentesModel;
import models.Grupo;
import models.GruposModel;
import models.alumno_has_grupoModel;

public class GruposView {
	
	private static final int BASE_ANCHO = 1024;
	private static final int BASE_ALTURA = 768;
	private JFrame ventana;
	private JPanel mipanel;
    List<Alumno> alumnosEnGrupo = new ArrayList<>();
    List<Alumno> alumnos = new AlumnoModel().getAll();
    Connection conn = new ConnectionModel().getConnection();
    alumno_has_grupoModel alumnoGrupoModel = new alumno_has_grupoModel(conn);
	private int idGrupo;
	private int añadidos = 0;
	private int eliminados;
	private JTable table;
	private JPanel opciones_panel;
	private  String nombreGrupo;
	private int grupoAEliminarId;
	List<Integer> alumnosSeleccionadosTemporal = new ArrayList<>();
	private String docenteSeleccionado = null;
	private String turnoSeleccionado = null;
	private List<Integer> alumnosTemporales = new ArrayList<>();
	private List<Alumno> alumnosParaEliminar = new ArrayList<>();
	private List<Integer> idsAlumnosEnGrupo = new ArrayList<>();
//	private Grupo grupo; 
	
	
	public GruposView() {
		inicializar();
		
	}
	
	
	public void inicializar() {
		//VENTANA PRINCIPAL
		ventana = new JFrame("CONTROL ESCOLAR - GRUPOS");
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
		
		panel_grupos(addScaled);
		ventana.setVisible(true);
	}
	
	
	//===========================================================================================================================
	
	
	public void panel_grupos(Consumer<JComponent> addScaled) {
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
				GruposView.this.mostrar_lista_editar(addScaled);
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
				GruposView.this.mostrar_lista_editar(addScaled);
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
				GruposView.this.añadir_grupo(addScaled, null, nombreGrupo);	
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
				GruposView.this.añadir_grupo(addScaled, null, nombreGrupo);
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
				GruposView.this.detalles_grupos(addScaled);
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
		
		JButton btn_detalles_label = new JButton("Detalles de \r\ngrupos");
		btn_detalles_label.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.detalles_grupos(addScaled);	
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
				GruposView.this.detalles_grupos(addScaled);	
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
				GruposView.this.detalles_grupos(addScaled);
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
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							GruposView.this.detalles_grupos(addScaled);
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
							GruposView.this.añadir_grupo(addScaled, null, nombreGrupo);	
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
							opciones_panel.setVisible(false);
							
								GruposView.this.detalles_grupos(addScaled);
						
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
	
	
	public void detalles_grupos(Consumer<JComponent> addScaled) {
		remover();
		opciones_panel.setVisible(false);
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Grupos escolares");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JPanel asignaturasContainer = new JPanel();
		asignaturasContainer.setLayout(new BoxLayout(asignaturasContainer, BoxLayout.Y_AXIS));
		asignaturasContainer.setBackground(Color.decode("#27548A"));
		Connection conn = new ConnectionModel().getConnection();

		 try {
			    GruposModel asignaturaModel = new GruposModel(conn);
			    List<Grupo> asignaturas = asignaturaModel.getAll();

			    for (Grupo asignatura : asignaturas) {
			        JButton btnAsignatura = new JButton(asignatura.getNombreGrupo());
			        btnAsignatura.setFont(new Font("SansSerif", Font.PLAIN, 26));
			        btnAsignatura.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
			        btnAsignatura.setBackground(new Color(170, 196, 255));
			        btnAsignatura.setAlignmentX(Component.CENTER_ALIGNMENT);
			        btnAsignatura.setMaximumSize(new Dimension(1348, 117));

			        btnAsignatura.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                opciones_panel.setVisible(false);
			              
			                try {
			                    informacion_grupos(addScaled, asignatura.getIdGrupo()); 
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

			                
			            }
			        });

			        asignaturasContainer.add(btnAsignatura);
			        asignaturasContainer.add(Box.createRigidArea(new Dimension(0, 10)));
			    }

			} catch (SQLException ex) {
			    ex.printStackTrace();
			    JOptionPane.showMessageDialog(null, "Error al cargar asignaturas: " + ex.getMessage());
			}
		 
		JScrollPane scrollGrupos = new JScrollPane(asignaturasContainer);
		scrollGrupos.setBorder(BorderFactory.createEmptyBorder());
		scrollGrupos.setBounds(93, 274, 1348, 345); 
		scrollGrupos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		addScaled.accept(scrollGrupos);
		mipanel.add(scrollGrupos);
			    
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							try {
								GruposView.this.informacion_grupos(addScaled, idGrupo);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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
							GruposView.this.añadir_grupo(addScaled, null, nombreGrupo);	
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
							opciones_panel.setVisible(false);
							try {
								GruposView.this.editar_grupo(addScaled, idGrupo);
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
				GruposView.this.panel_grupos(addScaled);
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
	
	
	public void añadir_grupo(Consumer<JComponent> addScaled, String letraSeleccionada, String nombreGrupoPrellenado) {
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
		
		JLabel fondo_barra_2 = new JLabel("Añadir grupo");
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
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							GruposView.this.detalles_grupos(addScaled);
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
							GruposView.this.añadir_grupo(addScaled, null, nombreGrupoPrellenado);	
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
							opciones_panel.setVisible(false);
						
								GruposView.this.detalles_grupos(addScaled);
							
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
				GruposView.this.panel_grupos(addScaled);
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
		
		JLabel nombre_grupoLabel = new JLabel("Nombre del grupo:");
		nombre_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_grupoLabel.setBounds(111, 243, 192, 29);
		addScaled.accept(nombre_grupoLabel);
		mipanel.add(nombre_grupoLabel);
		
		JTextField nombre_grupoField = new JTextField();
		nombre_grupoField.setBackground(Color.decode("#D9D9D9"));
		nombre_grupoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		nombre_grupoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		nombre_grupoField.setBounds(313, 239, 386, 40);
		nombre_grupoField.setColumns(10);
		nombre_grupoField.setText(nombreGrupoPrellenado != null ? nombreGrupoPrellenado : "");
		((AbstractDocument) nombre_grupoField.getDocument()).setDocumentFilter(new TextDocumentFilter(30));
		addScaled.accept(nombre_grupoField);
		mipanel.add(nombre_grupoField);
		
		JLabel docente_grupoLabel = new JLabel("Docente del grupo:");
		docente_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_grupoLabel.setBounds(111, 301, 192, 29);
		addScaled.accept(docente_grupoLabel);
		mipanel.add(docente_grupoLabel);
		
	    JComboBox<String> docenteComboBox = new JComboBox<>();
	    docenteComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
	    docenteComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	    docenteComboBox.setBackground(new Color(217, 217, 217));
	    docenteComboBox.setBounds(313, 297, 386, 40);

	    DocentesModel docenteModel1 = new DocentesModel();
	    List<Docente> docentes = docenteModel1.getAll();

	    DefaultComboBoxModel<String> comboDocenteModel = new DefaultComboBoxModel<>();
	    for (Docente docente1 : docentes) {
	        comboDocenteModel.addElement(docente1.getIdDocente() + " - " + docente1.getNombre());
	    }
	    docenteComboBox.setModel(comboDocenteModel);

	    if (docenteSeleccionado != null) {
	        docenteComboBox.setSelectedItem(docenteSeleccionado);
	    }

	    if (docentes.isEmpty()) {
	        docenteComboBox.addItem("No hay docentes disponibles");
	    }

	    addScaled.accept(docenteComboBox);
	    mipanel.add(docenteComboBox);
	    
	    JComboBox<String> turnoComboBox = new JComboBox<>(new String[]{"Matutino", "Vespertino"});
	    turnoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
	    turnoComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	    turnoComboBox.setBackground(new Color(217, 217, 217));
	    turnoComboBox.setBounds(313, 357, 386, 40);

	    if (turnoSeleccionado != null) {
	        turnoComboBox.setSelectedItem(turnoSeleccionado);
	    }

	    addScaled.accept(turnoComboBox);
	    mipanel.add(turnoComboBox);
	    
		
		JLabel añadir_alumnosLabel = new JLabel("Añadir alumnos:");
		añadir_alumnosLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		añadir_alumnosLabel.setBounds(136, 540, 192, 29);
		addScaled.accept(añadir_alumnosLabel);
		mipanel.add(añadir_alumnosLabel);
		
		JButton btn_añadir_alumnos = new JButton();
		btn_añadir_alumnos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		        opciones_panel.setVisible(false);

		        docenteSeleccionado = (String) docenteComboBox.getSelectedItem();
		        turnoSeleccionado = (String) turnoComboBox.getSelectedItem();

		        String nombreActual = nombre_grupoField.getText().trim();
		        GruposView.this.añadir_alumno(addScaled, "crear_grupo", nombreActual);
		    }
		});
		btn_añadir_alumnos.setText(alumnosTemporales.isEmpty() ? "Haz clic aquí" : "Alumnos seleccionados: " + alumnosTemporales.size());
		btn_añadir_alumnos.setBackground(Color.decode("#AAC4FF"));
		btn_añadir_alumnos.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir_alumnos.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir_alumnos.setBounds(313, 534, 386, 40);
		addScaled.accept(btn_añadir_alumnos);
		mipanel.add(btn_añadir_alumnos);
		
	
		
		JLabel periodoLabel = new JLabel("Turno:");
		periodoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		periodoLabel.setBounds(230, 362, 129, 29);
		addScaled.accept(periodoLabel);
		mipanel.add(periodoLabel);

		String[] periodos = {"TV", "TM"};
		JComboBox<String> periodo_comboBox = new JComboBox<>(periodos);
		periodo_comboBox.setBackground(new Color(217, 217, 217));
		periodo_comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		periodo_comboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
		periodo_comboBox.setBounds(313, 358, 386, 40);
		addScaled.accept(periodo_comboBox);
		mipanel.add(periodo_comboBox);
		
		JLabel letra_img = new JLabel();
		letra_img.setIcon(new ImageIcon(getClass().getResource("/img/icono_letraA.png")));
		letra_img.setBounds(1276, 176, 101, 222);
		addScaled.accept(letra_img);
		mipanel.add(letra_img);
		
		JButton btn_crear = new JButton("Crear");
		btn_crear.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        opciones_panel.setVisible(false);

		        nombreGrupo = nombre_grupoField.getText().trim();
		        String turnoSeleccionado = (String) turnoComboBox.getSelectedItem(); 
		        String docenteSeleccionadoLocal = (String) docenteComboBox.getSelectedItem();

		        if (nombreGrupo.isEmpty() || docenteSeleccionadoLocal == null ||
		            docenteSeleccionadoLocal.equals("No hay docentes disponibles")) {

		            nombre_grupoField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		            JOptionPane.showMessageDialog(null, "Por favor completa todos los campos obligatorios.",
		                "Error", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        try (Connection conn = new ConnectionModel().getConnection()) {
		            GruposModel gruposModel = new GruposModel(conn);

		            Grupo.Turno turnoEnum = turnoSeleccionado.equalsIgnoreCase("Matutino")
		                ? Grupo.Turno.TM
		                : Grupo.Turno.TV;

		            if (gruposModel.existeGrupo(nombreGrupo, turnoEnum.name())) {
		                nombre_grupoField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		                JOptionPane.showMessageDialog(null, "Ya existe un grupo con este nombre y turno.",
		                    "Error", JOptionPane.WARNING_MESSAGE);
		                return;
		            }

		            int idDocente = Integer.parseInt(docenteSeleccionadoLocal.split(" - ")[0]);

		            if (!gruposModel.existeDocente(idDocente)) {
		                JOptionPane.showMessageDialog(null, "El docente seleccionado no existe.",
		                    "Error", JOptionPane.ERROR_MESSAGE);
		                return;
		            }

		            Grupo nuevoGrupo = new Grupo();
		            nuevoGrupo.setNombreGrupo(nombreGrupo);
		            nuevoGrupo.setTurno(turnoEnum);
		            nuevoGrupo.setPeriodo("2024");
		            nuevoGrupo.setIdAsignatura(0);
		            nuevoGrupo.setIdDocente(idDocente);

		            boolean grupoCreado = gruposModel.create(nuevoGrupo);

		            if (grupoCreado) {
		                if (!alumnosTemporales.isEmpty()) {
		                    for (int idAlumno : alumnosTemporales) {
		                        gruposModel.agregarAlumnoAGrupo(idAlumno, nuevoGrupo.getIdGrupo());
		                    }
		                }

		                nombre_grupoField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

		                nombreGrupo = null;
		                turnoSeleccionado = null;
		                docenteSeleccionado = null;
		                alumnosTemporales.clear();

		                GruposView.this.confirmar_grupoCreado(addScaled);
		            } else {
		                JOptionPane.showMessageDialog(null, "Error al crear el grupo.",
		                    "Error", JOptionPane.ERROR_MESSAGE);
		            }

		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Error en el formato del ID del docente.",
		                "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage(),
		                "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(),
		                "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
		    }
		});
		btn_crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_crear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		btn_crear.setBackground(new Color(170, 196, 255));
		btn_crear.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_crear);
		mipanel.add(btn_crear);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		fondo_grupo.setBackground(Color.WHITE);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================
	
	

	//===========================================================================================================================
	
	
	public void informacion_grupos(Consumer<JComponent> addScaled, int idGrupo) throws SQLException {
		remover();
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		

	    try (Connection conn = new ConnectionModel().getConnection()) {
	        GruposModel grupoModel = new GruposModel(conn);
	        Grupo grupo = grupoModel.getGrupoById(idGrupo);
	        
	        if (grupo == null) {
	            JOptionPane.showMessageDialog(null, 
	                "No se encontró el grupo con ID: " + idGrupo, 
	                "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    }
		
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
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							try {
								GruposView.this.informacion_grupos(addScaled, idGrupo);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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
							GruposView.this.añadir_grupo(addScaled, null, nombreGrupo);	
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
							opciones_panel.setVisible(false);
							try {
								GruposView.this.editar_grupo(addScaled, idGrupo);
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
				GruposView.this.detalles_grupos(addScaled);
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
		
		JButton btn_editar = new JButton();
		btn_editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				try {
					GruposView.this.editar_grupo(addScaled, idGrupo);
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
		btn_editar.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
	

	    Connection conn = new ConnectionModel().getConnection();

		
	    GruposModel grupoModel = new GruposModel(conn);
	    Grupo grupo = grupoModel.getGrupoById(idGrupo);
	    

	    
	    DocentesModel docenteModel = new DocentesModel();
        Docente docente = DocentesModel.busca_docente(grupo.getIdDocente());
	   
	    alumno_has_grupoModel alumnoGrupoModel = new alumno_has_grupoModel(conn);
	    List<Alumno> alumnos = alumnoGrupoModel.obtenerAlumnosPorGrupo(idGrupo);
	    
	    JLabel titulo = new JLabel(grupo.getNombreGrupo());
	    titulo.setFont(new Font("SansSerif", Font.PLAIN, 24));
	    titulo.setBounds(750, 146, 500, 65); 
	    addScaled.accept(titulo);
	    mipanel.add(titulo);
	    
	    JLabel docenteLabel = new JLabel("Docente a cargo: " + docente.getNombre() + " " + 
	                                   docente.getPrimer_apellido() + " " + 
	                                   (docente.getSegundo_apellido() != null ? docente.getSegundo_apellido() : ""));
	    docenteLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
	    docenteLabel.setBounds(111, 229, 600, 29); 
	    addScaled.accept(docenteLabel);
	    mipanel.add(docenteLabel);
	    
	    JLabel idLabel = new JLabel("ID del docente: " + docente.getIdDocente());
	    idLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
	    idLabel.setBounds(130, 279, 499, 29);
	    addScaled.accept(idLabel);
	    mipanel.add(idLabel);
	    
	    JLabel turnoLabel = new JLabel("Turno: " + grupo.getTurno().getDescripcion());
	    turnoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
	    turnoLabel.setBounds(213, 318, 499, 29);
	    addScaled.accept(turnoLabel);
	    mipanel.add(turnoLabel);
	    
	    String[] titles = {"No. Control", "Apellido paterno", "Apellido materno", "Nombres"};
	    String[][] data = new String[alumnos.size()][4];
	    

	    for (int i = 0; i < alumnos.size(); i++) {
	        Alumno alumno = alumnos.get(i);
	        data[i][1] = alumno.getPrimer_apellido();
	        data[i][2] = alumno.getSegundo_apellido() != null ? alumno.getSegundo_apellido() : "";
	        data[i][3] = alumno.getNombre();
	        data[i][0] = alumno.getNo_control() + "";
	    }
	    
		
		JTable table = new JTable(data,titles) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.GRAY); 
		header.setForeground(Color.WHITE);
		header.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		header.setFont(new Font("SansSerif", Font.PLAIN, 18));
		header.setPreferredSize(new Dimension(header.getWidth(), 50));
		
		table.setFont(new Font("SansSerif", Font.PLAIN, 18));
		table.setRowHeight(40);
		table.setShowGrid(true);
		table.setBackground(Color.decode("#D9D9D9"));
		table.setGridColor(Color.BLACK);
		table.setFillsViewportHeight(true);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
		    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setFont(new Font("SansSerif", Font.PLAIN, 24));
		scroll.setBounds(76, 372, 1396, 234);
		addScaled.accept(scroll);
		mipanel.add(scroll);
		
		JButton btn_descargar = new JButton();
		btn_descargar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        opciones_panel.setVisible(false);
		        
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setDialogTitle("Guardar grupo como PDF");

		        String defaultFileName = "Grupo_" + grupo.getNombreGrupo().replaceAll("\\s+", "_") + ".pdf";
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

		        int userSelection = fileChooser.showSaveDialog(null);

		        if (userSelection == JFileChooser.APPROVE_OPTION) {
		            File fileToSave = fileChooser.getSelectedFile();
		            String filePath = fileToSave.getAbsolutePath();

		            if (!filePath.toLowerCase().endsWith(".pdf")) {
		                filePath += ".pdf";
		                fileToSave = new File(filePath);
		            }

		            if (fileToSave.exists()) {
		                int confirm = JOptionPane.showConfirmDialog(null,
		                        "El archivo ya existe. ¿Desea reemplazarlo?",
		                        "Confirmar sobrescritura",
		                        JOptionPane.YES_NO_OPTION,
		                        JOptionPane.WARNING_MESSAGE);

		                if (confirm != JOptionPane.YES_OPTION) {
		                    return;
		                }
		            }

		            try {
		                alumno_has_grupoModel alumnoGrupoModel = new alumno_has_grupoModel(new ConnectionModel().getConnection());
		                List<Alumno> alumnos = alumnoGrupoModel.obtenerAlumnosPorGrupo(grupo.getIdGrupo());

		                PDFGenerator pdfGenerator = new PDFGenerator();
		                pdfGenerator.generarGrupoPDF(grupo, alumnos, filePath);
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error al generar PDF: " + ex.getMessage());
		            }
		        }
		    }
		});
		btn_descargar.setText("Descargar Grupo");
		btn_descargar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_descargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_descargar.setBackground(new Color(170, 196, 255));
		btn_descargar.setBounds(1230, 630, 238, 40);
		addScaled.accept(btn_descargar);
		mipanel.add(btn_descargar);
		
		JLabel letra_img = new JLabel();
		letra_img.setIcon(new ImageIcon(getClass().getResource("/img/icono_letraA.png")));
		letra_img.setBounds(1371, 146, 101, 222);
		addScaled.accept(letra_img);
		mipanel.add(letra_img);
		
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
	
	
	public void alerta_btnDescargar(Consumer<JComponent> addScaled) {

	    JDialog dialogo = new JDialog(ventana, "Confirmación", true);
	    dialogo.setLayout(null);
	    dialogo.setSize(764, 353);
	    dialogo.setLocationRelativeTo(ventana);
		
		JPanel alerta_panel = new JPanel();
		alerta_panel.setBounds(0, 0, 750, 316);
		alerta_panel.setLayout(null);
		alerta_panel.setBackground(Color.WHITE);
		alerta_panel.setOpaque(true);
		alerta_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		JLabel mensajeLabel = new JLabel("¿¡Confirma que desea descargar este grupo!?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(144, 10, 483, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_aceptar = new JButton();
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				GruposView.this.confirmar_btnDescargar(addScaled);
			}
		});
		btn_aceptar.setText("Aceptar");
		btn_aceptar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_aceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_aceptar.setBackground(new Color(170, 196, 255));
		btn_aceptar.setBounds(435, 250, 192, 40);
		alerta_panel.add(btn_aceptar);
		
		JButton btn_no = new JButton();
		btn_no.setText("No, volver");
		btn_no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
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
	
	
	public void confirmar_btnDescargar(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¡Grupo descargado exitosamente!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(204, 10, 335, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
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
	
	
	public void añadir_alumno(Consumer<JComponent> addScaled, String origen, String nombreGrupoPrellenado) {
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
		
		JLabel infoLabel = new JLabel("Haga clic para seleccionar solo a un alumno o mantenga presionado crtl al hacer clic para seleccionar a varios alumnos");
		infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setBounds(155, 180, 1221, 20);
		addScaled.accept(infoLabel);
		mipanel.add(infoLabel);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							GruposView.this.detalles_grupos(addScaled);
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
							GruposView.this.añadir_grupo(addScaled, null, nombreGrupoPrellenado);	
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
							opciones_panel.setVisible(false);

								GruposView.this.detalles_grupos(addScaled);
			
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
		
		JLabel fondo_barra = new JLabel("Seleccione a los alumnos");
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#27548A"));
		fondo_barra.setForeground(Color.WHITE);
		fondo_barra.setBounds(0, 105, 1540, 90);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JLabel fondo_barra2 = new JLabel();
		fondo_barra2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra2.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra2.setBackground(Color.decode("#EEF1FF"));
		fondo_barra2.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra2.setBounds(0, 0, 1540, 102);
		fondo_barra2.setOpaque(true);
		addScaled.accept(fondo_barra2);
		mipanel.add(fondo_barra2);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				if ("crear_grupo".equals(origen)) {
					GruposView.this.añadir_grupo(addScaled, null, nombreGrupoPrellenado);					
				} else if ("editar_grupo".equals(origen)) {
					try {
						GruposView.this.editar_grupo(addScaled, idGrupo);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				} else {
					GruposView.this.panel_grupos(addScaled);
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
	    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);		
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
		
		DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		        
		        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        setHorizontalAlignment(JLabel.CENTER);

		        if (row >= 0 && row < alumnos.size()) {
		            Alumno alumno = alumnos.get(row);
		            boolean estaSeleccionado = alumnosTemporales.contains(alumno.getIdAlumno());

		            if (isSelected) {
		                c.setBackground(new Color(150, 200, 255));
		            } else if (estaSeleccionado) {
		                c.setBackground(new Color(200, 255, 200)); 
		            } else {
		                c.setBackground(Color.WHITE);
		            }
		        }
		        return c;
		    }
		};
		
		for (int i = 0; i < table.getColumnCount(); i++) {
		    table.getColumnModel().getColumn(i).setCellRenderer(renderizador);
		}
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setFont(new Font("SansSerif", Font.PLAIN, 24));
		scroll.setBounds(155, 206, 1221, 480);
		addScaled.accept(scroll);
		mipanel.add(scroll);
		
		JButton btn_añadir = new JButton();
		btn_añadir.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int[] selectedRows = table.getSelectedRows();
		        alumnosTemporales.clear(); 
		        
		        for (int row : selectedRows) {
		    
		            int idAlumno = alumnos.get(row).getIdAlumno(); 
		            alumnosTemporales.add(idAlumno);
		        }
		        opciones_panel.setVisible(false);
		        GruposView.this.alerta_añadirAlumnos(addScaled);
		        table.repaint();
		}
		});
		btn_añadir.setText("Añadir");
		btn_añadir.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir.setBackground(new Color(170, 196, 255));
		btn_añadir.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_añadir);
		mipanel.add(btn_añadir);
	}
	
	
	//===========================================================================================================================
	
	

	
	//===========================================================================================================================

	
	public void alerta_añadirAlumnos(Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¡" + alumnosTemporales.size() + " alumnos añadidos con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(221, 10, 324, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
		btn_volver.setForeground(Color.BLACK);
		btn_volver.setText("Aceptar");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#AAC4FF"));
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

	
	public void alerta_añadir_alumnos(Consumer<JComponent> addScaled) {
		//ESTA ALERTA VA EN EDITAR ALUMNO

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
		
		JLabel mensajeLabel = new JLabel("¡" + añadidos + " alumnos añadidos al grupo!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(221, 10, 324, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
		btn_volver.setForeground(Color.BLACK);
		btn_volver.setText("Aceptar");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#AAC4FF"));
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
	
	
	public void editar_grupo(Consumer<JComponent> addScaled, int idGrupo) throws  SQLException {
	    remover();
	    addScaled.accept(opciones_panel);
	    mipanel.add(opciones_panel);

	    Connection conn = new ConnectionModel().getConnection();
	    GruposModel gruposModel = new GruposModel(conn);
	    alumno_has_grupoModel alumnoGrupoModel = new alumno_has_grupoModel(conn);

	    Grupo grupo = gruposModel.getGrupoById(idGrupo);
	    if (grupo == null) {
	        JOptionPane.showMessageDialog(null, "No se encontró el grupo con ID: " + idGrupo, "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    if (nombreGrupo == null) {
	        nombreGrupo = grupo.getNombreGrupo();  
	    }
	    Docente docente = DocentesModel.busca_docente(grupo.getIdDocente());
	    
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Editar grupo");
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
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							try {
								GruposView.this.informacion_grupos(addScaled, idGrupo);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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
							GruposView.this.añadir_grupo(addScaled, null, nombreGrupo);	
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
							opciones_panel.setVisible(false);
							try {
								GruposView.this.editar_grupo(addScaled, idGrupo);
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
				alumnosTemporales.clear();
		        alumnosParaEliminar.clear();
		        nombreGrupo = null;
		        turnoSeleccionado = null;
		        docenteSeleccionado = null;
				GruposView.this.panel_grupos(addScaled);
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
		
		JLabel nombre_grupoLabel = new JLabel("Nombre del grupo: ");
		nombre_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_grupoLabel.setBounds(111, 243, 192, 29);
		addScaled.accept(nombre_grupoLabel);
		mipanel.add(nombre_grupoLabel);
		
		JTextField nombre_grupoField = new JTextField(nombreGrupo != null ? nombreGrupo : grupo.getNombreGrupo());
		nombre_grupoField.setBackground(Color.decode("#D9D9D9"));
		nombre_grupoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		nombre_grupoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		nombre_grupoField.setBounds(313, 239, 386, 40);
		nombre_grupoField.setColumns(10);
		((AbstractDocument) nombre_grupoField.getDocument()).setDocumentFilter(new TextDocumentFilter(30));
		addScaled.accept(nombre_grupoField);
		mipanel.add(nombre_grupoField);
		
		JLabel docente_grupoLabel = new JLabel("Docente del grupo:");
		docente_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_grupoLabel.setBounds(111, 301, 192, 29);
		addScaled.accept(docente_grupoLabel);
		mipanel.add(docente_grupoLabel);
		
		JLabel letra_img = new JLabel();
		letra_img.setIcon(new ImageIcon(getClass().getResource("/img/icono_letraA.png")));
		letra_img.setBounds(1276, 176, 101, 222);
		addScaled.accept(letra_img);
		mipanel.add(letra_img);
		
		JButton btn_descargar = new JButton();
		btn_descargar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        opciones_panel.setVisible(false);
		        
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setDialogTitle("Guardar grupo como PDF");

		        String defaultFileName = "Grupo_" + grupo.getNombreGrupo().replaceAll("\\s+", "_") + ".pdf";
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

		        int userSelection = fileChooser.showSaveDialog(null);

		        if (userSelection == JFileChooser.APPROVE_OPTION) {
		            File fileToSave = fileChooser.getSelectedFile();
		            String filePath = fileToSave.getAbsolutePath();

		            if (!filePath.toLowerCase().endsWith(".pdf")) {
		                filePath += ".pdf";
		                fileToSave = new File(filePath);
		            }

		            if (fileToSave.exists()) {
		                int confirm = JOptionPane.showConfirmDialog(null,
		                        "El archivo ya existe. ¿Desea reemplazarlo?",
		                        "Confirmar sobrescritura",
		                        JOptionPane.YES_NO_OPTION,
		                        JOptionPane.WARNING_MESSAGE);

		                if (confirm != JOptionPane.YES_OPTION) {
		                    return;
		                }
		            }

		            try {
		                alumno_has_grupoModel alumnoGrupoModel = new alumno_has_grupoModel(new ConnectionModel().getConnection());
		                List<Alumno> alumnos = alumnoGrupoModel.obtenerAlumnosPorGrupo(grupo.getIdGrupo());

		                PDFGenerator pdfGenerator = new PDFGenerator();
		                pdfGenerator.generarGrupoPDF(grupo, alumnos, filePath);
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error al generar PDF: " + ex.getMessage());
		            }
		        }
		    }
		});
		btn_descargar.setText("Descargar Grupo");
		btn_descargar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_descargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_descargar.setBackground(new Color(170, 196, 255));
		btn_descargar.setBounds(1215, 630, 238, 40);
		addScaled.accept(btn_descargar);
		mipanel.add(btn_descargar);

        JComboBox<String> docenteComboBox = new JComboBox<>();
        docenteComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        docenteComboBox.setBounds(313, 297, 386, 40);
        docenteComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        DocentesModel docentesModel = new DocentesModel();
        List<Docente> docentes = docentesModel.getAll();
        for (Docente d : docentes) {
            docenteComboBox.addItem(d.getIdDocente() + " - " + d.getNombre());
        }

        String docenteActual = grupo.getIdDocente() + " - " + docente.getNombre();
        docenteComboBox.setSelectedItem(docenteSeleccionado != null ? docenteSeleccionado : docenteActual);

        mipanel.add(docenteComboBox);
        addScaled.accept(docenteComboBox);
		

		JLabel editar_alumnosLabel = new JLabel("Editar alumnos:");
		editar_alumnosLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		editar_alumnosLabel.setBounds(136, 540, 192, 29);
		addScaled.accept(editar_alumnosLabel);
		mipanel.add(editar_alumnosLabel);

		
		
		JLabel turno_grupoLabel = new JLabel("Turno:");
		turno_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		turno_grupoLabel.setBounds(230, 362, 129, 29);
		addScaled.accept(turno_grupoLabel);
		mipanel.add(turno_grupoLabel);
		
	    JComboBox<String> turnoComboBox = new JComboBox<>(new String[]{"Matutino", "Vespertino"});
	    turnoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
	    turnoComboBox.setBounds(313, 358, 386, 40);
	    turnoComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

	    if (turnoSeleccionado != null) {
	        turnoComboBox.setSelectedItem(turnoSeleccionado);
	    } else {
	        turnoComboBox.setSelectedItem(grupo.getTurno().getDescripcion());
	    }

	    mipanel.add(turnoComboBox);
	    addScaled.accept(turnoComboBox);
	    
	    alumnosTemporales.clear();
	    List<Alumno> alumnosGrupo = alumnoGrupoModel.obtenerAlumnosPorGrupo(idGrupo);
	    for (Alumno alumno : alumnosGrupo) {
	        alumnosTemporales.add(alumno.getIdAlumno());
	    }
	    
		JButton btn_añadir_alumnos = new JButton();
		btn_añadir_alumnos.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent e) {
	            docenteSeleccionado = (String) docenteComboBox.getSelectedItem();
	            turnoSeleccionado = (String) turnoComboBox.getSelectedItem();
	            nombreGrupo = nombre_grupoField.getText().trim();
	            try {
	                GruposView.this.editar_alumno(addScaled, "editar_grupo", idGrupo);
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    });
		btn_añadir_alumnos.setText("Haz clic aquí");
		btn_añadir_alumnos.setBackground(Color.decode("#AAC4FF"));
		btn_añadir_alumnos.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir_alumnos.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir_alumnos.setBounds(313, 534, 386, 40);
		addScaled.accept(btn_añadir_alumnos);
		mipanel.add(btn_añadir_alumnos);
	
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
		
		JButton btn_guardar = new JButton();
		btn_guardar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                String nuevoNombre = nombre_grupoField.getText().trim();
	                String nuevoTurno = (String) turnoComboBox.getSelectedItem();
	                String docenteSeleccionado = (String) docenteComboBox.getSelectedItem();

	                Grupo.Turno turnoEnum = nuevoTurno.equalsIgnoreCase("Matutino") ? Grupo.Turno.TM : Grupo.Turno.TV;
	                int idDocente = Integer.parseInt(docenteSeleccionado.split(" - ")[0]);

	                grupo.setNombreGrupo(nuevoNombre);
	                grupo.setTurno(turnoEnum);
	                grupo.setIdDocente(idDocente);

	                gruposModel.update(grupo);

	                List<Integer> actuales = gruposModel.getAlumnosEnGrupo(grupo.getIdGrupo());
	                for (int idAlumno : actuales) {
	                    gruposModel.removerAlumnoDeGrupo(idAlumno, grupo.getIdGrupo());
	                }
	                for (int idAlumno : alumnosTemporales) {
	                    gruposModel.agregarAlumnoAGrupo(idAlumno, grupo.getIdGrupo());
	                }

	                alumnosTemporales.clear();
	         
	                opciones_panel.setVisible(false);
	                GruposView.this.confirmar_grupoEditado(addScaled);
	                
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage());
	                ex.printStackTrace();
	            }
	            nombreGrupo = null;
	            turnoSeleccionado = null;
	            docenteSeleccionado = null;
	        }
	        
	    });
		btn_guardar.setText("Guardar");
		btn_guardar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_guardar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_guardar.setBackground(new Color(170, 196, 255));
		btn_guardar.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_guardar);
		mipanel.add(btn_guardar);
		
		JButton btn_basura = new JButton();
		btn_basura.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	opciones_panel.setVisible(false);
		    	grupoAEliminarId = grupo.getIdGrupo();
		    	GruposView.this.alerta_eliminarGrupo(addScaled);
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
		
	}
	
	
	//===========================================================================================================================

	
	public void alerta_eliminarGrupo(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¿Desea eliminar este grupo?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(229, 10, 290, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_si = new JButton();
		btn_si.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			       dialogo.dispose();

			        try {
			            Connection conn = new ConnectionModel().getConnection();
			            GruposModel gruposModel = new GruposModel(conn);

			            boolean eliminado = gruposModel.delete(grupoAEliminarId); 

			            if (eliminado) {
			                opciones_panel.setVisible(false);
			                GruposView.this.confirmar_grupoEliminado(addScaled);
			            } else {
			                JOptionPane.showMessageDialog(null, "No se pudo eliminar el grupo.");
			            }

			        } catch (SQLException ex) {
			            JOptionPane.showMessageDialog(null, "Error SQL al eliminar grupo: " + ex.getMessage());
			            ex.printStackTrace();
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

	
	public void confirmar_grupoEditado(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¡Grupo editado con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(241, 43, 324, 51);
		alerta_panel.add(mensajeLabel);
		
		JLabel alerta_img = new JLabel();
		alerta_img.setIcon(new ImageIcon(getClass().getResource("/img/like.png")));
		alerta_img.setBounds(333, 133, 70, 84);
		alerta_panel.add(alerta_img);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				GruposView.this.panel_grupos(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Grupos");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	//===========================================================================================================================

	
	public void confirmar_grupoEliminado(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¡Grupo eliminado con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(241, 43, 324, 51);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				GruposView.this.panel_grupos(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Grupos");
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

	
	public void confirmar_grupoCreado(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¡Grupo creado con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(241, 58, 500, 51);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				GruposView.this.panel_grupos(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Grupos");
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
	
	public void mostrar_lista_editar(Consumer<JComponent> addScaled) {
		remover();
		opciones_panel.setVisible(false);
		addScaled.accept(opciones_panel);
		mipanel.add(opciones_panel);
		
		JLabel logo = new JLabel();
		logo.setBackground(Color.decode("#EEF1FF"));
		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
		logo.setOpaque(true);
		logo.setBounds(0, 10, 101, 85);
		addScaled.accept(logo);
		mipanel.add(logo);
		
		JLabel fondo_barra_2 = new JLabel("Grupos escolares");
		fondo_barra_2.setOpaque(true);
		fondo_barra_2.setForeground(Color.WHITE);
		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra_2.setBackground(Color.decode("#27548A"));
		fondo_barra_2.setBounds(0, 101, 1540, 102);
		addScaled.accept(fondo_barra_2);
		mipanel.add(fondo_barra_2);
		
		JPanel asignaturasContainer = new JPanel();
		asignaturasContainer.setLayout(new BoxLayout(asignaturasContainer, BoxLayout.Y_AXIS));
		asignaturasContainer.setBackground(Color.decode("#27548A"));
		Connection conn = new ConnectionModel().getConnection();

		 try {
			    GruposModel asignaturaModel = new GruposModel(conn);
			    List<Grupo> asignaturas = asignaturaModel.getAll();

			    for (Grupo asignatura : asignaturas) {
			        JButton btnAsignatura = new JButton(asignatura.getNombreGrupo());
			        btnAsignatura.setFont(new Font("SansSerif", Font.PLAIN, 26));
			        btnAsignatura.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
			        btnAsignatura.setBackground(new Color(170, 196, 255));
			        btnAsignatura.setAlignmentX(Component.CENTER_ALIGNMENT);
			        btnAsignatura.setMaximumSize(new Dimension(1348, 117));

			        btnAsignatura.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                opciones_panel.setVisible(false);
			              
			                try {
			     
			                    GruposView.this.editar_grupo(addScaled, asignatura.getIdGrupo());
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

			                
			            }
			        });

			        asignaturasContainer.add(btnAsignatura);
			        asignaturasContainer.add(Box.createRigidArea(new Dimension(0, 10)));
			    }

			} catch (SQLException ex) {
			    ex.printStackTrace();
			    JOptionPane.showMessageDialog(null, "Error al cargar asignaturas: " + ex.getMessage());
			}
		 
		JScrollPane scrollGrupos = new JScrollPane(asignaturasContainer);
		scrollGrupos.setBorder(BorderFactory.createEmptyBorder());
		scrollGrupos.setBounds(93, 274, 1348, 345); 
		scrollGrupos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		addScaled.accept(scrollGrupos);
		mipanel.add(scrollGrupos);
			    
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							GruposView.this.detalles_grupos(addScaled);
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
							GruposView.this.añadir_grupo(addScaled, null, nombreGrupo);	
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
							opciones_panel.setVisible(false);
							
								GruposView.this.detalles_grupos(addScaled);
							
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
				GruposView.this.panel_grupos(addScaled);
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
	
	private Grupo grupo;
//	private List<Integer> idsAlumnosEnGrupo = new ArrayList<>();

	public void actualizarAlumnosEnGrupo() {
	    try (Connection conn = new ConnectionModel().getConnection()) {
	        alumno_has_grupoModel modelo = new alumno_has_grupoModel(conn);
	        idsAlumnosEnGrupo = modelo.getAlumnosEnGrupo(grupo.getIdGrupo());
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}

	
	public void editar_alumno(Consumer<JComponent> addScaled, String origen, int grupoId) throws SQLException {
	    remover();
	    addScaled.accept(opciones_panel);
	    mipanel.add(opciones_panel);

	    Connection conn = new ConnectionModel().getConnection();
	    AlumnoModel alumnoModel = new AlumnoModel();
	    GruposModel gruposModel = new GruposModel(conn);
	    alumno_has_grupoModel alumnoGrupoModel = new alumno_has_grupoModel(conn);

	    this.idGrupo = grupoId;
	    this.grupo = gruposModel.getGrupoById(this.idGrupo);
	    List<Alumno> todosAlumnos = alumnoModel.getAll();
	    List<Integer> actualesIds = gruposModel.getAlumnosEnGrupo(idGrupo);
	    idsAlumnosEnGrupo = new ArrayList<>(actualesIds);

	    JLabel logo = new JLabel();
	    logo.setBackground(Color.decode("#EEF1FF"));
	    logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
	    logo.setOpaque(true);
	    logo.setBounds(0, 10, 101, 85);
	    addScaled.accept(logo);
	    mipanel.add(logo);

	    JLabel infoLabel = new JLabel("Alumnos asignados ya están en el grupo");
	    infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
	    infoLabel.setForeground(Color.WHITE);
	    infoLabel.setBounds(155, 180, 1221, 20);
	    addScaled.accept(infoLabel);
	    mipanel.add(infoLabel);
		
		JButton c_escolar_barraLabel = new JButton("Control Escolar");
		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
		c_escolar_barraLabel.setBackground(Color.decode("#EEF1FF"));
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
				
				opciones_panel.setSize(266, 200);
				opciones_panel.setLocation(376, 101);
				
				boolean visible = !opciones_panel.isVisible();
				opciones_panel.setVisible(visible);
				
				if(visible) {
					opciones_panel.removeAll();
					JButton registros = new JButton("Registros");
					registros.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							opciones_panel.setVisible(false);
							GruposView.this.detalles_grupos(addScaled);		
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
							try {
								GruposView.this.informacion_grupos(addScaled, grupoId);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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
							GruposView.this.añadir_grupo(addScaled, null, origen);	
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
							opciones_panel.setVisible(false);
							try {
								GruposView.this.editar_grupo(addScaled, idGrupo);
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
		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
		grupos_barraLabel.setBackground(Color.decode("#AAC4FF"));
		grupos_barraLabel.setForeground(Color.BLACK);
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
				GruposView.this.alerta_IrAlumnos(addScaled);
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
				GruposView.this.alerta_IrDocentes(addScaled);	
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
				GruposView.this.alerta_IrAsignaturas(addScaled);	
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
		
		JLabel fondo_barra = new JLabel("Añada o elimine alumnos");
		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra.setBackground(Color.decode("#27548A"));
		fondo_barra.setForeground(Color.WHITE);
		fondo_barra.setBounds(0, 105, 1540, 90);
		fondo_barra.setOpaque(true);
		addScaled.accept(fondo_barra);
		mipanel.add(fondo_barra);
		
		JLabel fondo_barra2 = new JLabel();
		fondo_barra2.setFont(new Font("SansSerif", Font.PLAIN, 32));
		fondo_barra2.setHorizontalAlignment(JLabel.CENTER);
		fondo_barra2.setBackground(Color.decode("#EEF1FF"));
		fondo_barra2.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_barra2.setBounds(0, 0, 1540, 102);
		fondo_barra2.setOpaque(true);
		addScaled.accept(fondo_barra2);
		mipanel.add(fondo_barra2);
		
		JButton btn_volver = new JButton("Volver");
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				try {
					GruposView.this.editar_grupo(addScaled, idGrupo);
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
		

	    String[] columnNames = {"ID", "Nombre", "Carrera", "Grado", "¿Asignado?"};
	    Object[][] data = new Object[todosAlumnos.size()][5];

	    for (int i = 0; i < todosAlumnos.size(); i++) {
	        Alumno a = todosAlumnos.get(i);
	        data[i][0] = a.getIdAlumno();
	        data[i][1] = a.getNombre() + " " + a.getPrimer_apellido();
	        data[i][2] = a.getCarrera();
	        data[i][3] = a.getGrado_alumno();
	        data[i][4] = actualesIds.contains(a.getIdAlumno());
	    }

	    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
	        @Override
	        public Class<?> getColumnClass(int column) {
	            return column == 4 ? Boolean.class : String.class;
	        }
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return column == 4;
	        }
	    };

	    JTable tabla = new JTable(model);
	    tabla.setRowHeight(30);
	    JScrollPane scrollPane = new JScrollPane(tabla);
	    scrollPane.setBounds(155, 206, 1221, 480);
	    mipanel.add(scrollPane);
	    addScaled.accept(scrollPane);

	    JButton btn_añadir = new JButton("Guardar Cambios");
	    btn_añadir.setFont(new Font("SansSerif", Font.PLAIN, 22));
	    btn_añadir.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	    btn_añadir.setBackground(new Color(170, 196, 255));
	    btn_añadir.setBounds(650, 716, 192, 40);
	    btn_añadir.addActionListener(e -> {
	        try {
	            List<Integer> nuevosAsignados = new ArrayList<>();
	            for (int i = 0; i < model.getRowCount(); i++) {
	                boolean asignado = (boolean) model.getValueAt(i, 4);
	                int idAlumno = (int) model.getValueAt(i, 0);
	                if (asignado) nuevosAsignados.add(idAlumno);
	            }

	            List<Integer> paraAgregar = new ArrayList<>(nuevosAsignados);
	            paraAgregar.removeAll(actualesIds);

	            List<Integer> paraEliminar = new ArrayList<>(actualesIds);
	            paraEliminar.removeAll(nuevosAsignados);

	            for (int id : paraAgregar) {
	                alumnoGrupoModel.agregarAlumnoAGrupo(id, idGrupo);
	            }
	            for (int id : paraEliminar) {
	                alumnoGrupoModel.eliminarAlumnoDeGrupo(id, idGrupo);
	            }

	            alumnosTemporales = nuevosAsignados;
	            idsAlumnosEnGrupo = nuevosAsignados;


	            opciones_panel.setVisible(false);
	            GruposView.this.confirmar_alumnoEditado(addScaled);

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al guardar cambios: " + ex.getMessage());
	        }
	    });
	    addScaled.accept(btn_añadir);
	    mipanel.add(btn_añadir);

	}
	
	
	//===========================================================================================================================

	
	public void alerta_eliminarAlumnos(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¿Desea eliminar "+ eliminados +" alumnos de este grupo?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(180, 10, 490, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_si = new JButton();
		btn_si.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        dialogo.dispose();

		        try {
		            Connection conn = new ConnectionModel().getConnection();
		            alumno_has_grupoModel alumnoGrupoModel = new alumno_has_grupoModel(conn);

		            StringBuilder detalles = new StringBuilder();
		            int eliminadosExitosos = 0;

		            for (Alumno alumno : alumnosParaEliminar) {
		                if (alumnoGrupoModel.eliminarAlumnoDeGrupo(alumno.getIdAlumno(), idGrupo)) {
		                    eliminadosExitosos++;
		                    idsAlumnosEnGrupo.remove(Integer.valueOf(alumno.getIdAlumno()));
		                    detalles.append("Eliminado: ").append(alumno.getNo_control()).append("\n");
		                } else {
		                    detalles.append("No se pudo eliminar: ").append(alumno.getNo_control()).append("\n");
		                }
		            }

		            actualizarAlumnosEnGrupo();
		            table.repaint();
		            opciones_panel.setVisible(false);
		            confirmar_alumnoEliminado(addScaled);

		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error SQL al eliminar: " + ex.getMessage());
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
	
	public void confirmar_alumnoEliminado(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¡" + eliminados + " Alumnos eliminados con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(220, 43, 500, 51);
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
			}
		});
		btn_volver.setForeground(Color.BLACK);
		btn_volver.setText("Aceptar");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(new Color(170, 196, 255));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	public void confirmar_alumnoEditado(Consumer<JComponent> addScaled) {

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
		
		JLabel mensajeLabel = new JLabel("¡Alumnos editados con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(220, 43, 500, 51);
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
			}
		});
		btn_volver.setForeground(Color.BLACK);
		btn_volver.setText("Aceptar");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(new Color(170, 196, 255));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		
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
		mensajeLabel.setBounds(200, 10, 414, 97);
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
	
	public void remover() {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
	}

}
