package ru.aosges.dao;

import org.hibernate.Session;

import java.util.List;

/**
 * Created by user on 10.03.2016.
 */
public class CommonDAO {

    private

    public QueryResult add(Object object) {
        int id;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            id = (Integer) session.save(object);
            session.getTransaction().commit();
        } catch (ExceptionInInitializerError e) {
            return new QueryResult(false, e.getMessage());
        }
        return new QueryResult(true, id);
    }

    public QueryResult update(Object object) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } catch (ExceptionInInitializerError e) {
            return new QueryResult(false, e.getMessage());
        }
        return new QueryResult(true, null);
    }

    public QueryResult delete(Object object) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (ExceptionInInitializerError e) {
            return new QueryResult(false, e.getMessage());
        }
        return new QueryResult(true, null);
    }

    public QueryResult getById(int id, Class objClass) {
        Object object;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            object = session.get(objClass, id);
        } catch (ExceptionInInitializerError e) {
            return new QueryResult(false, e.getMessage());
        }
        return new QueryResult(true, object);
    }

    public QueryResult listAll(Class objClass) {
        List<Object> list;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            list = session.createCriteria(objClass).list();
        } catch (ExceptionInInitializerError e) {
            return new QueryResult(false, e.getMessage());
        }
        return new QueryResult(true, list);
    }
}
