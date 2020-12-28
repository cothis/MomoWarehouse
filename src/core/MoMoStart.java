package core;

import core.common.CommonView;

public class MoMoStart {
	public static void main(String[] args) {
		ApplicationConfig app = new ApplicationConfig();
		CommonView.logo();
		app.start();
	}
}
