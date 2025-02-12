package br.com.leaf.controller;

import br.com.leaf.entity.User;
import br.com.leaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User()); // Important: Add an empty User object
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            // Check if the username already exists
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                redirectAttributes.addAttribute("error", true);
                redirectAttributes.addFlashAttribute("errorMessage", "Username already exists.");
                return "redirect:/signup";
            }

            user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
            userRepository.save(user);

            return "redirect:/login"; // Redirect to login after successful signup

        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred during registration. Please try again.");
            return "redirect:/signup";
        }

    }
}