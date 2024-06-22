package User_interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.swing.ButtonGroup;
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
import Tables_Screen.Employee_Table;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;

public class Employee_Screen extends JFrame{
	public JFrame frame;
	private JTable table;
	private JTextField employeeIDField;
	private JTextField EmployeenameField;
	private JTextField AddressField;
	private JTextField BranchIDField;
	private JTextField dept_ID;
	private JTextField phone_Num;
	private JTextField textField_4;
	private int mode = 0;
	private JTextField password;
	private JRadioButton RadioButtonFindbyID;
	private JRadioButton RadioButtonFindbyname;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee_Screen window = new Employee_Screen();
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
	public  Employee_Screen() {
		setTitle("employee");
		setBounds(100, 100, 789, 546);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		table = new JTable();
		table.setBounds(373, 10, 381, 398);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
				if (mode == 0) {
					employeeIDField.setText(table.getValueAt(selectedindex, 0).toString());
					EmployeenameField.setText(table.getValueAt(selectedindex, 1).toString());
					password.setText(table.getValueAt(selectedindex, 2).toString());
					phone_Num.setText(table.getValueAt(selectedindex, 3).toString());
					AddressField.setText(table.getValueAt(selectedindex, 4).toString());
					dept_ID.setText(table.getValueAt(selectedindex, 5).toString());
					BranchIDField.setText(table.getValueAt(selectedindex, 6).toString());
					
				}else if (mode == 1) {
					BranchIDField.setText(table.getValueAt(selectedindex, 0).toString());
				}
				else if (mode == 2) {
					dept_ID.setText(table.getValueAt(selectedindex, 0).toString());
				}
			}
		});
		getContentPane().add(table);
		
		JLabel lblIdNumber = new JLabel("Employee ID");
		lblIdNumber.setBorder(new LineBorder(Color.BLACK));
		lblIdNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIdNumber.setBounds(10, 11, 94, 19);
		getContentPane().add(lblIdNumber);

		employeeIDField = new JTextField();
		employeeIDField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				mode = 0;
				Load_Table("");
			}
		});
		employeeIDField.setBorder(new LineBorder(Color.BLACK));
		employeeIDField.setColumns(10);
		employeeIDField.setBounds(10, 30, 163, 25);
		getContentPane().add(employeeIDField);

		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setBorder(new LineBorder(Color.BLACK));
		lblEmployeeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmployeeName.setBounds(10, 66, 136, 19);
		getContentPane().add(lblEmployeeName);

		EmployeenameField = new JTextField();
		EmployeenameField.setBorder(new LineBorder(Color.BLACK));
		EmployeenameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				mode = 0;
				Load_Table("");
			}
		});
		EmployeenameField.setColumns(10);
		EmployeenameField.setBounds(10, 85, 163, 25);
		getContentPane().add(EmployeenameField);

		JLabel lblAddress = new JLabel("address");
		lblAddress.setBorder(new LineBorder(Color.BLACK));
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddress.setBounds(10, 121, 94, 16);
		getContentPane().add(lblAddress);

		AddressField = new JTextField();
		AddressField.setBorder(new LineBorder(Color.BLACK));
		AddressField.setColumns(10);
		AddressField.setBounds(10, 137, 163, 25);
		AddressField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				mode = 0;
				Load_Table("");
			}
		});
		getContentPane().add(AddressField);

		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					insert_Employee(employeeIDField.getText(),EmployeenameField.getText(),password.getText(), phone_Num.getText()
							, AddressField.getText(), dept_ID.getText(), BranchIDField.getText());
					Load_Table("");
				} catch (Exception e1) {
					try {
						Delete_Employee(employeeIDField.getText());
						JOptionPane.showMessageDialog(null, e1);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				}
				
			}
		});
		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(20, 409, 101, 40);
		getContentPane().add(addBurron);

		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Update_Employee(employeeIDField.getText(),EmployeenameField.getText(),password.getText() ,phone_Num.getText(),
							AddressField.getText(),dept_ID.getText(), BranchIDField.getText());
					Load_Table("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(131, 456, 101, 40);
		getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Employee(employeeIDField.getText());
					Load_Table("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(131, 409, 101, 40);
		getContentPane().add(btnDelete);

		JButton backBurron = new JButton("back");
		backBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBurron.setBounds(20, 456, 101, 40);
		getContentPane().add(backBurron);

		JLabel lblBranchId = new JLabel("Branch ID");
		lblBranchId.setBorder(new LineBorder(Color.BLACK));
		lblBranchId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBranchId.setBounds(10, 283, 94, 19);
		getContentPane().add(lblBranchId);

		BranchIDField = new JTextField();
		BranchIDField.setBorder(new LineBorder(Color.BLACK));
		BranchIDField.setColumns(10);
		BranchIDField.setBounds(10, 301, 163, 25);
		BranchIDField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try {
					table.setModel(DbUtils.resultSetToTableModel(Branch_Screen.Desplay_Branch()));
					table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
					TableColumnModel tcm = table.getColumnModel();
					for (int i = 0; i < tcm.getColumnCount(); i++) {
						tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
					}
					mode = 1;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(e1);
				}
				
			}
		});
		getContentPane().add(BranchIDField);

		JLabel lblDepartmentId = new JLabel("Department ID");
		lblDepartmentId.setBorder(new LineBorder(Color.BLACK));
		lblDepartmentId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDepartmentId.setBounds(10, 228, 101, 19);
		getContentPane().add(lblDepartmentId);

		dept_ID = new JTextField();
		dept_ID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try {
					table.setModel(DbUtils.resultSetToTableModel(Department_Screen.Desplay_Department()));
					table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
					TableColumnModel tcm = table.getColumnModel();
					for (int i = 0; i < tcm.getColumnCount(); i++) {
						tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
					}
					mode = 2;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		dept_ID.setBorder(new LineBorder(Color.BLACK));
		dept_ID.setColumns(10);
		dept_ID.setBounds(10, 247, 163, 25);
		getContentPane().add(dept_ID);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBorder(new LineBorder(Color.BLACK));
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhoneNumber.setBounds(10, 173, 111, 19);
		getContentPane().add(lblPhoneNumber);

		phone_Num = new JTextField();
		phone_Num.setBorder(new LineBorder(Color.BLACK));
		phone_Num.setColumns(10);
		phone_Num.setBounds(10, 192, 163, 25);
		phone_Num.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				mode = 0;
				Load_Table("");
			}
		});
		getContentPane().add(phone_Num);

		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(new LineBorder(Color.BLACK));
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(3);
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
				 else if(false)
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
				 else if(combo.equalsIgnoreCase("delivery informaton"))
				 {
					 Delivers_information_Screen.Show();
					 dispose();
				 }
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(54, 366, 136, 32);
		getContentPane().add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(Color.BLACK));
		scrollPane.setBounds(373, 10, 381, 398);
		getContentPane().add(scrollPane);
		
		JLabel lblSearchBy = new JLabel("search by Employee ID");
		lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchBy.setBorder(new LineBorder(Color.BLACK));
		lblSearchBy.setBounds(386, 443, 151, 31);
		getContentPane().add(lblSearchBy);
		
		textField_4 = new JTextField();

		textField_4.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table("");
				mode  = 0;
			}
		});
		textField_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (!textField_4.getText().isBlank()) {
						if (RadioButtonFindbyID.isSelected()) {
							Find_Employee(textField_4.getText());
						}else if (RadioButtonFindbyname.isSelected()) {
							Load_Table("where Employee_Name Like \""+textField_4.getText()+"%\" ");
						}
						return;
					}
					Load_Table("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		textField_4.setColumns(10);
		textField_4.setBorder(new LineBorder(Color.BLACK));
		textField_4.setBounds(536, 443, 201, 31);
		getContentPane().add(textField_4);
		
		JLabel PasswordL = new JLabel("password");
		PasswordL.setFont(new Font("Tahoma", Font.PLAIN, 14));
		PasswordL.setBorder(new LineBorder(Color.BLACK));
		PasswordL.setBounds(200, 11, 94, 19);
		getContentPane().add(PasswordL);
		
		password = new JTextField();
		password.setColumns(10);
		password.setBorder(new LineBorder(Color.BLACK));
		password.setBounds(200, 30, 163, 25);
		password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table("");
				mode  = 0;
			}
		});
		getContentPane().add(password);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(521, 409, 89, 23);
		getContentPane().add(ShowTableButton);
		
		RadioButtonFindbyID = new JRadioButton("Find by id");
		RadioButtonFindbyID.setSelected(true);
		RadioButtonFindbyID.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		RadioButtonFindbyID.setBounds(440, 481, 109, 23);
		getContentPane().add(RadioButtonFindbyID);
		
		RadioButtonFindbyname = new JRadioButton("Find by name");
		RadioButtonFindbyname.setBorder(new LineBorder(Color.BLACK));
		RadioButtonFindbyname.setBounds(557, 481, 109, 23);
		getContentPane().add(RadioButtonFindbyname);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(RadioButtonFindbyID);
		buttonGroup.add(RadioButtonFindbyname);
		
		
		
		Load_Table("");
	}
	public void Load_Table(String op) {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Employee(op)));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

