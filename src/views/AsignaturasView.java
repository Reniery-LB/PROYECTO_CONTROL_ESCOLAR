package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import PDF.PDFGenerator;
import aplication.NumericDocumentFilter;
import aplication.ScalableUtils;
import aplication.TextDocumentFilter;
import controllers.AlumnosController;
import controllers.AuthController;
import controllers.DocentesController;
import controllers.GruposController;
import models.Alumno;
import models.Asignatura;
import models.AsignaturasModel;
import models.ConnectionModel;
import models.Docente;
import models.DocentesModel;

public class AsignaturasView {

	private static final int BASE_ANCHO = 1024;
	private static final int BASE_ALTURA = 768;
	private JFrame ventana;
	private JPanel mipanel;
	private JPanel opciones_panel;
	public  Connection conn = new ConnectionModel().getConnection();
	private JTextArea descField;
	
	
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
				AsignaturasView.this.mostrar_lista_para_editar(addScaled);
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
				AsignaturasView.this.mostrar_lista_para_editar(addScaled);
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
				AsignaturasView.this.asignaturas(addScaled);
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
				AsignaturasView.this.asignaturas(addScaled);			}
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
							AsignaturasView.this.asignaturas(addScaled);
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
							AsignaturasView.this.mostrar_lista_para_editar(addScaled);
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
		
		JPanel asignaturasContainer = new JPanel();
		asignaturasContainer.setLayout(new BoxLayout(asignaturasContainer, BoxLayout.Y_AXIS));
		asignaturasContainer.setBackground(Color.decode("#27548A"));
		
		try {
			    AsignaturasModel asignaturaModel = new AsignaturasModel();
			    List<Asignatura> asignaturas = asignaturaModel.gettAll();
	
			    for (Asignatura asignatura : asignaturas) {
			    	
			        JButton btnAsignatura = new JButton(asignatura.getNombre());
			        btnAsignatura.setFont(new Font("SansSerif", Font.PLAIN, 26));
			        btnAsignatura.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
			        btnAsignatura.setBackground(new Color(170, 196, 255));
			        btnAsignatura.setAlignmentX(Component.CENTER_ALIGNMENT);
			        btnAsignatura.setMaximumSize(new Dimension(1348, 117));
	
			        btnAsignatura.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                opciones_panel.setVisible(false);
			                AsignaturasView.this.detalles_asignatura(asignatura, addScaled);
			               
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
							AsignaturasView.this.asignaturas(addScaled);
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
							AsignaturasView.this.mostrar_lista_para_editar(addScaled);			
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
		
	
	}
	
	
	//===========================================================================================================================
	
	
	
//	public void detalles(Consumer<JComponent> addScaled) {
//		remover();
//		addScaled.accept(opciones_panel);
//		mipanel.add(opciones_panel);
//		
//		JLabel logo = new JLabel();
//		logo.setBackground(Color.decode("#EEF1FF"));
//		logo.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
//		logo.setOpaque(true);
//		logo.setBounds(0, 10, 101, 85);
//		addScaled.accept(logo);
//		mipanel.add(logo);
//		
//		JLabel fondo_barra_2 = new JLabel("Detalles de la asignatura");
//		fondo_barra_2.setOpaque(true);
//		fondo_barra_2.setForeground(Color.WHITE);
//		fondo_barra_2.setHorizontalAlignment(SwingConstants.CENTER);
//		fondo_barra_2.setFont(new Font("SansSerif", Font.PLAIN, 32));
//		fondo_barra_2.setBackground(Color.decode("#27548A"));
//		fondo_barra_2.setBounds(0, 101, 1540, 102);
//		addScaled.accept(fondo_barra_2);
//		mipanel.add(fondo_barra_2);
//		
//		JPanel asignaturasContainer = new JPanel();
//		 asignaturasContainer.setLayout(new BoxLayout(asignaturasContainer, BoxLayout.Y_AXIS));
//		 asignaturasContainer.setBackground(Color.decode("#27548A"));
//		
//		 try {
//			    AsignaturasModel asignaturaModel = new AsignaturasModel();
//			    List<Asignatura> asignaturas = asignaturaModel.gettAll();
//
//			    for (Asignatura asignatura : asignaturas) {
//			        JButton btnAsignatura = new JButton(asignatura.getNombre());
//			        btnAsignatura.setFont(new Font("SansSerif", Font.PLAIN, 26));
//			        btnAsignatura.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
//			        btnAsignatura.setBackground(new Color(170, 196, 255));
//			        btnAsignatura.setAlignmentX(Component.CENTER_ALIGNMENT);
//			        btnAsignatura.setMaximumSize(new Dimension(1348, 117));
//
//			        btnAsignatura.addActionListener(new ActionListener() {
//			            @Override
//			            public void actionPerformed(ActionEvent e) {
//			                opciones_panel.setVisible(false);
//							AsignaturasView.this.detalles_asignatura(asignatura,addScaled);
//
//			                
//			            }
//			        });
//
//			        asignaturasContainer.add(btnAsignatura);
//			        asignaturasContainer.add(Box.createRigidArea(new Dimension(0, 10)));
//			    }
//
//			} catch (SQLException ex) {
//			    ex.printStackTrace();
//			    JOptionPane.showMessageDialog(null, "Error al cargar asignaturas: " + ex.getMessage());
//			}
//		 
//		 JScrollPane scrollGrupos = new JScrollPane(asignaturasContainer);
//		 scrollGrupos.setBorder(BorderFactory.createEmptyBorder());
//		 scrollGrupos.setBounds(93, 274, 1348, 345); 
//		 scrollGrupos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		 addScaled.accept(scrollGrupos);
//		 mipanel.add(scrollGrupos);
//		
//		
//		
//		JLabel c_escolar_barraLabel = new JLabel("Control Escolar");
//		c_escolar_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		c_escolar_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/control_escolar.png")));
//		c_escolar_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		c_escolar_barraLabel.setBounds(111, 0, 263, 102);
//		c_escolar_barraLabel.setHorizontalAlignment(JLabel.CENTER);
//		addScaled.accept(c_escolar_barraLabel);
//		mipanel.add(c_escolar_barraLabel);
//		
//		JButton grupos_barraLabel = new JButton("    Grupos");
//		grupos_barraLabel.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				AsignaturasView.this.alerta_IrGrupos(addScaled);
//			}
//		});
//		grupos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/grupos_barra.png")));
//		grupos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		grupos_barraLabel.setBackground(Color.decode("#EEF1FF"));
//		grupos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		grupos_barraLabel.setBounds(372, 0, 263, 102);
//		addScaled.accept(grupos_barraLabel);
//		mipanel.add(grupos_barraLabel);
//		
//		JButton alumnos_barraLabel = new JButton("    Alumnos");
//		alumnos_barraLabel.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				AsignaturasView.this.alerta_IrAlumnos(addScaled);
//			}
//		});
//		alumnos_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/alumnos_barra.png")));
//		alumnos_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		alumnos_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		alumnos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		alumnos_barraLabel.setBackground(new Color(238, 241, 255));
//		alumnos_barraLabel.setBounds(633, 0, 263, 102);
//		addScaled.accept(alumnos_barraLabel);
//		mipanel.add(alumnos_barraLabel);
//		
//		JButton docentes_barraLabel = new JButton(" Docentes");
//		docentes_barraLabel.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				AsignaturasView.this.alerta_IrDocentes(addScaled);	
//			}
//		});
//		docentes_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/docentes_barra.png")));
//		docentes_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		docentes_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		docentes_barraLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		docentes_barraLabel.setBackground(new Color(238, 241, 255));
//		docentes_barraLabel.setBounds(894, 0, 263, 102);
//		addScaled.accept(docentes_barraLabel);
//		mipanel.add(docentes_barraLabel);
//		
//		JButton asignaturas_barraLabel = new JButton(" Asignaturas");
//		asignaturas_barraLabel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.removeAll();
//				opciones_panel.revalidate();
//				opciones_panel.repaint();
//				opciones_panel.setBounds(1168, 101, 386, 200);		
//				boolean visible = !opciones_panel.isVisible();
//		
//				opciones_panel.setVisible(visible);
//				
//				if(visible) {
//					
//					JButton registros = new JButton("Registros");
//					registros.addActionListener(new ActionListener() {
//						
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							opciones_panel.setVisible(false);
//							AsignaturasView.this.asignaturas(addScaled);
//						}
//						
//					});
//					registros.setFont(new Font("SansSerif", Font.PLAIN, 22));
//					registros.setBackground(Color.decode("#EEF1FF"));
//					registros.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//					registros.setBounds(0, 0, 386, 50);
//					opciones_panel.add(registros);
//					
//					JButton detalles = new JButton("Detalles");
//					detalles.addActionListener(new ActionListener() {
//						
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							opciones_panel.setVisible(false);
//							AsignaturasView.this.asignaturas(addScaled);
//						}
//					});
//					detalles.setFont(new Font("SansSerif", Font.PLAIN, 22));
//					detalles.setBackground(Color.decode("#EEF1FF"));
//					detalles.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//					detalles.setBounds(0, 50, 386, 50);
//					opciones_panel.add(detalles);
//					
//					JButton crear = new JButton("Crear");
//					crear.addActionListener(new ActionListener() {
//						
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							opciones_panel.setVisible(false);
//							AsignaturasView.this.añadir_asignatura(addScaled);
//						}
//					});
//					crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
//					crear.setBackground(Color.decode("#EEF1FF"));
//					crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//					crear.setBounds(0, 100, 386, 50);
//					opciones_panel.add(crear);
//					
//					JButton editar = new JButton("Editar");
//					editar.addActionListener(new ActionListener() {
//						
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							opciones_panel.setVisible(false);
//							AsignaturasView.this.mostrar_lista_para_editar(addScaled);
//						}
//					});
//					editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
//					editar.setBackground(Color.decode("#EEF1FF"));
//					editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//					editar.setBounds(0, 150, 386, 50);
//					opciones_panel.add(editar);
//					
//					opciones_panel.revalidate();
//					opciones_panel.repaint();
//				}
//				
//			}
//		});
//		asignaturas_barraLabel.setIcon(new ImageIcon(getClass().getResource("/img/asignaturas_barra.png")));
////		asignaturas_barraLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		asignaturas_barraLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		asignaturas_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
//		asignaturas_barraLabel.setBackground(Color.decode("#AAC4FF"));
//		asignaturas_barraLabel.setBounds(1154, 0, 386, 102);
//		addScaled.accept(asignaturas_barraLabel);
//		mipanel.add(asignaturas_barraLabel);
//		
//		JLabel fondo_barra = new JLabel();
//		fondo_barra.setFont(new Font("SansSerif", Font.PLAIN, 32));
//		fondo_barra.setHorizontalAlignment(JLabel.CENTER);
//		fondo_barra.setBackground(Color.decode("#EEF1FF"));
//		fondo_barra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		fondo_barra.setBounds(0, 0, 1540, 102);
//		fondo_barra.setOpaque(true);
//		addScaled.accept(fondo_barra);
//		mipanel.add(fondo_barra);
//		
//		JButton btn_volver = new JButton("Volver");
//		btn_volver.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				AsignaturasView.this.panel_asignaturas(addScaled);
//			}
//		});
//		btn_volver.setIcon(new ImageIcon(getClass().getResource("/img/cerrar_sesion.png")));
//		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 26));
//		btn_volver.setHorizontalAlignment(JLabel.LEFT);
//		btn_volver.setBorder(BorderFactory.createLineBorder(Color.decode("#27548A"),3));
//		btn_volver.setForeground(Color.WHITE);
//		btn_volver.setBackground(new Color(238, 241, 255));
//		btn_volver.setBounds(10, 716, 263, 58);
//		btn_volver.setOpaque(false);
//		addScaled.accept(btn_volver);
//		mipanel.add(btn_volver);
//		
//		JLabel nombre_materia = new JLabel("Nombre de la materia:     Programación");
//		nombre_materia.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		nombre_materia.setBounds(111, 243, 400, 29);
//		addScaled.accept(nombre_materia);
//		mipanel.add(nombre_materia);
//		
//		JButton btn_editar = new JButton();
//		btn_editar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				AsignaturasView.this.editar_asignatura(null, addScaled, null);
//			}
//		});
//		btn_editar.setText("Editar");
//		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		btn_editar.setBackground(new Color(170, 196, 255));
//		btn_editar.setBounds(678, 716, 192, 40);
//		addScaled.accept(btn_editar);
//		mipanel.add(btn_editar);
//		
//		JLabel docente_cargo = new JLabel("Docente a cargo:     Jonathan Giovanni Soto");
//		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		docente_cargo.setBounds(160, 300, 460, 29);
//		addScaled.accept(docente_cargo);
//		mipanel.add(docente_cargo);
//		
//		JLabel id_docente = new JLabel("ID del docente:     12");
//		id_docente.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		id_docente.setBounds(179, 363, 460, 29);
//		addScaled.accept(id_docente);
//		mipanel.add(id_docente);
//		
//		JTextArea descripcion = new JTextArea();
//		descripcion.setText("Descripción:    Programación es la disciplina que enseña a diseñar,\r\n                        escribir y depurar instrucciones en un lenguaje que\r\n                        una computadora puede entender para resolver\r\n                        problemas o automatizar tareas.");
//		descripcion.setFont(new Font("SansSerif", Font.PLAIN, 22));
//		descripcion.setBounds(203, 423, 695, 120);
//		addScaled.accept(descripcion);
//		mipanel.add(descripcion);
//		
//		JLabel fondo_grupo = new JLabel();
//		fondo_grupo.setBackground(new Color(255, 255, 255));
//		fondo_grupo.setOpaque(true);
//		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		fondo_grupo.setBounds(56, 210, 1435, 480);
//		addScaled.accept(fondo_grupo);
//		mipanel.add(fondo_grupo);
//	}
	
	
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
							AsignaturasView.this.asignaturas(addScaled);
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
							AsignaturasView.this.mostrar_lista_para_editar(addScaled);
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
		
		JLabel docente_cargo = new JLabel("Docente a cargo:");
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 169, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
	
		
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
		((AbstractDocument) materiaField.getDocument()).setDocumentFilter(new TextDocumentFilter(30));
		addScaled.accept(materiaField);
		mipanel.add(materiaField);
		
		JComboBox<String> docenteComboBox = new JComboBox<>();
		docenteComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
		docenteComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docenteComboBox.setBackground(new Color(217, 217, 217));
		docenteComboBox.setBounds(341, 292, 510, 40);

		//Connection conn = new ConnectionModel().getConnection();

		DocentesModel docenteModel = new DocentesModel();
		List<Docente> docentes = docenteModel.getAll();
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
		for (Docente docente : docentes) {
		    comboModel.addElement(docente.getIdDocente() + " - " + docente.getNombre());
		}
		docenteComboBox.setModel(comboModel);
		
		if (docentes.isEmpty()) {
		    docenteComboBox.addItem("No hay docentes disponibles");
		}

		addScaled.accept(docenteComboBox);
		mipanel.add(docenteComboBox);
		
		JTextArea descField = new JTextArea();
		descField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		descField.setColumns(10);
		descField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		descField.setBackground(new Color(217, 217, 217));
		descField.setBounds(340, 423, 700, 200);

		descField.setLineWrap(true);    
		descField.setWrapStyleWord(true); 

		JScrollPane scrollPane = new JScrollPane(descField);
		scrollPane.setBounds(340, 423, 700, 200);
		scrollPane.setBorder(null);    
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		((AbstractDocument) descField.getDocument()).setDocumentFilter(new TextDocumentFilter(255));
		addScaled.accept(scrollPane);
		mipanel.add(scrollPane);       
		
		JLabel charCountLabel = new JLabel("0/255 caracteres");
		charCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		charCountLabel.setBounds(340, 630, 200, 20);
		addScaled.accept(charCountLabel);
		mipanel.add(charCountLabel);

		descField.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        updateCount();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        updateCount();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		        updateCount();
		    }

		    private void updateCount() {
		        int length = descField.getText().length();
		        charCountLabel.setText(length + "/255 caracteres");
		        if (length >= 255) {
		            charCountLabel.setForeground(Color.RED);
		        } else {
		            charCountLabel.setForeground(Color.BLACK);
		        }
		    }
		});
		
		JButton btn_crear = new JButton();
		btn_crear.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        opciones_panel.setVisible(false);
		        
		        String nombreMateria = materiaField.getText().trim();
		        String descripcionMateria = descField.getText().trim();
		        String docenteSeleccionado = (String) docenteComboBox.getSelectedItem();

		        if (nombreMateria.isEmpty() || descripcionMateria.isEmpty() || docenteSeleccionado == null || 
		            docenteSeleccionado.equals("No hay docentes disponibles")) {
		            
		            materiaField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		            descField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		            JOptionPane.showMessageDialog(null, "Por favor completa todos los campos correctamente.");
		            return;
		        }

		        try {
		            AsignaturasModel asignaturaModel = new AsignaturasModel();
		            if (asignaturaModel.existeAsignatura(nombreMateria)) {
		                materiaField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		                JOptionPane.showMessageDialog(null, "Ya existe una asignatura con ese nombre.", "Error", JOptionPane.WARNING_MESSAGE);
		                return;
		            }

		            if (descripcionMateria.length() > 255) {
		                descField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		                JOptionPane.showMessageDialog(null, "La descripción no puede exceder los 255 caracteres.");
		                return;
		            }

		            int idDocente = Integer.parseInt(docenteSeleccionado.split(" - ")[0]);

		            Asignatura nuevaAsignatura = new Asignatura(nombreMateria, descripcionMateria);
		            boolean insertado = (asignaturaModel).insert(nuevaAsignatura);

		            if (insertado) {
		                int idAsignatura = asignaturaModel.obtenerUltimoId();
		                asignaturaModel.asignarDocente(idDocente, idAsignatura);
		    
		                materiaField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		                descField.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		                AsignaturasView.this.confirmar_asignaturaCreada(addScaled);
		            } else {
		                JOptionPane.showMessageDialog(null, "Error al guardar la asignatura.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Error al obtener el ID del docente.", "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error de conexión con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
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
		
	
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================	
	
	
	public void editar_asignatura(Asignatura asignatura, Consumer<JComponent> addScaled, Asignatura asignaturaSeleccionada) {
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
							AsignaturasView.this.detalles_asignatura(asignaturaSeleccionada, addScaled);
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
							AsignaturasView.this.editar_asignatura(asignatura,addScaled, asignaturaSeleccionada);
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
			
		JLabel docente_cargo = new JLabel("Docente a cargo:");
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 169, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
		
		
		JTextArea descripcionArea = new JTextArea();
		descripcionArea.setText("Descripción:");
		descripcionArea.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descripcionArea.setBounds(203, 423, 127, 40);
		addScaled.accept(descripcionArea);
		mipanel.add(descripcionArea);
		
		JTextField materiaField = new JTextField();
		materiaField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		materiaField.setColumns(10);
		materiaField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		materiaField.setBackground(new Color(217, 217, 217));
		materiaField.setBounds(341, 239, 510, 40);
		((AbstractDocument) materiaField.getDocument()).setDocumentFilter(new TextDocumentFilter(30));
		addScaled.accept(materiaField);
		mipanel.add(materiaField);
		
		JComboBox<String> docenteComboBox = new JComboBox<>();
		docenteComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
		docenteComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docenteComboBox.setBackground(new Color(217, 217, 217));
		docenteComboBox.setBounds(341, 292, 510, 40);

		DocentesModel docenteModel = new DocentesModel();
		List<Docente> docentes = docenteModel.getAll();
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
		for (Docente docente : docentes) {
		    comboModel.addElement(docente.getIdDocente() + " - " + docente.getNombre());
		}
		docenteComboBox.setModel(comboModel);
		
		if (docentes.isEmpty()) {
		    docenteComboBox.addItem("No hay docentes disponibles");
		}

		addScaled.accept(docenteComboBox);
		mipanel.add(docenteComboBox);
		
		descField = new JTextArea(asignaturaSeleccionada.getDescripcion());
	    descField.setFont(new Font("SansSerif", Font.PLAIN, 18));
	    descField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	    descField.setBackground(new Color(217, 217, 217));
	    descField.setLineWrap(true);
	    descField.setWrapStyleWord(true);
		
	    JScrollPane scrollDesc = new JScrollPane(descField);
	    scrollDesc.setBounds(340, 423, 700, 200);
	    ((AbstractDocument) descField.getDocument()).setDocumentFilter(new TextDocumentFilter(255));
	    
	    JLabel charCountLabel = new JLabel(descField.getText().length() + "/255 caracteres");
	    charCountLabel.setBounds(340, 630, 200, 20);
	    descField.getDocument().addDocumentListener(new DocumentListener() {
	        public void insertUpdate(DocumentEvent e) { updateCount(); }
	        public void removeUpdate(DocumentEvent e) { updateCount(); }
	        public void changedUpdate(DocumentEvent e) { updateCount(); }
	        private void updateCount() {
	            int length = descField.getText().length();
	            charCountLabel.setText(length + "/255 caracteres");
	            charCountLabel.setForeground(length >= 255 ? Color.RED : Color.BLACK);
	        }
	    });

	    addScaled.accept(scrollDesc);
	    addScaled.accept(charCountLabel);
	    mipanel.add(scrollDesc);
	    mipanel.add(charCountLabel);
	    
		if (asignaturaSeleccionada != null) {
	        materiaField.setText(asignaturaSeleccionada.getNombre());
	        descField.setText(asignaturaSeleccionada.getDescripcion());

	        try {
	            DocentesModel docenteModel1 = new DocentesModel();
	            List<Docente> docentes1 = docenteModel1.getAll();
	            
	            DefaultComboBoxModel<String> comboModel1 = new DefaultComboBoxModel<>();
	            for (Docente docente : docentes1) {
	                comboModel1.addElement(docente.getIdDocente() + " - " + docente.getNombre());
	            }
	            docenteComboBox.setModel(comboModel1);
	            
	            AsignaturasModel asignaturaModel = new AsignaturasModel();

	            List<Integer> docentesAsignados = asignaturaModel.obtenerDocentesPorAsignatura(asignaturaSeleccionada.getIdAsignatura());
	            if (!docentesAsignados.isEmpty()) {
	                for (int i = 0; i < docenteComboBox.getItemCount(); i++) {
	                    String item = docenteComboBox.getItemAt(i);
	                    if (item.startsWith(docentesAsignados.get(0) + " - ")) {
	                        docenteComboBox.setSelectedIndex(i);
	                        break;
	                    }
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al cargar los docentes", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

		
		
	    JButton btn_guardar = new JButton("Guardar");
	    btn_guardar.addActionListener(e -> {
	        try {
	            String nombre = materiaField.getText().trim();
	            String descripcion = descField.getText().trim();
	            String docenteSel = (String) docenteComboBox.getSelectedItem();
	            
	            if (nombre.isEmpty() || descripcion.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Nombre y descripción son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            int idDocenteNuevo = -1;
	            if (!docenteSel.equals("No hay docentes disponibles")) {
	                idDocenteNuevo = Integer.parseInt(docenteSel.split(" - ")[0]);
	            }
	            
	            asignaturaSeleccionada.setNombre(nombre);
	            asignaturaSeleccionada.setDescripcion(descripcion);
	            
	            AsignaturasModel model = new AsignaturasModel();
	            boolean actualizacionOk = model.update(asignaturaSeleccionada);
	            
	            if (actualizacionOk && idDocenteNuevo != -1) {
	                List<Integer> docentesActuales = model.obtenerDocentesPorAsignatura(asignaturaSeleccionada.getIdAsignatura());
	                int idDocenteActual = docentesActuales.isEmpty() ? -1 : docentesActuales.get(0);
	                
	                if (idDocenteActual != idDocenteNuevo) {
	                    model.asignarDocente(idDocenteNuevo, asignaturaSeleccionada.getIdAsignatura());
	                }
	            }
	            
	            opciones_panel.setVisible(false);
	            AsignaturasView.this.confirmar_asignaturaEditada(addScaled);
	            
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, 
	                "Error al guardar: " + ex.getMessage(), 
	                "Error", 
	                JOptionPane.ERROR_MESSAGE);
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(null, 
	                "Formato de docente inválido", 
	                "Error", 
	                JOptionPane.ERROR_MESSAGE);
	        }
	    });
		btn_guardar.setText("Guardar");
		btn_guardar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_guardar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_guardar.setBackground(new Color(170, 196, 255));
		btn_guardar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_guardar);
		mipanel.add(btn_guardar);
		
		JButton btn_basura = new JButton();
		 btn_basura.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
                try {
                    AsignaturasModel model = new AsignaturasModel();
                    
                    String sqlDeleteRelaciones = "DELETE FROM Docente_has_Asignatura WHERE Asignatura_idAsignatura = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteRelaciones)) {
                        stmt.setInt(1, asignaturaSeleccionada.getIdAsignatura());
                        stmt.executeUpdate();
                    }
                    
                    boolean exito = model.delete(asignaturaSeleccionada.getIdAsignatura());
                    
                    if (exito) {
                    	 opciones_panel.setVisible(false);
                    	 AsignaturasView.this.alerta_eliminar(addScaled);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar la asignatura", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error de base de datos al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
		
		JButton btn_descargar = new JButton();
		btn_descargar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	opciones_panel.setVisible(false);
		    	AsignaturasView.this.alerta_DescargarAsignatura(asignaturaSeleccionada, addScaled);
		    }
		});
		btn_descargar.setText("Descargar Asignatura");
		btn_descargar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_descargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_descargar.setBackground(new Color(170, 196, 255));
		btn_descargar.setBounds(1230, 641, 238, 40);
		addScaled.accept(btn_descargar);
		mipanel.add(btn_descargar);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================	
	
	
	public void detalles_asignatura(Asignatura asignatura,Consumer<JComponent> addScaled) {
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
							AsignaturasView.this.detalles_asignatura(asignatura, addScaled);
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
							AsignaturasView.this.editar_asignatura(asignatura, addScaled, asignatura);
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
		
		JLabel nombre_materia = new JLabel("Nombre de la materia: " + asignatura.getNombre() );
		nombre_materia.setFont(new Font("SansSerif", Font.PLAIN, 22));
		nombre_materia.setBounds(111, 243, 800, 29);
		addScaled.accept(nombre_materia);
		mipanel.add(nombre_materia);
		
		JButton btn_editar = new JButton();
		btn_editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				AsignaturasView.this.editar_asignatura(asignatura, addScaled, asignatura);
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(678, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		Docente docente = null;
		try {
		    DocentesModel docentesModel = new DocentesModel();
		    List<Integer> docentes = new AsignaturasModel().obtenerDocentesPorAsignatura(asignatura.getIdAsignatura());

		    if (!docentes.isEmpty()) {
		        int idDocente = docentes.get(0); 
		        docente = docentesModel.busca_docente(idDocente);
		        System.out.println("Asignatura ID: " + asignatura.getIdAsignatura());
		        System.out.println("Docentes relacionados: " + docentes.size());
		    }
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
		
		JLabel docente_cargo = new JLabel("Docente a cargo:  " + docente.getNombre()  + " "+ docente.getPrimer_apellido()+ " " + docente.getSegundo_apellido());
		docente_cargo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_cargo.setBounds(160, 300, 800, 29);
		addScaled.accept(docente_cargo);
		mipanel.add(docente_cargo);
		
		JLabel id_docente = new JLabel("ID del docente:  " +  docente.getIdDocente());
		id_docente.setFont(new Font("SansSerif", Font.PLAIN, 22));
		id_docente.setBounds(179, 363, 800, 29);
		addScaled.accept(id_docente);
		mipanel.add(id_docente);
		
		JPanel descripcionContainer = new JPanel(null);
		descripcionContainer.setOpaque(false);
		descripcionContainer.setBounds(203, 423, 800, 120);

		JLabel descLabel = new JLabel("Descripción:    ");
		descLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descLabel.setBounds(0, 0, 150, 30);
		descripcionContainer.add(descLabel);

		JTextArea descText = new JTextArea(asignatura.getDescripcion());
		descText.setFont(new Font("SansSerif", Font.PLAIN, 22));
		descText.setLineWrap(true);
		descText.setWrapStyleWord(true);
		descText.setEditable(false);
		descText.setOpaque(false);
		descText.setBounds(150, 0, 650, 120); 

		descripcionContainer.add(descText);
		addScaled.accept(descripcionContainer);
		mipanel.add(descripcionContainer);
		
		JButton btn_descargar = new JButton();
		btn_descargar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	opciones_panel.setVisible(false);
		    	AsignaturasView.this.alerta_DescargarAsignatura(asignatura, addScaled);
		    }
		});
		btn_descargar.setText("Descargar Asignatura");
		btn_descargar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_descargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_descargar.setBackground(new Color(170, 196, 255));
		btn_descargar.setBounds(1230, 641, 238, 40);
		addScaled.accept(btn_descargar);
		mipanel.add(btn_descargar);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	

	//===========================================================================================================================
	
	
	
	
	
	
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
		mensajeLabel.setBounds(222, 10, 370, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_si = new JButton();
		btn_si.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();	
				AsignaturasView.this.confirmar_eliminarAsignatura(addScaled);
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
	
	
	public void confirmar_eliminarAsignatura(Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¡Asignatura eliminada con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(222, 56, 360, 51);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				opciones_panel.setVisible(false);
				AsignaturasView.this.panel_asignaturas(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Asignaturas");
		btn_volver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_volver.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_volver.setBackground(Color.decode("#02A115"));
		btn_volver.setBounds(245, 250, 248, 40);
		alerta_panel.add(btn_volver);
		

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
				opciones_panel.setVisible(false);
				AsignaturasView.this.panel_asignaturas(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Asignaturas");
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
				opciones_panel.setVisible(false);
				AsignaturasView.this.panel_asignaturas(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Panel de Asignaturas");
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

	
	public void mostrar_lista_para_editar(Consumer<JComponent> addScaled) {
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
		
		JPanel asignaturasContainer = new JPanel();
		asignaturasContainer.setLayout(new BoxLayout(asignaturasContainer, BoxLayout.Y_AXIS));
		asignaturasContainer.setBackground(Color.decode("#27548A"));
		
		try {
			    AsignaturasModel asignaturaModel = new AsignaturasModel();
			    List<Asignatura> asignaturas = asignaturaModel.gettAll();
	
			    for (Asignatura asignatura : asignaturas) {
			    	
			        JButton btnAsignatura = new JButton(asignatura.getNombre());
			        btnAsignatura.setFont(new Font("SansSerif", Font.PLAIN, 26));
			        btnAsignatura.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
			        btnAsignatura.setBackground(new Color(170, 196, 255));
			        btnAsignatura.setAlignmentX(Component.CENTER_ALIGNMENT);
			        btnAsignatura.setMaximumSize(new Dimension(1348, 117));
	
			        btnAsignatura.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                opciones_panel.setVisible(false);
			                AsignaturasView.this.editar_asignatura(asignatura, addScaled, asignatura);    
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
							AsignaturasView.this.detalles_asignatura(null, addScaled);
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
							AsignaturasView.this.mostrar_lista_para_editar(addScaled);						
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
	}
	
	
	//===========================================================================================================================
	
	
	public void alerta_DescargarAsignatura(Asignatura asignatura, Consumer<JComponent> addScaled) {

	    if (asignatura == null) {
	        JOptionPane.showMessageDialog(ventana, 
	            "No se ha seleccionado una asignatura válida", 
	            "Error", 
	            JOptionPane.ERROR_MESSAGE);
	        return;
	    }

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
	    
	    JLabel mensajeLabel = new JLabel("¿Confirma que desea descargar esta asignatura?");
	    mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
	    mensajeLabel.setBounds(138, 10, 505, 97);
	    alerta_panel.add(mensajeLabel);
	    
	    JButton btn_aceptar = new JButton("Aceptar");
	    btn_aceptar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            dialogo.dispose();
	            opciones_panel.setVisible(false);
	            
	            JFileChooser fileChooser = new JFileChooser();
	            fileChooser.setDialogTitle("Guardar información de asignatura como PDF");
	            String defaultFileName = "Asignatura_" + asignatura.getNombre().replace(" ", "_") + ".pdf";
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
	                
	                try {
	                    PDFGenerator pdfGenerator = new PDFGenerator();
	                    pdfGenerator.generarAsignaturaPDF(asignatura, filePath);
	                    
	                    confirmar_AsignaturaDescargada(addScaled);
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(ventana, 
	                        "Error al generar el PDF: " + ex.getMessage(),
	                        "Error", 
	                        JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
	            }
	        }
	    });
	    btn_aceptar.setFont(new Font("SansSerif", Font.PLAIN, 22));
	    btn_aceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
	    btn_aceptar.setBackground(new Color(170, 196, 255));
	    btn_aceptar.setBounds(435, 250, 192, 40);
	    alerta_panel.add(btn_aceptar);
	    
	    JButton btn_no = new JButton("No, volver");
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
	
	public void confirmar_AsignaturaDescargada(Consumer<JComponent> addScaled) {
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
		
		JLabel mensajeLabel = new JLabel("¡Asignatura descargada correctamente!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(185, 10, 400, 97);
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
