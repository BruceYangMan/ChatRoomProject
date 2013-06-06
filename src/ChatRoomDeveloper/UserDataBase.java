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
	      errorMessage("MySQL驅動程式安裝失敗！");
	    }
	    try {
	      connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/members"
	                   +"?user=root&password=lion204270");
	      statement = connection.createStatement();
	      //connectMessage("OK");
	    } catch (SQLException e) { 
	    	e.printStackTrace();
	    	errorMessage("MySQL無法啟動！");
	    } catch (Exception e) { errorMessage("發生其他錯誤！");
	    }
	}
	
	// 根據輸入的帳號密碼查詢資料庫
	public boolean RegistrationDataBaseSearch(String id_get,int int_status){
		try {
	   	    // 根據輸入的帳號密碼查詢資料庫
	   	    String comm_data = "SELECT * FROM personal_data WHERE " + 
	   	  	       "acc_id='" + id_get + "'";
	   	    statement = connection.createStatement(); 
	   	    result = statement.executeQuery(comm_data);
	   	    if (result.next()) {
	   	      // 找到符合條件資料
	   	    	if(int_status==1){
		   	    	connectMessage("帳號重複");
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
	   	    // 根據輸入的帳號密碼查詢資料庫
	   	    String comm_data = "SELECT * FROM personal_data WHERE " + 
	   	  	       "acc_id='" + id_get + "'";
	   	    
	   	    statement = connection.createStatement(); 
	   	    result = statement.executeQuery(comm_data);
	   	    if (result.next()) {
	   	      // 找到符合條件資料
	   	    	if(password_get.equals(result.getString("password").trim())){
	   	    		int islog = 1;
	   	    		statement.execute("UPDATE personal_data SET islog=1 WHERE acc_id =" +
	   	    				"'"+id_get+"'");
	   	    		
	   	    	connectMessage("登入成功");
	   	    	new ClientOneToMany_ClientFrame_bk().setVisible(true);
	   	    	
	   	    	
	   	    	
	   	    	}else{
	   	    		connectMessage("密碼錯誤");
	   	    	}
	   	    	if(int_status==1){
	   	    	connectMessage("帳號重複");
	   	    	return true;
	   	    	}
	   	    }else{
	   	    	connectMessage("無此帳號");
	   	    }
	   	    }catch(Exception e){
	   	    	e.printStackTrace();
	   	    }
		return true;
		
	}
	
	
	
	// 資料庫為插入新資料筆數
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
			connectMessage("帳號已存在");
		}else{
		try {
			statement = connection.createStatement();
			statement.execute(comm_data);
			connectMessage("帳號建置成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connectMessage("資料庫錯誤");
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
		    correctMessage("資料更正成功！");
		    }catch(Exception e){
		    	correctMessage("資料更@@@@！");
		    	e.printStackTrace();
		    }
													
													
		return 0;
		
	}
	
	public DefaultListModel  RegistrationDataBaseLoginUser(){
		String username = null;
		DefaultListModel m= new DefaultListModel<String>();
		try {
	   	    // 根據輸入的帳號密碼查詢資料庫
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
//	   	      // 找到符合條件資料
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
	
	
	// 資料庫連線成功對話面版
      public void connectMessage(String msg) {
      String message = msg;
      JOptionPane.showMessageDialog(null, message, "資料庫連線成功",     JOptionPane.INFORMATION_MESSAGE);
    } 
	    // 資料庫異動成功對話面版
	    public void correctMessage(String msg) {
	      String message = msg;
	      JOptionPane.showMessageDialog(null, message, "資料庫異動",     JOptionPane.INFORMATION_MESSAGE);
	    } 
	    
	    // 資料庫嚴重錯誤對話面版
	    public void errorMessage(String msg) {
	      String message = msg;
	      JOptionPane.showMessageDialog(null, message, "嚴重錯誤", JOptionPane.ERROR_MESSAGE);
	      System.exit(0);
	    }
	    
	    // 資料庫錯誤訊息對話面版
	    public static void warnMessage(String msg) {
	      String message = msg;
	      JOptionPane.showMessageDialog(null, message, "錯誤訊息", JOptionPane.ERROR_MESSAGE);
	      
	    } 
}
