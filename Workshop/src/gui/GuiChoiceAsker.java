package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Workshop.ChoiceAsker;


public class GuiChoiceAsker extends Thread implements ChoiceAsker, ActionListener{

	private boolean finish;
	public int res;
	
	public GuiChoiceAsker()
	{
		super();
		res=-1;
		finish = false;
	}
	
	public void run()
	{
		
	}
	
	public synchronized int askInteger() {
		
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		finish = true;
		res = 7;
		this.notifyAll();
	}
}
