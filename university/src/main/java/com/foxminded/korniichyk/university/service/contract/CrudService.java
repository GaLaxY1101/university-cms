package com.foxminded.korniichyk.university.service.contract;

import org.springframework.data.domain.Page;

public interface CrudService<E,D> {

    D findById(Long id);
    void save(E e);
    void deleteById(Long id);

    Page<D> findPage(int pageNumber, int pageSize);

}
