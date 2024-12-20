package com.rentacar.repository;

import com.rentacar.model.Userlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Userlist, Long> {
    Userlist findByUsername(String username);
}
