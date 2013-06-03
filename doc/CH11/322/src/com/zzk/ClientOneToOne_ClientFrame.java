package com.zzk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientOneToOne_ClientFrame extends JFrame {
    private JTextField tf_newUser;
    private JList user_list;
    private JTextArea ta_info;
    private JTextField tf_send;
    PrintWriter out;// �ŧi��X�y�ﹳ
    private boolean loginFlag = false;// ��true�ɪ��ܤw�g�n�J�A��false�ɪ��ܥ��n�J
    
    /**
     * Launch the application
     * 
     * @param args
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientOneToOne_ClientFrame frame = new ClientOneToOne_ClientFrame();
                    frame.setVisible(true);
                    frame.createClientSocket();// �I�s��k�إ߮M���r�ﹳ
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void createClientSocket() {
        try {
            Socket socket = new Socket("localhost", 1978);// �إ߮M���r�ﹳ
            out = new PrintWriter(socket.getOutputStream(), true);// �إ߿�X�y�ﹳ
            new ClientThread(socket).start();// �إߨñҰʽu�{�ﹳ
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    class ClientThread extends Thread {
        Socket socket;
        
        public ClientThread(Socket socket) {
            this.socket = socket;
        }
        
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));// �إ߿�J�y�ﹳ
                DefaultComboBoxModel model = (DefaultComboBoxModel) user_list
                                .getModel();// ��o�C���ت��ҫ�
                while (true) {
                    String info = in.readLine().trim();// Ū����T
                    
                    if (!info.startsWith("MSG:")) {
                        boolean itemFlag = false;// �аO�O�_���C���ؼW�[�C�O���A��true���W�[�A��false�W�[
                        for (int i = 0; i < model.getSize(); i++) {
                            if (info.equals((String) model.getElementAt(i))) {
                                itemFlag = true;
                            }
                        }
                        if (!itemFlag) {
                            model.addElement(info);// �W�[�C�O��
                        } else {
                            itemFlag = false;
                        }
                    } else {
                        ta_info.append(info + "\n");// �b�¤�r�줤��ܸ�T
                        if (info.equals("88")) {
                            break;// �����u�{
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void send() {
        if (!loginFlag) {
            JOptionPane.showMessageDialog(null, "�Х��n�J�C");
            return;
        }
        String sendUserName = tf_newUser.getText().trim();
        String info = tf_send.getText();// ��o��J����T
        if (info.equals("")) {
            return;// �p�G�S��J��T�h�Ǧ^�A�Y���o�e
        }
        String receiveUserName = (String) user_list.getSelectedValue();// ��o������T���ϥΪ�
        String msg = sendUserName + "�G�o�e���G" + receiveUserName + "�G����T�O�G "
                + info;// �w�q�o�e����T
        if (info.equals("88")) {
            System.exit(0);// �p�G�S��J��T�O88�A�h�h�X
        }
        out.println(msg);// �o�e��T
        out.flush();// ��s��X�w�İ�
        tf_send.setText(null);// �M�ů¤�r��
    }
    
    /**
     * Create the frame
     */
    public ClientOneToOne_ClientFrame() {
        super();
        setTitle("�Ȥ�ݤ@��@�q�T�X�X�Ȥ�ݵ{��");
        setBounds(100, 100, 385, 288);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
        
        final JLabel label = new JLabel();
        label.setText("��J��Ѥ��e�G");
        panel.add(label);
        
        tf_send = new JTextField();
        tf_send.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                send();// �I�s��k�o�e��T
            }
        });
        tf_send.setPreferredSize(new Dimension(180, 25));
        panel.add(tf_send);
        
        final JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                send();// �I�s��k�o�e��T
            }
        });
        button.setText("�o  �e");
        panel.add(button);
        
        final JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(100);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        
        final JScrollPane scrollPane = new JScrollPane();
        splitPane.setRightComponent(scrollPane);
        
        ta_info = new JTextArea();
        scrollPane.setViewportView(ta_info);
        
        final JScrollPane scrollPane_1 = new JScrollPane();
        splitPane.setLeftComponent(scrollPane_1);
        
        user_list = new JList();
        user_list.setModel(new DefaultComboBoxModel(new String[] { "" }));
        scrollPane_1.setViewportView(user_list);
        
        final JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.NORTH);
        
        final JLabel label_1 = new JLabel();
        label_1.setText("��J�ϥΪ̦W�ٺ١G");
        panel_1.add(label_1);
        
        tf_newUser = new JTextField();
        tf_newUser.setPreferredSize(new Dimension(180, 25));
        panel_1.add(tf_newUser);
        
        final JButton button_1 = new JButton();
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (loginFlag) {
                    JOptionPane.showMessageDialog(null, "�b�P�@�����u��n�J�@���C");
                    return;
                }
                String userName = tf_newUser.getText().trim();// ��o�n�J�ϥΪ̦W��
                out.println("�ϥΪ̡G" + userName);// �o�e�n�J�ϥΪ̪��W��
                out.flush();// ��s��X�w�İ�
                tf_newUser.setEnabled(false);
                loginFlag = true;
            }
        });
        button_1.setText("�n  ��");
        panel_1.add(button_1);
    }
}
