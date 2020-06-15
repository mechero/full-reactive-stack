package com.thepracticaldeveloper.reactiveweb.repository;

import java.util.List;

import com.thepracticaldeveloper.reactiveweb.domain.Quote;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuoteMongoBlockingRepository extends PagingAndSortingRepository<Quote, String> {

    List<Quote> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
