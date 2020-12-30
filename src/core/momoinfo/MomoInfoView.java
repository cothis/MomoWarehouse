package core.momoinfo;

import core.common.exception.ExitException;
import core.common.exception.NoIncomingException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.DetailsOption;
import core.momoinfo.option.InOutOption;
import core.momoinfo.statistcs.TotalPayment;

import java.util.List;
import java.util.Optional;

public interface MomoInfoView {

    InOutOption inOut();

    DetailsOption details();

    String statisticsMenu();

    // 하위 기능들
    void printList(List<MomoInfo> list, String header);

    Optional<Member> selectUser(List<Member> list) throws ExitException;

    MomoInfo selectOutItem(List<MomoInfo> inItems) throws ExitException, NoIncomingException;

    void printTotalPaymentStatistics(List<TotalPayment> list);

    void printMonthlyPaymentStatistics(List<TotalPayment> list, Member session);

    String selectYear(List<String> yearList);
}
