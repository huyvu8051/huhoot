package com.huhoot.repository;

import com.huhoot.dto.ChallengeResponse;
import com.huhoot.model.Customer;
import com.huhoot.model.Participant;
import com.huhoot.organize.StudentInChallengeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    Page<Participant> findAllByKeyCustomerUsernameContainingIgnoreCaseAndKeyChallengeId(String studentUsername, int challengeId, Pageable pageable);

    List<Participant> findAllByKeyChallengeIdAndKeyChallengeCustomerId(int challengeId, int adminId);

    /**
     * @param studentIds  List of {@link Customer} ids
     * @param challengeId {@link com.huhoot.model.Challenge} id
     * @param adminId     {@link com.huhoot.model.Customer} id
     * @return List of {@link Participant}
     */
    @Query("SELECT n " +
            "FROM Participant n " +
            "WHERE n.key.customer.id IN :studentIds " +
            "AND n.key.challenge.id = :challengeId " +
            "AND n.key.challenge.customer.id = :adminId")
    List<Participant> findAllByStudentIdInAndChallengeIdAndAdminId(@Param("studentIds") List<Integer> studentIds,
                                                                   @Param("challengeId") int challengeId,
                                                                   @Param("adminId") int adminId);

    Optional<Participant> findOneByKeyCustomerIdAndKeyChallengeIdAndKeyChallengeCustomerId(int studentId, int challengeId, int adminId);

    @Query("SELECT n " +
            "FROM Participant n " +
            "WHERE n.key.challenge.id = :challengeId " +
            "AND n.key.customer.id = :studentId " +
            "AND n.isNonDeleted = TRUE " +
            "AND n.isKicked = FALSE " +
            "AND n.key.challenge.challengeStatus NOT IN (com.huhoot.enums.ChallengeStatus.BUILDING, com.huhoot.enums.ChallengeStatus.ENDED)")
    Optional<Participant> findOneByChallengeIdAndStudentIdAndAvailable(@Param("challengeId") int challengeId, @Param("studentId") int studentId);


    @Query("SELECT new com.huhoot.organize.StudentInChallengeResponse(n.key.customer.id, n.key.customer.username, n.key.customer.fullName, n.isLogin, n.isKicked, n.isOnline, n.createdBy, n.createdDate, n.modifiedBy, n.modifiedDate, n.isNonDeleted) " +
            "FROM Participant n " +
            "WHERE n.key.challenge.id = :challengeId " +
            "AND n.key.challenge.customer.id = :adminId " +
            "AND n.isLogin = TRUE " +
            "AND n.isNonDeleted = TRUE " +
            "AND n.isKicked = FALSE")
    List<StudentInChallengeResponse> findAllStudentIsLogin(@Param("challengeId") int challengeId, @Param("adminId") int adminId);


    @Query("SELECT new com.huhoot.organize.StudentInChallengeResponse(n.key.customer.id, n.key.customer.username, n.key.customer.fullName, n.isLogin, n.isKicked, n.isOnline, n.createdBy, n.createdDate, n.modifiedBy, n.modifiedDate, n.isNonDeleted) " +
            "FROM Participant n " +
            "WHERE n.key.challenge.id = :challengeId " +
            "AND n.isLogin = TRUE " +
            "AND n.isNonDeleted = TRUE " +
            "AND n.isKicked = FALSE")
    Page<StudentInChallengeResponse> findAllStudentIsLogin(@Param("challengeId") int challengeId, Pageable pageable);

    @Query("SELECT new com.huhoot.organize.StudentInChallengeResponse(n.key.customer.id, n.key.customer.username, n.key.customer.fullName, n.isLogin, n.isKicked, n.isOnline, n.createdBy, n.createdDate, n.modifiedBy, n.modifiedDate,  n.isNonDeleted) " +
            "FROM Participant n " +
            "WHERE n.key.challenge.id = :challengeId ")
    Page<StudentInChallengeResponse> findAllByChallengeIdAndAdminId(@Param("challengeId") int challengeId, Pageable pageable);

    @Query("SELECT COUNT(a.key.customer.id) FROM Participant a WHERE a.key.challenge.id = :challengeId AND a.isLogin = true")
    int getTotalStudentInChallenge(@Param("challengeId") int challengeId);


    @Query("SELECT new com.huhoot.dto.ChallengeResponse(n.key.challenge.id, n.key.challenge.title,  n.key.challenge.challengeStatus, n.key.challenge.customer.username, n.key.challenge.customer.socketId, n.key.challenge.createdDate, n.key.challenge.createdBy, " +
            "n.key.challenge.modifiedDate, n.key.challenge.modifiedBy) " +
            "FROM Participant n " +
            "WHERE n.key.customer.username = :username")
    Page<ChallengeResponse> findAllChallengeResByStudent(@Param("username") String username, Pageable pageable1);
}
