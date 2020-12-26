package core;

import core.item.*;
import core.member.MemberController;
import core.spot.*;

public class ApplicationConfig {
	private final ItemDao itemDao = new ItemDao();
	private final ItemView itemView = new ItemViewImpl();
	private final ItemController itemController = new ItemControllerImpl(itemDao, itemView);

	private final SpotDao spotDao = new SpotDao();
	private final SpotView spotView = new SpotViewImpl();
	private final SpotController spotController = new SpotControllerImpl(spotDao, spotView);


	private final MemberController memberController = null;
	
	public void start() {
		//memberController.indexMenu();
		spotController.spotMenu();
	}
}
