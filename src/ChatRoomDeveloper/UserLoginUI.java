package ChatRoomDeveloper;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserLoginUI extends JFrame{
	JPanel jpl_login;
	JLabel jlb_account,jlb_password,jlb_age;
	JTextField jtf_account;
	JPasswordField jtf_password;
	JButton btnLogin;
	private JButton btnRegistered;   
	String str_account;
	int status;
	UserDataBase udb;
	//status = 1 註冊
	//status = 0 登入

	public static void main(String[] args){
		
		UserLoginUI u1 = new UserLoginUI();
		
		u1.setVisible(true);
	}
	 public UserLoginUI() {
		udb = new UserDataBase();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jpl_login = new JPanel();
		jpl_login.setBounds(10, 10, 224, 209);
		jpl_login.setVisible(true);
		jpl_login.setLayout(null);
		getContentPane().add(jpl_login);
		
		jlb_account = new JLabel("Account:");
		jlb_account.setBounds(10, 10, 63, 20);
		jpl_login.add(jlb_account);
		
		
		jtf_account = new JTextField();
		jtf_account.setBounds(83, 10, 112, 20);
		jpl_login.add(jtf_account);
		
		
		jlb_password = new JLabel("Password:");
		jlb_password.setBounds(10, 40, 63, 20);
		jpl_login.add(jlb_password);
		
		
		
		
		jtf_password = new JPasswordField();
		jtf_password.setBounds(83, 40, 112, 20);
		jpl_login.add(jtf_password);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(86, 70, 109, 23);
		btnLogin.addActionListener(login);
		jpl_login.add(btnLogin);
		
		btnRegistered = new JButton("Registered");
		btnRegistered.setBounds(86, 103, 109, 23);
		btnRegistered.addActionListener(regist);
		jpl_login.add(btnRegistered);
		
		
		setTitle("Account Information");
		setBounds(100, 100, 260, 267);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
	}
	
	
	ActionListener login = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			str_account = jtf_account.getText().trim();
			String str_password = jtf_password.getText().trim();
			
			// 如果帳號或密碼欄位空白則不處理
		      if (str_account.equals("") | str_password.equals("")) return;
		   // 如果帳號或密碼欄位超過10字則顯示警告並不再處理
		   	  if (str_account.length() >10 | str_password.length() >10) {
		   	    UserDataBase.warnMessage("帳號及密碼最多10字！");
		   	    return;
		   	   
		   	  }
		   	status = 0;
		   	udb.RegistrationDataBaseSearch(str_account,str_password,status);
		   	
		}
	};
	
	
	ActionListener regist = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			UserAddUI useaddui = new UserAddUI();
			useaddui.setVisible(true);
			
			
		   	
		}
		};
		}