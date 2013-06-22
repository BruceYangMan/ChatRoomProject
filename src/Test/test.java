import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.List;
import javax.swing.border.*;

public class test extends JFrame implements ItemListener,ActionListener,KeyListener,MouseListener
{   private static HashSet<ChatUserThread> userThreadList = new HashSet<ChatUserThread>();
    private Container container;
    private JButton	s_create_butn,s_inter_butn,c_conn_butn,c_inter_butn,
    				enter_butn,call_butn,end_butn;
    private JTextField name_tf,ip_tf;
    private JRadioButton s_radio,c_radio;
    private ButtonGroup btgroup;
    private JLabel lip,lname,picture;
    
    private DataInputStream instream;
    private DataOutputStream outstream;
    private static List list;
    private String name,address,user,adrs;
    private static JTextArea out,in,warning;
    private Thread thread;
    private ServerSocket audioserver = null;
    private AudioClass audioc = null;
    private Boolean isconnect = false;
    private Boolean key = false;
   public test()
   {  super("請選擇角色：");
      container = getContentPane();
      container.setLayout(new BorderLayout());
      
      //-------------p1--------------
      lname = new JLabel("暱稱：");
      lip =new JLabel("伺服器IP：");
      name_tf = new JTextField(10);
      ip_tf = new JTextField(8);
      picture = new JLabel(new ImageIcon("phone.gif"));
      picture.setPreferredSize(new Dimension(165,60));
      JPanel pic_panel = new JPanel();
      pic_panel.setLayout(new BorderLayout());
      pic_panel.add(picture,BorderLayout.WEST);
      
      warning = new JTextArea("",5,5);
      warning.setLineWrap(true);
      warning.setEditable(false);
      warning.setBackground(new Color(238,238,238));
      JScrollPane war_area = new JScrollPane(warning);
      war_area.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	  war_area.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      war_area.setBorder(BorderFactory.createTitledBorder(
      		BorderFactory.createMatteBorder(3,3,3,3,Color.LIGHT_GRAY),"電話蟲提醒您~"));
      JPanel war_panel = new JPanel();
      war_panel.setLayout(new BorderLayout());
      war_panel.add(war_area,BorderLayout.CENTER);
      
      //s_create_butn 建立鈕
      s_create_butn = new JButton("確定");
      s_create_butn.addActionListener(this);
      
      //s_inter_butn 中斷鈕
      s_inter_butn = new JButton("中斷");
      
      //c_conn_butn 連接鈕
      c_conn_butn = new JButton("連接");
      c_conn_butn.addActionListener(this);
      
      //c_inter_butn 中斷鈕
      c_inter_butn = new JButton("中斷");
       
      //radiobutton
      btgroup = new ButtonGroup();
      s_radio = new JRadioButton("Server");
      s_radio.addItemListener(this);
      btgroup.add(s_radio);
      
      c_radio = new JRadioButton("Client");
      c_radio.addItemListener(this);
      btgroup.add(c_radio);
 	  
      JPanel ip_panel = new JPanel();
      JPanel name_panel = new JPanel();
      JPanel s_panel = new JPanel();
      JPanel c_panel = new JPanel();
      JPanel sbt_panel = new JPanel();
      JPanel cbt_panel = new JPanel();
      	
      s_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
      s_panel.add(s_radio);
      
      c_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
      c_panel.add(c_radio);
      
      sbt_panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
      sbt_panel.add(s_create_butn);
      sbt_panel.add(Box.createRigidArea(new Dimension(5,0)));
      sbt_panel.add(s_inter_butn);
      
      cbt_panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
      cbt_panel.add(c_conn_butn);
      cbt_panel.add(Box.createRigidArea(new Dimension(5,0)));
      cbt_panel.add(c_inter_butn);
      
      ip_panel.setLayout(new BorderLayout());
      ip_panel.add(Box.createRigidArea(new Dimension(14,0)),BorderLayout.WEST);
      ip_panel.add(lip,BorderLayout.NORTH);
      ip_panel.add(ip_tf,BorderLayout.CENTER);
      ip_panel.add(Box.createRigidArea(new Dimension(7,0)),BorderLayout.EAST);
    
      name_panel.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
      name_panel.add(lname);
      name_panel.add(name_tf);
         
      Box leftbox = Box.createVerticalBox();
      leftbox.add(s_panel);
      leftbox.add(Box.createVerticalStrut(5));
      leftbox.add(sbt_panel);
      leftbox.add(Box.createVerticalStrut(20));
      leftbox.add(c_panel);
      leftbox.add(ip_panel);
      leftbox.add(Box.createVerticalStrut(5));
      leftbox.add(name_panel);
      leftbox.add(Box.createVerticalStrut(5));
      leftbox.add(cbt_panel);
      leftbox.add(Box.createVerticalStrut(15));
      leftbox.add(pic_panel);
      leftbox.add(war_panel);
     
      JPanel p1 = new JPanel();
      p1.setLayout(new BorderLayout());
      p1.add(leftbox,BorderLayout.NORTH);
      
      //---------------p2--------------
      //input & output area
      in = new JTextArea("",2,25);
      in.setLineWrap(true);
      JScrollPane i_area = new JScrollPane(in);
      i_area.setBorder(BorderFactory.createEmptyBorder());
      i_area.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	  i_area.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      in.addKeyListener(this);
      
      out = new JTextArea("", 15, 30);
      out.setLineWrap(true);
      out.setEditable(false);
      JScrollPane o_area = new JScrollPane(out);
      o_area.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	  o_area.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

      enter_butn = new JButton(new ImageIcon("BL7.gif"));
      enter_butn.setBackground(Color.white);
      enter_butn.setBorder(BorderFactory.createEmptyBorder());
      enter_butn.setPreferredSize(new Dimension(58,58));
      enter_butn.addActionListener(this);
      enter_butn.addMouseListener(this);
      
      JPanel iarea_enter_panel = new JPanel();
      iarea_enter_panel.setLayout(new BorderLayout());
      iarea_enter_panel.setBackground(Color.white);
      Border border = iarea_enter_panel.getBorder();
      Border margin = BorderFactory.createMatteBorder(1,1,1,1,Color.GRAY);
      iarea_enter_panel.setBorder(new CompoundBorder(border, margin));
      iarea_enter_panel.add(i_area,BorderLayout.CENTER); 
      iarea_enter_panel.add(enter_butn,BorderLayout.EAST); 
      
      JPanel testpanel =new JPanel();
      testpanel.setLayout(new BorderLayout());
      testpanel.add(o_area,BorderLayout.CENTER);
      testpanel.add(iarea_enter_panel,BorderLayout.SOUTH);
      
      JPanel p2 = new JPanel();
      BoxLayout by;
      by = new BoxLayout(p2, BoxLayout.Y_AXIS);// 設定容器預設的版面配置 垂直
      p2.setLayout(by);
      p2.add(Box.createRigidArea(new Dimension(10,5))); 
      p2.add(testpanel);
      p2.add(Box.createRigidArea(new Dimension(10,5)));  
      
      //-----------p3------------
      list = new List();
      
      call_butn = new JButton(new ImageIcon("GR8.gif"));
      call_butn.setPreferredSize(new Dimension(68,68));
      call_butn.setBorderPainted(false);
      call_butn.setBackground(new Color(238,238,238));
      call_butn.addActionListener(this);
      call_butn.addMouseListener(this);
      
      end_butn = new JButton(new ImageIcon("RE7.gif"));
      end_butn.setPreferredSize(new Dimension(68,68));
      end_butn.setBorderPainted(false);
      end_butn.setBackground(new Color(238,238,238));
      end_butn.addActionListener(this);
      end_butn.addMouseListener(this);
      
      JPanel phonebt_panel = new JPanel();
      phonebt_panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
      phonebt_panel.add(call_butn);
      phonebt_panel.add(Box.createRigidArea(new Dimension(10,0)));
      phonebt_panel.add(end_butn);
      
      JPanel p3 = new JPanel();
      p3.setBorder(BorderFactory.createTitledBorder("選擇通話對象~"));
      p3.setLayout(new BorderLayout());
      p3.setPreferredSize(new Dimension(200,400));
      p3.add(list,BorderLayout.CENTER);
      p3.add(phonebt_panel,BorderLayout.SOUTH);

      container.add(p1,BorderLayout.WEST);
      container.add(p2,BorderLayout.CENTER);
      container.add(p3,BorderLayout.EAST);
      setBounds(300,200,700,400);	// 設定視窗位置與大小
      pack();						// 自動調整視窗大小，至能夠放入所有元件
      setVisible(true);				// 顯示視窗
      
   	  s_create_butn.setEnabled(false);
   	  s_inter_butn.setEnabled(false);
   	  ip_tf.setEnabled(false);
   	  name_tf.setEnabled(false);
   	  c_conn_butn.setEnabled(false);
   	  c_inter_butn.setEnabled(false);
   	  call_butn.setEnabled(false);
   	  end_butn.setEnabled(false);
   	  in.setEnabled(false);
   	  enter_butn.setEnabled(false);
   }//視窗設定end
   
