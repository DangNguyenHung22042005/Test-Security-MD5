package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class WellComeView extends JFrame {
	public WellComeView() {
		getContentPane().setLayout(null);
		this.setSize(500, 300);
		JLabel lblNewLabel = new JLabel("WELLCOME!\r\n");
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 60));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 436, 263);
		getContentPane().add(lblNewLabel);
		this.setVisible(true);
	}

}
