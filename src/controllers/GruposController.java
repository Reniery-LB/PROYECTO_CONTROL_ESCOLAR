package controllers;

import java.util.function.Consumer;

import javax.swing.JComponent;

import views.GruposView;

public class GruposController {
	
	private GruposView vista;
	
	public GruposController() {
		vista = new GruposView();
	}
	
	public void grupos(Consumer<JComponent> addScaled) {
		vista.panel_grupos(addScaled);
	}
	
	public void grupos_registros(Consumer<JComponent> addScaled) {
		vista.grupos_registros(addScaled);
	}

	
}