   //==============================================================
   // 實作事件處理方法
   public void itemStateChanged(ItemEvent e){
   	if(e.getSource() == s_radio)
   		{
   		try{InetAddress adrs = InetAddress.getLocalHost();
   		 	setTitle("本機IP："+ adrs.getHostAddress());
   		 	s_create_butn.setEnabled(true);
   		 	s_inter_butn.setEnabled(false);
   		 	ip_tf.setEnabled(false);
   	 		name_tf.setEnabled(false);
   		 	c_conn_butn.setEnabled(false);
   		 	c_inter_butn.setEnabled(false);
   		 	call_butn.setEnabled(false);
   	 		end_butn.setEnabled(false);
   	 		}
   		catch(UnknownHostException evt){}
   		}
   	else if(e.getSource() == c_radio)
   		{
   		setTitle("請輸入暱稱與IP：");
   		 	s_create_butn.setEnabled(false);
   		 	s_inter_butn.setEnabled(false);
   		 	ip_tf.setEnabled(true);
   	 		name_tf.setEnabled(true);
   		 	c_conn_butn.setEnabled(true);
   		 	c_inter_butn.setEnabled(false);
   		 	call_butn.setEnabled(false);
   	 		end_butn.setEnabled(false);
   		}
   } 
   public void actionPerformed(ActionEvent evt)
   {    
    JPanel j = new JPanel();
    ServerThread st;
    Thread m;
   	final ServerSocket server;
   	final Socket socket;
   	if(evt.getSource() == s_create_butn){	//建立Server
      	try{
      		server = new ServerSocket(2000);
      		st = new ServerThread(server);
      		m = new Thread(st);
      		m.start();
      		out.append("Server已建立~~~~~\n");
   		 	resetScroll(out);
      		s_create_butn.setEnabled(false);
      		s_inter_butn.setEnabled(true);
      		c_radio.setEnabled(false);
      		s_inter_butn.addActionListener(new ActionListener(){
      					public void actionPerformed(ActionEvent e){
      						try{
      							server.close();
      							out.append("Server已關閉.....\n");
   		 						resetScroll(out);
    	    					s_create_butn.setEnabled(true);
      							s_inter_butn.setEnabled(false);
      							c_radio.setEnabled(true);
   	  							s_inter_butn.removeActionListener(this);
      						}
      						catch(IOException ev){}
      					}});
      		
      		}
      	catch(IOException e){JOptionPane.showMessageDialog(j,"This connect is in Use");}	
   		}
    else if(evt.getSource() == c_conn_butn){	//連接Server
    	if((name_tf.getText().length()!= 0) && (ip_tf.getText().length()!=0)){
    		try{
      			socket = new Socket(ip_tf.getText(),2000);
      			c_conn_butn.setEnabled(false);
      			c_inter_butn.setEnabled(true);
      			s_radio.setEnabled(false);
   	  			call_butn.setEnabled(true);
   	  			end_butn.setEnabled(false);
   	  			in.setEnabled(true);
   	  			enter_butn.setEnabled(true);
   	  			name = name_tf.getText();
   	  			address = socket.getInetAddress().toString();
      			instream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      			outstream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
      			Thread thread = new Thread(new clientThread(socket,name));
      			thread.start();
      			
      			c_inter_butn.addActionListener(new ActionListener(){
      					public void actionPerformed(ActionEvent e){
      						try{
      							socket.close();
      							name = "";
   	  							address = "";
    	    					c_conn_butn.setEnabled(true);
      							c_inter_butn.setEnabled(false);
      							s_radio.setEnabled(true);
   	  							call_butn.setEnabled(false);
   	  							end_butn.setEnabled(false);
   	  							in.setEnabled(false);
   	  							enter_butn.setEnabled(false);
   	  							c_inter_butn.removeActionListener(this);
      						}
      						catch(IOException ev){}
      					}});
      			
      			}
      		catch ( IOException e ){JOptionPane.showMessageDialog(j,e);}
    		}
    	else if((name_tf.getText().length()== 0) && (ip_tf.getText().length()!=0)){
   			warning.append("忘記輸入暱稱囉!\n");
   			resetScroll(warning);
   			}
   		else if((name_tf.getText().length()!= 0) && (ip_tf.getText().length()==0)){
   			warning.append("忘記輸入ip囉!\n");
   			resetScroll(warning);
   			}
   		else if((name_tf.getText().length()== 0) && (ip_tf.getText().length()==0)){
   			warning.append("忘記輸入暱稱和ip囉!\n");
   			resetScroll(warning);
   			}
    	}
    else if(evt.getSource() == call_butn){	//通話鍵
    	try{
    		int index = list.getSelectedIndex();
    		if(index != -1){
    			if(list.getItem(index).equals(name+address)){
    				warning.append("請選擇自己以外的通話對象!\n");
   		 			resetScroll(warning);
   		 			}
    			else{
    				outstream.writeUTF("OpenAudio" + list.getItem(index));
	       	 		outstream.flush();
	       	 		storedata(list.getItem(index));
	       	 		audioserver = new ServerSocket(30000,1);
	       	 		Thread s = new Thread(new audioServerThread(audioserver));
	       	 		s.start();
	       	 		call_butn.setEnabled(false);
	       	 		end_butn.setEnabled(true);
       	 			}
    			}
    		else{
    			warning.append("請先點選欲通話對象!\n");
   		 		resetScroll(warning);
   		 		}
    		}
    	catch(IOException e){}
    	}
    else if(evt.getSource() == end_butn){	//結束通話鍵
    	try{
    		if(!audioserver.isClosed()){
    			audioserver.close();
    	    	if(audioc != null){
   					audioc.setState(true);
    	    		}
    			}
    		else{
    			audioc.close();
  		 		}
       	 		call_butn.setEnabled(true);
       	 		end_butn.setEnabled(false);
    		}
    	catch(IOException e){}
    	}
    else if(evt.getSource() == enter_butn){	//傳送鍵
    	try{
      		outstream.writeUTF(in.getText());
       	 	outstream.flush();
      			}
      		catch ( IOException ev ){}
      		in.setText("");
    	}
    }
    //鍵盤事件
    public void keyPressed(KeyEvent e){		//壓下Enter
    	if(e.getKeyCode() == 10){	//Enter code = 10
    		try{
	      		outstream.writeUTF(in.getText());
	       	 	outstream.flush();
				enter_butn.setIcon(new ImageIcon("B2.gif"));
				key = true;
      			}
      		catch ( IOException ev ){}
	      		in.setText("");
    		}
    	}
    public void keyReleased(KeyEvent e){	//放開Enter
		enter_butn.setIcon(new ImageIcon("B1.gif"));
		if(e.getKeyCode() == 10 && key){
    		in.setText("");
    		key = false;
    		}
    	}
    public void keyTyped(KeyEvent e){}

