package Tables_Screen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import DataBase.DataBase_Tools;
import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Employee2Cars_Table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField EmployeeField;
	private JLabel CarNumLabel;
	private JTextField carnum;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee2Cars_Table frame = new Employee2Cars_Table();
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
	public Employee2Cars_Table() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel find_EIDLabel = new JLabel("Find by Employee ID");
		find_EIDLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(find_EIDLabel);
		
		EmployeeField = new JTextField();
		EmployeeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!EmployeeField.getText().isBlank()) {
					if (EmployeeField.getText().isBlank() && carnum.getText().isBlank()) {
						load_Table("");
					} else {
						if (carnum.getText().isBlank()) {
							load_Table("Where E2C.Employee_ID = " + EmployeeField.getText()+" ");
						} else {
							load_Table("Where E2C.Employee_ID = " + EmployeeField.getText()+" AND E2C.Car_Number = "+carnum.getText());
						}
						
					}
				}
				if (EmployeeField.getText().isBlank() && carnum.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		panel.add(EmployeeField);
		EmployeeField.setColumns(10);
		
		CarNumLabel = new JLabel("Find by Car Number");
		CarNumLabel.setAlignmentX(1.0f);
		panel.add(CarNumLabel);
		
		carnum = new JTextField();
		carnum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!carnum.getText().isBlank()) {
					if (EmployeeField.getText().isBlank() && carnum.getText().isBlank()) {
						load_Table("");
					} else {
						if (EmployeeField.getText().isBlank()) {
							load_Table("Where E2C.Car_Number = "+carnum.getText()+" ");
						} else {
							load_Table("Where E2C.Employee_ID = " + EmployeeField.getText()+" AND E2C.Car_Number = "+carnum.getText());
						}
						
					}
				}
				if (EmployeeField.getText().isBlank() && carnum.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		carnum.setColumns(10);
		panel.add(carnum);
		load_Table("");
	}

	private void load_Table(String op) {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Table(op)));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	private ResultSet Desplay_Table(String op) throws Exception{
		Connection conn = DataBase_Tools.getConnection();
		String Statement = 
				"select E2C.Employee_ID,E.Employee_Name,DC.*,E2C.Start_Date,E2C.End_Date	" + 
				"from Employee2Cars E2C 	" + 
				"join Employee E on E2C.Employee_ID = E.ID_Number	" + 
				"join Delivery_Cars DC on E2C.Car_Number = DC.Car_Number	" + 
				op+
				";";
				
		PreparedStatement ps = conn.prepareStatement(Statement);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
