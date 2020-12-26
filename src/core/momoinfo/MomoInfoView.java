package core.momoinfo;

import core.member.Member;

import java.util.List;
import java.util.Optional;

public interface MomoInfoView {

    String history();

    void printList(List<MomoInfo> list);

    Optional<Member> selectUser(List<Member> list);
}
