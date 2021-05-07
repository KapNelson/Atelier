package com.web.atelier.repo;

import com.web.atelier.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
