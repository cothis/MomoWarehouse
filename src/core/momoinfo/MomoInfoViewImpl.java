package core.momoinfo;

import core.common.InputValidator;
import core.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MomoInfoViewImpl implements MomoInfoView {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public String history() {
        System.out.println("입출고 내역");

        return InputValidator.inputUserChoice("입고내역", "출고내역", "전체보기", "종료");
    }

    @Override
    public void printList(List<MomoInfo> list) {
        if (list == null) {
            System.out.println("내역이 없습니다.");
            return;
        }
        for (MomoInfo momoInfo : list) {
            System.out.println(momoInfo);
        }
    }

    @Override
    public Optional<Member> selectUser(List<Member> list) {
        if (list == null || list.size() == 0) {
            System.out.println("저장된 유저가 없습니다.");
            return Optional.empty();
        }
        while (true) {
            System.out.println("확인할 유저의 행 번호를 선택해주세요.");

            int i = 1;
            for (Member member : list) {
                System.out.printf("%d. id: %s, name: %s%n", i + 1, member.getMemberId(), member.getName());
                i++;
            }
            System.out.printf("%d. 전체보기", i);

            try {
                int rowNum = Integer.parseInt(sc.nextLine());
                if(rowNum == i) {
                    return Optional.empty();
                } else {
                    return Optional.of(list.get(rowNum - 1));
                }
            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }
}
