package com.toolstore.database.impl.hibernate;

import com.toolstore.database.IToolDAO;
import com.toolstore.model.Tool;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class ToolDAOImpl implements IToolDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Tool> getTools() {
        Session session = this.sessionFactory.openSession();
        Query<Tool> query = session.createQuery("FROM com.toolstore.model.Tool");
        List<Tool> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Optional<Tool> getToolById(int toolId) {
        Session session = this.sessionFactory.openSession();
        Query<Tool> query = session.createQuery("FROM com.toolstore.model.Tool WHERE id = :id");
        query.setParameter("id", toolId);
        try {
            Tool book = query.getSingleResult();
            session.close();
            return Optional.of(book);
        } catch (NoResultException e) {
            session.close();
            return Optional.empty();
        }
    }

    @Override
    public void updateTool(Tool tool) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(tool);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
