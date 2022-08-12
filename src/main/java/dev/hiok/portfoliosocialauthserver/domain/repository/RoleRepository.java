package dev.hiok.portfoliosocialauthserver.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hiok.portfoliosocialauthserver.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
