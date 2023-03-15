package dev.hiok.portfoliosocialauthserver.domain.repository;

import dev.hiok.portfoliosocialauthserver.domain.model.SocialId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialIdRepository extends JpaRepository<SocialId, Long> {

}
