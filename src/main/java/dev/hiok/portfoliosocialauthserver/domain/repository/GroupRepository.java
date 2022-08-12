package dev.hiok.portfoliosocialauthserver.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hiok.portfoliosocialauthserver.domain.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
}
