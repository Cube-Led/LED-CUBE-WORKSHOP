package gui;

import gui.Cube3D.View3D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Workshop.Display;
import Workshop.Instruction;
import Workshop.ApplicationPolling;

/**
 * Graphical implementation of the display
 * 
 * @author Clement
 * 
 */
public class GUIDisplay extends JFrame implements Display, ActionListener {

	private static final long serialVersionUID = 1L;

	private ApplicationPolling polling;

	public static final Dimension DEFAULT_DIMENSION = new Dimension(375, 175);

	private MenuBar menuBar;
	private Menu menuFichier;
	private Menu menuConfig;
	private Menu menuVue;

	public GUIDisplay() {
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Worshop");
		this.setSize(350, 175);
		this.setResizable(false);
		configureMenu();
		this.setContentPane(new ViewMainMenu(this));
		this.getContentPane().update(this.getGraphics());
		this.setVisible(true);

	}

	private void configureMenu() {
		this.menuBar = new MenuBar();
		this.menuFichier = new Menu("Fichier");
		this.menuConfig = new Menu("Options");
		this.menuVue = new Menu("Vues");

		MenuItem send = new MenuItem("Envoyer sur l'Arduino");
		send.addActionListener(this);
		this.menuFichier.add(send);

		MenuItem quitter = new MenuItem("Quitter");
		quitter.addActionListener(this);
		this.menuFichier.add(quitter);

		MenuItem configCube = new MenuItem("Définir le cube");
		configCube.addActionListener(this);
		this.menuConfig.add(configCube);

		MenuItem viewCreateProg = new MenuItem("VueArguments");
		viewCreateProg.addActionListener(this);
		this.menuVue.add(viewCreateProg);

		MenuItem viewDynLED = new MenuItem("Vue2D");
		viewDynLED.addActionListener(this);
		this.menuVue.add(viewDynLED);

		MenuItem viewVue3D = new MenuItem("Vue3D");
		viewVue3D.addActionListener(this);
		this.menuVue.add(viewVue3D);

		this.menuBar.add(this.menuFichier);
		this.menuBar.add(this.menuConfig);
		this.menuBar.add(this.menuVue);

		this.setMenuBar(menuBar);
		this.getContentPane().validate();
	}

	public void setApplicationPolling(ApplicationPolling poll) {
		this.polling = poll;
	}

	@Override
	public void displayChoiceOfInstruction(List<Instruction> inst) {
		if (this.getContentPane() instanceof ViewCreateProgram) {
			((ViewCreateProgram) this.getContentPane())
					.displayChoiceOfInstruction(inst);
		}
	}

	@Override
	public void println(String str) {
		JOptionPane.showMessageDialog(this.getContentPane(), str);
	}

	@Override
	public void print(String str) {
		this.println(str);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() instanceof MenuItem) {
			if (((MenuItem) arg0.getSource()).getLabel().equals("Quitter"))
				System.exit(0);
			else if (((MenuItem) arg0.getSource()).getLabel().equals(
					"Définir le cube"))
				new ConfigFrame(polling);
			else if (((MenuItem) arg0.getSource()).getLabel().equals(
					"VueArguments"))
				new ViewCreateProgram(this);
			else if (((MenuItem) arg0.getSource()).getLabel().equals("VueLED"))
				new View2D(this);
			else if (((MenuItem) arg0.getSource()).getLabel().equals("Vue3D")) {
				Thread thread = new Thread(new View3D(this.polling));
				thread.start();
			} else if (((MenuItem) arg0.getSource()).getLabel().equals(
					"Envoyer sur l'Arduino")) {
				JFileChooser saveFile = new JFileChooser();
				saveFile.setApproveButtonText("Envoyer");
				saveFile.showOpenDialog(this);
				File saveInFile = saveFile.getSelectedFile();
				if (saveInFile != null) {
					this.getPolling().sendFileToArduino(saveInFile);
				}
			}
		}

	}

	@Override
	public void displayBuffer(List<Instruction> inst, int countInstructions) {

		if (this.getContentPane() instanceof ViewCreateProgram) {
			((ViewCreateProgram) this.getContentPane()).displayBuffer(inst,
					countInstructions);
		}
		if (this.getContentPane() instanceof View2D) {
			((View2D) this.getContentPane()).displayBuffer(inst,
					countInstructions);
		}
	}

	public ApplicationPolling getPolling() {
		return polling;
	}
}