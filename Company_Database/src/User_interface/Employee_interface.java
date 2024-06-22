package User_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumnModel;

import DataBase.DataBase_Tools;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Employee_interface extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField Search;
	private JLabel OrderID;
	private JLabel Customer_Name;
	private JLabel Address;
	private JLabel E_ID;
	private JLabel E_name;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee_interface frame = new Employee_interface();
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
	public Employee_interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOrderID = new JLabel("Order ID");
		lblOrderID.setBounds(10, 139, 87, 14);
		lblOrderID.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(lblOrderID);
		
		OrderID = new JLabel("");
		OrderID.setBounds(10, 153, 231, 33);
		OrderID.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(OrderID);
		
		table = new JTable();
		table.setBounds(556, 11, -248, 228);
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
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(285, 10, 300, 249);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(scrollPane);
		
		JButton Delivered = new JButton("Delivered");
		Delivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Update(OrderID.getText());
					Load_Table();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Delivered.setBounds(10, 325, 231, 33);
		Delivered.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(Delivered);
		
		JButton Sing_Out = new JButton("Sing Out");
		Sing_Out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = DataBase_Tools.getConnection();
					conn.close();
					LoginScreen.Show();
					dispose();
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Sing_Out.setBounds(10, 369, 231, 33);
		Sing_Out.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(Sing_Out);
		
		JLabel lblCustomer_Name = new JLabel("Customer Name");
		lblCustomer_Name.setBounds(10, 197, 95, 14);
		lblCustomer_Name.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(lblCustomer_Name);
		
		Customer_Name = new JLabel("");
		Customer_Name.setBounds(10, 210, 231, 33);
		Customer_Name.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(Customer_Name);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 254, 87, 14);
		lblAddress.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(lblAddress);
		
		Address = new JLabel("");
		Address.setBounds(10, 268, 231, 33);
		Address.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(Address);
		
		JLabel lblSearchByOrders = new JLabel("Search By Orders");
		lblSearchByOrders.setBorder(new LineBorder(Color.BLACK));
		lblSearchByOrders.setBounds(295, 270, 107, 24);
		contentPane.add(lblSearchByOrders);
		
		Search = new JTextField();
		Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Orders(Search.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Search.setBounds(401, 270, 178, 24);
		Search.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(Search);
		Search.setColumns(10);
		
		JLabel lDl_E_ID = new JLabel("ID");
		lDl_E_ID.setBorder(new LineBorder(Color.BLACK));
		lDl_E_ID.setBounds(10, 11, 117, 14);
		contentPane.add(lDl_E_ID);
		
		E_ID = new JLabel(DataBase_Tools.getUsername());
		E_ID.setBorder(new LineBorder(Color.BLACK));
		E_ID.setBounds(10, 25, 117, 24);
		contentPane.add(E_ID);
		
		JLabel lbl_E_Name = new JLabel("Name");
		lbl_E_Name.setBorder(new LineBorder(Color.BLACK));
		lbl_E_Name.setBounds(134, 11, 107, 14);
		contentPane.add(lbl_E_Name);
		
		E_name = new JLabel(E_Name());
		E_name.setBorder(new LineBorder(Color.BLACK));
		E_name.setBounds(134, 25, 107, 24);
		contentPane.add(E_name);
		
		JLabel lDl_Car_ID = new JLabel("Car Number");
		lDl_Car_ID.setBorder(new LineBorder(Color.BLACK));
		lDl_Car_ID.setBounds(10, 60, 117, 14);
		contentPane.add(lDl_Car_ID);
		
		JLabel Car_namber = new JLabel(Car_num());
		Car_namber.setBorder(new LineBorder(Color.BLACK));
		Car_namber.setBounds(10, 74, 117, 24);
		contentPane.add(Car_namber);
		Load_Table();
	}
	public void Load_Table() {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Delivers_information()));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private ResultSet Delivers_information() throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				" select  O.Orders_ID, C.Customer_Name,O.Address " +
				" from Delivers_information DI join Orders O on DI.Orders_ID = O.Orders_ID,Customer C "+
				" where O.Customer_ID = C.Customer_ID AND O.Date_of_Deliver is null AND DI.ID_Employee = ? "+
				" ORDER BY O.Date_of_Order ;");
		ps.setString(1, E_ID.getText());

		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private void Find_Orders(String Orders_ID) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				" select  O.Orders_ID, C.Customer_Name,O.Address " +
				" from Delivers_information DI join Orders O on DI.Orders_ID = O.Orders_ID,Customer C "+
				" where DI.ID_Employee = ? AND DI.Orders_ID = ? AND O.Customer_ID = C.Customer_ID AND O.Date_of_Deliver is null  "+
				" ORDER BY O.Date_of_Order ;");
		ps.setString(1, E_ID.getText());
		ps.setString(2, Orders_ID);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			OrderID.setText(rs.getString(1));
			Customer_Name.setText(rs.getString(2));
			Address.setText(rs.getString(3));
			
		}
	}
	private String E_Name() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement("select Employee_Name from Employee E where E.ID_Number = ? ;");
			ps.setString(1, E_ID.getText());
			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				return rs.getString(1);
			}
			
		} catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e1);
		}
		return "";
	}
	private String Car_num() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"select E2C.Car_Number "+
					"from Employee2Cars E2C "+ 
					"join Employee E on E2C.Employee_ID = E.ID_Number " + 
					"where E2C.End_Date is null AND " + 
					"E2C.Employee_ID = ? "+
					"; ");
			ps.setString(1, E_ID.getText());
			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				return rs.getString(1);
			}
			
		} catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e1);
		}
		return "";
	}
	private void Update(String ID) throws Exception{
		
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("update Orders set Date_of_Deliver = CAST(now() AS date) where Orders_ID = ? ;");
	
		ps.setString(1, ID);
		
		int x = ps.executeUpdate();
		if (x>0) {
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}
}
