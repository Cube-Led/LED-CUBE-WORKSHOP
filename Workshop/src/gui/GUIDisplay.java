package gui;

import gui.Cube3D.LwjglTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Workshop.Display;
import Workshop.Instruction;
import Workshop.UserPolling;


/**
 * Graphical implementation of the display
 * @author Clement
 *
 */
public class GUIDisplay extends JFrame implements Display, ActionListener{

	private UserPolling polling;
	
	private GuiChoiceAsker choiceComponent;
	
	public static final Dimension DEFAULT_DIMENSION = new Dimension(375,175);
	
	private MenuBar menuBar;
	private Menu menuFichier;
	private Menu menuConfig;
	
	
	public GUIDisplay(GuiChoiceAsker choice)
	{
		this.choiceComponent = choice;
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Worshop");
		this.setVisible(true);
		this.setSize(350, 175);
		this.setResizable(false);

		configureMenu();
		this.setContentPane(new ViewMainMenu(this));
		this.getContentPane().update(this.getGraphics());
		
		
		
	}
	private void configureMenu()
	{
		this.menuBar = new MenuBar();
		this.menuFichier = new Menu("Fichier");
		this.menuConfig = new Menu("Options");
		
		MenuItem vue3D = new MenuItem("Vue3D");
		vue3D.addActionListener(this);
		this.menuFichier.add(vue3D);
		
		MenuItem quitter = new MenuItem("Quitter");
		quitter.addActionListener(this);
		this.menuFichier.add(quitter);
		
		MenuItem configCube = new MenuItem("Définir le cube");
		configCube.addActionListener(this);
		this.menuConfig.add(configCube);
		
		this.menuBar.add(menuFichier);
		this.menuBar.add(menuConfig);
		
		this.setMenuBar(menuBar);
		this.getContentPane().validate();
	}
	public void setUserPolling(UserPolling poll)
	{
		this.polling = poll;
	}
	
	@Override
	public void displayChoiceOfInstruction(Instruction[] inst) {
		if(this.getContentPane() instanceof ViewCreateProgram)
		{
			((ViewCreateProgram)this.getContentPane()).displayChoiceOfInstruction(inst);
		}
	}

	@Override
	public void println(String str) {
		JOptionPane.showMessageDialog(this.getContentPane(), str);
	}

	private void displayMessageInMsgBox(String str)
	{
		JOptionPane.showMessageDialog(this.getContentPane(), str);
	}
	
	@Override
	public void print(String str) {}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof MenuItem)
		{
			if(((MenuItem)arg0.getSource()).getLabel().equals("Quitter"))
				System.exit(0);
			
			if(((MenuItem)arg0.getSource()).getLabel().equals("Définir le cube"))
				new ConfigFrame(polling);
			
			if(((MenuItem)arg0.getSource()).getLabel().equals("Vue3D"))
			{
				new LwjglTest(this.polling);
			}
		}
			
	}
	@Override
	public void displayBuffer(Instruction[] inst, int countInstructions) {

		if(this.getContentPane() instanceof ViewCreateProgram)
		{
			((ViewCreateProgram)this.getContentPane()).displayBuffer(inst, countInstructions);
		}
		if(this.getContentPane() instanceof ViewDynamicLED)
		{
			((ViewDynamicLED)this.getContentPane()).displayBuffer(inst, countInstructions);
		}

		}
	

	public UserPolling getPolling() {
		return polling;
	}

	public GuiChoiceAsker getChoice() {
		return choiceComponent;
	}
	@Override
	public void displayAskingOfAnArgument(String str) {
		if(this.getContentPane() instanceof ViewCreateProgram)
		{
			((ViewCreateProgram)this.getContentPane()).askArgument(str);
		}
	}
}