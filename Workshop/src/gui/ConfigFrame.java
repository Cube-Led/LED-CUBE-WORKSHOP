package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Workshop.Cube;
import Workshop.ApplicationPolling;

public class ConfigFrame extends JFrame implements WindowListener{
	
	private static final long serialVersionUID = 752538686633551204L;

	private final ApplicationPolling polling;
	
	private JLabel lb_size;
	private JLabel lb_isMono;
	
	private JTextField txt_size;
	private JCheckBox ck_isMono;
	/*private JCheckBox ck_isRGB;*/
	
	ConfigFrame(ApplicationPolling u)
	{
		super();
		this.polling = u;
		this.setTitle("Configuration");
		this.setSize(300, 225);
		this.addWindowListener(this);
		init();
		this.setVisible(true);
	}
	
	public void init()
	{
		JPanel defaultPan = new JPanel();
		defaultPan.setLayout(null);
		
		this.lb_size = new JLabel("Taille du cube :");
		this.lb_size.setName("lb_size");
		this.lb_size.setBounds(50, 25, 100, 25);
		defaultPan.add(lb_size);
		
		this.txt_size = new JTextField("" + Cube.DEFAULT_CUBE_SIZE);
		this.txt_size.setName("txt_size");
		this.txt_size.setAlignmentX(RIGHT_ALIGNMENT);
		this.txt_size.setBounds(150, 25, 25, 25);
		defaultPan.add(txt_size);
		
		this.lb_isMono = new JLabel("Cube composé de LED : ");
		this.lb_isMono.setName("lb_isMono");
		this.lb_isMono.setBounds(50, 65, 150, 25);
		defaultPan.add(lb_isMono);

		this.ck_isMono = new JCheckBox("Monochrome", false);
		this.ck_isMono.setName("ck_isMono");
		this.ck_isMono.setBounds(100, 100, 100, 25);
		defaultPan.add(ck_isMono);
		
		/*this.ck_isRGB = new JCheckBox("RGB", false);
		this.ck_isRGB.setName("ck_isRGB");
		this.ck_isRGB.setBounds(100, 130, 100, 25);
		defaultPan.add(ck_isRGB);*/
		
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
