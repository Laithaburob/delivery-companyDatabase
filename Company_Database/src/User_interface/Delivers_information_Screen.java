package User_interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableColumnModel;

import DataBase.*;
import Tables_Screen.Delivers_information_Table;
import net.proteanit.sql.DbUtils;
import javax.swing.JCheckBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Component;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Delivers_information_Screen extends JFrame{
	
	private JTextField Employee_ID;
	private JTextField order_ID;
	private JTable Delivers_information_table;
	private JLabel Emp_name;
	private JLabel Customer_Name;
	private JLabel Product_Name;
	private JLabel Car_ID;
	private JTextField EMP_ID_Serach;
	private JTextField orderID_Search;
	private JTable Employee_table;
	private JTable Order_table;
	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delivers_information_Screen window = new Delivers_information_Screen();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the 
	 */
	public  Delivers_information_Screen() {

		setTitle("delivery information");
		setBounds(100, 100, 686, 454);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lbEmployee = new JLabel("Employee ID");
		lbEmployee.setBorder(new LineBorder(Color.BLACK));
		lbEmployee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbEmployee.setBounds(10, 20, 101, 19);
		getContentPane().add(lbEmployee);
		
		Employee_ID = new JTextField();
		Employee_ID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Employee(Employee_ID.getText());
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		Employee_ID.setBorder(new LineBorder(Color.BLACK));
		Employee_ID.setColumns(10);
		Employee_ID.setBounds(10, 37, 163, 32);
		getContentPane().add(Employee_ID);
		
		JLabel lblorderID = new JLabel("order ID");
		lblorderID.setBorder(new LineBorder(Color.BLACK));
		lblorderID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblorderID.setBounds(183, 20, 71, 19);
		getContentPane().add(lblorderID);
		
		order_ID = new JTextField();
		order_ID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Orders(order_ID.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		order_ID.setColumns(10);
		order_ID.setBorder(new LineBorder(Color.BLACK));
		order_ID.setBounds(183, 39, 163, 32);
		getContentPane().add(order_ID);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Delivers_information(Employee_ID.getText(),order_ID.getText());
					Load_Table("");
					Desplay_Order();
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(121, 320, 101, 40);
		getContentPane().add(btnDelete);
		
		JButton backBurron = new JButton("back");
		backBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBurron.setBounds(10, 370, 101, 40);
		getContentPane().add(backBurron);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String newEmployee_ID = JOptionPane.showInputDialog("Ender new Employee ID");
					Update_Delivers_information(Employee_ID.getText(), order_ID.getText(), newEmployee_ID);
					Load_Table("");
					Desplay_Order();
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(121, 370, 101, 40);
		getContentPane().add(btnUpdate);
		
		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					insert_Delivers_information(Employee_ID.getText(), order_ID.getText());
					Load_Table("");
					Desplay_Order();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(10, 320, 101, 40);
		getContentPane().add(addBurron);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(9);
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
				String combo=comboBox.getSelectedItem().toString();
				if (combo.equalsIgnoreCase("branch")) {
					Branch_Screen.Show();
					dispose();
				}
				else if(combo.equalsIgnoreCase("department"))
				 {
						Department_Screen.Show();
						dispose();
						
				 }
				 else if(combo.equalsIgnoreCase("storage"))
				 {
					 
						Storages_Screen.Show();
						dispose();
						
				 }
				 else if(combo.equalsIgnoreCase("employee"))
				 {
					 Employee_Screen.Show();
					 dispose();
					 
				 }
				 else if(combo.equalsIgnoreCase("cars"))
				 {
					 Delivery_Cars_Screen.Show();
					 dispose();
				 }
				 else if(combo.equalsIgnoreCase("customer"))
				 {
					 Customer_Screen.Show();
					 dispose();
				 }
				 else if(combo.equalsIgnoreCase("product"))
				 {
					 Product_Screen.Show();
					 dispose();
				 }
				 else if(combo.equalsIgnoreCase("Car Owners"))
				 {
					 Employee2Cars_Screen.Show();
					 dispose();
				 }
				 else if(combo.equalsIgnoreCase("Orders"))
				 {
					 Orders_Screen.Show();
					 dispose();
				 }
				 else if(false)
				 {
					 Delivers_information_Screen.Show();
					 dispose();
				 }
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(46, 277, 147, 32);
		getContentPane().add(comboBox);
		
		JLabel lbEmployeeName = new JLabel("Employee Name");
		lbEmployeeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbEmployeeName.setBorder(new LineBorder(Color.BLACK));
		lbEmployeeName.setBounds(10, 80, 115, 19);
		getContentPane().add(lbEmployeeName);
		
		Emp_name = new JLabel();
		Emp_name.setBorder(new LineBorder(Color.BLACK));
		Emp_name.setBounds(10, 97, 163, 32);
		getContentPane().add(Emp_name);
		
		JLabel lbCustomer_Name = new JLabel("Customer Name");
		lbCustomer_Name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbCustomer_Name.setBorder(new LineBorder(Color.BLACK));
		lbCustomer_Name.setBounds(183, 80, 115, 19);
		getContentPane().add(lbCustomer_Name);
		
		Customer_Name = new JLabel();
		Customer_Name.setBorder(new LineBorder(Color.BLACK));
		Customer_Name.setBounds(183, 97, 163, 32);
		getContentPane().add(Customer_Name);
		
		JLabel lbProduct_Name = new JLabel("Product Name");
		lbProduct_Name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbProduct_Name.setBorder(new LineBorder(Color.BLACK));
		lbProduct_Name.setBounds(183, 140, 115, 19);
		getContentPane().add(lbProduct_Name);
		
		Product_Name = new JLabel();
		Product_Name.setBorder(new LineBorder(Color.BLACK));
		Product_Name.setBounds(183, 157, 163, 32);
		getContentPane().add(Product_Name);
		
		JLabel lbCarID = new JLabel("Car ID");
		lbCarID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbCarID.setBorder(new LineBorder(Color.BLACK));
		lbCarID.setBounds(10, 140, 115, 19);
		getContentPane().add(lbCarID);
		
		Car_ID = new JLabel();
		Car_ID.setBorder(new LineBorder(Color.BLACK));
		Car_ID.setBounds(10, 157, 163, 32);
		getContentPane().add(Car_ID);
		
		JLabel lblSearchBy = new JLabel("search by Employee ID");
		lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchBy.setBorder(new LineBorder(Color.BLACK));
		lblSearchBy.setBounds(368, 320, 170, 31);
		getContentPane().add(lblSearchBy);
		
		EMP_ID_Serach = new JTextField();
		EMP_ID_Serach.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!EMP_ID_Serach.getText().isBlank()) {
					if (orderID_Search.getText().isBlank() && EMP_ID_Serach.getText().isBlank()) {
						Load_Table("");
					} else {
						if (orderID_Search.getText().isBlank()) {
							Load_Table(" AND DI.ID_Employee = "+EMP_ID_Serach.getText());
						} else {
							Load_Table(" AND DI.ID_Employee = " + EMP_ID_Serach.getText()+" AND DI.Orders_ID = "+orderID_Search.getText());
						}
						
					}
				}
				if (orderID_Search.getText().isBlank() && EMP_ID_Serach.getText().isBlank()) {
					Load_Table("");
				}
			}
		});
		EMP_ID_Serach.setColumns(10);
		EMP_ID_Serach.setBorder(new LineBorder(Color.BLACK));
		EMP_ID_Serach.setBounds(538, 320, 124, 31);
		getContentPane().add(EMP_ID_Serach);
		
		JLabel lblSearchByorder = new JLabel("search by Order ID");
		lblSearchByorder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchByorder.setBorder(new LineBorder(Color.BLACK));
		lblSearchByorder.setBounds(368, 358, 170, 31);
		getContentPane().add(lblSearchByorder);
		
		orderID_Search = new JTextField();
		orderID_Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!orderID_Search.getText().isBlank()) {
					if (orderID_Search.getText().isBlank() && EMP_ID_Serach.getText().isBlank()) {
						Load_Table("");
					} else {
						if (EMP_ID_Serach.getText().isBlank()) {
							Load_Table(" AND DI.Orders_ID = "+orderID_Search.getText());
						} else {
							Load_Table(" AND DI.ID_Employee = " + EMP_ID_Serach.getText()+" AND DI.Orders_ID = "+orderID_Search.getText());
						}
						
					}
				}
				if (orderID_Search.getText().isBlank() && EMP_ID_Serach.getText().isBlank()) {
					Load_Table("");
				}
			}
		});
		orderID_Search.setColumns(10);
		orderID_Search.setBorder(new LineBorder(Color.BLACK));
		orderID_Search.setBounds(538, 358, 124, 31);
		getContentPane().add(orderID_Search);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delivers_information_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(474, 284, 89, 23);
		getContentPane().add(ShowTableButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(363, 10, 299, 266);
		tabbedPane.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(tabbedPane);
		
		Delivers_information_table = new JTable();
		Delivers_information_table.setBounds(363, 10, 299, 388);
		Delivers_information_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = Delivers_information_table.getSelectedRow();
				try {
					Find_Delivers_information(Delivers_information_table.getValueAt(selectedindex, 0).toString(),
							Delivers_information_table.getValueAt(selectedindex, 1).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		getContentPane().add(Delivers_information_table);
		Employee_table = new JTable();
		Employee_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Employee_table.setBounds(125, 230, 0, 0);
		Employee_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = Employee_table.getSelectedRow();
				try {
					Find_Employee(Employee_table.getValueAt(selectedindex, 0).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		getContentPane().add(Employee_table);
		
		Order_table = new JTable();
		Order_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Order_table.setBounds(254, 230, 0, 0);
		Order_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = Order_table.getSelectedRow();
				try {
					Find_Orders(Order_table.getValueAt(selectedindex, 0).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		getContentPane().add(Order_table);
		
		JScrollPane Delivers_information_scrollPane = new JScrollPane(Delivers_information_table);
		Delivers_information_scrollPane.setBorder(new LineBorder(Color.BLACK));
		tabbedPane.addTab("Delivers Information", null, Delivers_information_scrollPane,null);

		JScrollPane Employee_scrollPane = new JScrollPane(Employee_table);
		Employee_scrollPane.setBorder(new LineBorder(Color.BLACK));
		tabbedPane.addTab("Employee", null, Employee_scrollPane, null);
		
		JScrollPane Order_scrollPane = new JScrollPane(Order_table);
		Order_scrollPane.setBorder(new LineBorder(Color.BLACK));
		tabbedPane.addTab("Order", null, Order_scrollPane, null);
		
		

		Load_Table("");
		Load_Employee_Table();
		Load_Order_Table();
	}

	public void Load_Table(String op) {
		try {
			Delivers_information_table.setModel(DbUtils.resultSetToTableModel(Desplay_Delivers_information(op)));
			Delivers_information_table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = Delivers_information_table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (Delivers_information_table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void Load_Order_Table() {
		try {
			Order_table.setModel(DbUtils.resultSetToTableModel(Desplay_Order()));
			Order_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel tcm = Order_table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (Order_table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void Load_Employee_Table() {
		try {
			Employee_table.setModel(DbUtils.resultSetToTableModel(Desplay_Employee()));
			Employee_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel tcm = Employee_table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (Employee_table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}


	/* MySQL Code for Delivers_information Table */

	private void insert_Delivers_information(String Employee_ID , String Orders_ID) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"insert into Delivers_information(ID_Employee, Orders_ID)values(?,?);");
		ps.setString(1, Employee_ID);
		ps.setString(2, Orders_ID);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Add Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Add Failed");
		}
	}

	private void Update_Delivers_information(String oldEmployee_ID,String Orders_ID,String newEmployee_ID)
			throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"update Delivers_information set ID_Employee = ? , Orders_ID = ? where ID_Employee = ? AND Orders_ID = ? ;");

		ps.setString(1, newEmployee_ID);
		ps.setString(2, Orders_ID);
		ps.setString(3, oldEmployee_ID);
		ps.setString(4, Orders_ID);
		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Update Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Update Failed");
		}
	}

	private void Delete_Delivers_information(String Employee_ID , String Orders_ID) throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(" delete  from  Delivers_information  where  ID_Employee = ? AND Orders_ID = ? ;");

		ps.setString(1, Employee_ID);
		ps.setString(2, Orders_ID);
		
		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Delete Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Delete Failed");
		}
	}

	private ResultSet Desplay_Delivers_information(String op) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				 "  select DI.*,O.Address,O.Date_of_Order,O.Date_of_Deliver "
				+" from Delivers_information DI join Orders O on DI.Orders_ID = O.Orders_ID "
				+" where O.Date_of_Deliver is null "
				+op
				+" ORDER BY O.Date_of_Order asc;");

		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private ResultSet Desplay_Employee() throws Exception{
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				" select E2C.Employee_ID , E.Employee_Name , DC.* "+
				" from Employee2Cars E2C "+
				" join Employee E on E2C.Employee_ID = E.ID_Number "+
				" join Delivery_Cars DC on E2C.Car_Number = DC.Car_Number "+
				" where E2C.End_Date is null "+
				" ; ");

		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private ResultSet Desplay_Order() throws Exception{
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				" select O.* "+
				" from Orders O "+
				" left join Delivers_information DI on O.Orders_ID = DI.Orders_ID "+
				" where DI.Orders_ID is null AND O.Date_of_Deliver is null"+
				" ; ");

		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private void Find_Delivers_information(String Employee_ID , String Orders_ID) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select ID_Employee, Orders_ID from Delivers_information where ID_Employee = ? AND Orders_ID = ?;");
		ps.setString(1, Employee_ID);
		ps.setString(2, Orders_ID);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			this.Employee_ID.setText(rs.getString(1));
			Find_Employee(this.Employee_ID.getText());
			order_ID.setText(rs.getString(2));
			Find_Orders(order_ID.getText());
		}
	}
	private void Find_Employee(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select E.Employee_Name , DC.Car_Number "
				+ "from Employee2Cars E2C,Employee E , Delivery_Cars DC "
				+ "where E2C.Employee_ID = E.ID_Number AND E2C.Car_Number = DC.Car_Number AND End_Date is null AND ID_Number = ? ;");

		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		Emp_name.setText("");
		Car_ID.setText("");
		if (rs.next() == true) {
			Employee_ID.setText(id);
			Emp_name.setText(rs.getString(1));
			Car_ID.setText(rs.getString(2));
		}		
	}
	
	private void Find_Orders(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"	select C.Customer_Name , P.Product_Name" + 
				"	from Orders O , Customer C , Product P" + 
				"	where O.Customer_ID = C.Customer_ID AND O.Product_ID = P.Product_ID AND O.Date_of_Deliver is null AND O.Orders_ID = ?" + 
				"	;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		Customer_Name.setText("");
		Product_Name.setText("");
		if (rs.next() == true) {
			order_ID.setText(id);
			Customer_Name.setText(rs.getString(1));
			Product_Name.setText(rs.getString(2));
		}		
	}
}
