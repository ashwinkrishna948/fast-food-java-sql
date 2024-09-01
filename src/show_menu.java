import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;

public class show_menu extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	public static Admin_class admin_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					show_menu frame = new show_menu(admin_1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
		tableModel.fireTableDataChanged();
		return tableModel;

	}

	/**
	 * Create the frame.
	 */
	public show_menu(Admin_class admin_1) {
		
		this.admin_1=admin_1;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblRestaurantId = new JLabel("Restaurant Name");
		lblRestaurantId.setBounds(90, 11, 124, 15);
		panel.add(lblRestaurantId);
		
		JLabel label = new JLabel("");
		label.setBounds(218, 11, 104, 14);
		panel.add(label);
		
		table = new JTable();
		table.setBounds(38, 36, 348, 174);
		//panel.add(table);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(325, 228, 89, 23);
		panel.add(btnBack);
		
		try {
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "16181618");
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String q1="select r_name from restaurant where r_id = "+admin_1.ad_id;
			ResultSet rs= stmt.executeQuery(q1);
			
			if(rs.next())
			{
				label.setText(rs.getString(1));
			}
			else
			 label.setText("No restaurant selected");
			
			String q = "select d_id as Dish_Id, d_name as Dish_Name, price as Price, isveg from dish where r_id = " + admin_1.ad_id;
			ResultSet rs1 = stmt.executeQuery(q);
			table_1 = new JTable(buildTableModel(rs1));
			table_1.setShowVerticalLines(false);
			table_1.setShowHorizontalLines(false);
			table_1.setShowGrid(false);
			table_1.setBounds(21, 61, 381, 145);
			//contentPane.add(table);
			panel.add(table_1);
			
			JLabel lblId = new JLabel("ID");
			lblId.setBounds(21, 35, 46, 14);
			panel.add(lblId);
			
			JLabel lblDishName = new JLabel("Dish Name");
			lblDishName.setBounds(113, 36, 75, 15);
			panel.add(lblDishName);
			
			JLabel lblPrice = new JLabel("Price");
			lblPrice.setBounds(228, 37, 46, 14);
			panel.add(lblPrice);
			
			JLabel lblType = new JLabel("Type");
			lblType.setBounds(305, 35, 46, 14);
			panel.add(lblType);
			con.commit();
			con.close();
		}
		catch(Exception e)
		{
		   	e.printStackTrace();
		}

		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					Admin_landing new_frame2=new Admin_landing(admin_1);
					new_frame2.setVisible(true);
					dispose();
				}
			
				catch(Exception e)
				{
					
					e.printStackTrace();
				}
			}
		});
		
	
	}
}
	

