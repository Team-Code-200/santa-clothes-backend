package io.wisoft.capstonedesign.domain.information.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InformationRepository extends JpaRepository<Information, Long> {

    List<Information> findByUser(final User user);
}
