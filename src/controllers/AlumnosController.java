package controllers;

import java.util.function.Consumer;

import javax.swing.JComponent;

import views.AlumnosView;

public class AlumnosController {
	
	private AlumnosView vista;
	
	public AlumnosController() {
		 vista = new AlumnosView();
	}
	
	public void alumnos(Consumer<JComponent> addScaled) {
		vista.panel_alumno(addScaled);
	}

}
