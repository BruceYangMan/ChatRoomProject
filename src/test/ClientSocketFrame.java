package test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientSocketFrame extends JFrame{

	private Socket socket;
	private PrintWriter writer;
	private  JTextArea ta_info = new JTextArea();
	private JTextField tf_send = new JTextField();
	
	public ClientSocketFrame(){
		setTitle("建立客戶端...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,254,166);
		add(tf_send, "South"); // 將純文字框放在窗體的下部
        tf_send.addActionListener(new ActionListener() { // 綁定事件
                    public void actionPerformed(ActionEvent e) {
                        writer.println(tf_send.getText()); // 將純文字框中資訊寫入流
                        ta_info.append("客戶端發送的資訊是：" + tf_send.getText() + "\n"); // 將純文字框中資訊顯示在純文字域中
                        tf_send.setText(""); // 將純文字框清空
                    }
                });
		final JScrollPane scrollPane = new JScrollPane();
		add(scrollPane,BorderLayout.CENTER);
		scrollPane.setViewportView(ta_info);
		
	}
	
	  private void connect() { // 連接套接字方法
	        ta_info.append("嘗試連接......\n"); // 純文字域中資訊資訊
	        try { // 捕捉例外
	            socket = new Socket("203.68.167.42", 8124); // 實例化Socket對像
	            writer = new PrintWriter(socket.getOutputStream(), true);
	            ta_info.append("完成連接。\n"); // 純文字域中提示資訊
	            InetAddress netAddress = socket.getInetAddress();
	            String netIp = netAddress.getHostAddress();
	            int netPort = socket.getPort();
	            InetAddress localAddress = socket.getLocalAddress();
	            String localIp = localAddress.getHostAddress();
	            int localPort = socket.getLocalPort();
	            ta_info.append("遠端服務器的IP位址"+netIp+"\n");
	            ta_info.append("遠端服務器的通訊附號"+netPort+"\n");
	            ta_info.append("客戶本機的IP位址"+localIp+"\n");
	            ta_info.append("客戶機本機的通訊埠號：" + localPort + "\n");
	        } catch (Exception e) {
	            e.printStackTrace(); // 輸出例外資訊
	        }
	    }
	
	
	
	public static void main(String[] args){
		ClientSocketFrame client = new ClientSocketFrame();
		client.setVisible(true);
		client.connect();
	}
	
}
