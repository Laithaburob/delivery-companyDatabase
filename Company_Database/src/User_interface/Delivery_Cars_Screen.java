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
import Tables_Screen.Delivery_Cars_Table;
import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Delivery_Cars_Screen extends JFrame {
	
	private JTextField car_num;
	private JTextField car_type;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delivery_Cars_Screen window = new Delivery_Cars_Screen();
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
	public Delivery_Cars_Screen() {

		setTitle("cars");
		setBounds(100, 100, 671, 435);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblCarNumber = new JLabel("Car Number");
		lblCarNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCarNumber.setBounds(10, 11, 101, 21);
		lblCarNumber.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(lblCarNumber);
		
		car_num = new JTextField();
		car_num.setColumns(10);
		car_num.setBorder(new LineBorder(Color.BLACK));
		car_num.setBounds(10, 31, 163, 32);
		getContentPane().add(car_num);
		
		JLabel lblCarType = new JLabel("car type");
		lblCarType.setBorder(new LineBorder(Color.BLACK));
		lblCarType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCarType.setBounds(10, 83, 74, 21);
		getContentPane().add(lblCarType);
		
		car_type = new JTextField();
		car_type.setColumns(10);
		car_type.setBorder(new LineBorder(Color.BLACK));
		car_type.setBounds(10, 103, 163, 32);
		getContentPane().add(car_type);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
					car_num.setText(table.getValueAt(selectedindex, 0).toString());
					car_type.setText(table.getValueAt(selectedindex, 1).toString());
			}
		});
		table.setBounds(363, 11, 294, 375);
		getContentPane().add(table);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Delivery_Cars(car_num.getText());
					Load_Table();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(121, 308, 101, 40);
		getContentPane().add(btnDelete);
		
		JButton backBurron = new JButton("back");
		backBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBurron.setBounds(10, 358, 101, 40);
		getContentPane().add(backBurron);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Update_Delivery_Cars(car_num.getText(), car_type.getText());
					Load_Table();
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(121, 359, 101, 40);
		getContentPane().add(btnUpdate);
		
		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					insert_Delivery_Cars(car_num.getText(), car_type.getText());
					Load_Table();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(10, 308, 101, 40);
		getContentPane().add(addBurron);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(4);
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
				 else if(false)
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
		comboBox.setBounds(50, 265, 136, 32);
		getContentPane().add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(294, 11, 346, 248);
		getContentPane().add(scrollPane);
		
		JLabel lblSearchByCar = new JLabel("search by Car ID");
		lblSearchByCar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchByCar.setBorder(new LineBorder(Color.BLACK));
		lblSearchByCar.setBounds(329, 289, 111, 31);
		getContentPane().add(lblSearchByCar);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Delivery_Cars(textField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		textField.setColumns(10);
		textField.setBorder(new LineBorder(Color.BLACK));
		textField.setBounds(439, 289, 201, 31);
		getContentPane().add(textField);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delivery_Cars_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(446, 265, 89, 23);
		getContentPane().add(ShowTableButton);
		Load_Table();
	}
	
	public void Load_Table() {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Delivery_Cars()));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/*MySQL Code for Delivery_Cars Table*/
	private void insert_Delivery_Cars(String Car_Number, String Car_Type) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"insert into Delivery_Cars(Car_Number, Car_Type)values(?,?);");
		ps.setString(1, Car_Number);
		ps.setString(2, Car_Type);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Add Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Add Failed");
		}
	}

	private void Update_Delivery_Cars(String Car_Number, String Car_Type)
			throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"update Delivery_Cars set Car_Type = ? where Car_Number = ? ;");

		ps.setString(1, Car_Type);
		ps.setString(2, Car_Number);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Update Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Update Failed");
		}
	}

	private void Delete_Delivery_Cars(String Car_Number) throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(" delete  from  Delivery_Cars  where  Car_Number = ? ;");

		ps.setString(1, Car_Number);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Delete Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Delete Failed");
		}
	}

	private ResultSet Desplay_Delivery_Cars() throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from Delivery_Cars;");

		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private void Find_Delivery_Cars(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select Car_Number, Car_Type from Delivery_Cars where Car_Number = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			car_num.setText(rs.getString(1));
			car_type.setText(rs.getString(2));

		}
	}
}
