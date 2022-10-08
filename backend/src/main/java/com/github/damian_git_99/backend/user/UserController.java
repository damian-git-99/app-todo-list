package com.github.damian_git_99.backend.user;

import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    ResponseEntity<Map<String, Object>> signUp(@RequestBody @Valid UserRequest request, BindingResult result) {
        if (result.hasErrors()) {
            var firstError = result.getFieldErrors().stream().findFirst().get();
            Map<String , Object> response = new HashMap<>();
            response.put("error", firstError.getDefaultMessage());

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        userService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


}
