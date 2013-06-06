package ChatRoomDeveloper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UserDataBase extends JFrame{

	Connection connection;
	Statement statement;
	ResultSet result;
	UserDataBase(){
	 try {
	      Class.forName("com.mysql.jdbc.Driver");
	      
	    } catch (Exception e) {
	      errorMessage("MySQL�X�ʵ{���w�˥��ѡI");
	    }
	    try {
	      connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/members"
	                   +"?user=root&password=lion204270");
	      statement = connection.createStatement();
	      //connectMessage("OK");
	    } catch (SQLException e) { 
	    	e.printStackTrace();
	    	errorMessage("MySQL�L�k�ҰʡI");
	    } catch (Exception e) { errorMessage("�o�ͨ�L���~�I");
	    }
	}
	
	// �ھڿ�J���b���K�X�d�߸�Ʈw
	public boolean RegistrationDataBaseSearch(String id_get,int int_status){
		try {
	   	    // �ھڿ�J���b���K�X�d�߸�Ʈw
	   	    String comm_data = "SELECT * FROM personal_data WHERE " + 
	   	  	       "acc_id='" + id_get + "'";
	   	    statement = connection.createStatement(); 
	   	    result = statement.executeQuery(comm_data);
	   	    if (result.next()) {
	   	      // ���ŦX������
	   	    	if(int_status==1){
		   	    	connectMessage("�b������");
		   	    	return true;
		   	    	}
	   	    	
	   	    }
	   	    }catch(Exception e){
	   	    	e.printStackTrace();
	   	    }
		return false;
		
	}
	public boolean RegistrationDataBaseSearch(String id_get,String password_get,int int_status){
		
		try {
	   	    // �ھڿ�J���b���K�X�d�߸�Ʈw
	   	    String comm_data = "SELECT * FROM personal_data WHERE " + 
	   	  	       "acc_id='" + id_get + "'";
	   	    
	   	    statement = connection.createStatement(); 
	   	    result = statement.executeQuery(comm_data);
	   	    if (result.next()) {
	   	      // ���ŦX������
	   	    	if(password_get.equals(result.getString("password").trim())){
	   	    		int islog = 1;
	   	    		statement.execute("UPDATE personal_data SET islog=1 WHERE acc_id =" +
	   	    				"'"+id_get+"'");
	   	    		
	   	    	connectMessage("�n�J���\");
	   	    	new ClientOneToMany_ClientFrame_bk().setVisible(true);
	   	    	
	   	    	
	   	    	
	   	    	}else{
	   	    		connectMessage("�K�X���~");
	   	    	}
	   	    	if(int_status==1){
	   	    	connectMessage("�b������");
	   	    	return true;
	   	    	}
	   	    }else{
	   	    	connectMessage("�L���b��");
	   	    }
	   	    }catch(Exception e){
	   	    	e.printStackTrace();
	   	    }
		return true;
		
	}
	
	
	
	// ��Ʈw�����J�s��Ƶ���
	public int RegistrationDataBaseInsert(String id_get,String passowrd_get,String date_join,String name,int sex,int age){
		String comm_data = "INSERT personal_data (acc_id,password,date_join,name,age,sex,islog) values ("+
	                                                             "'"+id_get+"',"+
	                                                             "'"+passowrd_get+"',"+
	                                                             ""+date_join+","+
	                                                             "'"+name+"',"+
	                                                             ""+age+","+
	                                                             ""+sex+","+
	                                                             1+
	                                                             ")";
		
		if(RegistrationDataBaseSearch(id_get,1)){
			connectMessage("�b���w�s�b");
		}else{
		try {
			statement = connection.createStatement();
			statement.execute(comm_data);
			connectMessage("�b���ظm���\");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connectMessage("��Ʈw���~");
		} 
		}
	   
		
		return 0;
		
	}
	
	
	public int RegistrationDataBaseUpdate(String id_get,String password,String name,int sex,int age){
		String comm_data = "UPDATE personal_data SET neme='" + name +"',"+
													"sex=" + sex + "," +
													"age=" + age  +"WHERE acc_id='bruce'";
		
		try {	
		    statement = connection.createStatement(); 
		    statement.execute(comm_data);
		    correctMessage("��Ƨ󥿦��\�I");
		    }catch(Exception e){
		    	correctMessage("��Ƨ�@@@@�I");
		    	e.printStackTrace();
		    }
													
													
		return 0;
		
	}
	
	public DefaultListModel  RegistrationDataBaseLoginUser(){
		String username = null;
		DefaultListModel m= new DefaultListModel<String>();
		try {
	   	    // �ھڿ�J���b���K�X�d�߸�Ʈw
	   	    String comm_data = "SELECT * FROM personal_data WHERE " + 
	   	  	       "islog =" + 1 ;
	   	    
	   	    statement = connection.createStatement(); 
	   	    result = statement.executeQuery(comm_data);
	   	   while(result.next()){
	   		username= result.getString("name");
	   		m.addElement(username);
	   	   }
	   	   //m.removeElement(username);
//	   	    if (result.next()) {
//	   	    	
//	   	      // ���ŦX������
//	   	    	username= result.getString("name");
//	   	    	
//	   	    	m.addElement(username);
//	   	    	result.refreshRow();
//	   	    }else{
//	   	    	m.removeElement(username);
//	   	    	//v.remove(result.getString("name"));
//	   	    }
	   	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}
	
	
	// ��Ʈw�s�u���\��ܭ���
      public void connectMessage(String msg) {
      String message = msg;
      JOptionPane.showMessageDialog(null, message, "��Ʈw�s�u���\",     JOptionPane.INFORMATION_MESSAGE);
    } 
	    // ��Ʈw���ʦ��\��ܭ���
	    public void correctMessage(String msg) {
	      String message = msg;
	      JOptionPane.showMessageDialog(null, message, "��Ʈw����",     JOptionPane.INFORMATION_MESSAGE);
	    } 
	    
	    // ��Ʈw�Y�����~��ܭ���
	    public void errorMessage(String msg) {
	      String message = msg;
	      JOptionPane.showMessageDialog(null, message, "�Y�����~", JOptionPane.ERROR_MESSAGE);
	      System.exit(0);
	    }
	    
	    // ��Ʈw���~�T����ܭ���
	    public static void warnMessage(String msg) {
	      String message = msg;
	      JOptionPane.showMessageDialog(null, message, "���~�T��", JOptionPane.ERROR_MESSAGE);
	      
	    } 
}
