package ChatRoomDeveloper;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientOneToMany_ClientFrame_bk extends JFrame{

	JPanel jpl_chatroom;
	JTextArea jtf_showmessage;
	JTextField jtf_inputmessage;
	JButton jbtn_send;
	PrintWriter output;
	JList<?> user_list;
	JScrollPane scrollPane_user_list;
	Vector<String> v;
	DefaultListModel m;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientOneToMany_ClientFrame_bk frame = new ClientOneToMany_ClientFrame_bk();
                    frame.setVisible(true);
                    frame.createClientSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	/**
	 * Create the application.
	 */
	public ClientOneToMany_ClientFrame_bk() {
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		
		jpl_chatroom = new JPanel();
		jpl_chatroom.setBounds(10, 10, 402, 394);
		jpl_chatroom.setVisible(true);
		jpl_chatroom.setLayout(null);
		getContentPane().add(jpl_chatroom);
		
		final JScrollPane scrollPane_msg = new JScrollPane();
		final JScrollPane scrollPane_chat = new JScrollPane();
		jpl_chatroom.add(scrollPane_msg, BorderLayout.CENTER);
		jpl_chatroom.add(scrollPane_chat, BorderLayout.CENTER);
		
		
		jtf_showmessage = new JTextArea();
		jtf_showmessage.setEnabled(false);
		//jtf_showmessage.setBounds(0, 0, 402, 274);
		scrollPane_msg.setBounds(0, 0, 402, 274);
		scrollPane_msg.setViewportView(jtf_showmessage);
		
        
		jtf_inputmessage = new JTextField();
		//jtf_inputmessage.setBounds(0, 312, 402, 72);
		//jpl_chatroom.add(jtf_inputmessage);
		scrollPane_chat.setBounds(0, 312, 402, 72);
		scrollPane_chat.setViewportView(jtf_inputmessage);
		
		jbtn_send = new JButton("Send");
		jbtn_send.setBounds(452, 327, 76, 64);
		jbtn_send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				send();
				
			}
		});
		getContentPane().add(jbtn_send);
		
//		scrollPane_user_list = new JScrollPane();
//		scrollPane_user_list.
//		getContentPane().add(scrollPane_user_list);
		
		String[] s={"a","b","c"};
		 v = new Vector<String>();
		
		//v.addElement("a1");
		
		user_list = new JList<Object>(v);
		user_list.setBounds(420, 10, 138, 275);
		user_list.setBorder(BorderFactory.createTitledBorder("使用者清單"));
		
		//m = new UserDataBase().RegistrationDataBaseLoginUser();
		//user_list.setModel(UserListUPData());
		UserListUPData();
		//user_list.setModel(new UserDataBase().RegistrationDataBaseLoginUser());
		add(user_list);
        //user_list.setModel(new DefaultComboBoxModel(new String[] { "" }));
		
		setBounds(100, 100, 584, 452);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		
	}
	
	public void createClientSocket(){
		try {
			Socket socket = new Socket("203.68.167.42",8124);
			output = new PrintWriter(socket.getOutputStream(),true);
			new ClientThread(socket).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void send(){
		String send_info = jtf_inputmessage.getText();
		if(send_info.equals("")){
			return;
		}
		
		output.println(send_info);
		output.flush();
		jtf_showmessage.append(send_info+"\n");
		jtf_inputmessage.setText(null);
		
		
	}
	
	public void UserListUPData(){
		Integer cacheTime = 1000 * 1;   
        Timer timer = new Timer();   
        // (TimerTask task, long delay, long period)
        timer.schedule(new TimerTask() {   
  
            @Override  
            public void run() {   
                //System.out.println(new Date());  
                m = new UserDataBase().RegistrationDataBaseLoginUser();
    			user_list.setModel(m);
            }   
        }, 1000, cacheTime);  
		  
		    
		
		
	}
		
	
	
	class ClientThread extends Thread{
		Socket socket;
		String info;
		public ClientThread(Socket socket){
			this.socket = socket;
			
		}
		
		
		
		public void run(){
			try{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				info = in.readLine();
				new UserDataBase().RegistrationDataBaseLoginUser();
				jtf_showmessage.append(info+"\n");
				if(info.equals("88")){
					break;
				}
				
			}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
}
