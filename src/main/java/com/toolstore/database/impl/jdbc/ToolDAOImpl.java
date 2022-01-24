package com.toolstore.database.impl.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.toolstore.database.IToolDAO;
import com.toolstore.model.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToolDAOImpl implements IToolDAO {

    @Autowired
    Connection connection;

    @Override
    public List<Tool> getTools() {
        List<Tool> tools = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ttool";

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Tool tool = new Tool();
                tool.setId(rs.getInt("id"));
                tool.setTitle(rs.getString("title"));
                tool.setManufacturer(rs.getString("manufacturer"));
                tool.setPrice(rs.getDouble("price"));
                tool.setQuantity(rs.getInt("quantity"));
                tool.setArticle_number(rs.getString("article_number"));

                tools.add(tool);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return tools;
    }

    @Override
    public Optional<Tool> getToolById(int toolId) {
        try {
            String sql = "SELECT * FROM ttool WHERE id = ?";

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, toolId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Tool tool = new Tool();
                tool.setId(resultSet.getInt("id"));
                tool.setTitle(resultSet.getString("title"));
                tool.setManufacturer(resultSet.getString("manufacturer"));
                tool.setPrice(resultSet.getDouble("price"));
                tool.setQuantity(resultSet.getInt("quantity"));
                tool.setArticle_number(resultSet.getString("article_number"));

                return Optional.of(tool);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void updateTool(Tool tool) {
        try {
            String sql = "UPDATE ttool SET title = ?, manufacturer = ?, price = ?, article_number  = ?, quantity = ? WHERE id = ?";

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, tool.getTitle());
            preparedStatement.setString(2, tool.getManufacturer());
            preparedStatement.setDouble(3, tool.getPrice());
            preparedStatement.setString(4, tool.getArticle_number());
            preparedStatement.setInt(5, tool.getQuantity());
            preparedStatement.setInt(6, tool.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
