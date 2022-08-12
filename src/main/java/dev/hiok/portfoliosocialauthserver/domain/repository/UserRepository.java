package dev.hiok.portfoliosocialauthserver.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.hiok.portfoliosocialauthserver.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u join fetch u.groups where u.email = :email")
	Optional<User> findByEmail(@Param("email") String email);

  boolean existsByEmail(String email);

}
