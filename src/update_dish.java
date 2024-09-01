import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class update_dish extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	public static Admin_class admin_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					update_dish frame = new update_dish(admin_1);
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
	public update_dish(Admin_class admin_1) {

		this.admin_1 = admin_1;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblDishId = new JLabel("Dish Id");
		lblDishId.setBounds(109, 37, 73, 14);
		panel.add(lblDishId);

		textField = new JTextField();
		textField.setBounds(222, 35, 86, 20);
		panel.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(127, 191, 181, 14);
		panel.add(label);

		JLabel lblNewPrice = new JLabel("New Price");
		lblNewPrice.setBounds(110, 121, 70, 15);
		panel.add(lblNewPrice);

		textField_1 = new JTextField();
		textField_1.setBounds(222, 118, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(167, 156, 89, 23);
		panel.add(btnUpdate);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(311, 217, 89, 23);
		panel.add(btnBack);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(325, 0, 89, 23);
		panel.add(btnLogout);

		JLabel lblNewDishName = new JLabel("New Dish Name");
		lblNewDishName.setBounds(110, 78, 110, 15);
		panel.add(lblNewDishName);

		textField_2 = new JTextField();
		textField_2.setBounds(222, 75, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try {

					int admin_id1 = admin_1.ad_id;
					String dish_id = textField.getText();
					String new_dish_name = textField_2.getText();
					String p = textField_1.getText();
					Class.forName("oracle.jdbc.driver.OracleDriver");
					// step2 create the connection object
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
							"16181618");
					// System.out.println("helo");
					// create statement'
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					// String q = "update dish set price = "+ p + " where d_name = '"+ dish_name +
					// "' and r_id = " + admin_id1;

					// System.out.println(q);
					// con.commit();
					// ResultSet rs= stmt.executeQuery(q);
					// System.out.println(q);
					// System.out.println("hello1");
					try {
						
						if (dish_id.equals(""))
							throw new Invalid_Dish_Id("Enter Dish Id!");
						String k = "select * from dish where d_id =" + dish_id + " and r_id = " + admin_id1;
						ResultSet rs2 = stmt.executeQuery(k);
						if (!rs2.next())
						{
							throw new Invalid_Dish_Id("Not your dish!");
						}
						if ((!new_dish_name.equals("")) && (!p.equals("")))
							throw new Invalid_Dish_Id("Enter New Dish Name/Price");
						if (!p.equals("")) {
							int p1 = Integer.parseInt(p);
							int d1 = Integer.parseInt (dish_id);
							String q = "update dish set price = " + p1 + " where d_id = " + d1 + " and r_id = "
									+ admin_id1;
							ResultSet rs = stmt.executeQuery(q);
							label.setText("Updation Successful");
						}

						if (!new_dish_name.equals("")) {
							int d1 = Integer.parseInt (dish_id);
							String q = "update dish set d_name = '" + new_dish_name + "' where d_id = " + dish_id
									+ " and r_id = " + admin_id1;
							ResultSet rs = stmt.executeQuery(q);
							label.setText("Updation Successful");
						}

					} catch (Invalid_Dish_Id e) {
						System.out.println (e.toString());
						label.setText(e.toString());
					}
					/*
					 * String k = "select * from dish where d_id ='"+ dish_id +"'"; ResultSet rs2 =
					 * stmt.executeQuery(k); try{ System.out.println(k); if(!rs2.next()) throw new
					 * Invalid_Dish_Id("Enter valid Dish Id!");
					 * 
					 * } catch(Invalid_Dish_Id e) { label.setText(e.toString()); }
					 */

					
					con.commit();
					con.close();
				}

				catch (Exception e) {
					label.setText("Updation Unsuccessful");
					e.printStackTrace();
				}
			}
		});

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try {
					Admin_landing new_frame2 = new Admin_landing(admin_1);
					new_frame2.setVisible(true);
					dispose();
				}

				catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try {
					admin_1.ad_id = 0;
					Home new_f1 = new Home();
					new_f1.setVisible(true);
					dispose();
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
