package User_interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import Tables_Screen.Product_Table;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;

public class Product_Screen extends JFrame{
	private JTable table;
	private JTextField product_ID;
	private JTextField product_name;
	private JTextField textField;
	private JRadioButton RadioButtonFindbyID;
	private JRadioButton RadioButtonFindbyname;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Product_Screen window = new Product_Screen();
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
	public Product_Screen() {
		setTitle("product");
		setBounds(100, 100, 752, 420);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		table = new JTable();
		table.setBounds(381, 21, 336, 263);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int selectedindex = table.getSelectedRow();
				product_ID.setText(table.getValueAt(selectedindex, 0).toString());
				product_name.setText(table.getValueAt(selectedindex, 1).toString());

			}
		});
		getContentPane().add(table);

		JLabel lblProductId = new JLabel("product ID");
		lblProductId.setBorder(new LineBorder(Color.BLACK));
		lblProductId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProductId.setBounds(10, 15, 89, 24);
		getContentPane().add(lblProductId);

		product_ID = new JTextField();
		product_ID.setBorder(new LineBorder(Color.BLACK));
		product_ID.setColumns(10);
		product_ID.setBounds(10, 39, 163, 32);
		getContentPane().add(product_ID);

		JLabel lblProductName = new JLabel("product name");
		lblProductName.setBorder(new LineBorder(Color.BLACK));
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProductName.setBounds(10, 82, 89, 24);
		getContentPane().add(lblProductName);

		product_name = new JTextField();
		product_name.setColumns(10);
		product_name.setBorder(new LineBorder(Color.BLACK));
		product_name.setBounds(10, 105, 163, 32);
		getContentPane().add(product_name);

		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					insert_Product(product_ID.getText(), product_name.getText());
					Load_Table("");
					
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(10, 272, 101, 40);
		getContentPane().add(addBurron);

		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Update_Product(product_ID.getText(), product_name.getText());
					Load_Table("");
					
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(121, 319, 101, 40);
		getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Product(product_ID.getText());
					Load_Table("");
					
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(121, 272, 101, 40);
		getContentPane().add(btnDelete);

		JButton backBurron = new JButton("back");
		backBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBurron.setBounds(10, 319, 101, 40);
		getContentPane().add(backBurron);

		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(6);
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
				 else if(false)
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
		comboBox.setBounds(43, 229, 136, 32);
		getContentPane().add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(Color.BLACK));
		scrollPane.setBounds(381, 21, 336, 263);
		getContentPane().add(scrollPane);
		
		JLabel lblSearchBy = new JLabel("search by Product ID");
		lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchBy.setBorder(new LineBorder(Color.BLACK));
		lblSearchBy.setBounds(381, 319, 136, 31);
		getContentPane().add(lblSearchBy);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (!textField.getText().isBlank()) {
						if (RadioButtonFindbyID.isSelected()) {
							Find_Product(textField.getText());
						}else if (RadioButtonFindbyname.isSelected()) {
							Load_Table("where Product_Name Like \""+textField.getText()+"%\" ");
						}
						return;
					}
					Load_Table("");
				} catch (Exception e1) {
					System.out.println(e1);
				}
				
			}
		});
		textField.setColumns(10);
		textField.setBorder(new LineBorder(Color.BLACK));
		textField.setBounds(516, 319, 201, 31);
		getContentPane().add(textField);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(503, 289, 89, 23);
		getContentPane().add(ShowTableButton);
		
		RadioButtonFindbyID = new JRadioButton("Find by id");
		RadioButtonFindbyID.setSelected(true);
		RadioButtonFindbyID.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		RadioButtonFindbyID.setBounds(412, 351, 109, 23);
		getContentPane().add(RadioButtonFindbyID);
		
		RadioButtonFindbyname = new JRadioButton("Find by name");
		RadioButtonFindbyname.setBorder(new LineBorder(Color.BLACK));
		RadioButtonFindbyname.setBounds(529, 351, 109, 23);
		getContentPane().add(RadioButtonFindbyname);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(RadioButtonFindbyID);
		buttonGroup.add(RadioButtonFindbyname);
		
		
		
		Load_Table("");
	}
	
	public void Load_Table(String op) {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Product(op)));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	/*MySQL Code for Product Table*/
	public static void insert_Product(String Product_ID,String Product_Name) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"insert into Product(Product_ID, Product_Name)values(?,?);");
		ps.setString(1, Product_ID);
		ps.setString(2, Product_Name);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Add Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Add Failed");
		}
	}

	private void Update_Product(String Product_ID,String Product_Name)
			throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"update Product "
				+ "set Product_Name = ? "
				+ "where Product_ID = ? ;");

		ps.setString(1, Product_Name);
		ps.setString(2, Product_ID);
		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Update Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Update Failed");
		}
	}

	private void Delete_Product(String Product_ID) throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(" delete  from  Product  where  Product_ID = ? ;");

		ps.setString(1, Product_ID);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Delete Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Delete Failed");
		}
	}

	private ResultSet Desplay_Product(String op) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from Product "+op+" ;");

		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private void Find_Product(String Product_ID) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select Product_ID,Product_Name from Product where Product_ID = ?;");
		ps.setString(1, Product_ID);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			product_ID.setText(rs.getString(1));
			product_name.setText(rs.getString(2));
			
		}
	}
}
