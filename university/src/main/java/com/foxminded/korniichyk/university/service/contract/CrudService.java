package com.foxminded.korniichyk.university.service.contract;

import org.springframework.data.domain.Page;

import java.util.List;

public interface CrudService<E,D> {

    D findById(Long id);
    List<D> findAll();
    void save(E e);
    void delete(Long id);

    Page<D> findPage(int pageNumber, int pageSize);

}
