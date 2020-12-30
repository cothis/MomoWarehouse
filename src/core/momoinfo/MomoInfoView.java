package core.momoinfo;

import core.common.exception.ExitException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.DetailsOption;
import core.momoinfo.option.InOutOption;
import core.momoinfo.statistcs.TotalPayment;

import java.util.List;
import java.util.Optional;

public interface MomoInfoView {

    DetailsOption details();

    InOutOption inOut();

    void printList(List<MomoInfo> list, String header);

    Optional<Member> selectUser(List<Member> list) throws ExitException;

    Optional<Item> selectItem(List<Item> read) throws Exception;

    Optional<MomoInfo> selectOutItem(List<MomoInfo> inItems) throws ExitException;

    void printTotalPaymentStatistics(List<TotalPayment> list);
    void printMonthlyPaymentStatistics(List<TotalPayment> list, Member session);

    String staticMenu();
}
