package br.com.file.service.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.file.service.model.Entidade;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

public abstract class RepositoryImpl<E extends Entidade> implements Repository<E> {

    private Class<E> persistentClass = null;

    protected EntityManager entityManager;

    protected Root<E> entityRoot;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEntityManager(EntityManager entityManagerArg) {
        entityManager = entityManagerArg;

    }

    public E save(E entidade) throws Exception {
        entidade = this.entityManager.merge(entidade);
        return entidade;
    }

    @Override
    public void remove(E entidade) throws Exception {
        entidade = findBy(new Long(String.valueOf(entidade.getId())));
        this.entityManager.remove(entidade);
    }
    
    @Override
    public void removeBy(Long id) throws Exception {
        E entidade = findBy(id);
        this.entityManager.remove(entidade);
    }

    @Override
    public E findBy(Long id) {
        return entityManager.find(getPersistentClass(), id);
    }

    @SuppressWarnings("unchecked")
    public Class<E> getPersistentClass() {
        if (persistentClass == null) {
            this.persistentClass = (Class<E>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return persistentClass;
    }

    @Override
    public List<E> findAll() {
        CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        this.persistentClass = getPersistentClass();
        CriteriaQuery<E> c = (CriteriaQuery<E>) qb.createQuery(persistentClass);
        c.from(persistentClass);
        Query query = this.entityManager.createQuery(c);
        return query.getResultList();
    }

    @Override
    public Session getSession() {
        Session session = (Session) this.entityManager.getDelegate();
        return session;
    }

    @Override
    public List<E> buscarPorAtributosPreenchidos(E entidade) {
        Session session = (Session) entityManager.getDelegate();
        Criteria criteria = session.createCriteria(getPersistentClass());
        List<E> lista = criteria.add(Example.create(entidade).ignoreCase()).list();
        return lista;
    }

    @Override
    public E buscaPorAtributosPreenchidos(E entidade) {
        List<E> list = buscarPorAtributosPreenchidos(entidade);
        return (!list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    public E obterPorCampo(Class cls, Serializable campo, Serializable valor)
            throws NoResultException {

        Query qry = entityManager.createQuery("select o from " + cls.getSimpleName()
                + " o where o." + campo + " =:nomeParam");
        qry.setParameter("nomeParam", valor);
        try {
            Entidade entidade = (Entidade) qry.getSingleResult();
            return (E) entidade;
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return (E) qry.getResultList().get(0);
        }

    }

    @Override
    public List<E> obterPor(Class cls, Serializable campo, Serializable valor) {
        Query qry = entityManager.createQuery("select o from " + cls.getSimpleName()
                + " o where o." + campo + " =:nomeParam");
        qry.setParameter("nomeParam", valor);
        List<E> lista = qry.getResultList();
        return lista;
    }

    @Override
    public List<E> readByQuery(CriteriaQuery<?> criteria, Integer initionPos,
        Integer quantResultados) {
        entityManager.clear();
        Query query = entityManager.createQuery(criteria);
        query.setFirstResult(initionPos);
        query.setMaxResults(quantResultados);
        query.setHint("hibernate.cache.use_query_cache", false);
        query.setHint("hibernate.cache.use_second_level_cache", false);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Long totalOfQuery(CriteriaQuery<?> criteria) {
        criteria.orderBy();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery countCriteria = criteria;
        countCriteria.select(builder.count(entityRoot));
        Query query = entityManager.createQuery(countCriteria);
        Long result = (Long) query.getSingleResult();
        return result;
    }

    @Override
    public void refresh(E entidade) {
        if (entidade != null) {
            entityManager.refresh(entidade);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected CriteriaQuery<E> getCriteria() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        entityRoot = criteria.from(getPersistentClass());
        entityRoot.alias("e1");
        criteria.select(entityRoot);
        return criteria;
    }

    protected Root<E> getEntityRoot() {
        return entityRoot;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> getOrderByCampo(@SuppressWarnings("rawtypes") Class cls, Serializable campo) {
        return (List<E>) entityManager.createQuery(
                "SELECT x FROM " + cls.getSimpleName() + " x ORDER BY x." + campo + " DESC")
                .getResultList();
    }

    @Override
    public List<E> obterPorOrderByCampo(Class cls, Serializable obtain, Serializable value,
        Serializable order, boolean ascending) {
        Query qry = entityManager.createQuery("select o from " + cls.getSimpleName()
                + " o where o." + obtain + " =:nomeParam " + "ORDER BY " + order
                + (ascending ? " ASC" : " DESC"));
        qry.setParameter("nomeParam", value);
        return qry.getResultList();
    }

    @Override
    public List<E> paginate(int currentPage, int pageSize) {
        TypedQuery<E> typedQuery = entityManager.createQuery(getCriteria());
        typedQuery.setFirstResult(currentPage);
        typedQuery.setMaxResults(pageSize);
        List<E> list = typedQuery.getResultList();
        return list;
    }

    @Override
    public Long countRecords() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery countCriteria = this.getCriteria();
        countCriteria.select(builder.count(this.getEntityRoot()));
        Long result = (Long) entityManager.createQuery(countCriteria).getSingleResult();
        return result;
    }

}
