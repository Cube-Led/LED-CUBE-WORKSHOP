import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUIMainMenu extends JPanel implements ActionListener{

	private final JFrame motherFrame;
	
	private JTextField txt_title;
	private JButton bt_creaPicture;
	private JButton bt_creaProg;
	private JButton bt_loadProg;
	private JButton bt_exit;
	
	public GUIMainMenu(JFrame motherF)
	{
		this.motherFrame = motherF;
		this.setLayout(new GridLayout(2, 2, 10, 10));
		init();
		this.motherFrame.setContentPane(this);
	}
	
	public void init()
	{
		this.bt_creaPicture = new JButton("Creer une image");
		this.bt_creaPicture.addActionListener(this);
		
		this.bt_creaProg = new JButton("Creer un programme");
		this.bt_creaProg.addActionListener(this);
		
		this.bt_exit = new JButton("Quitter");
		this.bt_exit.addActionListener(this);
		
		this.bt_loadProg = new JButton("Charger un programme");
		this.bt_loadProg.addActionListener(this);
		
		this.add(bt_creaPicture);
		this.add(bt_creaProg);
		this.add(bt_loadProg);
		this.add(bt_exit);
		
		//this.txt_title = new JTextField("Bienvenue");
		//this.add(txt_title);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_exit))
		{
			System.exit(1);
		}
		else if (e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_loadProg))
		{
			System.out.println(this.motherFrame.getSize());
		}
		else if (e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_creaPicture))
		{
			System.out.println("clic");
		}
		else if (e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_creaProg))
		{
			System.out.println("clic");
		}
	}
	
}
