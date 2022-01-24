package com.toolstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.toolstore.database.DB;
import com.toolstore.database.IToolDAO;
import com.toolstore.database.IOrderDAO;
import com.toolstore.model.Tool;
import com.toolstore.model.Order;
import com.toolstore.model.OrderPosition;
import com.toolstore.service.IOrderService;
import com.toolstore.session.SessionObject;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import java.util.Optional;

public class OrderServiceOld implements IOrderService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IOrderDAO orderDAO;

    @Autowired
    IToolDAO toolDAO;

    @Override
    public void confirmOrder() {
        Order order = new Order(this.sessionObject.getUser(), new HashSet<>(this.sessionObject.getCart().getOrderPositions()));
        this.orderDAO.addOrder(order);
        for (OrderPosition orderPosition : order.getOrderPositions()) {
            Optional<Tool> toolBox = toolDAO.getToolById(orderPosition.getTool().getId());
            if(toolBox.isPresent()) {
                toolBox.get().setQuantity(toolBox.get().getQuantity() - orderPosition.getQuantity());
            }
        }
        this.sessionObject.getCart().clearOrderPositions();
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        return this.orderDAO.getOrdersByUserId(this.sessionObject.getUser().getId());
    }
}
