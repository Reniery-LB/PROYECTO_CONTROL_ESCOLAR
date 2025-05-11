package aplication;

import controllers.AuthController;

public class Main {

	public static void main(String[] args) {
		
		AuthController app = new AuthController();
		app.login();
	}

}
