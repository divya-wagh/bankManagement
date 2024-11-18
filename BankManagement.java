package bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankManagement {
	
	public static void main(String[] args) {
		try {
			System.out.println("Welcome To Bank Mangement System");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Diva19@7@03");
			
			while(true) {
				System.out.println("MENU");
				System.out.println("1.Insert Record\n2.Delete Records\n3.Update records\n4.Display Records\n5.Transactions\n6.Exit");
				System.out.println("Enter your choice: ");
				int choice= Integer.parseInt(br.readLine());
				switch (choice) {
				case 1:
					String query="insert into bank_manage(name, contactno, balance) values(?,?,?)";
					PreparedStatement pstmt=con.prepareStatement(query);
					System.out.println("Enter your name: ");
					String n=br.readLine();
					System.out.println("Enter your contact number: ");
					String c=br.readLine();
					System.out.println("Enter minimum amount: ");
					int b=Integer.parseInt(br.readLine());
					pstmt.setString(1, n);
					pstmt.setString(2, c);
					pstmt.setInt(3, b);
					pstmt.executeUpdate();
					System.out.println("Record Inserted Successfully");
					break;
				case 2:
					try {
						System.out.println("Enter your id to delete record: ");
						int userid=Integer.parseInt(br.readLine());
						String qu="Delete from bank_manage where id=?";
						
						PreparedStatement pst=con.prepareStatement(qu);
						pst.setInt(1, userid);
						
						pst.executeUpdate();
						System.out.println("Record Deleted...");
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						System.out.println("Enter your id:");
						int eid=Integer.parseInt(br.readLine());
						String op = "SELECT * FROM bank_manage WHERE id = ?";
						PreparedStatement p = con.prepareStatement(op);
			            p.setInt(1, eid);
			            ResultSet resultSet = p.executeQuery();
			            
			            if (resultSet.next()) {
			            	System.out.println("Enter new name: ");
							String nam=br.readLine();
							System.out.println("Enter new contact number:");
							String cn=br.readLine();
							String que="Update bank_manage SET name=?, contactno=? where id=?";
							PreparedStatement pstm=con.prepareStatement(que);
							
							pstm.setString(1,nam);
							pstm.setString(2, cn);
							pstm.setInt(3, eid);
							
							pstm.executeUpdate();
							System.out.println("Data updated successfully...");
			            } else {
			                System.out.println("Record does not exist.");
			            }
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 4:
					System.out.println("Displaying data");
					String q="Select * from bank_manage";
					PreparedStatement ps=con.prepareStatement(q);
					ResultSet r=ps.executeQuery();
					System.out.println("Name"+"\t\t"+"Contact No.");
					while(r.next()) {
						String name=r.getString(2);
						String contact=r.getString(3);
						System.out.println(name+"\t\t"+contact);
					}
					
					break;
				case 5:
					try {
						System.out.println("Enter your id:");
						int eid=Integer.parseInt(br.readLine());
						String op = "SELECT * FROM bank_manage WHERE id = ?";
						PreparedStatement pt = con.prepareStatement(op);
			            pt.setInt(1, eid);
			            ResultSet result = pt.executeQuery();
			            if (result.next()) {
			            	a:
			            	while(true) {
			            		System.out.println("MENU");
			            		System.out.println("1.Deposit\n2.Withdraw\n3.Check Balance\n4.Exit");
			            		System.out.println("Enter your choice: ");
			            		int ch=Integer.parseInt(br.readLine());
			            		
			            		switch (ch) {
								case 1:
									System.out.println("Enter amount to deposit:");
									int amount=Integer.parseInt(br.readLine());
									//fetching balance of id
									String fetch="Select balance from bank_manage where id=?";
									PreparedStatement pp=con.prepareStatement(fetch);
									pp.setInt(1, eid);
									ResultSet rs=pp.executeQuery();
									int ba=0;
									if(rs.next()) {
										ba=rs.getInt(1);
									}
									//adding amount 
									int finalamount=amount+ba;
									//update new amount
									String updateamount="Update bank_manage SET balance=?";
									PreparedStatement pStatement=con.prepareStatement(updateamount);
									pStatement.setInt(1, finalamount);
									pStatement.executeUpdate();
									System.out.println("Amount Deposit Successfully...");
									break;
								case 2:
									System.out.println("Enter amount to withdraw:");
									int amo=Integer.parseInt(br.readLine());
									//fetching balance of id
									String fetch1="Select balance from bank_manage where id=?";
									PreparedStatement pp1=con.prepareStatement(fetch1);
									pp1.setInt(1, eid);
									ResultSet rs1=pp1.executeQuery();
									int ba1=0;
									if(rs1.next()) {
										ba1=rs1.getInt(1);
									}
									//subtracting amount
									int finalamount1;
									if(ba1>amo) {
										finalamount1=ba1-amo;
										String updateamount1="Update bank_manage SET balance=?";
										PreparedStatement pStatement1=con.prepareStatement(updateamount1);
										pStatement1.setInt(1, finalamount1);
										pStatement1.executeUpdate();
										System.out.println("Amount withdraw successfully...");
									}else {
										System.out.println("Balance is not suffiecient");
									}
									break;
								case 3:
									//fetching balance of id
									String fetchbalance="Select balance from bank_manage where id=?";
									PreparedStatement pp2=con.prepareStatement(fetchbalance);
									pp2.setInt(1, eid);
									ResultSet rs2=pp2.executeQuery();
									int ba2=0;
									if(rs2.next()) {
										ba2=rs2.getInt(1);
									}
									System.out.println("Balance: "+ba2);
									break;
								case 4:
									break a;
			            	}
			            }
					} 
			        }catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 6:
					System.out.println("Program ends...");
					con.close();
					System.exit(0);
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
