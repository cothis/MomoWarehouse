package core;

import core.item.*;
import core.member.MemberController;

public class ApplicationConfig {
	private final ItemDao itemDao = new ItemDao();
	private final ItemView itemView = new ItemViewImpl();
	private final ItemController itemController = new ItemControllerImpl(itemDao, itemView);


	private final MemberController memberController = null;
	
	public void start() {
		//memberController.indexMenu();
		itemController.itemMenu();
	}
}
