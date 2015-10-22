package br.com.file.service.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import br.com.file.service.model.Entidade;
import br.com.file.service.repository.Repository;

public class ServiceImpl<E extends Entidade> implements Service<E> {

    private Repository<E> repository;

    public Repository<E> getRepository() {
        return repository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public E save(E entidade) throws Exception {
        return getRepository().save(entidade);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(E entidade) throws Exception {
        getRepository().remove(entidade);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBy(Long id) throws Exception {
    	getRepository().removeBy(id);
    }

    @Override
    @Transactional()
    public E findBy(Long id) {
        return getRepository().findBy(id);
    }

    @Override
    @Transactional()
    public List<E> findAll() {
        return getRepository().findAll();
    }

    @Override
    @Transactional()
    public List<E> buscarPorAtributosPreenchidos(E entidade) {
        return getRepository().buscarPorAtributosPreenchidos(entidade);
    }

    @Override
    @Transactional()
    public E buscaPorAtributosPreenchidos(E entidade) {
        return getRepository().buscaPorAtributosPreenchidos(entidade);
    }

    @Override
    @Transactional()
    public E obterPorCampo(Class cls, Serializable campo, Serializable valor)
            throws NoResultException {
        return getRepository().obterPorCampo(cls, campo, valor);
    }

    @Override
    public org.hibernate.Session getSession() {
        return getRepository().getSession();
    }

    @Override
    public List<E> readByQuery(CriteriaQuery<?> criteria, int initionPos, int finalPos) {
        return getRepository().readByQuery(criteria, initionPos, finalPos);
    }

    @Override
    public Long totalOfQuery(CriteriaQuery<?> criteria) {
        return getRepository().totalOfQuery(criteria);
    }

    @Override
    public void refresh(E entidade) {
        getRepository().refresh(entidade);
    }

    @Override
    public List<E> obterPor(Class cls, Serializable campo, Serializable valor) {
        return getRepository().obterPor(cls, campo, valor);
    }

    @Override
    public List<E> getOrderByCampo(Class cls, Serializable campo, boolean ascending) {
        return getRepository().getOrderByCampo(cls, campo);
    }

    @Override
    public List<E> obterPorOrderByCampo(Class cls, Serializable obtain, Serializable value,
        Serializable order, boolean ascending) {
        return getRepository().obterPorOrderByCampo(cls, obtain, value, order, ascending);
    }

    @Override
    public List<E> paginate(int currentPage, int pageSize) {
        return getRepository().paginate(currentPage, pageSize);
    }

    @Override
    public Long countRecords() {
        return getRepository().countRecords();
    }

}
