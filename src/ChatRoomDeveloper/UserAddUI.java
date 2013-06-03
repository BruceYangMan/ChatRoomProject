package ChatRoomDeveloper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

class UserAddUI extends JFrame{
	JLabel jlb_account, jlb_password,jlb_name,jlb_sex,jlb_age,jlb_date;
	String str_dateNow;
	JTextField jtf_account,jtf_name;
	JTextField jtf_password;
	ButtonGroup btn_group;
	JRadioButton jrbg1_boy, jrbg1_girl;
	JSpinner jspin_age;
	JButton jbtn_create,jbtn_cancel;
	UserDataBase udb;
	String dateNow;
	java.sql.Date startDate;
	
	public UserAddUI() {
		
	    initialize();
		udb = new UserDataBase();
		
		 
	}
	
	
	
	
	private void initialize() {
		 setTitle("Registered");
		 //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		 Date date = new Date();
//		 str_dateNow = DateFormat.getDateInstance().format(date);
		 Calendar calendar = Calendar.getInstance();
	     startDate = new java.sql.Date(calendar.getTime().getTime());
	     
	     
		 setBounds( 0, 0, 394, 204);
		 getContentPane().setLayout(null);
		 
		 jbtn_create = new JButton("Create");
		 jbtn_create.setBounds(280, 105, 88, 25);
		 jbtn_create.addActionListener(create);
		 getContentPane().add(jbtn_create);
		 
		 jbtn_cancel = new JButton("Cancel");
		 jbtn_cancel.setBounds(280, 140, 88, 25);
		 getContentPane().add(jbtn_cancel);
		 
		 jlb_account = new JLabel("Account:");
		 jlb_account.setBounds(10, 10, 50, 25);
		 getContentPane().add(jlb_account);
		 
		 jtf_account = new JTextField();
		 jtf_account.setBounds(86, 10, 95, 25);
		 getContentPane().add(jtf_account);
		 
		 jlb_password = new JLabel("Password:");
		 jlb_password.setBounds(10, 40, 66, 25);
		 getContentPane().add(jlb_password);
		 
		 jtf_password = new JTextField();
		 jtf_password.setBounds(86, 40, 95, 25);
		 getContentPane().add(jtf_password);
		 
		 jlb_name = new JLabel("Name:");
		 jlb_name.setBounds(10, 70, 80, 25);
		 getContentPane().add(jlb_name);
		 
		 jtf_name = new JTextField();
		 jtf_name.setBounds(86, 70, 95, 25);
		 getContentPane().add(jtf_name);
		 
		 jlb_age = new JLabel("Age:");
		 jlb_age.setBounds(10, 105, 80, 25);
		 getContentPane().add(jlb_age);
		 
		 jspin_age = new JSpinner(new SpinnerNumberModel(20, 1, 100, 1));
		 jspin_age.setBounds(86, 105, 95, 22);
		 getContentPane().add(jspin_age);
		 
		 jlb_sex = new JLabel("Sex:");
		 jlb_sex.setBounds(10, 140, 50, 25);
		 getContentPane().add(jlb_sex);
		 
		 btn_group = new ButtonGroup();
		 jrbg1_boy = new JRadioButton("Boy", false); jrbg1_boy.setBounds( 71, 142, 60, 20);
		 jrbg1_girl = new JRadioButton("Girl", false); jrbg1_girl.setBounds(133, 142, 60, 20);
		 btn_group.add(jrbg1_boy); btn_group.add(jrbg1_girl);
		 getContentPane().add(jrbg1_boy); 
		 getContentPane().add(jrbg1_girl);
		 
		 jlb_date = new JLabel();
		 jlb_date.setBounds(252, 10, 116, 25);
		 jlb_date.setText("系統時間:"+startDate);
		 getContentPane().add(jlb_date);
		 
		 
		 
		
	}
	
	
	ActionListener create = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			String str_account = jtf_account.getText().trim();
			String str_password = jtf_password.getText().trim();
			String str_name = jtf_name.getText().trim();
			int int_age  = (int) jspin_age.getValue();
			int msex = 0;
			if(jrbg1_boy.isSelected())
				msex = 1;
			else
				msex = 0;
			
			udb.RegistrationDataBaseInsert(str_account,str_password,"20130501",str_name,int_age,msex);
			
			
			
			
		   	
		}
	};
	
}