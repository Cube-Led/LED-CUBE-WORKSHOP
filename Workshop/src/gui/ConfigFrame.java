package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Workshop.Cube;
import Workshop.UserPolling;

public class ConfigFrame extends JFrame implements WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 752538686633551204L;

	private final UserPolling polling;
	
	private JLabel lb_size;
	private JLabel lb_isMono;
	
	private JTextField txt_size;
	private JCheckBox ck_isMono;
	
	ConfigFrame(UserPolling u)
	{
		super();
		this.polling = u;
		this.setTitle("Configuration");
		
		this.setSize(300, 200);
		this.setVisible(true);
		this.addWindowListener(this);
		
		init();
	}
	
	public void init()
	{
		this.lb_isMono = new JLabel("Cube monochrome ?");
		this.lb_isMono.setName("lb_isMono");
		
		this.lb_size = new JLabel("Taille du cube");
		this.lb_size.setName("lb_size");
		
		this.txt_size = new JTextField("" + Cube.DEFAULT_CUBE_SIZE);
		this.txt_size.setName("txt_size");
		
		this.ck_isMono = new JCheckBox("Monochrome", false);
		this.ck_isMono.setName("ck_isMono");
		
		JPanel defaultPan = new JPanel();
		defaultPan.add(lb_size);
		defaultPan.add(txt_size);
		defaultPan.add(lb_isMono);
		defaultPan.add(ck_isMono);
		this.setContentPane(defaultPan);
		this.update(getGraphics());
	}

	private void saveData()
	{
		this.polling.setTheCube(new Cube(Integer.parseInt(this.txt_size.getText()), ck_isMono.isSelected()));
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		saveData();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
	
	
}
