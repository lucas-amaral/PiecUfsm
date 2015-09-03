package br.ufsm.inf.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.Order;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lucas on 29/09/2014.
 */
@Component
public class CadastroDao {
    // Injected database connection:
    @PersistenceContext
    private EntityManager em;

    @PersistenceContext(type= PersistenceContextType.TRANSACTION)
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Transactional
    public void saveObject(Object objeto) {
        em.persist(objeto);
    }

    @Transactional
    public void removeObject(Class classe, Object idObjeto) {
        em.remove(em.getReference(classe, idObjeto));
    }

    @Transactional
    public void atualizaObjeto(Object objeto) {
        em.merge(objeto);
    }

    @Transactional
    public void refresh(Object objeto) {
        em.refresh(objeto);
    }

    @Transactional
    public void flush() {
        em.flush();
    }

    @Transactional
    public List consulta(String consulta) {
        return (List) em.createQuery(consulta).getResultList();
    }


    public enum Ordem {
        DESC, ASC
    }

    private static Order getOrdem(Ordem ordem, String campo) {
        switch (ordem) {
            case DESC:
                return Order.desc(campo);
            case ASC:
                return Order.asc(campo);
        }
        return null;
    }

    public Object getObjeto(Class classe, Object id) {
        return em.find(classe, id);
    }

    public Blob criaBlob(byte[] blob) {
        return Hibernate.createBlob(blob);
    }

    public Connection getConnection() throws SQLException {
        Session session = (Session)em.getDelegate();
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        ConnectionProvider cp = sfi.getConnectionProvider();
        return cp.getConnection();
    }
}
