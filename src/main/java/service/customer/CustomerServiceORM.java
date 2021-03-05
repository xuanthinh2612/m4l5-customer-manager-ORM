package service.customer;

import model.Customer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
//import org.hibernate.tutorial.util.HibernateUtil;
//import org.hibernate.

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerServiceORM implements ICustomerService {
    private Session session = null;
    private Transaction transaction = null;

    @Autowired
    public SessionFactory sessionFactory;

    @Autowired
    public EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
        String query = "SELECT c FROM Customer  AS c";
        TypedQuery<Customer> query1 = sessionFactory.openSession().createQuery(query, Customer.class);
        return query1.getResultList();
    }

    @Override
    public Customer findById(int id) {
        String query = " SELECT c FROM Customer AS c WHERE c.id= :id";
        TypedQuery<Customer> query1 = entityManager.createQuery(query, Customer.class);
        query1.setParameter("id", id);

        return query1.getSingleResult();
    }

    @Override
    public void create(Customer customer) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(Customer customer) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    @Override
    public void delete(int id) {
        Customer customer = this.findById(id);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
