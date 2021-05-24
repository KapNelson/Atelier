package com.web.atelier.repo;

import com.web.atelier.models.Pillow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PillowRepository extends CrudRepository<Pillow, Integer> {
}
