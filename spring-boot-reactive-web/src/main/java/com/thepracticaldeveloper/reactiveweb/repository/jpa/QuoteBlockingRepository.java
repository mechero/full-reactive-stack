package com.thepracticaldeveloper.reactiveweb.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteBlockingRepository extends JpaRepository<QuoteEntity, Long> {
    
    List<QuoteEntity> findAllByIdNotNullOrderByIdAsc(Pageable p);
}
