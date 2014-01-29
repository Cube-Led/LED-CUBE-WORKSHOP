import javax.swing.JOptionPane;


public class GuiChoiceAsker implements ChoiceAsker {

	@Override
	public int askInteger(String str) {
		String res =  null;
		while(res == null || res.length() < 1)res=JOptionPane.showInputDialog(str);
		if(res == "" || res == "\0")System.out.println("blob");
		
		return Integer.parseInt(res);
		
	}

}
