package com.toolstore.database;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import com.toolstore.model.Tool;
import com.toolstore.model.Order;
import com.toolstore.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DB {
    private List<Tool> tools = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public DB() {
        this.tools.add(
                new Tool(1,
                        "MŁOTEK CIESIELSKI DEKARSKI FIBERGLASS 600g",
                        "MAAN, Polska",
                        32.00,
                        "590-532431-621-5",
                        15));

        this.tools.add(new Tool(2,
                "ZESTAW ŚRUBOKRĘTÓW Z WKRĘTAKIEM 24w1",
                "Xiaomi, Chiny",
                78.20,
                "590-522371-341-3",
                20));

        this.tools.add(new Tool(3,
                "PŁATNICA DO DREWNA 500mm",
                "VERKE, Polska",
                19.00, "590-539821-571-2",
                10));

        this.tools.add(new Tool(4,
                "SIEKIERA FIBERGLASS 600g",
                "Tolsen, Chiny",
                47.90, "590-250534-521-7",
                15));

        this.tools.add(new Tool(5,
                "MIARA ZWIJANA 5m",
                "Jobi, Niemcy",
                8.00, "590-835140-329-6",
                20));

        this.tools.add(new Tool(6,
                "SZCZYPCE UNIWERSALNE 160mm",
                "JUFISTO, Chiny",
                21.00, "590-945123-379-4",
                15));

        this.users.add(new User(1, "Oleksandr", "Liubich", "admin", DigestUtils.md5Hex("admin")));
        this.users.add(new User(2, "Olek", "Kowalski", "user", DigestUtils.md5Hex("user")));
    }

    public List<Tool> getTools() {
        return tools;
    }

    public Optional<User> getUserByLogin(String login) {
        for(User user : this.users) {
            if(user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public Optional<Tool> getToolById(int toolId) {
        for(Tool tool : this.tools) {
            if(tool.getId() == toolId) {
                return Optional.of(tool);
            }
        }

        return Optional.empty();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> result = new ArrayList<>();
        for(Order order : this.orders) {
            if(order.getUser().getId() == userId) {
                result.add(order);
            }
        }

        return result;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}