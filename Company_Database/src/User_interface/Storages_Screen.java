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
import Tables_Screen.Storages_Table;
import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Storages_Screen extends JFrame {

	private JTable table;
	private JTextField storage_ID;
	private JTextField num_product;
	private JTextField address;
	private JTextField bID;
	static JComboBox comboBox = new JComboBox();
	private JTextField textField;
	private int mode = 0;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Storages_Screen window = new Storages_Screen();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the 
	 * 
	 * 
	 */

	public Storages_Screen() {

		setTitle("storage");
		setBounds(100, 100, 751, 425);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		table = new JTable();
		table.setBounds(381, 10, 336, 263);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
				if (mode == 0) {
					storage_ID.setText(table.getValueAt(selectedindex, 0).toString());
					num_product.setText(table.getValueAt(selectedindex, 1).toString());
					address.setText(table.getValueAt(selectedindex, 2).toString());
					bID.setText(table.getValueAt(selectedindex, 3).toString());
				}else if (mode == 1) {
					bID.setText(table.getValueAt(selectedindex, 0).toString());
				}
			}
		});
		getContentPane().add(table);

		JLabel lblStorageId = new JLabel("storage ID");
		lblStorageId.setBorder(new LineBorder(Color.BLACK));
		lblStorageId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStorageId.setBounds(10, 11, 69, 17);
		getContentPane().add(lblStorageId);

		storage_ID = new JTextField();
		storage_ID.setBorder(new LineBorder(Color.BLACK));
		storage_ID.setColumns(10);
		storage_ID.setBounds(10, 28, 163, 32);
		storage_ID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table();
				mode  = 0;
			}
		});
		getContentPane().add(storage_ID);

		JLabel lblQuantityProducts = new JLabel("Quantity products");
		lblQuantityProducts.setBorder(new LineBorder(Color.BLACK));
		lblQuantityProducts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantityProducts.setBounds(10, 66, 117, 19);
		getContentPane().add(lblQuantityProducts);

		num_product = new JTextField();
		num_product.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table();
				mode = 0;
			}
		});
		num_product.setBorder(new LineBorder(Color.BLACK));
		num_product.setColumns(10);
		num_product.setBounds(10, 85, 163, 32);
		getContentPane().add(num_product);

		JLabel lblAddress = new JLabel("address");
		lblAddress.setBorder(new LineBorder(Color.BLACK));
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddress.setBounds(10, 123, 101, 19);
		getContentPane().add(lblAddress);

		address = new JTextField();
		address.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table();
				mode = 0;
			}
		});
		address.setBorder(new LineBorder(Color.BLACK));
		address.setColumns(10);
		address.setBounds(10, 142, 163, 32);
		getContentPane().add(address);

		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					insert_Storages(storage_ID.getText(), num_product.getText(), address.getText(),bID.getText());
					Load_Table();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(10, 289, 101, 40);
		getContentPane().add(addBurron);

		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Update_Storages(storage_ID.getText(), num_product.getText(), address.getText(), bID.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
				Load_Table();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(121, 340, 101, 40);
		getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Storages(storage_ID.getText());
					Load_Table();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(121, 288, 101, 40);
		getContentPane().add(btnDelete);

		JButton backBurron = new JButton("back");
		backBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBurron.setBounds(10, 340, 101, 40);
		getContentPane().add(backBurron);

		JLabel lblBranchId = new JLabel("Branch ID");
		lblBranchId.setBorder(new LineBorder(Color.BLACK));
		lblBranchId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBranchId.setBounds(10, 185, 117, 17);
		getContentPane().add(lblBranchId);

		bID = new JTextField();
		bID.addFocusListener(new FocusAdapter() {
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
		bID.setBorder(new LineBorder(Color.BLACK));
		bID.setColumns(10);
		bID.setBounds(10, 201, 163, 32);
		getContentPane().add(bID);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(2);
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
				 else if(false)
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
				 else if(combo.equalsIgnoreCase("delivery informaton"))
				 {
					 Delivers_information_Screen.Show();
					 dispose();
				 }
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setBounds(45, 246, 136, 32);
		comboBox.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(Color.BLACK));
		scrollPane.setBounds(381, 10, 336, 263);
		getContentPane().add(scrollPane);
		
		JLabel lblSearchByStorage = new JLabel("search by Storage ID");
		lblSearchByStorage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchByStorage.setBorder(new LineBorder(Color.BLACK));
		lblSearchByStorage.setBounds(381, 311, 136, 31);
		getContentPane().add(lblSearchByStorage);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table();
				mode = 0;
			}
		});
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Storages(textField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		textField.setColumns(10);
		textField.setBorder(new LineBorder(Color.BLACK));
		textField.setBounds(516, 311, 201, 31);
		getContentPane().add(textField);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Storages_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(516, 277, 89, 23);
		getContentPane().add(ShowTableButton);
		Load_Table();
	}
	
	public void Load_Table() {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Storages()));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//Storages Storage_ID, Quantity_products, Address, Branch_ID	
	/* MySQL Code for Department Table */

	private void insert_Storages(String Storage_ID,String Quantity_products,String Address,String Branch_ID) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("insert into Storages(Storage_ID,Quantity_products, Address, Branch_ID)values(?,?,?,?);");
		ps.setString(1, Storage_ID);
		ps.setString(2, Quantity_products);
		ps.setString(3, Address);
		ps.setString(4, Branch_ID);
		int x = ps.executeUpdate();
		if (x>0) {
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private void Update_Storages(String Storage_ID,String Quantity_products,String Address,String Branch_ID) throws Exception {
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("update Storages set Quantity_products = ? , Address = ? , Branch_ID = ? where Storage_ID = ? ;");
	
		ps.setString(1, Quantity_products);
		ps.setString(2, Address);
		ps.setString(3, Branch_ID);
		ps.setString(4, Storage_ID);
		
		int x = ps.executeUpdate();
		if (x>0) {
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private void Delete_Storages(String Storage_ID) throws Exception {
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(" delete  from  Storages  where  Storage_ID = ? ;");
	
		ps.setString(1, Storage_ID);

		int x = ps.executeUpdate();
		if (x>0) {
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
	private  ResultSet Desplay_Storages() throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from Storages;");
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private void Find_Storages(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select Storage_ID, Quantity_products, Address, Branch_ID from Storages where Storage_ID = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			storage_ID.setText(rs.getString(1));
			num_product.setText(rs.getString(2));
			address.setText(rs.getString(3));
			bID.setText(rs.getString(4));
		}		
	}
}
