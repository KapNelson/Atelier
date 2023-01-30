package com.web.atelier.repo;

import com.web.atelier.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {
    List<News> findTop2ByOrderByPublicationDateDesc();

    Page<News> findAllByOrderByPublicationDateDesc(Pageable pageable);
}
