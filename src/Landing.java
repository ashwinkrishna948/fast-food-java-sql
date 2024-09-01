import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

public class Landing extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField tfName;
	private String restaurant;
	private String cuisine;
	private User user;
	public List<Order> order = new ArrayList<>();
/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Landing frame = new Landing(user, "", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
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
	public Landing(User user, String r, String c) {
		this.user = user;
		restaurant = r;
		cuisine = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JButton btnSearch = new JButton("");

		
		btnSearch.setBounds(382, 113, 40, 40);
		contentPane.add(btnSearch);
		// adding icon to search button
		try {
			Image img = ImageIO.read(getClass().getResource("resources/search.png"));
			Image resizedImage = img.getScaledInstance(btnSearch.getWidth(), btnSearch.getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			btnSearch.setIcon(new ImageIcon(resizedImage));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		JLabel lblCuisine = new JLabel("Search by Cuisine");
		lblCuisine.setBounds(192, 113, 126, 15);
		contentPane.add(lblCuisine);

		JLabel lblSearch = new JLabel("Search by name");
		lblSearch.setBounds(24, 113, 113, 15);
		contentPane.add(lblSearch);

		tfName = new JTextField();
		tfName.setBounds(24, 127, 156, 25);
		contentPane.add(tfName);
		tfName.setColumns(10);

		JComboBox<String> cuisineList = new JComboBox<String>();
		cuisineList.setBounds(192, 127, 156, 24);
		contentPane.add(cuisineList);
		// populate combobox
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "16181618");
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT c_name FROM cuisine");
			while (rs.next()) {
				cuisineList.addItem(rs.getString("c_name"));
			}
			// sets first entry to null
			cuisineList.setSelectedIndex(-1);
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// action for search button
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				restaurant = tfName.getText();
				cuisine = cuisineList.getItemAt(cuisineList.getSelectedIndex());
				if (cuisine == null)
					cuisine = "";
				Landing frame2 = new Landing(user, restaurant, cuisine);
				frame2.setVisible(true);
				dispose();
			}
		});
		// prepare query
		String query;
		if (restaurant.equals("") && cuisine.equals("")) {
			// populate all restaurants
			query = "select r_name, address from restaurant";
		} else if (!restaurant.equals("") && !cuisine.equals(""))
			query = "with rids (id) as (select r_id from restaurant_cuisine where cuisine = '" + cuisine
					+ "') select r_name, address from restaurant, rids where rids.id = restaurant.r_id and upper(r_name) = '"
					+ restaurant.toUpperCase() + "'";

		// query = "Select r_name from restaurant where upper(r_name) like '" +
		// restaurant.toUpperCase() + "'";
		else if (!restaurant.equals(""))
			query = "Select r_name, address from restaurant where upper(r_name) like '" + restaurant.toUpperCase() + "'";
		else {
			query = "with rids (id) as (select r_id from restaurant_cuisine where cuisine = '" + cuisine
					+ "') select r_name, address from restaurant, rids where rids.id = restaurant.r_id";
			// query = "select r_name from restuarant_cuisine, restaurant where cuisine = '"
			// + cuisine + "' and restaurant_cuisine.r_id = restaurant.r_id";
		}
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "16181618");
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			// build table
			table = new JTable(buildTableModel(rs));
			table.setAlignmentX(Component.RIGHT_ALIGNMENT);
			table.setRowMargin(4);
			table.setShowHorizontalLines(false);
			table.setRowHeight(25);
			table.setFont(new Font("Dialog", Font.PLAIN, 16));
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setShowVerticalLines(false);
			table.setBounds(24, 221, 390, 222);
			table.setIntercellSpacing(new Dimension(5, 5));
			contentPane.add(table);
			
			JButton btnLogout = new JButton("Logout");
			btnLogout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					LoggedOut d = new LoggedOut ();
					Home h = new Home ();
					h.setVisible(true);
					dispose ();
				}
			});
			btnLogout.setBounds(319, 35, 84, 25);
			contentPane.add(btnLogout);
			
			JButton btnProfile = new JButton("Profile");
			btnProfile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Profile p = new Profile (user, null, null);
					p.setVisible(true);
					dispose ();
				}
			});
			btnProfile.setBounds(175, 35, 80, 25);
			contentPane.add(btnProfile);
			
			JLabel lblNewLabel = new JLabel("Restaurant");
			lblNewLabel.setBounds(24, 194, 80, 15);
			contentPane.add(lblNewLabel);
			
			JLabel lblAddress = new JLabel("Address");
			lblAddress.setBounds(222, 194, 70, 15);
			contentPane.add(lblAddress);
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable jTable = (JTable) e.getSource();
				int row = jTable.getSelectedRow();
				int column = jTable.getSelectedColumn();
				// extract text of clicked cell
				String valueInCell="";
				try
				{
					valueInCell = (String) jTable.getValueAt(row, column);
				}
				catch (ArrayIndexOutOfBoundsException exc) {
					
				}
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					String query = "select * from restaurant where r_name = '" + valueInCell + "'";
					ResultSet rs = stmt.executeQuery(query);
					Restaurant rst;
					if (rs.next()) {
						rst = (Restaurant)new Restaurant(rs.getInt("r_id"), valueInCell, rs.getString("address"));
						// pass to restaurant page

						RestPage rp = new RestPage (user, rst, new Landing (user, "", ""), 0, "");
						rp.setVisible(true);
						dispose();
					}
					con.commit();
					con.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
