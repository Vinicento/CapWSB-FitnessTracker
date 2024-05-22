package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;



    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent()) {
            return userMapper.toDto(userOptional.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @PostMapping
    public User addUser(@RequestBody UserDto userDto) {
        log.info("User with e-mail: {} passed to the request", userDto.email());
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent()) {
            userService.deleteUser(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @GetMapping("/age/older-than/{age}")
    public List<UserDto> getUsersOlderThan(@PathVariable int age) {
        return userService.findUsersOlderThan(age)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    @PutMapping("/{id}/name")
    public UserDto updateUserName(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUserName(id, userDto.firstName(), userDto.lastName());
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/email")
    public List<UserDto> getUsersByEmailFragment(@RequestParam String emailFragment) {
        return userService.findUsersByEmailFragment(emailFragment)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }


}