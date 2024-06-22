package User_interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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

import com.toedter.calendar.JDateChooser;

import DataBase.DataBase_Tools;
import Tables_Screen.Employee2Cars_Table;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Employee2Cars_Screen extends JFrame {
	private JPanel contentPane;
	private JTextField Car_numField;
	private JLabel CarType;
	private JTextField employeeIDField;
	private JLabel employeeName;
	private JTable table;
	private AbstractButton automaticButton;
	private JRadioButton ManualButton;
	private JCheckBox CheckIsDoneBox;
	private JDateChooser Start_Date;
	private JDateChooser End_Date;
	private JTextField Searchempid;
	private JTextField CarID_search;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee2Cars_Screen frame = new Employee2Cars_Screen();
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
	public Employee2Cars_Screen() {
		setTitle("Car owners");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 441);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCarNumber = new JLabel("Car Number");
		lblCarNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCarNumber.setBorder(new LineBorder(Color.BLACK));
		lblCarNumber.setBounds(179, 11, 102, 21);
		contentPane.add(lblCarNumber);

		Car_numField = new JTextField();
		Car_numField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Delivery_Cars(Car_numField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		Car_numField.setColumns(10);
		Car_numField.setBorder(new LineBorder(Color.BLACK));
		Car_numField.setBounds(179, 31, 163, 25);
		contentPane.add(Car_numField);

		JLabel lblCarType = new JLabel("car type");
		lblCarType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCarType.setBorder(new LineBorder(Color.BLACK));
		lblCarType.setBounds(179, 66, 74, 21);
		contentPane.add(lblCarType);

		CarType = new JLabel();
		CarType.setBorder(new LineBorder(Color.BLACK));
		CarType.setBounds(179, 86, 163, 25);
		contentPane.add(CarType);

		JLabel lblIdemployee = new JLabel("Employee ID");
		lblIdemployee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIdemployee.setBorder(new LineBorder(Color.BLACK));
		lblIdemployee.setBounds(10, 12, 86, 19);
		contentPane.add(lblIdemployee);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedindex = table.getSelectedRow();
				try {
					Find_Employee2Cars(table.getValueAt(selectedindex, 0).toString(),
							table.getValueAt(selectedindex, 1).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(451, 11, 364, 245);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);

		CheckIsDoneBox = new JCheckBox("IS Done");
		CheckIsDoneBox.setBorder(new LineBorder(Color.BLACK));
		CheckIsDoneBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		CheckIsDoneBox.setBounds(85, 190, 97, 23);
		contentPane.add(CheckIsDoneBox);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(10, 190, 345, 40);
		contentPane.add(panel);
		panel.setLayout(null);

		Start_Date = new JDateChooser();
		Start_Date.setBorder(new LineBorder(Color.BLACK));
		Start_Date.setBounds(0, 20, 149, 20);
		panel.add(Start_Date);

		End_Date = new JDateChooser();
		End_Date.setBorder(new LineBorder(Color.BLACK));
		End_Date.setBounds(183, 20, 149, 20);
		panel.add(End_Date);

		JLabel End_DateLabel = new JLabel("End Date");
		End_DateLabel.setBorder(new LineBorder(Color.BLACK));
		End_DateLabel.setBounds(182, 0, 94, 14);
		panel.add(End_DateLabel);

		JLabel Start_DateLabel = new JLabel("Start Date");
		Start_DateLabel.setBounds(0, 0, 94, 14);
		panel.add(Start_DateLabel);
		Start_DateLabel.setBorder(new LineBorder(Color.BLACK));

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
		automaticButton.setBounds(32, 166, 94, 23);
		contentPane.add(automaticButton);

		ManualButton = new JRadioButton("Manual");
		ManualButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				CheckIsDoneBox.setVisible(false);
			}
		});

		ManualButton.setBounds(147, 166, 80, 23);
		contentPane.add(ManualButton);
		ButtonGroup G = new ButtonGroup();
		G.add(ManualButton);
		G.add(automaticButton);
		automaticButton.setSelected(true);
		panel.setVisible(false);
		employeeIDField = new JTextField();
		employeeIDField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Find_Employee(employeeIDField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		employeeIDField.setColumns(10);
		employeeIDField.setBorder(new LineBorder(Color.BLACK));
		employeeIDField.setBounds(10, 31, 163, 25);
		contentPane.add(employeeIDField);

		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmployeeName.setBorder(new LineBorder(Color.BLACK));
		lblEmployeeName.setBounds(10, 67, 116, 19);
		contentPane.add(lblEmployeeName);

		employeeName = new JLabel();
		employeeName.setBorder(new LineBorder(Color.BLACK));
		employeeName.setBounds(10, 86, 163, 25);
		contentPane.add(employeeName);

		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Delete_Employee2Cars(employeeIDField.getText(), Car_numField.getText());
					Load_Table("");
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(180, 309, 101, 40);
		contentPane.add(btnDelete);

		JButton backBurron = new JButton("back");
		backBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main_Screen.Show();
				dispose();
			}
		});
		backBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBurron.setBounds(69, 359, 101, 40);
		contentPane.add(backBurron);

		JButton btnUpdate = new JButton("update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(180, 359, 101, 40);
		contentPane.add(btnUpdate);

		JButton addBurron = new JButton("add");
		addBurron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (employeeIDField.getText().isBlank() || employeeName.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "missing Employee information");
					return;
				}
				if (Car_numField.getText().isBlank() || CarType.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Missing Car information");
					return;
				}

				try {
					if (automaticButton.isSelected()) {
						insert_Employee2Cars(employeeIDField.getText(), Car_numField.getText(), null, null);
					}
					if (ManualButton.isSelected()) {

						if (Start_Date.getDate() == null) {
							JOptionPane.showMessageDialog(null, "You forgot to enter the Start date");
							return;
						}

						if (End_Date.getDate() == null) {
							insert_Employee2Cars(employeeIDField.getText(), Car_numField.getText(), java.sql.Date
									.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Start_Date.getDate())), null);
						} else {
							insert_Employee2Cars(employeeIDField.getText(), Car_numField.getText(),
									java.sql.Date
											.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Start_Date.getDate())),
									java.sql.Date
											.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(End_Date.getDate())));
						}
					}

					Load_Table("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}

		});

		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					if (automaticButton.isSelected()) {
						Update_Employee2Cars(employeeIDField.getText(), Car_numField.getText(), null, null);

					} else if (ManualButton.isSelected()) {

						if (End_Date.getDate() == null) {
							Update_Employee2Cars(employeeIDField.getText(), Car_numField.getText(), java.sql.Date
									.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Start_Date.getDate())), null);
						} else {
							Update_Employee2Cars(employeeIDField.getText(), Car_numField.getText(),
									java.sql.Date
											.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Start_Date.getDate())),
									java.sql.Date
											.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(End_Date.getDate())));
						}
					}

					Load_Table("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});

		addBurron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addBurron.setBounds(69, 309, 101, 40);
		contentPane.add(addBurron);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"branch", "department", "storage", "employee" , "cars", "customer", "product" , "Car Owners", "Orders" ,"delivery informaton"}));
		comboBox.setSelectedIndex(7);
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
				 else if(false)
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
		comboBox.setBorder(new LineBorder(Color.BLACK));
		comboBox.setBounds(105, 266, 147, 32);
		contentPane.add(comboBox);

		JLabel lblSearchBy = new JLabel("search by Employee ID");
		lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchBy.setBorder(new LineBorder(Color.BLACK));
		lblSearchBy.setBounds(490, 309, 170, 31);
		contentPane.add(lblSearchBy);

		Searchempid = new JTextField();
		Searchempid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!Searchempid.getText().isBlank()) {
					if (Searchempid.getText().isBlank() && CarID_search.getText().isBlank()) {
						Load_Table("");
					} else {
						if (CarID_search.getText().isBlank()) {
							Load_Table(" AND Employee_ID = " + Searchempid.getText()+" ");
						} else {
							Load_Table(" AND Employee_ID = " + Searchempid.getText()+" AND Car_Number = "+CarID_search.getText());
						}
						
					}
				}
				if (Searchempid.getText().isBlank() && CarID_search.getText().isBlank()) {
					Load_Table("");
				}
			}
		});
		Searchempid.setColumns(10);
		Searchempid.setBorder(new LineBorder(Color.BLACK));
		Searchempid.setBounds(660, 309, 124, 31);
		contentPane.add(Searchempid);

		JLabel lblSearchByCar = new JLabel("search by Car ID");
		lblSearchByCar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchByCar.setBorder(new LineBorder(Color.BLACK));
		lblSearchByCar.setBounds(490, 347, 170, 31);
		contentPane.add(lblSearchByCar);

		CarID_search = new JTextField();
		CarID_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!CarID_search.getText().isBlank()) {
					if (Searchempid.getText().isBlank() && CarID_search.getText().isBlank()) {
						Load_Table("");
					} else {
						if (Searchempid.getText().isBlank()) {
							Load_Table(" AND Car_Number = "+CarID_search.getText()+" ");
						} else {
							Load_Table(" AND Employee_ID = " + Searchempid.getText()+" AND Car_Number = "+CarID_search.getText());
						}
						
					}
				}
				if (Searchempid.getText().isBlank() && CarID_search.getText().isBlank()) {
					Load_Table("");
				}
			}
		});
		CarID_search.setColumns(10);
		CarID_search.setBorder(new LineBorder(Color.BLACK));
		CarID_search.setBounds(660, 347, 124, 31);
		contentPane.add(CarID_search);
		
		JButton ShowTableButton = new JButton("Show Table");
		ShowTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee2Cars_Table.Show();
			}
		});
		ShowTableButton.setBorder(new LineBorder(Color.BLACK));
		ShowTableButton.setBounds(589, 267, 89, 23);
		contentPane.add(ShowTableButton);

		Load_Table("");

	}

	public void Load_Table(String op) {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Employee2Cars(op)));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/* MySQL Code for Employee2Cars Table */
	// Employee_ID, Car_Number, Start_Date, End_Date
	private void insert_Employee2Cars(String Employee_ID, String Car_Number, Date Start_Date, Date End_Date)
			throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = null;

		if (automaticButton.isSelected()) {
			ps = conn.prepareStatement(
					"insert into Employee2Cars(Employee_ID, Car_Number,Start_Date)values(?,?,now());");
			ps.setString(1, Employee_ID);
			ps.setString(2, Car_Number);

		} else if (ManualButton.isSelected()) {
			ps = conn.prepareStatement(
					"insert into Employee2Cars(Employee_ID, Car_Number,Start_Date,End_Date)values(?,?,?,?);");
			ps.setString(1, Employee_ID);
			ps.setString(2, Car_Number);
			ps.setDate(3, Start_Date);
			if (End_Date == null) {
				ps.setDate(4, null);
			} else {
				ps.setDate(4, End_Date);
			}
		}

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Add Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Add Failed");
		}
	}

	private void Update_Employee2Cars(String Employee_ID, String Car_Number, Date Start_Date, Date End_Date)
			throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = null;

		if (automaticButton.isSelected()) {
			String upofAutoselectnotcheck = "update Employee2Cars set End_Date = ? where Employee_ID = ? AND Car_Number = ?;";
			String upofAutoselectcheck = "update Employee2Cars set End_Date = CAST(now() AS date) where Employee_ID = ? AND Car_Number = ?;";

			if (CheckIsDoneBox.isSelected()) {
				ps = conn.prepareStatement(upofAutoselectcheck);
			} else {
				ps = conn.prepareStatement(upofAutoselectnotcheck);
			}

			if (!CheckIsDoneBox.isSelected()) {
				ps.setDate(1, null);
				ps.setString(2, Employee_ID);
				ps.setString(3, Car_Number);
			} else {
				ps.setString(1, Employee_ID);
				ps.setString(2, Car_Number);
			}
		} else if (ManualButton.isSelected()) {
			ps = conn.prepareStatement(
					"update Employee2Cars set Start_Date = ? , End_Date = ? where Employee_ID = ? AND Car_Number = ?;");
			ps.setDate(1, Start_Date);
			if (End_Date == null) {
				ps.setDate(2, null);
			} else {
				ps.setDate(2, End_Date);
			}
			ps.setString(3, Employee_ID);
			ps.setString(4, Car_Number);
		}
		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Update Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Update Failed");
		}
	}

	private void Delete_Employee2Cars(String Employee_ID, String Car_Number) throws Exception {

		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn
				.prepareStatement(" delete  from  Employee2Cars  where Employee_ID = ? AND Car_Number = ? ;");

		ps.setString(1, Employee_ID);
		ps.setString(2, Car_Number);

		int x = ps.executeUpdate();
		if (x > 0) {
			JOptionPane.showMessageDialog(null, "Delete Successfully");
		} else {
			JOptionPane.showMessageDialog(null, "Delete Failed");
		}
	}

	private ResultSet Desplay_Employee2Cars(String op) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("select *  from Employee2Cars Where End_Date is null "+op+" ;");

		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private void Find_Employee2Cars(String Employee_ID, String Car_Number) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"select Employee_ID, Car_Number, Start_Date, End_Date from Employee2Cars where Employee_ID = ? AND Car_Number = ? AND  End_Date is null;");
		ps.setString(1, Employee_ID);
		ps.setString(2, Car_Number);
		ResultSet rs = ps.executeQuery();
		if (rs.next() == true) {
			employeeIDField.setText(rs.getString(1));
			Find_Employee(employeeIDField.getText());
			Car_numField.setText(rs.getString(2));
			Find_Delivery_Cars(Car_numField.getText());

			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			Start_Date.setDate(new java.util.Date(dateFormat.format(rs.getDate(3))));
			if (rs.getDate(4) != null) {
				End_Date.setDate(new java.util.Date(dateFormat.format(rs.getDate(4))));
				CheckIsDoneBox.setSelected(true);
			} else {
				CheckIsDoneBox.setSelected(false);
			}
		}
	}

	private void Find_Employee(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("select ID_Number, Employee_Name " + "from Employee " + "where ID_Number = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		employeeName.setText("");
		if (rs.next() == true) {
			employeeIDField.setText(rs.getString(1));
			employeeName.setText(rs.getString(2));
		}
	}

	private void Find_Delivery_Cars(String id) throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("select Car_Number, Car_Type from Delivery_Cars where Car_Number = ?;");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		CarType.setText("");
		if (rs.next() == true) {
			Car_numField.setText(rs.getString(1));
			CarType.setText(rs.getString(2));
		}
	}
}
