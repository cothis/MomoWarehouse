package core.item;

import core.common.exception.EmptyListException;
import core.common.exception.ExitException;

import java.util.List;

public interface ItemController {
	void itemMenu(); //1.항목추가 2.항목삭제 3.항목조회 4.항목정보변경
	void create();
	void delete();
	void read();
	void update();

	// response body
	List<Item> findAll();
	Item selectItem() throws ExitException, EmptyListException;
}
