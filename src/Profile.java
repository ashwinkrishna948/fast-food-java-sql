import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class Profile extends JFrame {
	User user;
	Landing landing;
	Restaurant restaurant;
	private JPanel contentPane;
	private JTable table;

	

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
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Profile frame = new Profile();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	/**
	 * Create the frame.
	 */
	public Profile(User u, Landing l, Restaurant r) {
		user = u;
		landing = l;
		restaurant = r;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHead = new JLabel("");
		lblHead.setFont(new Font("Dialog", Font.BOLD, 16));
		lblHead.setBounds(32, 68, 206, 37);
		contentPane.add(lblHead);
		lblHead.setText("Welcome " + user.name);

		JLabel lblPhNo = new JLabel("");
		lblPhNo.setBounds(32, 104, 296, 15);
		contentPane.add(lblPhNo);
		lblPhNo.setText("Phone number: " + user.phone_num);

		JLabel lblAddress = new JLabel("");
		lblAddress.setVerticalAlignment(SwingConstants.TOP);
		lblAddress.setBounds(32, 151, 296, 69);
		contentPane.add(lblAddress);
		lblAddress.setText("Address: " + user.address);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (landing == null) {
					Landing l = new Landing(user, "", "");
					l.setVisible(true);
				} else {
					RestPage rp = new RestPage(user, restaurant, landing, 0, "");
					rp.setVisible(true);
				}
				dispose();
			}
		});
		btnBack.setBounds(32, 31, 67, 25);
		contentPane.add(btnBack);

		JTable table;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "16181618");
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "select * from (select o_id, r_name, price from orders join restaurant on orders.r_id = restaurant.r_id where phone_num = '" + user.phone_num + "' order by order_time desc )where rownum <= 10";
			//String query = "select o_id, r_name, price from orders join restaurant on orders.r_id = restaurant.r_id where phone_num = '" + user.phone_num + "' order by order_time desc";
			ResultSet rs = stmt.executeQuery(query);
			table = new JTable(buildTableModel(rs));
			table.setShowVerticalLines(false);
			table.setShowHorizontalLines(false);
			table.setShowGrid(false);
			table.setBounds(42, 259, 357, 187);
			contentPane.add(table);
			
			JLabel lblNewLabel = new JLabel("Order ID");
			lblNewLabel.setBounds(50, 232, 70, 15);
			contentPane.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Restaurant");
			lblNewLabel_1.setBounds(168, 232, 80, 15);
			contentPane.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("Total");
			lblNewLabel_2.setBounds(298, 232, 70, 15);
			contentPane.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("Past Orders");
			lblNewLabel_3.setBounds(32, 206, 85, 15);
			contentPane.add(lblNewLabel_3);
			
			JButton btnNewButton = new JButton("Update Profile");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					UpdateForm uf = new UpdateForm (user);
					uf.setVisible (true);
				}
			});
			
			btnNewButton.setBounds(293, 31, 136, 25);
			contentPane.add(btnNewButton);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
