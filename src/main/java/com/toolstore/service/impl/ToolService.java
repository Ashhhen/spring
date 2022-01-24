package com.toolstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.toolstore.database.DB;
import com.toolstore.database.IToolDAO;
import com.toolstore.model.Tool;
import com.toolstore.service.IToolService;

import java.util.List;

@Service
public class ToolService implements IToolService {

    @Autowired
    IToolDAO toolDAO;

    public List<Tool> getAllTools() {
        return this.toolDAO.getTools();
    }
}
