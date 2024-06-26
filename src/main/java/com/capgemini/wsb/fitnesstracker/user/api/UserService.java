package com.capgemini.wsb.fitnesstracker.user.api;
import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);
    void deleteUser(Long id);  // Update method to be void
    List<User> findUsersOlderThan(int age);
    User updateUserName(Long id, String firstName, String lastName); // New method

    List<User> findUsersByEmailFragment(String emailFragment); // New method

}
