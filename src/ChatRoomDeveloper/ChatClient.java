package ChatRoomDeveloper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ChatClient {
	private DataInputStream instream;
    private DataOutputStream outstream;
    String outMsg = "";
    
    
	public DataInputStream getInstream() {
		return instream;
	}
	public void setInstream(DataInputStream instream) {
		this.instream = instream;
	}
	public DataOutputStream getOutstream() {
		return outstream;
	}
	public void setOutstream(DataOutputStream outstream) {
		this.outstream = outstream;
	}
//	public static void main(String[] args){
//		ChatClient client=new ChatClient();
//	}
	public String getMessage(){
		
		return outMsg;
		
	}
	public void  setMessage(String in){
		this.outMsg = in;
	}
	ChatClient(){
		//connectServer("127.0.0.1", "Bruce");
	}
	
    
		void connectServer(String s_getip,String name) {
		final Socket socket;
		try {
			socket = new Socket(s_getip,8124);
			instream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
  			outstream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
  			Thread thread = new Thread(new clientThread(socket,name));
  			thread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
		

		
//InClass
	class clientThread implements Runnable{
		private Socket socket;
		private String name;
		public clientThread(Socket socket,String name){
			this.socket = socket;
			this.name = name;
			}
		 @Override
		public void run()
	      {
	      try  // 送出使用者名稱字串, 使用UTF-8加碼
	      {  
	    	  outstream.writeUTF(name);
	         outstream.flush();
	         // 接收聊天訊息
	         while (true)
	         {  // 讀取訊息, 使用UTF-8加碼
	            String msg = instream.readUTF();
	            //setMessage(msg);
	            ChatRoomPanel.getOutArea().append(msg + "\n");
	         }
	      }
	      catch ( IOException e){  
	      	try{
	      		socket.close();
	         	
	         	//list.removeAll();
	         } catch ( IOException e2 ){}
	      }
	   }
	}
}
