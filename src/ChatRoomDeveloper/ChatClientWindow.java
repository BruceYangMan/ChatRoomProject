package ChatRoomDeveloper;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChatClientWindow{

	private JFrame frame;
	private JPanel login_panel;
	private JLabel jl_account;
	private JLabel jl_password;
	private JButton jb_login;
	private JTextField jt_account;
	private JPasswordField jp_password;
	private JLabel jl_registration;
	

	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ChatClientWindow window = new ChatClientWindow();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatClientWindow() {
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1047, 692);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
       
		
		 login_panel = new JPanel(){
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g){
				 ImageIcon icon = new ImageIcon("img/demo/java.jpg");  
	                Image img = icon.getImage();  
	                g.drawImage(img, 0, 0, icon.getIconWidth(),  
	                        icon.getIconHeight(), icon.getImageObserver());  
	                 
	                
			 }
		};
		
		login_panel.setBounds(10, 10, 1010, 635);
		frame.getContentPane().add(login_panel);
		login_panel.setBackground(Color.WHITE);
		login_panel.setLayout(null);
		
		jl_account = new JLabel("Account:");
		jl_account.setBounds(400, 240, 75, 25); 
		setFonts(jl_account);
		login_panel.add(jl_account);
		
		jl_password = new JLabel("Password:");
		jl_password.setBounds(400, 280, 85, 25);
		setFonts(jl_password);
		login_panel.add(jl_password);
		
		jt_account = new JTextField();
		jt_account.setBounds(485, 240, 110, 25);
		login_panel.add(jt_account);
		
		jp_password = new JPasswordField();
		jp_password.setBounds(485, 280, 110, 25);
		login_panel.add(jp_password);
		
		jb_login = new JButton();
		jb_login.setBounds(620, 265, 115, 40);
		jb_login.setIcon(new ImageIcon("img/demo/button-login1.png"));
		jb_login.setOpaque(false);
		jb_login.setBackground(Color.white);
		jb_login.setBorder(BorderFactory.createEmptyBorder());
		jb_login.addActionListener(login);
		login_panel.add(jb_login);
		
		
		
		jl_registration = new JLabel("<html><a href=\"\">registration</a></html>");
		jl_registration.setBounds(620, 220, 115, 40); 
		jl_registration.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setFonts(jl_registration);
		login_panel.add(jl_registration);
		goWebsite(jl_registration);
		
		
		//login_panel.setOpaque(false);
		//*
		//login_panel.setVisible(false);
		
		
		
	
	}
	
	private void addChatRoomFrame(){
		frame.add(new ChatRoomPanel(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g){
			 ImageIcon icon = new ImageIcon("img/demo/java.jpg");  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, icon.getIconWidth(),  
                        icon.getIconHeight(), icon.getImageObserver());  
                 
                
		 }
	});
	}
	private void setFonts(JComponent c){
		c.setFont(new Font("SimSun",Font.BOLD,16));
	}
	
	private void goWebsite(JLabel website) {
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.google.com/"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
    }

	private boolean verification(String acc,String pass){
		if(acc.equals("123456"))
			return true;	
		else
			return false;
				
		
	}
	ActionListener login = new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jb_login){
			
			String str_account;
			String str_password;
			char[] char_password;
			str_account = jt_account.getText().trim();
			char_password=jp_password.getPassword();
			str_password = String.valueOf(char_password);
			System.out.println(str_account+str_password);
			// 如果帳號或密碼欄位空白則不處理
		      if (str_account.equals("") | str_password.equals("")) return;
		      if(verification(str_account,str_password)==true){
		    	  System.out.println("in");
		    	  login_panel.setVisible(false);
		    	  addChatRoomFrame();
		    	  //new ChatClient().connectServer("127.0.0.1", "Bruce");
		    	  
		    	  //20130620加入了帳號判別但未完成，另外每個panel各自listener各自的class 修改connectServer讓程式能夠傳使用者名稱
		    	  //20130622未來要加入網頁登入利用HTML5的技術 JAVA的部分則要新增MYSQL使用者密碼驗證查詢
		      }
		      
			
		}
	}
	};
	
}
