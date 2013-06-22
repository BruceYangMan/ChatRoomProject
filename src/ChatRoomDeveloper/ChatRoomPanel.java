package ChatRoomDeveloper;

import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatRoomPanel extends JPanel implements KeyListener   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTextArea out,in;
	private static List list;
	private JButton end_butn;
	private ChatClient client;
	private Boolean key = false;
	/**
	 * Create the panel.
	 */
	public ChatRoomPanel() {

		
		setBounds(10, 10, 1010, 635);
		setLayout(null);
		
		
		JPanel enter_panel = new JPanel();
	    enter_panel.setBounds(145, 480, 680, 130);
		enter_panel.setLayout(null);
		add(enter_panel);
		
		JPanel show_panel = new JPanel();
		show_panel.setBounds(145, 90, 680, 370);
		show_panel.setLayout(null);
		add(show_panel);
		
		JPanel list_panel = new JPanel();
		list_panel.setBounds(847, 90, 150, 370);
		list_panel.setLayout(null);
		add(list_panel);
	      
		
		in = new JTextArea("",2,25);
		in.setLineWrap(true);
		JScrollPane i_area = new JScrollPane(in);
		i_area.setBounds(0, 0, 680, 130);
		i_area.setBorder(BorderFactory.createEmptyBorder());
		in.addKeyListener(this);
		enter_panel.add(i_area);
		show_panel.setLayout(null);
		
		setOutArea(new JTextArea("", 15, 30));
		
		 getOutArea().setLineWrap(true);
	     getOutArea().setEditable(false);
	     JScrollPane o_area = new JScrollPane(getOutArea());
	     o_area.setBounds(0, 0, 680, 370);
	     show_panel.add(o_area);
	     
	     list = new List();
	     list.setBounds(0, 0, 150, 370);
	     list_panel.add(list);
	     
	      end_butn = new JButton(new ImageIcon("img/demo/send-icon.png"));
	      end_butn.setBorderPainted(false);
	      end_butn.setOpaque(false);
	      end_butn.setBackground(Color.white);
	      end_butn.setBorder(BorderFactory.createEmptyBorder());
	      end_butn.setBounds(847,480,150,130);
	      end_butn.addActionListener(enter);
	      add(end_butn);
	      
	      
	       client = new ChatClient();
	      client.connectServer("127.0.0.1", "Bruce");
	      
		
		
		
	}
	
	public static JTextArea getOutArea() {
		return out;
	}

	public static void setOutArea(JTextArea out) {
		ChatRoomPanel.out = out;
	}

	ActionListener enter = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				client.getOutstream().writeUTF(in.getText());
				client.getOutstream().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				in.setText("");
			}
			
		}
		 };
		 //鍵盤事件
		    public void keyPressed(KeyEvent e){		//壓下Enter
		    	//System.out.print(e.getKeyCode());
		    	if(e.getKeyCode() == 10){	//Enter code = 10
		    		try{
		    			client.getOutstream().writeUTF(in.getText());
		    			client.getOutstream().flush();
			       	 //end_butn.setIcon(new ImageIcon("B2.gif"));
						key = true;
		      			}
		      		catch ( IOException ev ){}
			      		in.setText("");
		    		}
		    	}
		    public void keyReleased(KeyEvent e){	//放開Enter
		    	//end_butn.setIcon(new ImageIcon("B1.gif"));
				if(e.getKeyCode() == 10 && key){
		    		in.setText("");
		    		key = false;
		    		}
		    	}
		    public void keyTyped(KeyEvent e){}
	
	
	

}
