package br.com.file.service.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import br.com.file.service.model.Entidade;

public interface Repository<E extends Entidade> extends Serializable {

     E save(E entidade) throws Exception;

     void remove(E entidade) throws Exception;
    
     void removeBy(Long id) throws Exception;

     E findBy(Long id);

     List<E> findAll();

    List<E> buscarPorAtributosPreenchidos(E entidade);

    E buscaPorAtributosPreenchidos(E entidade);

    E obterPorCampo(Class cls, Serializable campo, Serializable valor) throws NoResultException;

     Session getSession();

     List<E> readByQuery(CriteriaQuery<?> criteria, Integer initionPos,
        Integer quantResultados);

     Long totalOfQuery(CriteriaQuery<?> criteria);

    void refresh(E entidade);

    List<E> obterPor(Class cls, Serializable campo, Serializable valor);

     List<E> getOrderByCampo(Class cls, Serializable campo);

     List<E> obterPorOrderByCampo(Class cls, Serializable obtain, Serializable value,
        Serializable order, boolean ascending);

     List<E> paginate(int currentPage, int pageSize);

     Long countRecords();

    String value();
}
