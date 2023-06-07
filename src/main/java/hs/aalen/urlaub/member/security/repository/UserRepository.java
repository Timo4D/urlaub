package hs.aalen.urlaub.member.security.repository;


import org.springframework.data.repository.CrudRepository;

import hs.aalen.urlaub.member.security.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByUsername(String username);

}