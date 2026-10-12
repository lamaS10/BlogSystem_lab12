package com.example.blogsystem.Service;


import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public List<User> getUsers(Integer adminId){
        return authRepository.findAll();
    }

    public void register(User user){
        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
    }

    public void updateUser(Integer userId, User updatedUser) {
        User oldUser = authRepository.findUserById(userId);

        if (oldUser == null) {
            throw new ApiException("user not found");
        }

        User existingUser = authRepository.findUserByUsername(updatedUser.getUsername());

        if (existingUser != null && !existingUser.getId().equals(userId)) {
            throw new ApiException("username already exists");
        }

        oldUser.setUsername(updatedUser.getUsername());

        String hash = new BCryptPasswordEncoder().encode(updatedUser.getPassword());
        oldUser.setPassword(hash);

        authRepository.save(oldUser);
    }


    public void deleteUser(Integer userId){
        User user=authRepository.findUserById(userId);

        if (user==null){
            throw new ApiException("user not registered");
        }
        authRepository.delete(user);
    }




}
