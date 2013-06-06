package ChatRoomDeveloper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ClientOneToMany_Server_bk {
	private ServerSocket server; // 宣告ServerSocket對像
    private Socket socket; // 宣告Socket對像socket
    private Vector<Socket> vector = new Vector<Socket>();// 用於儲存連接到服務器的客戶端套接字對像
    
    public void createSocket(){
    	try {
			server = new ServerSocket(8124);
			while(true){
				System.out.println("Waitting Client to Connect");
				socket = server.accept(); 
				vector.add(socket);
				System.out.println("Client is Connect");
				new ServerThread(socket).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
    class ServerThread extends Thread{
    	Socket socket;
    	public ServerThread(Socket socket){
    		this.socket = socket;
    	}
    	public void run(){
    		try{
    			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			while(true){
    				String info = in.readLine();
    				for(Socket s : vector){
    					if(s != socket){
    						PrintWriter out  = new PrintWriter(s.getOutputStream(),true);
    					
    						out.println(info);
    						out.flush();
    					}
    				}
    			}
    		}catch(IOException e){
    			System.out.println("Exit");
    			vector.remove(socket);
    		}
    	}
    }
    
    public static void main(String args[]){
    	ClientOneToMany_Server_bk frame = new ClientOneToMany_Server_bk();
    	frame.createSocket();
    }
    
}


