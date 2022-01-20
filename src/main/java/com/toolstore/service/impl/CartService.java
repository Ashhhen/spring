package com.toolstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.toolstore.database.DB;
import com.toolstore.model.Tool;
import com.toolstore.model.OrderPosition;
import com.toolstore.service.ICartService;
import com.toolstore.session.SessionObject;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    DB database;

    @Resource
    SessionObject sessionObject;

    public void addToolToCart(int toolId) {
        Optional<Tool> toolBox = this.database.getToolById(toolId);

        if(toolBox.isEmpty()) {
            return;
        }

        Tool tool = toolBox.get();
        if(!(tool.getQuantity() > 0)) {
            return;
        }

        for(OrderPosition orderPosition : this.sessionObject
                .getCart().getOrderPositions()) {
            if(orderPosition.getTool().getId() == toolId) {
                orderPosition.incrementQuantity();
                return;
            }
        }

        OrderPosition orderPosition = new OrderPosition(tool, 1);
        this.sessionObject.getCart().getOrderPositions().add(orderPosition);
    }
}