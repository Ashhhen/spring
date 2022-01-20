package com.toolstore.model;

public class OrderPosition {
    private Tool tool;
    private int quantity;

    public OrderPosition(Tool tool, int quantity) {
        this.tool = tool;
        this.quantity = quantity;
    }

    public OrderPosition() {
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
