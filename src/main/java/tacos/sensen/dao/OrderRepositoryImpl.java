package tacos.sensen.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.sensen.model.Order;
import tacos.sensen.model.Taco;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Repository
public class OrderRepositoryImpl implements OrderRepository{
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("taco_order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("taco_order_tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        String format = now.format(dateTimeFormatter);
        order.setPlacedAt(format);
        long orderId = saveOrderDetails(order);
        // 保存订单和taco关联信息
        saveTacoToOrder(order.getDesign(), orderId);
        return null;
    }

    @Override
    public long saveOrderDetails(Order order) {
        Map values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());
        // 获取订单id
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    @Override
    public void saveTacoToOrder(Taco taco, long orderId) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
