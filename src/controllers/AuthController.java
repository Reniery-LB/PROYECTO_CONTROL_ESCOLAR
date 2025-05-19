package controllers;

import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import views.AuthView;

public class AuthController {
	
	private AuthView vista;
	
	public AuthController() {
		
		vista = new AuthView();
	}
	
	public void login(Consumer<JComponent> addScaled) {
		vista.login(addScaled);
	}
	
	public void registro(Consumer<JComponent> addScaled) {
		vista.registro(addScaled);
	}
	
	public void administrador(Consumer<JComponent> addScaled) {
		vista.administrador(addScaled);
	}

}
