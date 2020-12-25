package core.item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.common.CommonJdbc.*;

public class ItemDao {

    public void addItem(Item item) {
        String sql = "insert into ITEM(ITEM_ID, NAME, PRICE_BY_HOUR)" +
                " values(ITEM_ID_SEQ.nextval, ?, ?";
        try {
            connect();
            PreparedStatement pstmt = getPreparedStatement(sql);
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getPriceByHour());
            execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    private List<Item> parseItemList(ResultSet rs) {
        List<Item> list = new ArrayList<>();

        try {
            while(rs.next()) {
                int item_id = rs.getInt("ITEM_ID");
                String name = rs.getString("NAME");
                int price_by_hour = rs.getInt("PRICE_BY_HOUR");
                Item item = new Item(item_id, name, price_by_hour);
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Item selectById(int id) {
        Item result = null;
        String sql = "select ITEM_ID, NAME, PRICE_BY_HOUR" +
                " from ITEM" +
                " where ITEM_ID = ?";

        try {
            connect();
            getPreparedStatement(sql).setInt(1, id);

            ResultSet rs = executeQuery();

            List<Item> items = parseItemList(rs);
            if(items.size() > 0) {
                result = items.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public List<Item> selectAll() {
        List<Item> result = null;
        String sql = "select ITEM_ID, NAME, PRICE_BY_HOUR" +
                " from ITEM";

        try {
            connect();

            getPreparedStatement(sql);

            ResultSet rs = executeQuery();

            result = parseItemList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }

    public void delete(Item item) {

    }

    public void update(Item item) {

    }
}
