package com.zzk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientSocketFrame extends JFrame {
    private PrintWriter writer; // 宣告PrintWriter類別對像
    private BufferedReader reader; // 宣告BufferedReader對像
    private Socket socket; // 宣告Socket對像
    private JTextArea ta_info; // 建立JtextArea對像
    private JTextField tf_send; // 建立JtextField對像
    private void connect() { // 連接套接字方法
        ta_info.append("嘗試連接......\n"); // 純文字域中資訊資訊
        try { // 捕捉例外
            socket = new Socket("localhost", 1978); // 實例化Socket對像
            while (true) {
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket
                        .getInputStream())); // 實例化BufferedReader對像
                ta_info.append("完成連接。\n"); // 純文字域中提示資訊
                getServerInfo();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 輸出例外資訊
        }
    }
    
    public static void main(String[] args) { // 主方法
        ClientSocketFrame clien = new ClientSocketFrame(); // 建立本例對像
        clien.setVisible(true); // 將窗體顯示
        clien.connect(); // 呼叫連接方法
    }
    
    private void getServerInfo() {
        try {
            while (true) {
                if (reader != null) {
                    String line = reader.readLine();// 讀取服務器發送的資訊
                    if (line != null)
                        ta_info.append("接收到服務器發送的資訊：" + line + "\n"); // 顯示服務器端發送的資訊
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();// 關閉流
                }
                if (socket != null) {
                    socket.close(); // 關閉套接字
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Create the frame
     */
    public ClientSocketFrame() {
        super();
        setTitle("客戶端程式");
        setBounds(100, 100, 361, 257);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);

        final JLabel label = new JLabel();
        label.setForeground(new Color(0, 0, 255));
        label.setFont(new Font("", Font.BOLD, 22));
        label.setText("一對一通訊——客戶端程式");
        panel.add(label);

        final JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.SOUTH);

        final JLabel label_1 = new JLabel();
        label_1.setText("客戶端發送的資訊：");
        panel_1.add(label_1);

        tf_send = new JTextField();
        tf_send.setPreferredSize(new Dimension(140, 25));
        panel_1.add(tf_send);

        final JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                writer.println(tf_send.getText()); // 將純文字框中資訊寫入流
                ta_info.append("客戶端發送的資訊是：" + tf_send.getText()
                        + "\n"); // 將純文字框中資訊顯示在純文字域中
                tf_send.setText(""); // 將純文字框清空
            }
        });
        button.setText("發  送");
        panel_1.add(button);

        final JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        ta_info = new JTextArea();
        scrollPane.setViewportView(ta_info);
        //
    }
    
}
