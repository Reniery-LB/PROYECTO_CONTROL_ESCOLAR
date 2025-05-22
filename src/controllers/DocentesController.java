package controllers;

import java.util.function.Consumer;

import javax.swing.JComponent;

import views.DocentesView;

public class DocentesController {
	
	private DocentesView vista;
	
	public DocentesController() {
		vista = new DocentesView();
	}
	
	public void docentes(Consumer<JComponent> addScaled) {
		vista.docentes(addScaled);
	}

}
