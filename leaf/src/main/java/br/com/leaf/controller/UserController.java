package br.com.leaf.controller;

import br.com.leaf.entity.User;
import br.com.leaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public String listUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users); // Add users to the model
        return "users/list"; // Return the logical view name (users/list.html)
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User()); // Add an empty User object to the model
        return "users/add"; // Return the logical view name (users/add.html)
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        userRepository.save(user);
        return "redirect:/users"; // Redirect to /users after saving
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Integer id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));        
        model.addAttribute("user", user); // Add the user to the model for editing
        return "users/edit"; // Return the logical view name (users/edit.html)
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute User user) {
        // Fetch the existing user from the database
        User existingUser = userRepository.findById(user.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getUserId()));

        // Only update the password if a new one is provided
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the new password
        }

        // Update other user details
        existingUser.setFullName(user.getFullName());
        existingUser.setRole(user.getRole());
        existingUser.setUsername(user.getUsername());

        userRepository.save(existingUser); // Save the updated user
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}