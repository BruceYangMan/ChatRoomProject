package linux_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class ChatServer {
	 private ServerSocket server; // �ŧiServerSocket�ﹳ
	 private Socket socket; // �ŧiSocket�ﹳsocket
	 private Hashtable<String, Socket> map = new Hashtable<String, Socket>();// �Ω��x�s�s����A�Ⱦ����ϥΪ̩M�Ȥ�ݮM���r�ﹳ
	    
	  public void createSocket() {
	        try {
	            server = new ServerSocket(8124);// �إߪA�Ⱦ��M���r�ﹳ
	            while (true) {
	                System.out.println("Waiting for new client connections......\n");
	                socket = server.accept();// ��o�M���r�ﹳ
	                System.out.println("Successful client connection�C" + socket + "\n");
	                new ServerThread(socket).start();// �إߨñҰʽu�{�ﹳ
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  
	  class ServerThread extends Thread {
	        Socket socket;
	        public ServerThread(Socket socket) {
	            this.socket = socket;
	        }
	        public void run() {
	            try {
	                ObjectInputStream ins = new ObjectInputStream(socket
	                        .getInputStream());
	                while (true) {
	                    Vector v = null;
	                    try {
	                        v = (Vector) ins.readObject();
	                    } catch (ClassNotFoundException e) {
	                        e.printStackTrace();
	                    }
	                    if (v != null && v.size() > 0) {
	                        for (int i = 0; i < v.size(); i++) {
	                            String info = (String) v.get(i);// Ū����T
	                            String key = "";
	                            if (info.startsWith("�ϥΪ̡G")) {// �W�[�n�J�ϥΪ̨�Ȥ�ݦC��
	                                key = info.substring(3, info.length());// ��o�ϥΪ̦W�٨ç@����ϥ�
	                                map.put(key, socket);// �W�[��ȹ�
	                                Set<String> set = map.keySet();// ��o���X���Ҧ��䪺Set�˵�
	                                Iterator<String> keyIt = set.iterator();// ��o�Ҧ��䪺���N��
	                                while (keyIt.hasNext()) {
	                                    String receiveKey = keyIt.next();// ��o���ܱ�����T����
	                                    Socket s = map.get(receiveKey);// ��o�P����������M���r�ﹳ
	                                    PrintWriter out = new PrintWriter(s
	                                            .getOutputStream(), true);// �إ߿�X�y�ﹳ
	                                    Iterator<String> keyIt1 = set.iterator();// ��o�Ҧ��䪺���N��
	                                    while (keyIt1.hasNext()) {
	                                        String receiveKey1 = keyIt1.next();// ��o��A�Ω�V�Ȥ�ݼW�[�ϥΪ̦C��
	                                        out.println(receiveKey1);// �o�e��T
	                                        out.flush();// ��s��X�w�İ�
	                                    }
	                                }
	                            } else if (info.startsWith("�h�X�G")) {
	                                key = info.substring(3);// ��o�h�X�ϥΪ̪���
	                                map.remove(key);// �W�[��ȹ�
	                                Set<String> set = map.keySet();// ��o���X���Ҧ��䪺Set�˵�
	                                Iterator<String> keyIt = set.iterator();// ��o�Ҧ��䪺���N��
	                                while (keyIt.hasNext()) {
	                                    String receiveKey = keyIt.next();// ��o���ܱ�����T����
	                                    Socket s = map.get(receiveKey);// ��o�P����������M���r�ﹳ
	                                    PrintWriter out = new PrintWriter(s
	                                            .getOutputStream(), true);// �إ߿�X�y�ﹳ
	                                    out.println("�h�X�G" + key);// �o�e��T
	                                    out.flush();// ��s��X�w�İ�
	                                }
	                            } else {// ��o�������T��
	                                key = info.substring(info.indexOf("�G�o�e���G") + 5,
	                                        info.indexOf("�G����T�O�G"));// ��o�����誺key��,�Y�����誺�ϥΪ̦W��
	                                String sendUser = info.substring(0, info
	                                        .indexOf("�G�o�e���G"));// ��o�o�e�誺key��,�Y�o�e�誺�ϥΪ̦W��
	                                Set<String> set = map.keySet();// ��o���X���Ҧ��䪺Set�˵�
	                                Iterator<String> keyIt = set.iterator();// ��o�Ҧ��䪺���N��
	                                while (keyIt.hasNext()) {
	                                    String receiveKey = keyIt.next();// ��o���ܱ�����T����
	                                    if (key.equals(receiveKey) && !sendUser.equals(receiveKey)) {// �P�����ϥΪ̬ۦP�A�����O�o�e�ϥΪ�
	                                        Socket s = map.get(receiveKey);// ��o�P����������M���r�ﹳ
	                                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);// �إ߿�X�y�ﹳ
	                                        out.println("MSG:" + info);// �o�e��T
	                                        out.flush();// ��s��X�w�İ�
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            } catch (IOException e) {
	                System.out.println(socket + "has exited�C\n");
	            }
	        }
	    }
	  
	  
	  public static void main(String args[]) {
	        ChatServer frame = new ChatServer();
	        frame.createSocket();
	    }
	  
	  
}