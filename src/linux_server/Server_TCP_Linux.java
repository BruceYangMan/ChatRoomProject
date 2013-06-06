package linux_server;


import java.io.*;
import java.net.*;


public class Server_TCP_Linux{
private ServerSocket server;
private Socket socket;
private BufferedReader reader;

Server_TCP_Linux(){

}

public void getServer(){
		try{
			server = new ServerSocket(8124);
			//server.setSoTimeout(10000);
			System.out.println("Server Connection...");
			while(true){
				System.out.println("Watting Client Connection..");
				
				socket = server.accept();
				System.out.println("Connection OK...");
				reader = new BufferedReader(new InputStreamReader(socket
                        .getInputStream()));
				getClientInfo();
			}
			
		}catch(SocketTimeoutException e){
		System.out.println("Connection Timeout");
}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
private void getClientInfo() {
    try {
        while (true) { 
            System.out.println("Client information¡G" + reader.readLine() + "\n"); 
        }
    } catch (Exception e) {
    	System.out.println("Client logout\n"); 
    } finally {
        try {
            if (reader != null) {
                reader.close();
            }
            if (socket != null) {
                socket.close(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
	public static void main(String[] args){
		Server_TCP_Linux Ser = new Server_TCP_Linux();
		Ser.getServer();
	}


}
