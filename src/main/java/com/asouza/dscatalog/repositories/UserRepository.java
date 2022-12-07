package com.asouza.dscatalog.repositories;

import com.asouza.dscatalog.entities.Product;
import com.asouza.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
