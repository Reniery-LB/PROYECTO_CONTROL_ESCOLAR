package controllers;

import java.util.function.Consumer;

import javax.swing.JComponent;

import views.AsignaturasView;

public class AsignaturasController {
	
	private AsignaturasView vista;
	
	public AsignaturasController() {
		vista = new AsignaturasView();
	}
	
	public void asignaturas(Consumer<JComponent> addScaled) {
		vista.asignaturas(addScaled);
	}

}
