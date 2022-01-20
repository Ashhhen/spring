package com.toolstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.toolstore.database.DB;
import com.toolstore.model.Tool;
import com.toolstore.model.Order;
import com.toolstore.model.OrderPosition;
import com.toolstore.service.IOrderService;
import com.toolstore.session.SessionObject;
import java.util.List;

import javax.annotation.Resource;
import java.util.Optional;

public class OrderServiceOld implements IOrderService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    DB database;

    @Override
    public void confirmOrder() {
        Order order = new Order(this.sessionObject.getUser(), this.sessionObject.getCart().getOrderPositions());
        this.database.addOrder(order);
        for (OrderPosition orderPosition : order.getOrderPositions()) {
            Optional<Tool> toolBox = database.getToolById(orderPosition.getTool().getId());
            if(toolBox.isPresent()) {
                toolBox.get().setQuantity(toolBox.get().getQuantity() - orderPosition.getQuantity());
            }
        }
        this.sessionObject.getCart().clearOrderPositions();
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        return this.database.getOrdersByUserId(this.sessionObject.getUser().getId());
    }
}