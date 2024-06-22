package User_interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import Tables_Screen.Department_Table;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Department_Screen extends JFrame{

	private JTextField depID;
	private JTextField branch_ID;
	private JTextField type_description;
	private JTable table;
	private JTextField textField;
	public static int mode = 0;
	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Department_Screen window = new Department_Screen();
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
	public Department_Screen() {

		setTitle("department");
		setBounds(100, 100, 754, 421);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Department ID");
		lblNewLabel.setBorder(new LineBorder(Color.BLACK));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 30, 101, 17);
		getContentPane().add(lblNewLabel);
		
		depID = new JTextField();
		depID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table();
				mode = 0;
			}
		});
		depID.setBorder(new LineBorder(Color.BLACK));
		depID.setBounds(10, 47, 192, 31);
		getContentPane().add(depID);
		depID.setColumns(10);
		
		JLabel lblBranchId = new JLabel("branch ID");
		lblBranchId.setBorder(new LineBorder(Color.BLACK));
		lblBranchId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBranchId.setBounds(10, 89, 101, 19);
		getContentPane().add(lblBranchId);
		
		branch_ID = new JTextField();
		branch_ID.setBorder(new LineBorder(Color.BLACK));
		branch_ID.setColumns(10);
		branch_ID.setBounds(10, 108, 192, 32);
		branch_ID.addFocusListener(new FocusAdapter() {
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
					System.out.println(e);
				}
			}
		});
		getContentPane().add(branch_ID);
		
		JLabel lblTypeDescription = new JLabel("Type description ");
		lblTypeDescription.setBorder(new LineBorder(Color.BLACK));
		lblTypeDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTypeDescription.setBounds(10, 151, 136, 17);
		getContentPane().add(lblTypeDescription);
		
		type_description = new JTextField();
		type_description.setBorder(new LineBorder(Color.BLACK));
		type_description.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Load_Table();
				mode = 0;
			}
		});
		
		type_description.setColumns(10);
		type_description.setBounds(10, 168, 192, 32);
		
		getContentPane().add(type_description);
		
		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert_Department(depID.getText(), branch_ID.getText(),type_description.getText());
				Load_Table();
			}
		});
		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(0, 259, 101, 40);
		getContentPane().add(addBurron);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update_Department(depID.getText(), branch_ID.getText(), type_description.getText());
				Load_Table();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(111, 310, 101, 40);
		getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete_Department(depID.getText());
				Load_Table();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(111, 259, 101, 40);
		getContentPane().add(btnDelete);
		
		JButton addBurron_2_1 = new JButton("back");
		addBurron_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		addBurron_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron_2_1.setBounds(0, 310, 101, 40);
		getContentPane().add(addBurron_2_1);
		
		table = new JTable();
		table.setBounds(379, 21, 330, 278);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
				if (mode == 0) {
					depID.setText(table.getValueAt(selectedindex, 0).toString());
					branch_ID.setText(table.getValueAt(selectedindex, 1).toString());
					type_description.setText(table.getValueAt(selectedindex, 2).toString());
				}else if (mode == 1) {
					branch_ID.setText(table.getValueAt(selectedindex, 0).toString());
				}
			}
		});
		getContentPane().add(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(1);
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
				String combo=comboBox.getSelectedItem().toString();
				if (combo.equalsIgnoreCase("branch")) {
					Branch_Screen.Show();
					dispose();
				}
				else if(false)
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
		comboBox.setBounds(43, 222, 136, 32);
		getContentPane().add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(Color.BLACK));
		scrollPane.setBounds(367, 21, 342, 278);
		getContentPane().add(scrollPane);
		
		JLabel lblSearchByID = new JLabel("search by Department ID");
		lblSearchByID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchByID.setBorder(new LineBorder(Color.BLACK));
		lblSearchByID.setBounds(342, 340, 183, 31);
		getContentPane().add(lblSearchByID);
		
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
				Find_Department(textField.getText());
			}
		});
		textField.setColumns(10);
		textField.setBorder(new LineBorder(Color.BLACK));
		textField.setBounds(526, 340, 183, 31);
		getContentPane().add(textField);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Department_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(509, 306, 89, 23);
		getContentPane().add(ShowTableButton);
		Load_Table();
	}
	public void Load_Table() {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Department()));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		
	}
	/*MySQL Code for Department Table*/
	
	private void insert_Department(String Department_ID,String Branch_ID,String Type_description)  {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("insert into Department(Department_ID,Branch_ID, Type_description)values(?,?,?);");
			ps.setString(1, Department_ID);
			ps.setString(2, Branch_ID);
			ps.setString(3, Type_description);
			int x = ps.executeUpdate();
			if (x>0) {
				JOptionPane.showMessageDialog(null, "Successfully");
			} else {
				JOptionPane.showMessageDialog(null, "Failed");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	private void Update_Department(String Department_ID,String Branch_ID, String Type_description){
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement("update Department set Branch_ID = ?  , Type_description = ? where Department_ID = ? ;");
			ps.setString(1, Branch_ID);
			ps.setString(2, Type_description);
			ps.setString(3, Department_ID);
			
			int x = ps.executeUpdate();
			if (x>0) {
				JOptionPane.showMessageDialog(null, "Successfully");
			} else {
				JOptionPane.showMessageDialog(null, "Failed");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	private void Delete_Department(String Department_ID) {
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement(" delete  from  Department  where  Department_ID = ? ;");

			ps.setString(1, Department_ID);

			int x = ps.executeUpdate();
			if (x > 0) {
				JOptionPane.showMessageDialog(null, "Successfully");
			} else {
				JOptionPane.showMessageDialog(null, "Failed");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public static  ResultSet Desplay_Department() throws Exception {
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from Department;");
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private void Find_Department(String id) {
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"select Department_ID, Branch_ID, Type_description from Department where Department_ID = ?;");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				depID.setText(rs.getString(1));
				branch_ID.setText(rs.getString(2));
				type_description.setText(rs.getString(3));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
