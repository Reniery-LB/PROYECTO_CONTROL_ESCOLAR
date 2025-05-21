package aplication;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controllers.AuthController;
import aplication.ScalableUtils;

public class Main {

	public static void main(String[] args) {
		
		AuthController app = new AuthController();	
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Consumer<JComponent> addScaled = ScalableUtils.createScaler(screenSize.width, screenSize.height);	
		app.login(addScaled);
		
		//NOTA: IMPLEMENTAR FUNCION AL BTN BASURA Y COORDINAR LOS BTN VOLVER W
		//Y COLOCAR IMG LETRA EN EDITAR GRUPO
	}

}
