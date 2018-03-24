package ckj.chenkaiju;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class ManagerJM extends JFrame implements WindowListener{
	

	ManagerJM(String s){
		super(s);
		this.setSize(350, 30);
		this.setVisible(true);
		this.addWindowListener(this);
	}

	public void windowOpened(WindowEvent e) {
		
		
	}

	public void windowClosing(WindowEvent e) {
		System.exit(0);
		
	}

	public void windowClosed(WindowEvent e) {
		System.exit(0);
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
