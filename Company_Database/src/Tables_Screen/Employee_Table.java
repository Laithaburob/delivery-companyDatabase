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
import javax.swing.table.TableColumnModel;

import DataBase.DataBase_Tools;
import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Employee_Table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldbyid;
	private JLabel numofemployeeLabel;
	private JLabel numofemployee;
	private JLabel findLabelbyname;
	private JTextField textFieldbyname;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee_Table frame = new Employee_Table();
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
	public Employee_Table() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel findLabel = new JLabel("Find by ID");
		findLabel.setBorder(new LineBorder(Color.BLACK));
		findLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(findLabel);
		
		textFieldbyid = new JTextField();
		textFieldbyid.setBorder(new LineBorder(Color.BLACK));
		textFieldbyid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (textFieldbyid.getText().isBlank()) {
						load_Table("");
					} else {
						load_Table("Where ID_Number = " + textFieldbyid.getText()+" ");
					}
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});
		panel.add(textFieldbyid);
		textFieldbyid.setColumns(10);
		
		findLabelbyname = new JLabel("Find by name");
		findLabelbyname.setBorder(new LineBorder(Color.BLACK));
		findLabelbyname.setAlignmentX(1.0f);
		panel.add(findLabelbyname);
		
		textFieldbyname = new JTextField();
		textFieldbyname.setBorder(new LineBorder(Color.BLACK));
		textFieldbyname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!textFieldbyname.getText().isBlank()) {
					if (textFieldbyname.getText().isBlank() && textFieldbyid.getText().isBlank()) {
						load_Table("");
					} else {
						if (textFieldbyid.getText().isBlank()) {
							load_Table("Where E.Employee_Name Like \""+textFieldbyname.getText()+"%\" ");
						} else {
							load_Table("Where E.ID_Number = " + textFieldbyid.getText()+" AND E.Employee_Name Like \""+textFieldbyname.getText()+"%\" ");
						}
					}
				}
				if (textFieldbyname.getText().isBlank() && textFieldbyid.getText().isBlank()) {
					load_Table("");
				}
			}
		});
		textFieldbyname.setColumns(10);
		panel.add(textFieldbyname);
		
		numofemployeeLabel = new JLabel("Number of Employee");
		numofemployeeLabel.setBorder(new LineBorder(Color.BLACK));
		panel.add(numofemployeeLabel);
		
		numofemployee = new JLabel(getnumofemployee());
		numofemployee.setBorder(new LineBorder(Color.BLACK));
		panel.add(numofemployee);
		load_Table("");
	}

	private void load_Table(String find) {
		try {
			table.setModel(DbUtils.resultSetToTableModel(Desplay_Table(find)));
			table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
			TableColumnModel tcm = table.getColumnModel();
			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(10 * (table.getColumnName(i).length()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	private ResultSet Desplay_Table(String find) throws Exception{
		Connection conn = DataBase_Tools.getConnection();
		String Statement = 
				"	select E.*,count(O.Date_of_Deliver) AS \"Number of Deliver\" " + 
				"	from Employee E " + 
				"	left join Delivers_information DI on E.ID_Number = DI.ID_Employee " + 
				"   left join Orders O on DI.Orders_ID = O.Orders_ID " + 
					find+
				" 	group by E.ID_Number " + 
				" 	order by count(O.Date_of_Deliver) desc " + 
				" 	;";
		PreparedStatement ps = conn.prepareStatement(Statement);
		
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}

	private String getnumofemployee() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			String Statement = "select count(*) from Employee ;";
			PreparedStatement ps = conn.prepareStatement(Statement);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "0";
	}
	
	
}
