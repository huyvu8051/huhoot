package com.huhoot.repository;

import com.huhoot.dto.StudentResponse;
import com.huhoot.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findOneByUsername(String username);

    Optional<Customer> findOneById(int id);


    @Query("SELECT n.key.student " +
            "FROM Participant n " +
            "WHERE n.key.challenge.id = :challengeId")
    List<Customer> findAllStudentInChallenge(@Param("challengeId") int challengeId);

    @Modifying
    @Transactional
    @Query("UPDATE Customer n " + "SET n.socketId = :socketId " +
            "WHERE n.id = :studentId")
    void updateSocketId(@Param("socketId") UUID socketId, @Param("studentId") int studentId);


    @Query("SELECT new com.huhoot.dto.StudentResponse(n.id, n.username, n.fullName, n.createdDate, n.createdBy, n.modifiedDate, n.modifiedBy, n.isNonLocked) " +
            "FROM Customer n ")
    Page<StudentResponse> findAllStudent(Pageable pageable);

    @Query("SELECT new com.huhoot.dto.StudentResponse(n.id, n.username, n.fullName, n.createdDate, n.createdBy, n.modifiedDate, n.modifiedBy, n.isNonLocked) " +
            "FROM Customer n " +
            "WHERE n.username = :username")
    StudentResponse findOneStudentResponseByUsername(@Param("username") String username);
}
