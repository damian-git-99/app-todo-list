package com.github.damian_git_99.backend.user.controllers;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.dto.UserResponse;
import com.github.damian_git_99.backend.user.dto.UserResponseConverter;
import com.github.damian_git_99.backend.user.dto.UserUpdateRequest;
import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.user.services.UserService;
import com.github.damian_git_99.backend.utils.BindingResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Api( tags = "Users")
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private UserResponseConverter converter = new UserResponseConverter();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "This method is used to signup a user")
    @PostMapping("")
    ResponseEntity<Map<String, Object>> signUp(@RequestBody @Valid UserRequest request
            , BindingResult result) {
        if (result.hasErrors()) {
            var error =  BindingResultUtils.getFirstError(result);

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }

        userService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @ApiOperation(value = "This method is used to get the details of a user")
    @GetMapping("/info")
    ResponseEntity<UserResponse> getUserDetails(Authentication authentication) {
        AuthenticatedUser  auth = (AuthenticatedUser) authentication.getPrincipal();

        User user = userService.findById(auth.getId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        return ResponseEntity.ok(converter.toDto(user));
    }

    @ApiOperation(value = "This method is used to update the details of a user")
    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUser(@PathVariable(name = "id") Long id
            , @RequestBody UserUpdateRequest request){

        User user = this.userService.updateUser(id, request);

        return ResponseEntity.ok(converter.toDto(user));
    }

}
