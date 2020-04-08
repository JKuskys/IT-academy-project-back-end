package com.project.controller;

import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.model.request.PasswordResetRequest;
import com.project.model.response.GenericResponse;
import com.project.service.EmailService;
import com.project.service.PasswordResetService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/users")
public class PasswordResetController {

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetController(UserService userService, EmailService emailService, PasswordResetService passwordResetService){
        this.userService = userService;
        this.emailService = emailService;
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/changePassword")
    public String showChangePasswordPage(Model model, @RequestParam("id") long id, @RequestParam("token") String token) {
        String result = passwordResetService.validatePasswordResetToken(id, token);
        if(result != null) {
            model.addAttribute("message", result);
            return "redirect:/login";
        }
        return "redirect:/updatePassword";
    }

    @PostMapping("/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) throws UserNotFoundException {
        User user = userService.getByEmail(userEmail);
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        emailService.constructResetTokenEmail(getAppUrl(request), token, user);

        return new GenericResponse("Išsiųstas elektroninis laiškas su nuoroda pasikeisti slaptažodį");
    }

    @PostMapping("/savePassword")
    public GenericResponse savePassword(@Valid PasswordResetRequest passwordResetRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, passwordResetRequest.getNewPassword());
        return new GenericResponse("Slaptažodis pakeistas sėkmingai");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
