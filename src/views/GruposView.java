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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
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

import aplication.ScalableUtils;
import controllers.AlumnosController;
import controllers.AsignaturasController;
import controllers.AuthController;
import controllers.DocentesController;
import models.Alumno;
import models.AlumnoModel;
import models.ConnectionModel;
import models.Grupo;
import models.GruposModel;

public class GruposView {
	
	private static final int BASE_ANCHO = 1024;
	private static final int BASE_ALTURA = 768;
	private JFrame ventana;
	private JPanel mipanel;
	private JPanel opciones_panel;
	
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
				GruposView.this.editar_grupo(addScaled, null);
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
				GruposView.this.editar_grupo(addScaled, null);
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
				GruposView.this.crear_grupo(addScaled, null);	
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
				GruposView.this.crear_grupo(addScaled, null);
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
				GruposView.this.grupos_registros(addScaled);
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
				GruposView.this.grupos_registros(addScaled);	
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
				GruposView.this.grupos_registros(addScaled);	
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
				GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
	
	
	public void grupos_registros(Consumer<JComponent> addScaled) {
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
		
	    JPanel gruposContainer = new JPanel();
	    gruposContainer.setLayout(new BoxLayout(gruposContainer, BoxLayout.Y_AXIS));
	    gruposContainer.setBackground(Color.decode("#27548A"));
	    
	    //LO IMPORTANTE DE GRUPOS_REGISTROS
	    
	    String[] nombresGrupos = {"ITC", "IDS", "Ciberseguridad", "Grupo 4", "Grupo 5", "Grupo 6", "Grupo 7"};
	    
	    for (String nombreGrupo : nombresGrupos) {
	        JButton btnGrupo = new JButton(nombreGrupo);
	        btnGrupo.setFont(new Font("SansSerif", Font.PLAIN, 26));
	        btnGrupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
	        btnGrupo.setBackground(new Color(170, 196, 255));
	        btnGrupo.setAlignmentX(Component.CENTER_ALIGNMENT);
	        btnGrupo.setMaximumSize(new Dimension(1348, 117));
	        
	        btnGrupo.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                opciones_panel.setVisible(false);
	                JOptionPane.showMessageDialog(ventana, "Mostrando detalles del grupo: " + nombreGrupo);
	            }
	        });
	        
	        gruposContainer.add(btnGrupo);
	        gruposContainer.add(Box.createRigidArea(new Dimension(0, 10))); 
	    }
	    
	    JScrollPane scrollGrupos = new JScrollPane(gruposContainer);
	    scrollGrupos.setBorder(BorderFactory.createEmptyBorder());
	    scrollGrupos.setBounds(93, 274, 1348, 345); 
	    addScaled.accept(scrollGrupos);
	    mipanel.add(scrollGrupos);
		
	    /////////////////////////////////////////////////////////////////////////////////////////////
	    
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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
		
