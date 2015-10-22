package br.com.file.service.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;

import br.com.file.service.model.Entidade;

public interface Service<E extends Entidade> extends Serializable {

    public E save(E entidade) throws Exception;

    public void remove(E entidade) throws Exception;
    
    public void removeBy(Long id) throws Exception;

    public E findBy(Long id);

    public List<E> findAll();

    List<E> buscarPorAtributosPreenchidos(E entidade);

    E buscaPorAtributosPreenchidos(E entidade);

    E obterPorCampo(Class cls, Serializable campo, Serializable valor) throws NoResultException;

    /**
     * Metodo para obter uma session.
     * 
     * @return Session
     */
    public org.hibernate.Session getSession();

    public List<E> readByQuery(CriteriaQuery<?> criteria, int initionPos, int finalPos);

    public Long totalOfQuery(CriteriaQuery<?> criteria);

    void refresh(E entidade);

    List<E> obterPor(Class cls, Serializable campo, Serializable valor);

    public List<E> getOrderByCampo(Class cls, Serializable campo, boolean ascending);

    public List<E> obterPorOrderByCampo(Class cls, Serializable obtain, Serializable value,
        Serializable order, boolean ascending);

    /**
     * Used for table pagination.
     * 
     * @param currentPage
     *            index
     * @param pageSize
     * @return A list with records of the specified page.
     */
    public List<E> paginate(int currentPage, int pageSize);

    /**
     * Count the total records of a table.
     * 
     * @return
     */
    public Long countRecords();

}
