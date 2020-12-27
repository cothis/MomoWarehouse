package core;

import core.item.*;
import core.member.*;
import core.momoinfo.*;
import core.spot.*;

public class ApplicationConfig {
	private static final ItemDao itemDao = new ItemDao();
	private static final ItemView itemView = new ItemViewImpl();
	private static final ItemController itemController = new ItemControllerImpl(itemDao, itemView);

	private static final SpotDao spotDao = new SpotDao();
	private static final SpotView spotView = new SpotViewImpl();
	private static final SpotController spotController = new SpotControllerImpl(spotDao, spotView);

	private static final MomoInfoDao momoInfoDao = new MomoInfoDao();
	private static final MomoInfoView momoInfoView = new MomoInfoViewImpl();
	private static final MomoInfoController momoInfoController =
			new MomoInfoControllerImpl(itemController, momoInfoDao, momoInfoView);

	private static final MemberDao memberDao = new MemberDao();
	private static final MemberView memberView = new MemberViewImpl();
	private static final MemberController memberController =
			new MemberControllerImpl(memberView,
					memberDao,
					momoInfoController,
					itemController,
					spotController);

	public static MemberController getMemberController() {
		return memberController;
	}


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
