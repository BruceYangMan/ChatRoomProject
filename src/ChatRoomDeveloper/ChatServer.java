//package ChatRoomDeveloper;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class ChatServer {
	private boolean isconnect;
	private static List<String> list;
	private static ChatServer server_run;
	private ServerSocket server;
    private static HashSet<ChatUserThread> userThreadList = new HashSet<ChatUserThread>();
	public static void main(String args[]){
		server_run = new ChatServer();
		server_run.attachShutDownHook();
	}
ChatServer(){
	 list =  new ArrayList<String>();
	createServer();
	
	}
public void attachShutDownHook(){
	  Runtime.getRuntime().addShutdownHook(new Thread() {
	   @Override
	   public void run() {
	    
		   closeServer();
	   }
	  });
}

public void createServer(){
	ServerThread st;
    Thread m;
   	
   	try{
  		server = new ServerSocket(8124);
  		st = new ServerThread(server);
  		m = new Thread(st);
  		m.start();
  		System.out.println("Server Connect!!");
   	}catch(Exception e){
   		
   		e.printStackTrace();
   		
   	}
}
public void closeServer(){
	try {
		server.close();
		System.out.println("Server ShutDown");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("Server ShutDown Error");
		e.printStackTrace();
	}
}
//��s�M�椺�e
	private String listUpdate(){
		String str = ",";
		int leng = list.size();
		for(int i = 0; i < leng; i++){
			str += list.get(i)+ ",";
			}
		return str;
		}

public static void sendMsgs(String message)
{
  synchronized(userThreadList)
  {  // �ϥ�Iterator��������Ө��oHashSet����
     Iterator<ChatUserThread> iterator = userThreadList.iterator();
     // ���X�Ҧ����ϥΪ̰��������
     while (iterator.hasNext())
     {  ChatUserThread currentUser = iterator.next();
        try
        {  synchronized(currentUser.outstream)
           {  // �e�X�T��, �ϥ�UTF-8�[�X
              currentUser.outstream.writeUTF(message);
           }
           currentUser.outstream.flush();
        }
        catch(IOException e){}
     }
  }
}




//InClass
class ServerThread implements Runnable{

	private ServerSocket server;
	ThreadGroup utg = new ThreadGroup("usertg");
	public ServerThread(ServerSocket server){
		this.server = server;
	}
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		try{      		
      		isconnect = true;		
      		while(true)
   				{
   				Socket client = server.accept(); 
        		ChatUserThread currentUser = new ChatUserThread(client);
        		Thread ut = new Thread(utg,currentUser);
        		ut.start();  // �Ұʰ����
   				}	
   	   		}
   	   	catch(IOException e){
   			if(utg.activeCount()!=0){
   			isconnect = false;
         	utg.interrupt();}
   	  		
   	   	}
	}	
 }
//Server�ݭt�d�PClient�s���������
	class ChatUserThread implements Runnable
	{ 
	   private Socket socket;
	   private DataInputStream instream;
	   private DataOutputStream outstream;
	   private String name,address;
	   public ChatUserThread(Socket socket) throws IOException
	   {  this.socket = socket;
	      // �إߦ�y����
	      BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
	      instream = new DataInputStream(bis);
	      BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
	      outstream = new DataOutputStream(bos);
	   }
	   public void run()
	   {  
	      address = socket.getInetAddress().toString();	// ���oIP��}
	      name = "";   // �ϥΪ̦W��
	      try
	      {  name = instream.readUTF(); 	//Ū���r��, �ϥ�UTF-8�[�X
	         userThreadList.add(this);
	         list.add(name+address);		//�[�J�M��
	   		 System.out.println("<"+name+address+"-- �i�J��ѫ�>" + "\n");
	   		 sendMsgs("<"+name+address+"-- �i�J��ѫ�>");
	         String m = listUpdate();
	         sendMsgs(m);
	         while(true)
	         {  
	            String msg = instream.readUTF();
	            if(isconnect){
	            	sendMsgs(name+"�G" + msg);
	            	
	            	}
	            else break;
	         }
	         sendMsgs("----------�s�u���_----------");
	     	 userThreadList.clear();
	      }
	      catch ( IOException e ) {   	//�R���ϥΪ̰��������
	      	 list.remove(name+address);
	      	 userThreadList.remove(this); 
	      	 System.out.println("<"+name+address+"-- ���}��ѫ�>" + "\n");
	         sendMsgs("<"+name+address+"-- ���}��ѫ�>");
	      	 String m = listUpdate();
	         sendMsgs(m);
	         try{socket.close();}  		//����Socket����
	         catch(IOException en){}
	        }
	   }
	}	
}

