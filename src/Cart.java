import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class Cart extends JFrame {
	User user;
	Landing landing;
	Restaurant restaurant;
	int total;

	private JPanel contentPane;
	private JTable table;
	private JButton btnBack;
	private JButton btnOrder;
	private JLabel lblTotal;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Cart frame = new Cart();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void addRowToJTable(List<Order> list) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object rowData[] = new Object[4];
		for (int i = 0; i < list.size(); i++) {
			rowData[0] = list.get(i).d_name;
			rowData[1] = list.get(i).quantity;
			rowData[2] = list.get(i).perprice;
			rowData[3] = list.get(i).total;
			model.addRow(rowData);
		}

	}

	/**
	 * Create the frame.
	 */
	public Cart(User u, Landing l, Restaurant r) {
		user = u;
		landing = l;
		restaurant = r;
		total = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 488);
		contentPane.add(panel);
		panel.setLayout(null);

		table = new JTable();
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setBounds(42, 121, 358, 196);
		panel.add(table);
		try {

		int i;
		for (i = 0; i < landing.order.size(); i++)
			total += landing.order.get(i).total;

		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Id", "First Name", "Last Name", "Age" }));

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RestPage rp = new RestPage(user, restaurant, landing, 0, "");
				rp.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(32, 31, 67, 25);
		panel.add(btnBack);
		JLabel lblNewLabel = new JLabel("Dish");
		lblNewLabel.setBounds(41, 96, 31, 15);
		panel.add(lblNewLabel);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(134, 96, 61, 15);
		panel.add(lblQuantity);
		
		JLabel lblPriceunit = new JLabel("Price/Unit");
		lblPriceunit.setBounds(228, 96, 68, 15);
		panel.add(lblPriceunit);
		
		JLabel lblNewLabel_1 = new JLabel("Subtotal");
		lblNewLabel_1.setBounds(319, 96, 70, 15);
		panel.add(lblNewLabel_1);

		btnOrder = new JButton("Place Order");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH.mm.ss");
					// Date date = new Date();
					String date = dateFormat.format(new Date());
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					Statement stmt = con.createStatement();
					/*
					ResultSet rs = stmt.executeQuery("select max (o_id) as o_id from orders");
					int oid = 1;
					if (rs.next())
						oid = rs.getInt("o_id") + 1;
						*/
					//procedure to find order id
					String callProc = "{call insertOrders (?, ?, ?, ?, ?)}";
					CallableStatement cs = con.prepareCall(callProc);
					cs.setInt (1, total);
					cs.setString(2, user.phone_num);
					cs.setInt(3, restaurant.id);
					cs.setString(4, date);
					cs.registerOutParameter(5, java.sql.Types.NUMERIC);
					cs.executeUpdate();
					int oid = cs.getInt (5);
					//String query = "insert into orders values (" + oid + "," + total + ",'" + user.phone_num + "',"+ restaurant.id + ",to_timestamp('" + date + "', 'yyyy/MM/dd HH24.mi.ss'))";
					//ResultSet rs = stmt.executeQuery(query);
					// insert to order_has_dishes now

					Order o;
					for (int i = 0; i < landing.order.size(); i++) {
						o = landing.order.get(i);
						/*
						rs = stmt.executeQuery("select max(ohd_id) as ohd_id from order_has_dishes");
						int ohdid = 1;
						if (rs.next())
							ohdid = rs.getInt("ohd_id") + 1;
						*/
						callProc = "{call insertOrderHasDishes (?,?,?)}";
						cs = con.prepareCall(callProc);
						cs.setInt(1, oid);
						cs.setInt(2,o.d_id);
						cs.setInt(3, o.quantity);
						cs.executeUpdate();
						//String query = "insert into order_has_dishes values (" + ohdid + "," + oid + "," + o.d_id + ","
								//+ o.quantity + ")";
						//ResultSet rs = stmt.executeQuery(query);
					}
					Dialog d = new Dialog ("Order placed!");
					d.setVisible(true);
					landing.order.clear();
					con.close();
					con.close();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		}
		catch (NullPointerException e) {
			Dialog d = new Dialog ("Your cart is empty!");
			d.setVisible(true);
		}
		btnOrder.setBounds(162, 433, 117, 25);
		panel.add(btnOrder);

		lblTotal = new JLabel(Integer.toString(total));
		lblTotal.setBounds(344, 355, 70, 15);
		panel.add(lblTotal);
		
		lblNewLabel = new JLabel("Total");
		lblNewLabel.setBounds(252, 355, 70, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Cart");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel_2.setBounds(183, 31, 126, 46);
		panel.add(lblNewLabel_2);
		addRowToJTable(landing.order);

	}
}
