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
	 private ServerSocket server; // 宣告ServerSocket對像
	 private Socket socket; // 宣告Socket對像socket
	 private Hashtable<String, Socket> map = new Hashtable<String, Socket>();// 用於儲存連接到服務器的使用者和客戶端套接字對像
	    
	  public void createSocket() {
	        try {
	            server = new ServerSocket(8124);// 建立服務器套接字對像
	            while (true) {
	                System.out.println("Waiting for new client connections......\n");
	                socket = server.accept();// 獲得套接字對像
	                System.out.println("Successful client connection。" + socket + "\n");
	                new ServerThread(socket).start();// 建立並啟動線程對像
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
	                            String info = (String) v.get(i);// 讀取資訊
	                            String key = "";
	                            if (info.startsWith("使用者：")) {// 增加登入使用者到客戶端列表
	                                key = info.substring(3, info.length());// 獲得使用者名稱並作為鍵使用
	                                map.put(key, socket);// 增加鍵值對
	                                Set<String> set = map.keySet();// 獲得集合中所有鍵的Set檢視
	                                Iterator<String> keyIt = set.iterator();// 獲得所有鍵的迭代器
	                                while (keyIt.hasNext()) {
	                                    String receiveKey = keyIt.next();// 獲得表示接收資訊的鍵
	                                    Socket s = map.get(receiveKey);// 獲得與該鍵對應的套接字對像
	                                    PrintWriter out = new PrintWriter(s
	                                            .getOutputStream(), true);// 建立輸出流對像
	                                    Iterator<String> keyIt1 = set.iterator();// 獲得所有鍵的迭代器
	                                    while (keyIt1.hasNext()) {
	                                        String receiveKey1 = keyIt1.next();// 獲得鍵，用於向客戶端增加使用者列表
	                                        out.println(receiveKey1);// 發送資訊
	                                        out.flush();// 更新輸出緩衝區
	                                    }
	                                }
	                            } else if (info.startsWith("退出：")) {
	                                key = info.substring(3);// 獲得退出使用者的鍵
	                                map.remove(key);// 增加鍵值對
	                                Set<String> set = map.keySet();// 獲得集合中所有鍵的Set檢視
	                                Iterator<String> keyIt = set.iterator();// 獲得所有鍵的迭代器
	                                while (keyIt.hasNext()) {
	                                    String receiveKey = keyIt.next();// 獲得表示接收資訊的鍵
	                                    Socket s = map.get(receiveKey);// 獲得與該鍵對應的套接字對像
	                                    PrintWriter out = new PrintWriter(s
	                                            .getOutputStream(), true);// 建立輸出流對像
	                                    out.println("退出：" + key);// 發送資訊
	                                    out.flush();// 更新輸出緩衝區
	                                }
	                            } else {// 轉發接收的訊息
	                                key = info.substring(info.indexOf("：發送給：") + 5,
	                                        info.indexOf("：的資訊是："));// 獲得接收方的key值,即接收方的使用者名稱
	                                String sendUser = info.substring(0, info
	                                        .indexOf("：發送給："));// 獲得發送方的key值,即發送方的使用者名稱
	                                Set<String> set = map.keySet();// 獲得集合中所有鍵的Set檢視
	                                Iterator<String> keyIt = set.iterator();// 獲得所有鍵的迭代器
	                                while (keyIt.hasNext()) {
	                                    String receiveKey = keyIt.next();// 獲得表示接收資訊的鍵
	                                    if (key.equals(receiveKey) && !sendUser.equals(receiveKey)) {// 與接受使用者相同，但不是發送使用者
	                                        Socket s = map.get(receiveKey);// 獲得與該鍵對應的套接字對像
	                                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);// 建立輸出流對像
	                                        out.println("MSG:" + info);// 發送資訊
	                                        out.flush();// 更新輸出緩衝區
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            } catch (IOException e) {
	                System.out.println(socket + "has exited。\n");
	            }
	        }
	    }
	  
	  
	  public static void main(String args[]) {
	        ChatServer frame = new ChatServer();
	        frame.createSocket();
	    }
	  
	  
}
