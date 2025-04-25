package com.example.movierating.service;

import com.example.movierating.dto.UserDTO;
import com.example.movierating.model.User;
import com.example.movierating.repository.UserRepository;
import com.example.movierating.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                new User(1L, "User"),
                new User(2L, "User2")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("User", result.get(0).getUsername());
        assertEquals("User2", result.get(1).getUsername());
    }

    @Test
    void testGetUserById() {
        User user = new User(1L, "testuser");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testCreateUser() {
        UserDTO dto = new UserDTO(null, "newuser");
        User saved = new User(1L, "newuser");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserDTO result = userService.createUser(dto);

        assertNotNull(result.getId());
        assertEquals("newuser", result.getUsername());
    }

    @Test
    void testUpdateUser() {
        User existing = new User(1L, "olduser");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing); // saving the modified object

        UserDTO dto = new UserDTO(null, "updateduser");

        UserDTO result = userService.updateUser(1L, dto);

        assertEquals("updateduser", result.getUsername());
        verify(userRepository).findById(1L);
        verify(userRepository).save(existing);
    }


    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
