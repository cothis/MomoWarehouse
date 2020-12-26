package core.item;

import java.util.List;

public interface ItemController {
	void itemMenu(); //1.항목추가 2.항목삭제 3.항목조회 4.항목정보변경
	void create();
	void delete();
	List<Item> read();
	void update();
}
