package core;

import core.item.*;
import core.member.Member;
import core.member.MemberController;
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
			new MomoInfoControllerImpl(spotController, itemController, momoInfoDao, momoInfoView);

	private final MemberController memberController = null;
	
	public void start() {
//		memberController.indexMenu();
//		spotController.spotMenu();
		Member admin = new Member("admin", "admin", "admin", "123", "123", 0, "ADMIN", 0);
		Member user = new Member("user", "user", "user", "123", "123", 2004, "USER", 10000);
		momoInfoController.inOutHistory(admin);
	}
}
