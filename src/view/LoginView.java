package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import encrypt.MD5;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginView extends JFrame implements ActionListener {
	private JTextField textField_Name;
	private JTextField textField_Password;
	boolean check_ten;
	boolean check_mk;
	
	Connection con;
	Statement stm;
	ResultSet rst;
	
	public LoginView() {
		init();
	}
	
	void init() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String url = "jdbc:sqlserver://HUNG\\SQLEXPRESS:1433;databaseName=TESTMD5";
			String userName = "sa";
			String password = "123456789";

			con = DriverManager.getConnection(url, userName, password);
			stm = con.createStatement();
			con.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		check_ten = false;
		check_mk = false;
		
		this.setTitle("Log in");
		this.setSize(451, 335);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel label_Name = new JLabel("Name\r\n");
		label_Name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		label_Name.setForeground(Color.GREEN);
		label_Name.setHorizontalAlignment(SwingConstants.CENTER);
		label_Name.setBounds(10, 43, 100, 42);
		getContentPane().add(label_Name);
		
		JLabel label_Password = new JLabel("Password\r\n");
		label_Password.setHorizontalAlignment(SwingConstants.CENTER);
		label_Password.setForeground(Color.GREEN);
		label_Password.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		label_Password.setBounds(10, 121, 100, 42);
		getContentPane().add(label_Password);
		
		textField_Name = new JTextField();
		textField_Name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_Name.setBounds(124, 43, 302, 42);
		getContentPane().add(textField_Name);
		textField_Name.setColumns(10);
		
		textField_Password = new JTextField();
		textField_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_Password.setColumns(10);
		textField_Password.setBounds(124, 121, 302, 42);
		getContentPane().add(textField_Password);
		
		JButton button_Login = new JButton("LOG IN");
		button_Login.setFont(new Font("Tahoma", Font.PLAIN, 25));
		button_Login.setForeground(Color.RED);
		button_Login.setBounds(10, 193, 416, 42);
		button_Login.addActionListener(this);
		getContentPane().add(button_Login);
		
		JButton button_Signup = new JButton("SIGN UP");
		button_Signup.setForeground(Color.RED);
		button_Signup.setFont(new Font("Tahoma", Font.PLAIN, 25));
		button_Signup.setBounds(10, 245, 416, 42);
		button_Signup.addActionListener(this);
		getContentPane().add(button_Signup);
		
		this.setVisible(true);
	}
	
	boolean check() {
		try {
			ResultSet rst = stm.executeQuery("SELECT *\r\n" + "FROM TAIKHOAN");

			ResultSetMetaData rstmeta = rst.getMetaData();
			int num_column = rstmeta.getColumnCount();

			String tenNguoiDungNhap = textField_Name.getText();
			String matKhauNguoiDungNhap = textField_Password.getText();
			String matKhauSauKhiMaHoa = MD5.encryptMD5(matKhauNguoiDungNhap);
			
			while (rst.next()) {
				for (int i = 1; i <= num_column; i++) {
					if (i == 1) {
						if (tenNguoiDungNhap.equals(rst.getString(i))) {
							check_ten = true;
						}
					}
					if (i == 2) {
						if (matKhauSauKhiMaHoa.equals(rst.getString(i))) {
							check_mk = true;
						}
					}
				}
				if (check_ten && check_mk) {
					return true;
				} else {
					check_ten = false;
					check_mk = false;
				}
			}
			rst.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("SIGN UP")) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new SignupView();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			String tenNguoiDungNhap = textField_Name.getText();
			String matKhauNguoiDungNhap = textField_Password.getText();
			String matKhauSauKhiMaHoa = MD5.encryptMD5(matKhauNguoiDungNhap);
			
			if (tenNguoiDungNhap.equals("") || matKhauNguoiDungNhap.equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (check()) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						new WellComeView();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Sai tên tài khoản hoặc mật khẩu!", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		}
	}
}
