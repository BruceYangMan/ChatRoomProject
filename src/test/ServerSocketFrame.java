package test;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerSocketFrame extends JFrame{

	private JTextArea ta_info;
	private BufferedReader reader;
	private ServerSocket server;
	private Socket socket;
	
	//取的伺服器方法
	public void getServer(){
		try{
			server = new ServerSocket(8124);
			server.setSoTimeout(1000);
			ta_info.append("服務器已經成功建立\n");
			while(true){
				ta_info.append("等待客戶連接\n");
				socket = server.accept();
				ta_info.append("連線成功");
				reader = new BufferedReader(new InputStreamReader(socket
                        .getInputStream(),"UTF-8"));
				getClientInfo();
			}
			
		}
		catch(SocketTimeoutException e){
			ta_info.append("連接逾時......");
            JOptionPane.showMessageDialog(null, "連接逾時......");
            
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void getClientInfo(){
		try{
			while(true){
				ta_info.append("接收到客戶訊息:"+reader.readLine()+"\n");
			}
		}catch(Exception e){
			ta_info.append("客戶已退出。\n");
		}finally{
			try{
				if (reader != null){
					reader.close();
				}
				if(socket != null){
					socket.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	//建構子
	public ServerSocketFrame(){
		setTitle("建立服務器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,260,167);
		
		final JScrollPane scrollPane = new JScrollPane();
		add(scrollPane,BorderLayout.CENTER);
		ta_info = new JTextArea();
		scrollPane.setViewportView(ta_info);
	}
	
	public static void main(String[] args){
		ServerSocketFrame frame = new ServerSocketFrame();
		frame.setVisible(true);
		frame.getServer();
	}

	
	
	
}
