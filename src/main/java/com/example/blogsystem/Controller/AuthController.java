package com.example.blogsystem.Controller;


import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("the user added successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user, @Valid @RequestBody User updatedUser) {
        authService.updateUser(user.getId(), updatedUser);
        return ResponseEntity.status(200).body(new ApiResponse("user updated successfully"));
    }

    @GetMapping("/get-users")
    public ResponseEntity<?>getUsers(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(authService.getUsers(user.getId()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?>deleteUser(@AuthenticationPrincipal User user){
        authService.deleteUser(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("the user deleted successfully"));
    }

}
