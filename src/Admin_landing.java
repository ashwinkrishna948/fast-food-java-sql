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
import javax.swing.JButton;

public class Admin_landing extends JFrame {

	private JPanel contentPane;
	private static Admin_class admin_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_landing frame = new Admin_landing(admin_1);
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
	public Admin_landing(Admin_class admin) {
		admin_1=admin;
		int id=admin_1.ad_id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblRestaurantName = new JLabel("Restaurant Name :");
		lblRestaurantName.setBounds(87, 21, 133, 15);
		panel.add(lblRestaurantName);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(237, 21, 103, 15);
		panel.add(lblNewLabel);
		//lblNewLabel.setText(admin_1.r_name);
		
		JButton btnAddDishes = new JButton("Add Dishes");
		btnAddDishes.setBounds(59, 98, 141, 25);
		panel.add(btnAddDishes);
		
		JButton btnRemoveDishes = new JButton("Remove Dishes");
		btnRemoveDishes.setBounds(255, 98, 141, 25);
		panel.add(btnRemoveDishes);
		
		JButton btnUpdateDishes = new JButton("Update Dishes");
		btnUpdateDishes.setBounds(59, 159, 141, 25);
		panel.add(btnUpdateDishes);
		
		JButton btnShowMenu = new JButton("Show Menu");
		btnShowMenu.setBounds(255, 159, 141, 25);
		panel.add(btnShowMenu);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(325, 0, 89, 23);
		panel.add(btnLogout);
		
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// step2 create the connection object
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM",
				"16181618");
		// create statement'
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		String q="select r_name from restaurant where r_id = "+id;
		ResultSet rs= stmt.executeQuery(q);
		
		if(rs.next())
		{
			lblNewLabel.setText(rs.getString(1));
		}
		else
		 lblNewLabel.setText("No restaurant selected");
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		btnAddDishes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					add_dish new_f1 =new add_dish(admin_1);
					new_f1.setVisible(true);
					dispose();
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btnRemoveDishes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					remove_dish new_f1 =new remove_dish(admin_1);
					new_f1.setVisible(true);
					dispose();
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btnUpdateDishes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					update_dish new_f1 =new update_dish(admin_1);
					new_f1.setVisible(true);
					dispose();
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btnShowMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					show_menu new_f1 =new show_menu(admin_1);
					new_f1.setVisible(true);
					dispose();
				}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				try
				{
					admin_1.ad_id = 0;
					Home new_f1 =new Home();
					new_f1.setVisible(true);
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
