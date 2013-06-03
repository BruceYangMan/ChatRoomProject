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
	
	//�������A����k
	public void getServer(){
		try{
			server = new ServerSocket(8124);
			server.setSoTimeout(1000);
			ta_info.append("�A�Ⱦ��w�g���\�إ�\n");
			while(true){
				ta_info.append("���ݫȤ�s��\n");
				socket = server.accept();
				ta_info.append("�s�u���\");
				reader = new BufferedReader(new InputStreamReader(socket
                        .getInputStream(),"UTF-8"));
				getClientInfo();
			}
			
		}
		catch(SocketTimeoutException e){
			ta_info.append("�s���O��......");
            JOptionPane.showMessageDialog(null, "�s���O��......");
            
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void getClientInfo(){
		try{
			while(true){
				ta_info.append("������Ȥ�T��:"+reader.readLine()+"\n");
			}
		}catch(Exception e){
			ta_info.append("�Ȥ�w�h�X�C\n");
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
	
	//�غc�l
	public ServerSocketFrame(){
		setTitle("�إߪA�Ⱦ�");
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
