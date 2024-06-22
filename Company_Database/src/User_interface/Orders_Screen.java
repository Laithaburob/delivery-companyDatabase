package User_interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableColumnModel;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;
import com.toedter.calendar.JDateChooser;
import DataBase.*;
import Tables_Screen.Orders_Table;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Orders_Screen extends JFrame {
	private JPanel contentPane;
	private JTextField Phone_NumberField;
	private JTextField Customer_NameField;
	private JTextField Customer_IDField;
	private JTextField Product_ID;
	private JTextField ProductNameField;
	private JTextField addressField;
	private JTextField SearchField;
	private JTable table;
	private JRadioButton automaticButton;
	private JRadioButton ManualButton;
	private JDateChooser Date_of_Order;
	private JDateChooser Date_of_Deliver;
	private JLabel OrdersID;
	private JCheckBox CheckIsDoneBox;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Orders_Screen frame = new Orders_Screen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Orders_Screen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 507);
		setTitle("Order");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Phone_NumberField = new JTextField();
		Phone_NumberField.setColumns(10);
		Phone_NumberField.setBorder(new LineBorder(Color.BLACK));
		Phone_NumberField.setBounds(10, 174, 172, 30);
		contentPane.add(Phone_NumberField);

		JLabel Phone_Number_Label = new JLabel("Phone Number");
		Phone_Number_Label.setFont(new Font("Tahoma", Font.BOLD, 12));
		Phone_Number_Label.setBorder(new LineBorder(Color.BLACK));
		Phone_Number_Label.setBounds(10, 160, 94, 14);
		contentPane.add(Phone_Number_Label);

		Customer_NameField = new JTextField();
		Customer_NameField.setColumns(10);
		Customer_NameField.setBorder(new LineBorder(Color.BLACK));
		Customer_NameField.setBounds(10, 119, 172, 30);
		contentPane.add(Customer_NameField);

		JLabel Customer_Name_Label = new JLabel("Customer Name");
		Customer_Name_Label.setFont(new Font("Tahoma", Font.BOLD, 12));
		Customer_Name_Label.setBorder(new LineBorder(Color.BLACK));
		Customer_Name_Label.setBounds(10, 105, 116, 14);
		contentPane.add(Customer_Name_Label);

		JLabel Customer_ID_Label_1 = new JLabel("Customer ID");
		Customer_ID_Label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		Customer_ID_Label_1.setBorder(new LineBorder(Color.BLACK));
		Customer_ID_Label_1.setBounds(10, 58, 94, 14);
		contentPane.add(Customer_ID_Label_1);

		Customer_IDField = new JTextField();
		Customer_IDField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Customer(Customer_IDField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Customer_IDField.setBorder(new LineBorder(Color.BLACK));
		Customer_IDField.setBounds(10, 71, 172, 23);
		contentPane.add(Customer_IDField);

		JLabel lblProductId = new JLabel("Product ID");
		lblProductId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProductId.setBorder(new LineBorder(Color.BLACK));
		lblProductId.setBounds(192, 58, 75, 14);
		contentPane.add(lblProductId);

		Product_ID = new JTextField();
		Product_ID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (!Product_ID.getText().isBlank()) {
						if (Find_Product("Product_ID = "+Product_ID.getText())) {
							ProductNameField.setEditable(false);
							return;
						}
						ProductNameField.setEditable(true);
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Product_ID.setColumns(10);
		Product_ID.setBorder(new LineBorder(Color.BLACK));
		Product_ID.setBounds(192, 71, 163, 23);
		contentPane.add(Product_ID);

		JLabel lblProductName = new JLabel("Product name");
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProductName.setBorder(new LineBorder(Color.BLACK));
		lblProductName.setBounds(192, 105, 103, 14);
		contentPane.add(lblProductName);

		ProductNameField = new JTextField();
		ProductNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!ProductNameField.getText().isBlank()) {
					try {
						if(Find_Product(" Product_Name = \""+ProductNameField.getText() +"\"")) {
							Product_ID.setEditable(false);
							return;
						}
						Product_ID.setEditable(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		ProductNameField.setColumns(10);
		ProductNameField.setBorder(new LineBorder(Color.BLACK));
		ProductNameField.setBounds(192, 119, 163, 30);
		contentPane.add(ProductNameField);

		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Customer_IDField.getText().isBlank() || Customer_NameField.getText().isBlank()
						|| Phone_NumberField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "missing Customer information");
					return;
				}
				if (Product_ID.getText().isBlank() || ProductNameField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Missing Product information");
					return;
				}
				if (addressField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Missing Address information");
					return;
				}

				/* Customer */
				if (Customer_NameField.isEditable() && Phone_NumberField.isEditable()) {
					try {
						Customer_Screen.insert_Customer(Customer_IDField.getText(), Customer_NameField.getText(),
								Phone_NumberField.getText());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1);
					}
				}

				/* Product */
				if (ProductNameField.isEditable()) {
					try {
						Product_Screen.insert_Product(Product_ID.getText(), ProductNameField.getText());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1);
					}
				}

				/* Orders */

				try {
					if (automaticButton.isSelected()) {
						insert_Orders(Customer_IDField.getText(), Product_ID.getText(), addressField.getText(), null,
								null);
					}
					if (ManualButton.isSelected()) {

						if (Date_of_Order.getDate() == null) {
							JOptionPane.showMessageDialog(null, "You forgot to enter the order date");
							return;
						}

						if (Date_of_Deliver.getDate() == null) {
							insert_Orders(Customer_IDField.getText(), Product_ID.getText(), addressField.getText(),
									java.sql.Date.valueOf(
											new SimpleDateFormat("yyyy-MM-dd").format(Date_of_Order.getDate())),
									null);
						} else {
							insert_Orders(Customer_IDField.getText(), Product_ID.getText(), addressField.getText(),
									java.sql.Date.valueOf(
											new SimpleDateFormat("yyyy-MM-dd").format(Date_of_Order.getDate())),
									java.sql.Date.valueOf(
											new SimpleDateFormat("yyyy-MM-dd").format(Date_of_Deliver.getDate())));
						}
					}

					Load_Table();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(58, 370, 101, 40);
		contentPane.add(addBurron);

		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (automaticButton.isSelected()) {
						Update_Orders(OrdersID.getText(), Customer_IDField.getText(), Product_ID.getText(),
								addressField.getText(), null, null);
						
					} else if (ManualButton.isSelected()) {


						if (Date_of_Deliver.getDate() == null) {
							Update_Orders(OrdersID.getText(), Customer_IDField.getText(), Product_ID.getText(),
									addressField.getText(), java.sql.Date.valueOf(
											new SimpleDateFormat("yyyy-MM-dd").format(Date_of_Order.getDate())),
									null);
						} else {
							Update_Orders(OrdersID.getText(), Customer_IDField.getText(), Product_ID.getText(),
									addressField.getText(),
									java.sql.Date.valueOf(
											new SimpleDateFormat("yyyy-MM-dd").format(Date_of_Order.getDate())),
									java.sql.Date.valueOf(
											new SimpleDateFormat("yyyy-MM-dd").format(Date_of_Deliver.getDate())));
						}
					}

					Load_Table();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(169, 417, 101, 40);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Orders(OrdersID.getText());
					Load_Table();
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(169, 370, 101, 40);
		contentPane.add(btnDelete);

		JButton backBurron = new JButton("back");
		backBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBurron.setBounds(58, 417, 101, 40);
		contentPane.add(backBurron);

		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(8);
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
				 else if(false)
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
		comboBox.setBounds(91, 327, 136, 32);
		contentPane.add(comboBox);

		JLabel lbladdress = new JLabel("Address");
		lbladdress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbladdress.setBorder(new LineBorder(Color.BLACK));
		lbladdress.setBounds(192, 160, 127, 14);
		contentPane.add(lbladdress);

		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBorder(new LineBorder(Color.BLACK));
		addressField.setBounds(192, 174, 163, 30);
		contentPane.add(addressField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(384, 11, 364, 245);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
				try {
					Find_Orders(table.getValueAt(selectedindex, 0).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblSearchByOrders = new JLabel("search by Orders ID");
		lblSearchByOrders.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchByOrders.setBorder(new LineBorder(Color.BLACK));
		lblSearchByOrders.setBounds(376, 301, 136, 31);
		contentPane.add(lblSearchByOrders);

		SearchField = new JTextField();
		SearchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Orders(SearchField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		SearchField.setColumns(10);
		SearchField.setBorder(new LineBorder(Color.BLACK));
		SearchField.setBounds(511, 301, 237, 31);
		contentPane.add(SearchField);

		CheckIsDoneBox = new JCheckBox("IS Done");
		CheckIsDoneBox.setBorder(new LineBorder(Color.BLACK));
		CheckIsDoneBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		CheckIsDoneBox.setBounds(85, 247, 97, 23);
		contentPane.add(CheckIsDoneBox);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(10, 247, 345, 40);
		contentPane.add(panel);
		panel.setLayout(null);

		Date_of_Order = new JDateChooser();
		Date_of_Order.setBorder(new LineBorder(Color.BLACK));
		Date_of_Order.setBounds(0, 20, 149, 20);
		panel.add(Date_of_Order);

		Date_of_Deliver = new JDateChooser();
		Date_of_Deliver.setBorder(new LineBorder(Color.BLACK));
		Date_of_Deliver.setBounds(183, 20, 149, 20);
		panel.add(Date_of_Deliver);

		JLabel Date_of_DeliverLabel = new JLabel("Date of Deliver");
		Date_of_DeliverLabel.setBorder(new LineBorder(Color.BLACK));
		Date_of_DeliverLabel.setBounds(182, 0, 94, 14);
		panel.add(Date_of_DeliverLabel);

		JLabel Date_of_OrderLabel = new JLabel("Date of Order");
		Date_of_OrderLabel.setBounds(0, 0, 94, 14);
		panel.add(Date_of_OrderLabel);
		Date_of_OrderLabel.setBorder(new LineBorder(Color.BLACK));

		automaticButton = new JRadioButton("Automatic");
		automaticButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (automaticButton.isSelected()) {
					CheckIsDoneBox.setVisible(true);
					panel.setVisible(false);
				}
			}
		});

		automaticButton.setSelected(true);
		automaticButton.setBounds(32, 223, 94, 23);
		contentPane.add(automaticButton);

		ManualButton = new JRadioButton("Manual");
		ManualButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				CheckIsDoneBox.setVisible(false);
			}
		});

		ManualButton.setBounds(147, 223, 80, 23);
		contentPane.add(ManualButton);
		ButtonGroup G = new ButtonGroup();
		G.add(ManualButton);
		G.add(automaticButton);

		JLabel Orders_Label = new JLabel("Orders ID");
		Orders_Label.setBorder(new LineBorder(Color.BLACK));
		Orders_Label.setBounds(10, 11, 94, 14);
		contentPane.add(Orders_Label);

		OrdersID = new JLabel();
		OrdersID.setBorder(new LineBorder(Color.BLACK));
		OrdersID.setBounds(10, 24, 172, 23);
		contentPane.add(OrdersID);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Orders_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(511, 267, 89, 23);
		contentPane.add(ShowTableButton);
		panel.setVisible(false);
		CheckIsDoneBox.setVisible(true);
		Load_Table();
	}

	public void Load_Table() {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Orders()));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/* MySQL Code for Orders Table */
	// Orders_ID, Customer_ID, Product_ID , Address , Date_of_Order ,
	// Date_of_Deliver
	private void insert_Orders(String Customer_ID, String Product_ID, String Address, Date Date_of_Order,
			Date Date_of_Deliver) throws Exception {
		//
		Connection conn = DataBase_Tools.getConnection();
		String order_is_automaticButton = "insert into Orders(Customer_ID, Product_ID, Address, Date_of_Order)values(?,?,?,CAST(now() AS date));";
		String order_is_ManualButton = "insert into Orders(Customer_ID, Product_ID, Address, Date_of_Order , Date_of_Deliver)values(?,?,?,?,?);";
		PreparedStatement ps = null;
		if (automaticButton.isSelected()) {
			ps = conn.prepareStatement(order_is_automaticButton);
			ps.setString(1, Customer_ID);
			ps.setString(2, Product_ID);
			ps.setString(3, Address);
		} else if (ManualButton.isSelected()) {
			ps = conn.prepareStatement(order_is_ManualButton);
			ps.setString(1, Customer_ID);
			ps.setString(2, Product_ID);
			ps.setString(3, Address);
			ps.setDate(4, Date_of_Order);
			if (Date_of_Deliver == null) {
				ps.setDate(5, null);
			} else {
				ps.setDate(5, Date_of_Deliver);
			}
		}

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Add Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Add Failed");
		}
	}

	private void Update_Orders(String Orders_ID, String Customer_ID, String Product_ID, String Address,
			Date Date_of_Order, Date Date_of_Deliver) throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = null;
		if (automaticButton.isSelected()) {
			String upofAutoselectnotcheck = "update Orders set Customer_ID = ?, Product_ID = ? , Address = ?  , Date_of_Deliver = ? where Orders_ID = ? ;";
			String upofAutoselectcheck = "update Orders set Customer_ID = ?, Product_ID = ? , Address = ?  , Date_of_Deliver = CAST(now() AS date) where Orders_ID = ? ;";

			if (CheckIsDoneBox.isSelected()) {
				ps = conn.prepareStatement(upofAutoselectcheck);
			} else {
				ps = conn.prepareStatement(upofAutoselectnotcheck);
			}
			ps.setString(1, Customer_ID);
			ps.setString(2, Product_ID);
			ps.setString(3, Address);

			if (!CheckIsDoneBox.isSelected()) {
				ps.setDate(4, null);
				ps.setString(5, Orders_ID);
			} else {
				ps.setString(4, Orders_ID);
			}
		} else if (ManualButton.isSelected()) {
			ps = conn.prepareStatement("update Orders set Customer_ID = ?, Product_ID = ? , Address = ?  , Date_of_Order = ? ,Date_of_Deliver = ? where Orders_ID = ? ;");
			ps.setString(1, Customer_ID);
			ps.setString(2, Product_ID);
			ps.setString(3, Address);
			ps.setDate(4, Date_of_Order);
			if (Date_of_Deliver == null) {
				ps.setDate(5, null);
			} else {
				ps.setDate(5, Date_of_Deliver);
			}
			ps.setString(6, Orders_ID);
		}

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Update Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Update Failed");
		}
	}

	private void Delete_Orders(String Orders_ID) throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(" delete  from  Orders  where  Orders_ID = ? ;");

		ps.setString(1, Orders_ID);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Delete Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Delete Failed");
		}
	}

	private ResultSet Desplay_Orders() throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select * from Orders O where O.Date_of_Deliver is null ORDER BY O.Date_of_Order asc;");

		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private void Find_Orders(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select Orders_ID, Customer_ID, Product_ID, Address, Date_of_Order , Date_of_Deliver from Orders where Orders_ID = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			OrdersID.setText(rs.getString(1));
			Customer_IDField.setText(rs.getString(2));
			Find_Customer(Customer_IDField.getText());
			Product_ID.setText(rs.getString(3));
			Find_Product(" Product_ID = "+Product_ID.getText());
			addressField.setText(rs.getString(4));

			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			Date_of_Order.setDate(new java.util.Date(dateFormat.format(rs.getDate(5))));
			if (rs.getDate(6) != null) {
				Date_of_Deliver.setDate(new java.util.Date(dateFormat.format(rs.getDate(6))));
				CheckIsDoneBox.setSelected(true);
			}else {
				CheckIsDoneBox.setSelected(false);
			}
		}
	}

	private void Find_Customer(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select Customer_ID,Customer_Name,Phone_Number " + "from Customer " + "where Customer_id = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		Customer_NameField.setEditable(true);
		Phone_NumberField.setEditable(true);
		if (rs.next() == true) {
			Customer_IDField.setText(rs.getString(1));

			Customer_NameField.setText(rs.getString(2));
			Customer_NameField.setEditable(false);
			Phone_NumberField.setText(rs.getString(3));
			Phone_NumberField.setEditable(false);
		}
	}

	private boolean Find_Product(String Product) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("select Product_ID,Product_Name from Product where "+Product+" ;");
		ResultSet rs = ps.executeQuery();
		ProductNameField.setEditable(true);
		if (rs.next() == true) {
			this.Product_ID.setText(rs.getString(1));
			ProductNameField.setText(rs.getString(2));
			return true;
		}
		return false;
	}
}
