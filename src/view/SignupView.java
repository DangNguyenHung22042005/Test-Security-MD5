package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import encrypt.MD5;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class SignupView extends JFrame implements ActionListener {
	private JTextField textField_Name;
	private JTextField textField_Password;

	Connection con;
	Statement stm;

	public SignupView() {
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
		this.setTitle("Log in");
		this.setSize(451, 279);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel label_Name = new JLabel("Name");
		label_Name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		label_Name.setForeground(Color.GREEN);
		label_Name.setHorizontalAlignment(SwingConstants.CENTER);
		label_Name.setBounds(10, 43, 100, 42);
		getContentPane().add(label_Name);

		JLabel label_Password = new JLabel("Password");
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

		JButton button_Signup = new JButton("SIGN UP");
		button_Signup.setForeground(Color.RED);
		button_Signup.setFont(new Font("Tahoma", Font.PLAIN, 25));
		button_Signup.setBounds(10, 193, 416, 42);
		button_Signup.addActionListener(this);
		getContentPane().add(button_Signup);
		

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new LoginView();
	}

	public void actionPerformed(ActionEvent e) {
		InsertData();
	}

	public void InsertData() {
		try {
			String tenNguoiDungNhap = textField_Name.getText();
			String matKhauNguoiDungNhap = textField_Password.getText();
			String matKhauSauKhiMaHoa = MD5.encryptMD5(matKhauNguoiDungNhap);
			if (tenNguoiDungNhap.equals("") || matKhauNguoiDungNhap.equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "ERROR", JOptionPane.ERROR_MESSAGE);
			} else {
				String sql = "INSERT INTO TAIKHOAN " + "VALUES (N'" + tenNguoiDungNhap + "', N'" + matKhauSauKhiMaHoa + "')";
				con.setAutoCommit(false);
				stm.executeUpdate(sql);
				con.commit();
				JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công!", "COMPLETE", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Tên tài khoản và mật khẩu này đã được đăng ký trước đó, Bạn có thể dùng nó để đăng nhập!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
