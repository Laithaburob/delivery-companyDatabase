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

public class Branch_Table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField findtextField;
	private JLabel NumBranch;
	private JLabel Number_of_BranchLabel;

	/**
	 * Launch the application.
	 */
	public static void Show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Branch_Table frame = new Branch_Table();
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
	public Branch_Table() {
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
		findLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		findLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(findLabel);
		
		findtextField = new JTextField();
		findtextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (findtextField.getText().isBlank()) {
						load_Table("");
					} else {
						load_Table("Where B.Branch_ID = " + findtextField.getText()+" ");
					}
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});
		panel.add(findtextField);
		findtextField.setColumns(10);
		
		Number_of_BranchLabel = new JLabel("Number of Branch");
		Number_of_BranchLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(Number_of_BranchLabel);
		
		NumBranch = new JLabel(getNumofBranch());
		NumBranch.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(NumBranch);
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
				"select B.Branch_ID AS \"Branch ID\",B.Telephone_Number AS \"Telephone Number\",B.Address , count(D.Department_ID) AS \"Number of Department\" ,count(E.ID_Number) AS \"Number of Employee\"	" + 
				"from Branch B	" + 
				"left join Employee E on B.Branch_ID = E.Branch_ID	" + 
				"left join Department D on B.Branch_ID = D.Branch_ID	"
				+find + 
				"group by B.Branch_ID	" + 
				";";
		PreparedStatement ps = conn.prepareStatement(Statement);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private String getNumofBranch() {
		try {
			Connection conn = DataBase_Tools.getConnection();
			String Statement = "select count(*) from Branch ;";
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
