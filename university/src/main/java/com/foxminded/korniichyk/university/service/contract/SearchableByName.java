package com.foxminded.korniichyk.university.service.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchableByName<D> {

    Page<D> findByName(String search, Pageable pageable);


}
