package tacos.sensen.dao;

import tacos.sensen.model.Order;
import tacos.sensen.model.Taco;

public interface  OrderRepository {
    Order save(Order order);
    long saveOrderDetails(Order order);
    void saveTacoToOrder(Taco taco, long orderId);

}
