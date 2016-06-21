package com.org.action;

import java.sql.Connection;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.org.util.DBconnect;

public class GetTableAction<E> extends ActionSupport {

	List<E> contacts;
	private static Connection  con= null;
	private PreparedStatement pstCheckExist = null;
	Statement stmt = null;
	public List<E> getContacts() {
		return contacts;
	}

	public void setContacts(List<E> contacts) {
		this.contacts = contacts;
	}
	
	
    
	
	public String advertisersCategory(){
		
		try{

			
			
			 con = DBconnect.getConnectionStatus();
			 
		    System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		    
		    stmt = con.createStatement();
		    
		    if (con != null) {
                DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
		    
			
		String query = "Select A1,A2,A3,A4,A5,A6 from UploadFirstFile ORDER BY NULL;";
			//pstCheckExist = con.prepareStatement(query);
		
			
		      ResultSet rs = stmt.executeQuery(query);
		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		    	 String A1  = rs.getString("A1");
		    	 String A2 = rs.getString("A2");
		         String A3 = rs.getString("A3");
		         String A4 = rs.getString("A4");
		         String A5 = rs.getString("A5");
		         String A6 = rs.getString("A6");



		         //Display values
		         System.out.print("A1: " + A1);
		         System.out.print(", A2: " + A2);
		         System.out.print(", A3:" + A3);
		         System.out.print(", A4: " + A4);
		         System.out.print(", A5: " + A5);
		         System.out.print(", A6: " + A6);
		         
		         System.out.println("");
		         contacts.add((E) A1);
		      }
			
		//session = HibernateUtil.getSessionFactory().openSession();
		//List<AdvertisersCategoryDto> contacts = null;
		/*contacts = (List<E>) pstCheckExist.executeQuery();
		System.out.println("-----------"+contacts.size());
		System.out.println("-----------"+contacts.get(0));
		System.out.println("-----------"+contacts.get(1));
		System.out.println("-----------"+contacts.get(2));*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
