package core.spot;

import java.util.List;

public interface SpotController {
	void spotMenu();
	void create();
	void read();
	void update();
	void delete();

	// response body
	List<Spot> findAll();
}
