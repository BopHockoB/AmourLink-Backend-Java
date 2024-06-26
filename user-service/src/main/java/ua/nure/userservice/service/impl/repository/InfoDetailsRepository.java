package ua.nure.userservice.service.impl.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.nure.userservice.model.InfoDetails;
import ua.nure.userservice.model.compositePk.InfoDetailsKey;

import java.util.List;
import java.util.UUID;


@Repository
public interface InfoDetailsRepository extends JpaRepository<InfoDetails, InfoDetailsKey> {
//    List<InfoDetails> findByIdAnswerId(UUID answerId);
//    List<InfoDetails> findByIdInfoId(UUID infoId);
@Query("SELECT i " +
        "FROM InfoDetails i " +
        "JOIN FETCH i.profile p " +
        "WHERE i.infoDetailsId.userId = :userId")
    List<InfoDetails> findByIdUserId(@Param("userId") UUID userId);
}