	//滑鼠事件
	public void mouseClicked(MouseEvent e){	
	}
	public void mouseEntered(MouseEvent e){		//滑鼠進入
		if(e.getSource() == call_butn)
		call_butn.setIcon(new ImageIcon("G1.gif"));
		else if(e.getSource() == end_butn)
		end_butn.setIcon(new ImageIcon("R1.gif"));
		else if(e.getSource() == enter_butn)
		enter_butn.setIcon(new ImageIcon("B1.gif"));
	}
	public void mouseExited(MouseEvent e){		//滑鼠離開
		if(e.getSource() == call_butn)
		call_butn.setIcon(new ImageIcon("GR8.gif"));
		else if(e.getSource() == end_butn)
		end_butn.setIcon(new ImageIcon("RE7.gif"));
		else if(e.getSource() == enter_butn)
		enter_butn.setIcon(new ImageIcon("BL7.gif"));
	}
	public void mousePressed(MouseEvent e){		//滑鼠按下左鍵
		if(e.getSource() == call_butn)
		call_butn.setIcon(new ImageIcon("G2.gif"));
		else if(e.getSource() == end_butn)
		end_butn.setIcon(new ImageIcon("R2.gif"));
		else if(e.getSource() == enter_butn)
		enter_butn.setIcon(new ImageIcon("B2.gif"));
	}
	public void mouseReleased(MouseEvent e){	//滑鼠放開左鍵
		if(e.getSource() == call_butn)
		call_butn.setIcon(new ImageIcon("G1.gif"));
		else if(e.getSource() == end_butn)
		end_butn.setIcon(new ImageIcon("R1.gif"));
		else if(e.getSource() == enter_butn)
		enter_butn.setIcon(new ImageIcon("B1.gif"));
	}