//		JButton btn_itc = new JButton("ITC");
//		btn_itc.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				GruposView.this.ITC(addScaled);
//			}
//		});
//		btn_itc.setFont(new Font("SansSerif", Font.PLAIN, 26));
//		btn_itc.setBounds(93, 274, 1348, 117);
//		btn_itc.setBackground(Color.decode("#AAC4FF"));
//		btn_itc.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		addScaled.accept(btn_itc);
//		mipanel.add(btn_itc);
//		
//		JButton btn_ids = new JButton("IDS");
//		btn_ids.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				GruposView.this.IDS(addScaled);
//			}
//		});
//		btn_ids.setFont(new Font("SansSerif", Font.PLAIN, 26));
//		btn_ids.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		btn_ids.setBackground(new Color(170, 196, 255));
//		btn_ids.setBounds(93, 387, 1348, 117);
//		addScaled.accept(btn_ids);
//		mipanel.add(btn_ids);
//		
//		JButton btn_ciber = new JButton("Ciberseguridad");
//		btn_ciber.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				opciones_panel.setVisible(false);
//				GruposView.this.Ciberseguridad(addScaled);
//			}
//		});
//		btn_ciber.setFont(new Font("SansSerif", Font.PLAIN, 26));
//		btn_ciber.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
//		btn_ciber.setBackground(new Color(170, 196, 255));
//		btn_ciber.setBounds(93, 502, 1348, 117);
//		addScaled.accept(btn_ciber);
//		mipanel.add(btn_ciber);
	}
	
	
	//===========================================================================================================================
	
	
	public void crear_grupo(Consumer<JComponent> addScaled, String letraSeleccionada) {
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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
		
		if(letraSeleccionada != null) {
	        JLabel iconoLetra = new JLabel();
	        iconoLetra.setIcon(new ImageIcon(getClass().getResource("/img/icono_letra" + letraSeleccionada + ".png")));
	        iconoLetra.setBounds(1268, 258, 111, 110);
	        addScaled.accept(iconoLetra);
	        mipanel.add(iconoLetra);
	    }
		
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
		addScaled.accept(nombre_grupoField);
		mipanel.add(nombre_grupoField);
		
		JLabel docente_grupoLabel = new JLabel("Docente del grupo:");
		docente_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_grupoLabel.setBounds(111, 301, 192, 29);
		addScaled.accept(docente_grupoLabel);
		mipanel.add(docente_grupoLabel);
		
		JTextField docente_grupoField = new JTextField();
		docente_grupoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		docente_grupoField.setColumns(10);
		docente_grupoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docente_grupoField.setBackground(new Color(217, 217, 217));
		docente_grupoField.setBounds(313, 297, 386, 40);
		addScaled.accept(docente_grupoField);
		mipanel.add(docente_grupoField);
		
		JLabel ID_grupoLabel = new JLabel("ID del grupo:");
		ID_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		ID_grupoLabel.setBounds(169, 362, 129, 29);
		addScaled.accept(ID_grupoLabel);
		mipanel.add(ID_grupoLabel);
		
		JTextField ID_grupoField = new JTextField();
		ID_grupoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		ID_grupoField.setColumns(10);
		ID_grupoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		ID_grupoField.setBackground(new Color(217, 217, 217));
		ID_grupoField.setBounds(313, 358, 386, 40);
		addScaled.accept(ID_grupoField);
		mipanel.add(ID_grupoField);
		
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
				GruposView.this.añadir_alumno(addScaled, "crear_grupo");
			}
		});
		btn_añadir_alumnos.setText("Haz clic aquí");
		btn_añadir_alumnos.setBackground(Color.decode("#AAC4FF"));
		btn_añadir_alumnos.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir_alumnos.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir_alumnos.setBounds(313, 534, 386, 40);
		addScaled.accept(btn_añadir_alumnos);
		mipanel.add(btn_añadir_alumnos);
		
		JButton btn_añadir_letra = new JButton();
		btn_añadir_letra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.letra_grupos(addScaled, "crear_grupo");
			}
		});
		btn_añadir_letra.setText("Añadir letra");
		btn_añadir_letra.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir_letra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir_letra.setBackground(new Color(170, 196, 255));
		btn_añadir_letra.setBounds(1221, 421, 192, 40);
		addScaled.accept(btn_añadir_letra);
		mipanel.add(btn_añadir_letra);
		
		JLabel turno_grupoLabel = new JLabel("Turno:");
		turno_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		turno_grupoLabel.setBounds(230, 425, 69, 29);
		addScaled.accept(turno_grupoLabel);
		mipanel.add(turno_grupoLabel);
		
		
		
		String dataset []= {"Matutino", "Vespertino"};
		JComboBox turno_comboBox = new JComboBox(dataset);
		turno_comboBox.setBackground(new Color(217, 217, 217));
		turno_comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		turno_comboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
		turno_comboBox.setBounds(313, 425, 386, 40);
		addScaled.accept(turno_comboBox);
		mipanel.add(turno_comboBox);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(56, 210, 1435, 480);
		fondo_grupo.setBackground(Color.WHITE);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
		
		JButton btn_crear = new JButton("Crear");
	    btn_crear.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            
	        }
	    });
	    
		btn_crear.setText("Crear");
		btn_crear.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_crear.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_crear.setBackground(new Color(170, 196, 255));
		btn_crear.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_crear);
		mipanel.add(btn_crear);
	}
	
	
	//===========================================================================================================================
	
	
	public void letra_grupos(Consumer<JComponent> addScaled, String origen) {
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
		
		JLabel fondo_barra_2 = new JLabel("Seleccione su letra de grupo");
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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
	            if ("crear_grupo".equals(origen)) {
	                GruposView.this.crear_grupo(addScaled, null);
	            } else if ("editar_grupo".equals(origen)) {
	                GruposView.this.editar_grupo(addScaled, null);
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
		
		JButton btn_letraA = new JButton();
		btn_letraA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.alerta_letra("A", addScaled, origen);
			}
		});
		btn_letraA.setOpaque(true);
		btn_letraA.setBackground(Color.WHITE);
		btn_letraA.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_letraA.setIcon(new ImageIcon(getClass().getResource("/img/letra_a.png")));
		btn_letraA.setBounds(114, 287, 330, 257);
		addScaled.accept(btn_letraA);
		mipanel.add(btn_letraA);
		
		JButton btn_letraB = new JButton();
		btn_letraB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.alerta_letra("B", addScaled, origen);
			}
		});
		btn_letraB.setOpaque(true);
		btn_letraB.setBackground(Color.WHITE);
		btn_letraB.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_letraB.setIcon(new ImageIcon(getClass().getResource("/img/letra_b.png")));
		btn_letraB.setBounds(440, 287, 330, 257);
		addScaled.accept(btn_letraB);
		mipanel.add(btn_letraB);
		
		JButton btn_letraC = new JButton();
		btn_letraC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.alerta_letra("C", addScaled, origen);
			}
		});
		btn_letraC.setOpaque(true);
		btn_letraC.setBackground(Color.WHITE);
		btn_letraC.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_letraC.setIcon(new ImageIcon(getClass().getResource("/img/letra_c.png")));
		btn_letraC.setBounds(770, 287, 330, 257);
		addScaled.accept(btn_letraC);
		mipanel.add(btn_letraC);
		
		JButton btn_letraD = new JButton();
		btn_letraD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.alerta_letra("D", addScaled, origen);
			}
		});
		btn_letraD.setIcon(new ImageIcon(getClass().getResource("/img/letra_d.png")));
		btn_letraD.setOpaque(true);
		btn_letraD.setBackground(Color.WHITE);
		btn_letraD.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_letraD.setBounds(1097, 287, 330, 257);
		addScaled.accept(btn_letraD);
		mipanel.add(btn_letraD);
		
		JLabel fondo_grupo = new JLabel();
		fondo_grupo.setBackground(new Color(255, 255, 255));
		fondo_grupo.setOpaque(true);
		fondo_grupo.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		fondo_grupo.setBounds(114, 287, 1313, 257);
		addScaled.accept(fondo_grupo);
		mipanel.add(fondo_grupo);
	}
	
	
	//===========================================================================================================================
	
	
	public void IDS(Consumer<JComponent> addScaled) {

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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
				GruposView.this.grupos_registros(addScaled);
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
				GruposView.this.editar_grupo(addScaled, null);
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JLabel titulo_ids = new JLabel("IDS");
		titulo_ids.setFont(new Font("SansSerif", Font.PLAIN, 24));
		titulo_ids.setBounds(759, 146, 47, 65);
		addScaled.accept(titulo_ids);
		mipanel.add(titulo_ids);
		
		JLabel docenteLabel = new JLabel("Docente a cargo: Jhonathan Giovanni Soto");
		docenteLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docenteLabel.setBounds(111, 229, 499, 29);
		addScaled.accept(docenteLabel);
		mipanel.add(docenteLabel);
		
		JLabel idLabel = new JLabel("ID del docente: 12");
		idLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		idLabel.setBounds(130, 279, 499, 29);
		addScaled.accept(idLabel);
		mipanel.add(idLabel);
		
		JLabel listaLabel = new JLabel("Lista de estudiantes:");
		listaLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		listaLabel.setBounds(76, 333, 499, 29);
		addScaled.accept(listaLabel);
		mipanel.add(listaLabel);
		
		JLabel turnoLabel = new JLabel("Turno: Vespertino");
		turnoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		turnoLabel.setBounds(885, 333, 499, 29);
		addScaled.accept(turnoLabel);
		mipanel.add(turnoLabel);
		
		String titles []= {"Apellido paterno", "Apellido materno", "Nombre", "No. Control"};
		
		String data [][]= {
							{"Diaz"   , "Barrera"  , "Zahir Fernando"  , "28"},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "}
		};
		
		JTable table = new JTable(data,titles) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.GRAY); 
		header.setForeground(Color.BLACK);
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
				GruposView.this.alerta_btnDescargar(addScaled);
			}
		});
		btn_descargar.setText("Descargar");
		btn_descargar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_descargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_descargar.setBackground(new Color(170, 196, 255));
		btn_descargar.setBounds(1202, 616, 192, 40);
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
	
	public void ITC(Consumer<JComponent> addScaled) {

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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
				GruposView.this.grupos_registros(addScaled);
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
				GruposView.this.editar_grupo(addScaled, null);
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JLabel titulo_itc = new JLabel("ITC");
		titulo_itc.setFont(new Font("SansSerif", Font.PLAIN, 24));
		titulo_itc.setBounds(759, 146, 47, 65);
		addScaled.accept(titulo_itc);
		mipanel.add(titulo_itc);
		
		JLabel docenteLabel = new JLabel("Docente a cargo: Jhonathan Giovanni Soto");
		docenteLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docenteLabel.setBounds(111, 229, 499, 29);
		addScaled.accept(docenteLabel);
		mipanel.add(docenteLabel);
		
		JLabel idLabel = new JLabel("ID del docente: 12");
		idLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		idLabel.setBounds(130, 279, 499, 29);
		addScaled.accept(idLabel);
		mipanel.add(idLabel);
		
		JLabel listaLabel = new JLabel("Lista de estudiantes:");
		listaLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		listaLabel.setBounds(76, 333, 499, 29);
		addScaled.accept(listaLabel);
		mipanel.add(listaLabel);
		
		JLabel turnoLabel = new JLabel("Turno: Vespertino");
		turnoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		turnoLabel.setBounds(885, 333, 499, 29);
		addScaled.accept(turnoLabel);
		mipanel.add(turnoLabel);
		
		String titles []= {"Apellido paterno", "Apellido materno", "Nombre", "No. Control"};
		
		String data [][]= {
							{"Diaz"   , "Barrera"  , "Zahir Fernando"  , "28"},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "}
		};
		
		JTable table = new JTable(data,titles) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.GRAY); 
		header.setForeground(Color.BLACK);
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
				GruposView.this.alerta_btnDescargar(addScaled);
			}
		});
		btn_descargar.setText("Descargar");
		btn_descargar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_descargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_descargar.setBackground(new Color(170, 196, 255));
		btn_descargar.setBounds(1202, 616, 192, 40);
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
	
	public void Ciberseguridad(Consumer<JComponent> addScaled) {

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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
				GruposView.this.grupos_registros(addScaled);
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
				GruposView.this.editar_grupo(addScaled, null);
			}
		});
		btn_editar.setText("Editar");
		btn_editar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_editar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_editar.setBackground(new Color(170, 196, 255));
		btn_editar.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_editar);
		mipanel.add(btn_editar);
		
		JLabel titulo = new JLabel("Ciberseguridad");
		titulo.setFont(new Font("SansSerif", Font.PLAIN, 24));
		titulo.setBounds(689, 146, 207, 65);
		addScaled.accept(titulo);
		mipanel.add(titulo);
		
		JLabel docenteLabel = new JLabel("Docente a cargo: Jhonathan Giovanni Soto");
		docenteLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docenteLabel.setBounds(111, 229, 499, 29);
		addScaled.accept(docenteLabel);
		mipanel.add(docenteLabel);
		
		JLabel idLabel = new JLabel("ID del docente: 12");
		idLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		idLabel.setBounds(130, 279, 499, 29);
		addScaled.accept(idLabel);
		mipanel.add(idLabel);
		
		JLabel listaLabel = new JLabel("Lista de estudiantes:");
		listaLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		listaLabel.setBounds(76, 333, 499, 29);
		addScaled.accept(listaLabel);
		mipanel.add(listaLabel);
		
		JLabel turnoLabel = new JLabel("Turno: Vespertino");
		turnoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		turnoLabel.setBounds(885, 333, 499, 29);
		addScaled.accept(turnoLabel);
		mipanel.add(turnoLabel);
		
		String titles []= {"Apellido paterno", "Apellido materno", "Nombre", "No. Control"};
		
		String data [][]= {
							{"Diaz"   , "Barrera"  , "Zahir Fernando"  , "28"},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "},
							{" " , " ", " ", " "}
		};
		
		JTable table = new JTable(data,titles) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.GRAY); 
		header.setForeground(Color.BLACK);
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
				GruposView.this.alerta_btnDescargar(addScaled);
			}
		});
		btn_descargar.setText("Descargar");
		btn_descargar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_descargar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_descargar.setBackground(new Color(170, 196, 255));
		btn_descargar.setBounds(1202, 616, 192, 40);
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
	
	
	public void añadir_alumno(Consumer<JComponent> addScaled, String origen) {
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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
		
		JLabel fondo_barra = new JLabel("Ingrese los datos del alumno");
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
					GruposView.this.crear_grupo(addScaled, null);					
				} else if ("editar_grupo".equals(origen)) {
					GruposView.this.editar_grupo(addScaled, null);	
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
		
		JButton btn_guardar = new JButton();
		btn_guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.alerta_añadirAlumnos(addScaled);
			}
		});
		btn_guardar.setText("Guardar");
		btn_guardar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_guardar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_guardar.setBackground(new Color(170, 196, 255));
		btn_guardar.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_guardar);
		mipanel.add(btn_guardar);
		
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
		scroll.setBounds(272, 205, 1221, 480);
		addScaled.accept(scroll);
		mipanel.add(scroll);
		
		JButton btn_añadir1 = new JButton("Añadir");
		btn_añadir1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.eliminar_alumno(addScaled);
			}
		});
		btn_añadir1.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir1.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir1.setBackground(new Color(170, 196, 255));
		btn_añadir1.setBounds(28, 302, 192, 40);
		addScaled.accept(btn_añadir1);
		mipanel.add(btn_añadir1);
		
		JButton btn_añadir2 = new JButton("Añadir");
		btn_añadir2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.eliminar_alumno(addScaled);
			}
		});
		btn_añadir2.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir2.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir2.setBackground(new Color(170, 196, 255));
		btn_añadir2.setBounds(28, 379, 192, 40);
		addScaled.accept(btn_añadir2);
		mipanel.add(btn_añadir2);
		
		JButton btn_añadir3 = new JButton("Añadir");
		btn_añadir3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.eliminar_alumno(addScaled);
			}
		});
		btn_añadir3.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir3.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir3.setBackground(new Color(170, 196, 255));
		btn_añadir3.setBounds(28, 463, 192, 40);
		addScaled.accept(btn_añadir3);
		mipanel.add(btn_añadir3);
		
		JButton btn_añadir4 = new JButton("Añadir");
		btn_añadir4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.eliminar_alumno(addScaled);
			}
		});
		btn_añadir4.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir4.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir4.setBackground(new Color(170, 196, 255));
		btn_añadir4.setBounds(28, 546, 192, 40);
		addScaled.accept(btn_añadir4);
		mipanel.add(btn_añadir4);
		
		JButton btn_añadir5 = new JButton("Añadir");
		btn_añadir5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.eliminar_alumno(addScaled);
			}
		});
		btn_añadir5.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir5.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir5.setBackground(new Color(170, 196, 255));
		btn_añadir5.setBounds(28, 625, 192, 40);
		addScaled.accept(btn_añadir5);
		mipanel.add(btn_añadir5);
	}
	
	
	//===========================================================================================================================
	
	
	public void eliminar_alumno(Consumer<JComponent> addScaled) {

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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
		
		JLabel fondo_barra = new JLabel("Ingrese los datos del alumno");
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
				GruposView.this.añadir_alumno(addScaled, "editar_grupo");
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
		
		JButton btn_guardar = new JButton();
		btn_guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.alerta_añadirAlumnos(addScaled);
			}
		});
		btn_guardar.setText("Guardar");
		btn_guardar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_guardar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_guardar.setBackground(new Color(170, 196, 255));
		btn_guardar.setBounds(688, 716, 192, 40);
		addScaled.accept(btn_guardar);
		mipanel.add(btn_guardar);
		
		String titles []= {"Apellido paterno", "Apellido materno", "Nombres", "ID"};
		
		String data [][]= {
							{"Diaz"       , "Barrera"   , "Zahir Fernando"    , "28"},
							{"Grijalva"   , "Ochoa"     , "Keyra Yariely"     , "28"},
							{"Nuñez"      , "Cota"      , "Diego Emiliano"    , "28"},
							{" "   , " "  , " "  , " "},
							{" "   , " "  , " "  , " "},
		};
		
		DefaultTableModel model = new DefaultTableModel(data,titles) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		JTable table = new JTable(model);
		
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
		scroll.setBounds(272, 205, 1221, 480);
		addScaled.accept(scroll);
		mipanel.add(scroll);
		
		JButton btn_eliminar1 = new JButton("Eliminar");
		btn_eliminar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
		        DefaultTableModel model = (DefaultTableModel) table.getModel();
		        if(model.getRowCount() > 0) {
		        	 model.removeRow(0);
		        }
			}
		});
		btn_eliminar1.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_eliminar1.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_eliminar1.setBackground(new Color(170, 196, 255));
		btn_eliminar1.setBounds(28, 302, 192, 40);
		addScaled.accept(btn_eliminar1);
		mipanel.add(btn_eliminar1);
		
		JButton btn_eliminar2 = new JButton("Eliminar");
		btn_eliminar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
		        DefaultTableModel model = (DefaultTableModel) table.getModel();
		        if(model.getRowCount() > 1) {
		        	 model.removeRow(1);
		        }
			}
		});
		btn_eliminar2.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_eliminar2.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_eliminar2.setBackground(new Color(170, 196, 255));
		btn_eliminar2.setBounds(28, 379, 192, 40);
		addScaled.accept(btn_eliminar2);
		mipanel.add(btn_eliminar2);
		
		JButton btn_eliminar3 = new JButton("Eliminar");
		btn_eliminar3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
		        DefaultTableModel model = (DefaultTableModel) table.getModel();
		        if(model.getRowCount() > 2) {
		        	 model.removeRow(2);
		        }
			}
		});
		btn_eliminar3.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_eliminar3.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_eliminar3.setBackground(new Color(170, 196, 255));
		btn_eliminar3.setBounds(28, 463, 192, 40);
		addScaled.accept(btn_eliminar3);
		mipanel.add(btn_eliminar3);
		
		JButton btn_añadir4 = new JButton("Añadir");
		btn_añadir4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.eliminar_alumno(addScaled);
			}
		});
		btn_añadir4.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir4.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir4.setBackground(new Color(170, 196, 255));
		btn_añadir4.setBounds(28, 546, 192, 40);
		addScaled.accept(btn_añadir4);
		mipanel.add(btn_añadir4);
		
		JButton btn_añadir5 = new JButton("Añadir");
		btn_añadir5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.eliminar_alumno(addScaled);
			}
		});
		btn_añadir5.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir5.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir5.setBackground(new Color(170, 196, 255));
		btn_añadir5.setBounds(28, 625, 192, 40);
		addScaled.accept(btn_añadir5);
		mipanel.add(btn_añadir5);
	}
	
	
	//===========================================================================================================================

	
	public void alerta_letra(String letraSeleccionada, Consumer<JComponent> addScaled, String origen) {

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
		
		JLabel mensajeLabel = new JLabel("¡Por favor, confirme que usted selecciono esta letra!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(125, 10, 511, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_seleccionar = new JButton();
		btn_seleccionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
			}
		});
		btn_seleccionar.setText("Seleccionar otra");
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
				switch(origen) {
					case "crear_grupo":
						GruposView.this.crear_grupo(addScaled, letraSeleccionada);
						break;
					case "editar_grupo":
						GruposView.this.editar_grupo(addScaled, letraSeleccionada);
						break;
					default:
						GruposView.this.panel_grupos(addScaled);
				}
			}
		});
		btn_confirmar.setText("Confirmar");
		btn_confirmar.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_confirmar.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_confirmar.setBackground(new Color(170, 196, 255));
		btn_confirmar.setBounds(125, 250, 192, 40);
		alerta_panel.add(btn_confirmar);
		
		
		ImageIcon icono_letra = new ImageIcon(getClass().getResource("/img/icono_letra" + letraSeleccionada + ".png"));
		JLabel icono = new JLabel(icono_letra);
		icono.setBounds(323, 97, 111, 110);
		alerta_panel.add(icono);
		
		dialogo.add(alerta_panel);
		dialogo.setVisible(true);
	}
	
	
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
		
		JLabel mensajeLabel = new JLabel("¡Alumnos añadidos con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(221, 10, 324, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_volver = new JButton();
		btn_volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();
				ventana.dispose();
				opciones_panel.setVisible(false);
				AuthController ac = new AuthController();
				ac.administrador(addScaled);
			}
		});
		btn_volver.setForeground(new Color(255, 255, 255));
		btn_volver.setText("Volver al administrador");
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

	
	public void editar_grupo(Consumer<JComponent> addScaled, String letraSeleccionada) {

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
		
		JLabel fondo_barra_2 = new JLabel("Editar grupo");
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
							GruposView.this.grupos_registros(addScaled);		
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
							GruposView.this.grupos_registros(addScaled);
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
							GruposView.this.crear_grupo(addScaled, null);	
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
							GruposView.this.editar_grupo(addScaled, null);
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
		grupos_barraLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
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
		nombre_grupoField.setText("Ingles");
		nombre_grupoField.setBackground(Color.decode("#D9D9D9"));
		nombre_grupoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		nombre_grupoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		nombre_grupoField.setBounds(313, 239, 386, 40);
		nombre_grupoField.setColumns(10);
		addScaled.accept(nombre_grupoField);
		mipanel.add(nombre_grupoField);
		
		JLabel docente_grupoLabel = new JLabel("Docente del grupo:");
		docente_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		docente_grupoLabel.setBounds(111, 301, 192, 29);
		addScaled.accept(docente_grupoLabel);
		mipanel.add(docente_grupoLabel);
		
		JTextField docente_grupoField = new JTextField();
		docente_grupoField.setText("Reniery Lucero Beltran");
		docente_grupoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		docente_grupoField.setColumns(10);
		docente_grupoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		docente_grupoField.setBackground(new Color(217, 217, 217));
		docente_grupoField.setBounds(313, 297, 386, 40);
		addScaled.accept(docente_grupoField);
		mipanel.add(docente_grupoField);
		
		JLabel ID_grupoLabel = new JLabel("ID del grupo:");
		ID_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		ID_grupoLabel.setBounds(169, 362, 129, 29);
		addScaled.accept(ID_grupoLabel);
		mipanel.add(ID_grupoLabel);
		
		JTextField ID_grupoField = new JTextField();
		ID_grupoField.setText("40");
		ID_grupoField.setFont(new Font("SansSerif", Font.PLAIN, 18));
		ID_grupoField.setColumns(10);
		ID_grupoField.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		ID_grupoField.setBackground(new Color(217, 217, 217));
		ID_grupoField.setBounds(313, 358, 386, 40);
		addScaled.accept(ID_grupoField);
		mipanel.add(ID_grupoField);
		
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
				GruposView.this.añadir_alumno(addScaled, "editar_grupo");
			}
		});
		btn_añadir_alumnos.setText("Haz clic aquí");
		btn_añadir_alumnos.setBackground(Color.decode("#AAC4FF"));
		btn_añadir_alumnos.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir_alumnos.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir_alumnos.setBounds(313, 534, 386, 40);
		addScaled.accept(btn_añadir_alumnos);
		mipanel.add(btn_añadir_alumnos);
		
		JButton btn_añadir_letra = new JButton();
		btn_añadir_letra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones_panel.setVisible(false);
				GruposView.this.letra_grupos(addScaled, "editar_grupo");
			}
		});
		btn_añadir_letra.setText("Añadir letra");
		btn_añadir_letra.setFont(new Font("SansSerif", Font.PLAIN, 22));
		btn_añadir_letra.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		btn_añadir_letra.setBackground(new Color(170, 196, 255));
		btn_añadir_letra.setBounds(1221, 421, 192, 40);
		addScaled.accept(btn_añadir_letra);
		mipanel.add(btn_añadir_letra);
		
		JLabel turno_grupoLabel = new JLabel("Turno:");
		turno_grupoLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		turno_grupoLabel.setBounds(230, 425, 69, 29);
		addScaled.accept(turno_grupoLabel);
		mipanel.add(turno_grupoLabel);
		
		String dataset []= {"Matutino", "Vespertino"};
		JComboBox turno_comboBox = new JComboBox(dataset);
		turno_comboBox.setBackground(new Color(217, 217, 217));
		turno_comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		turno_comboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
		turno_comboBox.setBounds(313, 421, 386, 40);
		addScaled.accept(turno_comboBox);
		mipanel.add(turno_comboBox);
		
		if(letraSeleccionada != null) {
	        JLabel iconoLetra = new JLabel();
	        iconoLetra.setIcon(new ImageIcon(getClass().getResource("/img/icono_letra" + letraSeleccionada + ".png")));
	        iconoLetra.setBounds(1268, 258, 111, 110);
	        addScaled.accept(iconoLetra);
	        mipanel.add(iconoLetra);
	    }
		
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
				opciones_panel.setVisible(false);
				GruposView.this.confirmar_grupoCreado(addScaled);
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
				GruposView.this.alerta_eliminar(addScaled);
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
		
		JLabel mensajeLabel = new JLabel("¿Desea eliminar este grupo?");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(229, 10, 290, 97);
		alerta_panel.add(mensajeLabel);
		
		JButton btn_si = new JButton();
		btn_si.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogo.dispose();	
				GruposView.this.confirmar_grupoEliminado(addScaled);
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
		mensajeLabel.setBounds(231, 43, 324, 51);
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
		
		JLabel mensajeLabel_1 = new JLabel("¿Qué opción desea hacer?");
		mensajeLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 18));
		mensajeLabel_1.setBounds(256, 63, 230, 65);
		alerta_panel.add(mensajeLabel_1);
		
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
		
		JLabel mensajeLabel = new JLabel("¡Grupo editado con éxito!");
		mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		mensajeLabel.setBounds(245, 58, 259, 51);
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
	
	public void remover() {
		mipanel.removeAll();
		mipanel.revalidate();
		mipanel.repaint();
	}

}
