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
		setTitle("�إ߫Ȥ��...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,254,166);
		add(tf_send, "South"); // �N�¤�r�ة�b���骺�U��
        tf_send.addActionListener(new ActionListener() { // �j�w�ƥ�
                    public void actionPerformed(ActionEvent e) {
                        writer.println(tf_send.getText()); // �N�¤�r�ؤ���T�g�J�y
                        ta_info.append("�Ȥ�ݵo�e����T�O�G" + tf_send.getText() + "\n"); // �N�¤�r�ؤ���T��ܦb�¤�r�줤
                        tf_send.setText(""); // �N�¤�r�زM��
                    }
                });
		final JScrollPane scrollPane = new JScrollPane();
		add(scrollPane,BorderLayout.CENTER);
		scrollPane.setViewportView(ta_info);
		
	}
	
	  private void connect() { // �s���M���r��k
	        ta_info.append("���ճs��......\n"); // �¤�r�줤��T��T
	        try { // �����ҥ~
	            socket = new Socket("203.68.167.42", 8124); // ��Ҥ�Socket�ﹳ
	            writer = new PrintWriter(socket.getOutputStream(), true);
	            ta_info.append("�����s���C\n"); // �¤�r�줤���ܸ�T
	            InetAddress netAddress = socket.getInetAddress();
	            String netIp = netAddress.getHostAddress();
	            int netPort = socket.getPort();
	            InetAddress localAddress = socket.getLocalAddress();
	            String localIp = localAddress.getHostAddress();
	            int localPort = socket.getLocalPort();
	            ta_info.append("���ݪA�Ⱦ���IP��}"+netIp+"\n");
	            ta_info.append("���ݪA�Ⱦ����q�T����"+netPort+"\n");
	            ta_info.append("�Ȥ᥻����IP��}"+localIp+"\n");
	            ta_info.append("�Ȥ���������q�T�𸹡G" + localPort + "\n");
	        } catch (Exception e) {
	            e.printStackTrace(); // ��X�ҥ~��T
	        }
	    }
	
	
	
	public static void main(String[] args){
		ClientSocketFrame client = new ClientSocketFrame();
		client.setVisible(true);
		client.connect();
	}
	
}
