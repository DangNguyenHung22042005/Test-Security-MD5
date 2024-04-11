package run;

import javax.swing.UIManager;

import view.LoginView;
import view.SignupView;

public class StartProject {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new LoginView();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
