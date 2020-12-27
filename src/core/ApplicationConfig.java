package core;

import core.item.*;
import core.member.*;
import core.momoinfo.*;
import core.spot.*;

public class ApplicationConfig {
	private final ItemDao itemDao = new ItemDao();
	private final ItemView itemView = new ItemViewImpl();
	private final ItemController itemController = new ItemControllerImpl(itemDao, itemView);

	private final SpotDao spotDao = new SpotDao();
	private final SpotView spotView = new SpotViewImpl();
	private final SpotController spotController = new SpotControllerImpl(spotDao, spotView);

	private final MomoInfoDao momoInfoDao = new MomoInfoDao();
	private final MomoInfoView momoInfoView = new MomoInfoViewImpl();
	private final MomoInfoController momoInfoController =
			new MomoInfoControllerImpl(itemController, momoInfoDao, momoInfoView);

	MemberDao memberDao = new MemberDao();
	MemberView memberView = new MemberViewImpl(memberDao);
	private final MemberController memberController =
			new MemberControllerImpl(memberView,
					memberDao,
					momoInfoController,
					itemController,
					spotController);
	
	public void start() {
//		itemController.itemMenu();
//		spotController.spotMenu();
//		Member admin = new Member("admin", "admin", "admin", "123", "123", 0, "ADMIN", 0);
//		Member user = new Member("MINHO", "1234", "YUNMINHO", "010-2232-2342", "123", 2005, "USER", 10000);
//		momoInfoController.inOutMenu(user);
//		momoInfoController.inOutHistory(admin);
		memberController.indexMenu();
	}
}
