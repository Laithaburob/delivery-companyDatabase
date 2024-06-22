package User_interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableColumnModel;

import DataBase.DataBase_Tools;
import Tables_Screen.Branch_Table;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Branch_Screen extends JFrame{
	
	private JPanel contentPane;
	private JTextField bID;
	private JTextField tnum;
	private JTextField adcress;
	private JTable table;
	private JTextField searchTxt;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Branch_Screen window = new Branch_Screen();
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
	public Branch_Screen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 740, 429);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("branch ID");
		lblNewLabel.setBorder(new LineBorder(Color.BLACK));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 21, 73, 19);
		getContentPane().add(lblNewLabel);
		
		bID = new JTextField();
		bID.setBorder(new LineBorder(Color.BLACK));
		bID.setBounds(10, 40, 135, 31);
		getContentPane().add(bID);
		bID.setColumns(10);
		
		JLabel lblTelephoneNumber = new JLabel("telephone number");
		lblTelephoneNumber.setBorder(new LineBorder(Color.BLACK));
		lblTelephoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelephoneNumber.setBounds(10, 82, 121, 19);
		getContentPane().add(lblTelephoneNumber);
		
		tnum = new JTextField();
		tnum.setBorder(new LineBorder(Color.BLACK));
		tnum.setColumns(10);
		tnum.setBounds(10, 101, 135, 31);
		getContentPane().add(tnum);
		
		JLabel lblNewLabel_1_1 = new JLabel("address");
		lblNewLabel_1_1.setBorder(new LineBorder(Color.BLACK));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 143, 61, 19);
		getContentPane().add(lblNewLabel_1_1);
		
		adcress = new JTextField();
		adcress.setBorder(new LineBorder(Color.BLACK));
		adcress.setColumns(10);
		adcress.setBounds(10, 162, 135, 31);
		getContentPane().add(adcress);
		
		JButton addButton = new JButton("add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert_Branch(bID.getText(), tnum.getText(), adcress.getText());
				Load_Table();
				
			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addButton.setBounds(10, 289, 85, 31);
		getContentPane().add(addButton);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update_Branch(bID.getText(), tnum.getText(), adcress.getText());
				Load_Table();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(115, 331, 85, 31);
		getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete_Branch(bID.getText());
				Load_Table();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(115, 289, 85, 31);
		getContentPane().add(btnDelete);
		
		JButton backButton = new JButton("back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backButton.setBounds(10, 331, 85, 31);
		getContentPane().add(backButton);
		
		table = new JTable();
		table.setBounds(361, 21, 336, 263);
		getContentPane().add(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
				bID.setText(table.getValueAt(selectedindex, 0).toString());
				tnum.setText(table.getValueAt(selectedindex, 1).toString());
				adcress.setText(table.getValueAt(selectedindex, 2).toString());
			}
		});
		JLabel lblSearchByBranch = new JLabel("search by branch ID");
		lblSearchByBranch.setBorder(new LineBorder(Color.BLACK));
		lblSearchByBranch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchByBranch.setBounds(372, 316, 135, 31);
		getContentPane().add(lblSearchByBranch);
		
		searchTxt = new JTextField();
		searchTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Find_Branch(searchTxt.getText());
			}
		});
		searchTxt.setColumns(10);
		searchTxt.setBorder(new LineBorder(Color.BLACK));
		searchTxt.setBounds(504, 316, 193, 31);
		getContentPane().add(searchTxt);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(0);
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
				String combo=comboBox.getSelectedItem().toString();
				if (false) {
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
		comboBox.setBounds(39, 246, 136, 32);
		getContentPane().add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(Color.BLACK));
		scrollPane.setBounds(361, 15, 336, 263);
		getContentPane().add(scrollPane);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.setBounds(495, 282, 89, 23);
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Branch_Table.Show();
			}
		});
		contentPane.add(ShowTableButton);
		
		Load_Table();
	}
	
	public void Load_Table() {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Branch()));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/*MySQL Code for Branch Table*/
	
	private void insert_Branch(String Branch_ID,String Telephone_Number,String Address)  {
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into Branch(Branch_ID,Telephone_Number,Address)values(?,?,?);");
			ps.setString(1, Branch_ID);
			ps.setString(2, Telephone_Number);
			ps.setString(3, Address);
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
	private void Update_Branch(String Branch_ID, String Telephone_Number, String Address)  {
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement("update Branch set Telephone_Number = ? , Address = ?  where Branch_ID = ? ;");
		
			ps.setString(1, Telephone_Number);
			ps.setString(2, Address);
			ps.setString(3, Branch_ID);
			
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
	private void Delete_Branch(String Branch_ID) {
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement(" delete  from  Branch  where  Branch_ID = ? ;");
		
			ps.setString(1, Branch_ID);

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
	public static ResultSet Desplay_Branch() throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from  Branch;");
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private void Find_Branch(String id) {
		try {

			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select Branch_ID, Telephone_Number, Address from Branch where Branch_ID = ?;");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				bID.setText(rs.getString(1));
				tnum.setText(rs.getString(2));
				adcress.setText(rs.getString(3));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
