package com.web.atelier.repo;

import com.web.atelier.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findTop3ByVerifiedOrderByIdDesc(boolean verified);
}