/*MySQL Code for Employee Table*/
//ID_Number, Employee_Name,  Phone_Number, Address, Department_ID, Branch_ID
// Employee_Password
	private void insert_Employee(String ID_Number, String Employee_Name, String password, String Phone_Number,
			String Address, String Department_ID, String Branch_ID) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"insert into Employee(ID_Number,Employee_Name, Employee_Password ,Phone_Number, Address,Department_ID,Branch_ID)values(?,?,?,?,?,?,?);");
		ps.setString(1, ID_Number);
		ps.setString(2, Employee_Name);
		ps.setString(3, password);
		ps.setString(4, Phone_Number);
		ps.setString(5, Address);
		ps.setString(6, Department_ID);
		ps.setString(7, Branch_ID);

		int x = ps.executeUpdate();
		if (x > 0) {
			ps = conn.prepareStatement("CREATE USER '"+ID_Number+"'@'localhost' IDENTIFIED BY '"+password+"';");
			x = ps.executeUpdate();
			String UserName = "'"+ID_Number+"'@'localhost'";
			
			ps = conn.prepareStatement("GRANT SELECT  ON company.Delivers_information TO "+UserName+";");
			ps.executeUpdate();
			ps = conn.prepareStatement("GRANT select(ID_Number,Employee_Name) ON company.Employee TO "+UserName+";");
			ps.executeUpdate();
			ps = conn.prepareStatement("GRANT select,update(Date_of_Deliver) ON company.Orders TO "+UserName+";");
			ps.executeUpdate();
			ps = conn.prepareStatement("GRANT select(Customer_ID,Customer_Name) ON company.Customer TO "+UserName+";");
			ps.executeUpdate();
			ps = conn.prepareStatement("GRANT select ON company.Employee2Cars TO "+UserName+" ; ");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private void Update_Employee(String ID_Number,String Employee_Name,String password,String Phone_Number,String Address,String Department_ID,String Branch_ID) throws Exception {
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("update Employee set Employee_Name = ? ,Employee_Password = ? ,Phone_Number = ? , "
				+ "Address = ? , Department_ID = ?,Branch_ID = ?"
				+ "where ID_Number = ? ;");
	
		ps.setString(1, Employee_Name);
		ps.setString(2, password);
		ps.setString(3, Phone_Number);
		ps.setString(4, Address);
		ps.setString(5, Department_ID);
		ps.setString(6, Branch_ID);
		ps.setString(7, ID_Number);
		
		int x = ps.executeUpdate();
		if (x>0) {
			ps = conn.prepareStatement("ALTER USER '"+ID_Number+"'@'localhost' IDENTIFIED BY '"+password+"';");
			x = ps.executeUpdate();
			
			if (x > 0) {
				JOptionPane.showMessageDialog(null, "Successfully");
			} else {
				JOptionPane.showMessageDialog(null, "Failed");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private void Delete_Employee(String ID_Number) throws Exception {
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(" delete  from  Employee  where  ID_Number = ? ;");
	
		ps.setString(1, ID_Number);
		
		int x = ps.executeUpdate();
		if (x>0) {
			ps = conn.prepareStatement(" drop user '"+ID_Number+"'@'localhost';");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	public static ResultSet Desplay_Employee(String op) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from Employee "+op+" ;");
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private void Find_Employee(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select ID_Number, Employee_Name ,Employee_Password ,Phone_Number , "
				+ "Address,Department_ID , Branch_ID "
				+ "from Employee "
				+ "where ID_Number = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			employeeIDField.setText(rs.getString(1));
			EmployeenameField.setText(rs.getString(2));
			password.setText(rs.getString(3));
			AddressField.setText(rs.getString(4));
			phone_Num.setText(rs.getString(5));
			dept_ID.setText(rs.getString(6));
			BranchIDField.setText(rs.getString(7));
		}		
	}
}
