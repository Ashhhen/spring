package com.toolstore.database;

import com.toolstore.model.Tool;

import java.util.List;
import java.util.Optional;

public interface IToolDAO {
    List<Tool> getTools();
    Optional<Tool> getToolById(int toolId);
    void updateTool(Tool tool);
}
