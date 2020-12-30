package core;

import core.common.CommonView;
import core.item.*;
import core.member.*;
import core.memberlog.MemberLogController;
import core.memberlog.MemberLogControllerImpl;
import core.memberlog.MemberLogDao;
import core.memberlog.MemberLogView;
import core.memberlog.MemberLogViewImpl;
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
	private static final MomoInfoController momoInfoController = new MomoInfoControllerImpl(momoInfoDao, momoInfoView);

	private static final MemberDao memberDao = new MemberDao();
	private static final MemberView memberView = new MemberViewImpl();
	private static final MemberController memberController = new MemberControllerImpl(memberView, memberDao);
	
	private static final MemberLogDao memberLogDao = new MemberLogDao();
	private static final MemberLogView memberLogView = new MemberLogViewImpl();
	private static final MemberLogController memberLogController = new MemberLogControllerImpl(memberLogDao, memberLogView);

	private ApplicationConfig() {}

	public static MemberController getMemberController() {
		return memberController;
	}
	
	public static MemberLogController getMemberLogController() {
		return memberLogController;
	}

	public static MomoInfoController getMomoInfoController() {
		return momoInfoController;
	}

	public static SpotController getSpotController() {
		return spotController;
	}

	public static ItemController getItemController() {
		return itemController;
	}

	public static void start() {
		CommonView.logo();
		memberController.indexMenu();
	}
}
