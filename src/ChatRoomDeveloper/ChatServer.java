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
//更新清單內容
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
        		ut.start();  // 啟動執行緒
   				}	
   	   		}
   	   	catch(IOException e){
   			if(utg.activeCount()!=0){
   			isconnect = false;
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
	   		 System.out.println("<"+name+address+"-- 進入聊天室>" + "\n");
	   		 sendMsgs("<"+name+address+"-- 進入聊天室>");
	         String m = listUpdate();
	         sendMsgs(m);
	         while(true)
	         {  
	            String msg = instream.readUTF();
	            if(isconnect){
	            	sendMsgs(name+"：" + msg);
	            	
	            	}
	            else break;
	         }
	         sendMsgs("----------連線中斷----------");
	     	 userThreadList.clear();
	      }
	      catch ( IOException e ) {   	//刪除使用者執行緒物件
	      	 list.remove(name+address);
	      	 userThreadList.remove(this); 
	      	 System.out.println("<"+name+address+"-- 離開聊天室>" + "\n");
	         sendMsgs("<"+name+address+"-- 離開聊天室>");
	      	 String m = listUpdate();
	         sendMsgs(m);
	         try{socket.close();}  		//關閉Socket物件
	         catch(IOException en){}
	        }
	   }
	}	
}

