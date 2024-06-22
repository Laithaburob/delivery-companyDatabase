package User_interface;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JTabbedPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import DataBase.*;
import Tables_Screen.Customer_Table;
import net.proteanit.sql.DbUtils;
import javax.swing.JRadioButton;

public class Customer_Screen extends JFrame {

	private JPanel Content;
	private JTextField Phone_Number;
	private JTextField CustomerName;
	private JButton btnNewButton;
	private JTable table;
	private JButton Delete;
	private JTextField ID;
	private JTextField Find_textField;
	private JButton ShowTableButton;
	private JRadioButton RadioButtonFindbyname;
	private JRadioButton RadioButtonFindbyID;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer_Screen frame = new Customer_Screen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Customer_Screen() throws Exception {
		setTitle("Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 365);
		Content = new JPanel();
		Content.setBorder(new EmptyBorder(5, 5, 5, 5));
		Content.setLayout(null);
		setContentPane(Content);
		
		Phone_Number = new JTextField();
		Phone_Number.setBorder(new LineBorder(Color.BLACK));
		Phone_Number.setBounds(20, 127, 172, 30);
		Content.add(Phone_Number);
		Phone_Number.setColumns(10);
		
		JLabel Phone_Number_Label = new JLabel("Phone Number");
		Phone_Number_Label.setBorder(new LineBorder(Color.BLACK));
		Phone_Number_Label.setBounds(20, 113, 94, 14);
		Content.add(Phone_Number_Label);
		
		JButton ADD_Customer_Data = new JButton("ADD");
		ADD_Customer_Data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					insert_Customer(ID.getText(),CustomerName.getText(), Phone_Number.getText());
					Load_Table("");

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		ADD_Customer_Data.setBounds(10, 234, 94, 30);
		Content.add(ADD_Customer_Data);
		
		CustomerName = new JTextField();
		CustomerName.setColumns(10);
		CustomerName.setBorder(new LineBorder(Color.BLACK));
		CustomerName.setBounds(20, 72, 172, 30);
		Content.add(CustomerName);
		
		JLabel Customer_Name_Label = new JLabel("Customer Name");
		Customer_Name_Label.setBorder(new LineBorder(Color.BLACK));
		Customer_Name_Label.setBounds(20, 58, 94, 14);
		Content.add(Customer_Name_Label);
		
		btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		btnNewButton.setBounds(10, 268, 89, 23);
		Content.add(btnNewButton);
		
		JLabel Customer_ID_Label_1 = new JLabel("Customer ID");
		Customer_ID_Label_1.setBorder(new LineBorder(Color.BLACK));
		Customer_ID_Label_1.setBounds(20, 11, 94, 14);
		Content.add(Customer_ID_Label_1);
		
		JButton Update_Customer_Data_1 = new JButton("Update");
		Update_Customer_Data_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Update_Customer(ID.getText(), CustomerName.getText(), Phone_Number.getText());
					Load_Table("");
				} catch (Exception e1) {
					
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Update_Customer_Data_1.setBounds(109, 268, 94, 23);
		Content.add(Update_Customer_Data_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
				ID.setText(table.getValueAt(selectedindex, 0).toString());
				CustomerName.setText(table.getValueAt(selectedindex, 1).toString());
				Phone_Number.setText(table.getValueAt(selectedindex, 2).toString());
			}
		});
		table.setBounds(247, 24, 278, 252);
		Content.add(table);
		
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Customer(ID.getText());
					Load_Table("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		Delete.setBounds(108, 234, 94, 30);
		Content.add(Delete);
		
		ID = new JTextField();
		ID.setBounds(20, 24, 172, 23);
		ID.setBorder(new LineBorder(Color.BLACK));
		Content.add(ID);
		
		JScrollPane scrollBar = new JScrollPane(table);
		scrollBar.setBorder(new LineBorder(Color.BLACK));
		scrollBar.setBounds(246, 24, 421, 225);
		Content.add(scrollBar);
		
		Find_textField = new JTextField();
		Find_textField.setBorder(new LineBorder(Color.BLACK));
		Find_textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (!Find_textField.getText().isBlank()) {
						if (RadioButtonFindbyID.isSelected()) {
							Find_Customer(Find_textField.getText());
						}else if (RadioButtonFindbyname.isSelected()) {
							Load_Table("where Customer_Name Like \""+Find_textField.getText()+"%\" ");
						}
						return;
					}
					Load_Table("");
					
				} catch (Exception e1) {
					
					System.out.println(e1);
				}
			}
		});
		Find_textField.setBounds(387, 263, 196, 33);
		Content.add(Find_textField);
		Find_textField.setColumns(10);
		
		JLabel FindLabel = new JLabel("Find by ID");
		FindLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		FindLabel.setBounds(298, 264, 89, 31);
		FindLabel.setBorder(new LineBorder(Color.BLACK));
		Content.add(FindLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setBounds(37, 191, 136, 32);
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(5);
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
				 else if(false)
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
		Content.add(comboBox);
		
		ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(593, 268, 89, 23);
		Content.add(ShowTableButton);
		
		RadioButtonFindbyID = new JRadioButton("Find by id");
		RadioButtonFindbyID.setSelected(true);
		RadioButtonFindbyID.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		RadioButtonFindbyID.setBounds(338, 296, 109, 23);
		Content.add(RadioButtonFindbyID);
		
		RadioButtonFindbyname = new JRadioButton("Find by name");
		RadioButtonFindbyname.setBorder(new LineBorder(Color.BLACK));
		RadioButtonFindbyname.setBounds(455, 296, 109, 23);
		Content.add(RadioButtonFindbyname);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(RadioButtonFindbyID);
		buttonGroup.add(RadioButtonFindbyname);
		
		Load_Table("");
		
	}
	public void Load_Table(String op) throws Exception {
		table.setModel(DbUtils.resultSetToTableModel(Desplay_Customer(op)));
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
		}
	}
	/*MySQL Code for Customer Table*/
	public static void insert_Customer(String CustomerID, String CustomerName,String Phone_Number) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("insert into Customer(Customer_ID,Customer_Name,Phone_Number)values(?,?,?);");
		ps.setString(1, CustomerID);
		ps.setString(2, CustomerName);
		ps.setString(3, Phone_Number);
		
		int x = ps.executeUpdate();
		if (x>0) {
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private void Update_Customer(String Customer_ID,String CustomerName,String Phone_Number) throws Exception {
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("update Customer "
				+ "set Customer_Name = ? , Phone_Number = ?  "
				+ "where Customer_ID = ? ;");
	
		ps.setString(1, CustomerName);
		ps.setString(2, Phone_Number);
		ps.setString(3, Customer_ID);
		int x = ps.executeUpdate();
		if (x>0) {
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private void Delete_Customer(String Customer_ID) throws Exception {
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(" delete  from  Customer  where  Customer_ID = ? ;");
	
		ps.setString(1, Customer_ID);

		int x = ps.executeUpdate();
		if (x>0) {
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private  ResultSet Desplay_Customer(String op) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from  customer "+ op +" ;");
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private void Find_Customer(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select Customer_ID,Customer_Name,Phone_Number "
				+ "from Customer "
				+ "where Customer_id = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			ID.setText(rs.getString(1));
			CustomerName.setText(rs.getString(2));
			Phone_Number.setText(rs.getString(3));
		}		
		
	}
}
