package org.example.task.Service;

import lombok.AllArgsConstructor;
import org.example.task.Dto.RegisterRequest;
import org.example.task.Entity.Users;
import org.example.task.Repository.UsersRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    public Users save(RegisterRequest request) {
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Users.Role.USER);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        return usersRepo.save(user);
    }

    public Users findById(Long id) {
        return usersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
    }

}
