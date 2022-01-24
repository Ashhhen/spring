package com.toolstore.service.impl;

import com.toolstore.model.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.toolstore.database.DB;
import com.toolstore.database.IToolDAO;
import com.toolstore.model.OrderPosition;
import com.toolstore.service.ICartService;
import com.toolstore.session.SessionObject;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    IToolDAO toolDAO;

    @Resource
    SessionObject sessionObject;

    public void addToolToCart(int toolId) {
        Optional<Tool> toolBox = this.toolDAO.getToolById(toolId);

        if(toolBox.isEmpty()) {
            return;
        }

        Tool tool = toolBox.get();
        if(tool.getQuantity() <= 0) {
            return;
        }

        for(OrderPosition orderPosition : this.sessionObject
                .getCart().getOrderPositions()) {
            if(orderPosition.getTool().getId() == toolId) {
                orderPosition.incrementQuantity();
                return;
            }
        }

        OrderPosition orderPosition = new OrderPosition(0, tool, 1);
        this.sessionObject.getCart().getOrderPositions().add(orderPosition);
    }
}
