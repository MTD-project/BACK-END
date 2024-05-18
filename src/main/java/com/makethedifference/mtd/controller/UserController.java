package com.makethedifference.mtd.controller;

import com.makethedifference.mtd.model.User;
import com.makethedifference.mtd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list"; // Devuelve la vista user-list.html
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-detail"; // Devuelve la vista user-detail.html
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Devuelve la vista register.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/users"; // Redirige a la lista de usuarios
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users"; // Redirige a la lista de usuarios
    }
}
