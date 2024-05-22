package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }



    /**
     * Query searching users older than a given age.
     *
     * @param cutoffDate birthdate cutoff for age
     * @return {@link List} of users older than the given age
     */
    default List<User> findUsersOlderThan(LocalDate cutoffDate) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                .toList();
    }


    /**
     * Query searching users by email fragments (case-insensitive).
     *
     * @param emailFragment fragment of the email to search
     * @return {@link List} of users matching the email fragment
     */
    default List<User> findByEmailFragment(String emailFragment) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(emailFragment.toLowerCase()))
                .collect(Collectors.toList());
    }



}
