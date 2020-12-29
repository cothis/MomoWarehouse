package core.momoinfo;

import core.common.exception.ExitException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.HistoryOption;
import core.momoinfo.option.InOutOption;

import java.util.List;
import java.util.Optional;

public interface MomoInfoView {

    HistoryOption history();

    InOutOption inOut();

    void printList(List<MomoInfo> list);

    Optional<Member> selectUser(List<Member> list) throws ExitException;

    Optional<Item> selectItem(List<Item> read) throws Exception;

    Optional<MomoInfo> selectOutItem(List<MomoInfo> inItems);
}
