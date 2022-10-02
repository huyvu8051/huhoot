package com.huhoot.repository;

import com.huhoot.enums.ChallengeStatus;
import com.huhoot.dto.ChallengeResponse;
import com.huhoot.model.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {


    @Query("SELECT new com.huhoot.dto.ChallengeResponse(n.id, n.title, n.challengeStatus, n.customer.username, n.customer.socketId, n.createdDate, n.createdBy, " +
            "n.modifiedDate, n.modifiedBy) " +
            "FROM Challenge n " +
            "WHERE n.customer.id = :adminId")
    Page<ChallengeResponse> findAllByAdminId(@Param("adminId") int adminId, Pageable pageable);

    Page<Challenge> findAll(Pageable pageable);


    /**
     * @param challengeId {@link Challenge} id
     * @return Challenge
     */
    Optional<Challenge> findOneById(int challengeId);

    /**
     * Update challenge status
     *
     * @param challengeStatus challenge status
     * @param challengeId     challenge id
     */
    @Modifying
    @Transactional
    @Query("UPDATE Challenge c " +
            "SET c.challengeStatus = :challengeStatus " +
            "WHERE c.id =:challengeId")
    void updateChallengeStatusById(@Param("challengeStatus") ChallengeStatus challengeStatus,
                                   @Param("challengeId") int challengeId);


    /**
     * @param questionId question id
     * @return challenge id
     */
    @Query("SELECT n.challenge " +
            "FROM Question n " +
            "WHERE n.id = :questionId")
    Optional<Challenge> findOneByQuestionId(@Param("questionId") int questionId);



    @Query("SELECT n.key.challenge " +
            "FROM Participant n " +
            "WHERE n.key.customer.id = :studentId AND n.key.challenge.isNonDeleted = TRUE " +
            "AND n.key.challenge.challengeStatus <> com.huhoot.enums.ChallengeStatus.BUILDING")
    List<Challenge> findAllByStudentIdAndIsAvailable(@Param("studentId") int studentId, Pageable pageable);


    @Query("SELECT new com.huhoot.dto.ChallengeResponse(n.id, n.title, n.challengeStatus, n.customer.username, n.customer.socketId, n.createdDate, n.createdBy, " +
            "n.modifiedDate, n.modifiedBy) " +
            "FROM Challenge n ")
    Page<ChallengeResponse> findAllChallengeResponse(Pageable pageable);

    @Query("SELECT new com.huhoot.dto.ChallengeResponse(n.id, n.title, n.challengeStatus, n.customer.username, n.customer.socketId, n.createdDate, n.createdBy, " +
            "n.modifiedDate, n.modifiedBy) " +
            "FROM Challenge n " +
            "WHERE n.id = :challengeId")
    Optional<ChallengeResponse> findOneChallengeResponse(@Param("challengeId") int challengeId);

}
