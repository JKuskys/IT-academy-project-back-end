package com.project.controller;

import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.model.request.PasswordResetRequest;
import com.project.service.EmailService;
import com.project.service.PasswordResetService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
public class PasswordResetController {

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;
    private final MessageSource messageSource;

    @Autowired
    public PasswordResetController(
            UserService userService, EmailService emailService, PasswordResetService passwordResetService, MessageSource messageSource) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordResetService = passwordResetService;
        this.messageSource = messageSource;
    }

    @GetMapping("/change-password")
    public ResponseEntity<String> showChangePasswordPage(Model model, @RequestParam("id") Long id, @RequestParam("token") String token) {
        String result = passwordResetService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message", result);
            return new ResponseEntity<>("redirect:/home", HttpStatus.OK);
        }
        return new ResponseEntity<>("redirect:/update-password", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) throws UserNotFoundException {
        User user = userService.getByEmail(userEmail);
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        emailService.constructResetTokenEmail(getAppUrl(request), token, user);
        return new ResponseEntity<>(messageSource.getMessage("passwordResetController.passwordMessage", null, null), HttpStatus.OK);
    }

    @PostMapping("/save-password")
    public ResponseEntity<String> savePassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) throws UserNotFoundException {
        User user = userService.getById(passwordResetRequest.getId());
        userService.changeUserPassword(user, passwordResetRequest.getNewPassword());
        return new ResponseEntity<>(messageSource.getMessage("passwordResetController.passwordChangeSucceed", null, null), HttpStatus.OK);
    }

    private String getAppUrl(HttpServletRequest request) {
        return request.getHeader("origin");
    }
}
