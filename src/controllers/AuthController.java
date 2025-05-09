package controllers;

import javax.swing.JPanel;

import views.AuthView;

public class AuthController {
	
	private JPanel mipanel;
	private AuthView vista;
	
	public AuthController() {
		
		vista = new AuthView();
	}
	
	public void login() {
		
		vista.login(mipanel);
	}

}
