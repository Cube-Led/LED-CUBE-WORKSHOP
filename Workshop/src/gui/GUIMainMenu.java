package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


/**
 * Main menu view
 * @author Clement
 *
 */
public class GUIMainMenu extends JPanel implements ActionListener{

	private final GUIDisplay motherFrame;
	
	private JLabel txt_title;
	private JButton bt_creaPicture;
	private JButton bt_creaProg;
	private JButton bt_loadProg;
	private JButton bt_exit;
	
	public GUIMainMenu(GUIDisplay motherF)
	{
		this.motherFrame = motherF;
		init();
		this.motherFrame.setContentPane(this);
		this.updateUI();
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
		
		this.txt_title = new JLabel("Bienvenue");
		this.txt_title.setFont(new Font("Serif",Font.BOLD, 28));
		
		EmptySpace empty1 = new EmptySpace(20), empty2 = new EmptySpace(60);
		
		Box b1 = new Box(BoxLayout.LINE_AXIS);
	    b1.add(txt_title);

	    Box b2 = new Box(BoxLayout.LINE_AXIS);
	    Border bord = BorderFactory.createEmptyBorder(10, 10,10	, 10);
	    b2.setBorder(bord);
	    b2.add(bt_creaPicture);
	    b2.add(empty1);
	    b2.add(bt_creaProg);

	    Box b3 = new Box(BoxLayout.LINE_AXIS);
	    //Idem pour cette ligne
	    b3.add(bt_loadProg);
	    b3.add(empty2);
	    b3.add(bt_exit);

	    //On positionne maintenant ces trois lignes en colonne
	    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	    this.add(b1);
	    this.add(b2);
	    this.add(b3);

	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_exit))
		{
			System.exit(1);
		}
		else if (e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_loadProg))
		{
			
		}
		else if (e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_creaPicture))
		{
			
		}
		else if (e.getSource() instanceof JButton && ((JButton)e.getSource()).equals(this.bt_creaProg))
		{
			this.motherFrame.setContentPane(new CreateProgram(motherFrame));
			//this.motherFrame.getPolling().recordInstructions();
		}
	}
}