   // 主程式=============================================
   public static void main(String args[]){  
      	test app = new test();
      	app.addWindowListener(new WindowAdapter()
      	{  
	public void windowClosing(WindowEvent evt)
        {System.exit(0); }
	});
   }

	//==================內部其他執行緒&方法========================
	//Server傾聽執行緒
	class ServerThread implements Runnable{
		private ServerSocket server;
		ThreadGroup utg = new ThreadGroup("usertg");
		public ServerThread (ServerSocket server)
			{
			this.server = server;
			}
	   	public void run(){
	      	try{      		
	      		isconnect = true;		
	      		while(true)
	   				{
	   				Socket client = server.accept(); 
	        		ChatUserThread currentUser = new ChatUserThread(client);
	        		Thread ut = new Thread(utg,currentUser);
	        		ut.start();  // 啟動執行緒
	   				}	
	   	   		}
	   	   	catch(IOException e){
	   			if(utg.activeCount()!=0){
	   			isconnect = false;
	         	list.removeAll();
	   			utg.interrupt();}
	   	  		
	   	   	}
		}		
	}
	//Server端負責與Client連接的執行緒
	class ChatUserThread implements Runnable
	{ 
	   private Socket socket;
	   private DataInputStream instream;
	   private DataOutputStream outstream;
	   private String name,address;
	   public ChatUserThread(Socket socket) throws IOException
	   {  this.socket = socket;
	      // 建立串流物件
	      BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
	      instream = new DataInputStream(bis);
	      BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
	      outstream = new DataOutputStream(bos);
	   }
	   public void run()
	   {  
	      address = socket.getInetAddress().toString();	// 取得IP位址
	      name = "";   // 使用者名稱
	      try
	      {  name = instream.readUTF(); 	//讀取字串, 使用UTF-8加碼
	         userThreadList.add(this);
	         list.add(name+address);		//加入清單
	   		 out.append("<"+name+address+"-- 進入聊天室>" + "\n");
	   		 resetScroll(out);
	         sendMsgs("<"+name+address+"-- 進入聊天室>");
	         String m = listUpdate();
	         sendMsgs(m);
	         while(true)
	         {  
	            String msg = instream.readUTF();
	            if(isconnect){
	            	String invite = null;
	            	if((invite = audioMsg(msg)) != null){
	            		String no = null;
	            		if((no = audioNo(invite)) != null)
	            			audioInvite(false,(name+address),no);
	            		else audioInvite(true,(name+address),invite);
	            		}
	            	else{sendMsgs(name+"：" + msg);}
	            	}
	            else break;
	         }
	         sendMsgs("----------連線中斷----------");
	     	 userThreadList.clear();
	      }
	      catch ( IOException e ) {   	//刪除使用者執行緒物件
	      	 list.remove(name+address);
	      	 userThreadList.remove(this); 
	   		 out.append("<"+name+address+"-- 離開聊天室>" + "\n");
	         resetScroll(out);
	         sendMsgs("<"+name+address+"-- 離開聊天室>");
	      	 String m = listUpdate();
	         sendMsgs(m);
	         try{socket.close();}  		//關閉Socket物件
	         catch(IOException en){}
	        }
	   }
	}
	// 廣播訊息給所有的使用者
	public static void sendMsgs(String message)
	{
	  synchronized(userThreadList)
	  {  // 使用Iterator介面物件來取得HashSet元素
	     Iterator<ChatUserThread> iterator = userThreadList.iterator();
	     // 取出所有的使用者執行緒物件
	     while (iterator.hasNext())
	     {  ChatUserThread currentUser = iterator.next();
	        try
	        {  synchronized(currentUser.outstream)
	           {  // 送出訊息, 使用UTF-8加碼
	              currentUser.outstream.writeUTF(message);
	           }
	           currentUser.outstream.flush();
	        }
	        catch(IOException e){}
	     }
	  }
	}
	//Client端執行緒
	class clientThread implements Runnable{
		private Socket socket;
		private String name;
		public clientThread(Socket socket,String name){
			this.socket = socket;
			this.name = name;
			}
		 public void run()
	      {
	      try  // 送出使用者名稱字串, 使用UTF-8加碼
	      {  outstream.writeUTF(name);
	         outstream.flush();
	         // 接收聊天訊息
	         while (true)
	         {  // 讀取訊息, 使用UTF-8加碼
	            String msg = instream.readUTF();
	            if(msg.equals("No")){
	            	warning.append(user + "拒絕通話!\n");
	            	resetScroll(warning);
	       	 		call_butn.setEnabled(true);
	       	 		end_butn.setEnabled(false);
	            	try{audioserver.close();}
	            	catch(IOException e){}
	            	}
	            else if(openAudio(msg)){
	            	if(!(user.equals(name) && adrs.equals(InetAddress.getLocalHost().getHostAddress())))
	            		audioConfirm(user,adrs);
	            	}
	            else if(!listAction(msg)){
	            	out.append(msg + "\n");
	            	resetScroll(out);
	            }
	         }
	      }
	      catch ( IOException e){  
	      	try{
	      		s_create_butn.setEnabled(false);
				c_conn_butn.setEnabled(true);
				c_inter_butn.setEnabled(false);
				call_butn.setEnabled(false);
				end_butn.setEnabled(false);
				in.setEnabled(false);
				enter_butn.setEnabled(false);
	         	socket.close();
	         	out.append("----------離開聊天室----------\n");
	         	list.removeAll();
	         } catch ( IOException e2 ){}
	      }
	   }
	}
	//判斷字串是否為一般訊息
	private Boolean listAction(String masg){
		String[] str = masg.split("：");
		if(str.length == 1){
			if((str = masg.split(",")).length > 1){
				list.removeAll();
				for(int i = 1; i < str.length; i++){
					list.add(str[i]);
					}
				return true;
				}
			}
		return false;
		}
	//更新清單內容
	private String listUpdate(){
		String str = ",";
		int leng = list.getItemCount();
		for(int i = 0; i < leng; i++){
			str += list.getItem(i)+ ",";
			}
		return str;
		}
	//Client端判斷是否為開啟語音訊息
	private Boolean openAudio(String msg){
			String[] str = msg.split("：");
		if(str.length == 1){
			if((str = msg.split("OpenAudio")).length > 1){
				String[] temp = str[1].split("/");
				user = temp[0];
				adrs = temp[1];
				return true;
				}
			}
		return false;
		}
	//Server端判斷是否為開啟語音訊息
	private String audioMsg(String msg){
		String[] str = msg.split("OpenAudio");
		if(str.length > 1){
				return str[1];
			}
		else return null;
		}
	//Server端判斷語音是否被拒絕
	private String audioNo(String msg){
		String[] str = msg.split("No");
		if(str.length > 1){
				return str[1];
			}
		else return null;
		}
	//Server端儲存語音對象名稱與IP
	private void storedata(String msg){
		String[] str = msg.split("/");
		user = str[0];
		adrs = "/"+str[1];
		}
	//Server端發送語音邀請給指定Client
	private static void audioInvite(Boolean b,String local,String invite){
		String[] str = invite.split("/");
		Iterator<ChatUserThread> iterator = userThreadList.iterator();
	    while (iterator.hasNext()){
	    	ChatUserThread currentUser = iterator.next();
	    	if((currentUser.name.equals(str[0])) && (currentUser.address.equals("/"+str[1]))){
	    		try{
	    			synchronized(currentUser.outstream){
	    				if(b) currentUser.outstream.writeUTF("OpenAudio"+local);
	    				else currentUser.outstream.writeUTF("No");
	    			}
	            	currentUser.outstream.flush();
				   	break;
	            }
	            catch(IOException e){}
	    		}
	         }
		}
	//Client端顯示語音邀請訊息框
	private void audioConfirm(String user,String adrs){
		int optiontype = JOptionPane.YES_NO_OPTION;
		String title = "OpenAudio";
			String s = user + " 邀請你加入語音聊天";
			int result = JOptionPane.showConfirmDialog(null,s,title,optiontype);
				if(result == JOptionPane.YES_OPTION){
	       	 			audioc = new AudioClass(adrs,30000);
	       	 			Thread stop = new Thread(new stopThread());
	       	 			stop.start();
	    	    		warning.append("開始與"+user+"通話~~~\n");
	   		 			resetScroll(warning);
	       	 			call_butn.setEnabled(false);
	       	 			end_butn.setEnabled(true);
						}
				else{
					try{
						outstream.writeUTF("OpenAudioNo"+user+"/"+adrs);
	       	 			outstream.flush();}
					catch(IOException e){}
	       	 		}
		}
	//Client端判斷語音通話是否結束
	class stopThread implements Runnable{
		public void run(){
			while(true){
				if(audioc.isClose()){
		    	    warning.append("與"+user+"通話結束!!!\n");
					resetScroll(warning);
		       	 	call_butn.setEnabled(true);
		       	 	end_butn.setEnabled(false);
		       	 	audioc = null;
		       	 	break;
					}
				}
			}
		}
	//重置捲軸位置
	private void resetScroll(JTextArea area){
		int end;
		area.selectAll();
		end = area.getSelectionEnd();
		area.select(end,end);
		}
	//Client端建立的語音Server執行緒
	class audioServerThread implements Runnable{
		private ServerSocket server;
		public audioServerThread (ServerSocket server)
			{
			this.server = server;
			}
	   	public void run(){
	      	try{     
				Socket client = server.accept(); 
	    		audioc = new AudioClass(client);
	    	    warning.append("開始與"+user+"通話~~~\n");
	   		 	resetScroll(warning);
	    		while(!audioc.isClose()){}
	    	    warning.append("與"+user+"通話結束!!!\n");
				resetScroll(warning);
	       	 	call_butn.setEnabled(true);
	       	 	end_butn.setEnabled(false);
	    		audioc = null;
	    		server.close();
	   	   		}
	   	   	catch(IOException e){}
		}		
	}
}