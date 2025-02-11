package br.com.leaf.controller;

import br.com.leaf.entity.Assignment;
import br.com.leaf.entity.Category;
import br.com.leaf.entity.Disease;
import br.com.leaf.repository.AssignmentRepository;
import br.com.leaf.repository.CategoryRepository;
import br.com.leaf.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/assignments")
    public String listAssignments(Model model) {
        Iterable<Assignment> assignments = assignmentRepository.findAll();
        model.addAttribute("assignments", assignments);
        return "assignments/list";
    }

    @GetMapping("/assignments/add")
    public String addAssignmentForm(Model model) {
        model.addAttribute("assignment", new Assignment());

        // Populate dropdowns for Disease and Category
        List<Disease> diseases = diseaseRepository.findAll();
        model.addAttribute("diseases", diseases);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "assignments/add";
    }

    @PostMapping("/assignments/save")
    public String saveAssignment(@ModelAttribute Assignment assignment) {
        assignmentRepository.save(assignment);
        return "redirect:/assignments";
    }

    @GetMapping("/assignments/edit/{id}")
    public String editAssignmentForm(@PathVariable Long id, Model model) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assignment Id:" + id));
        model.addAttribute("assignment", assignment);

        // Populate dropdowns for Disease and Category (pre-select the existing values)
        List<Disease> diseases = diseaseRepository.findAll();
        model.addAttribute("diseases", diseases);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "assignments/edit";
    }

    @PostMapping("/assignments/update")
    public String updateAssignment(@ModelAttribute Assignment assignment) {
        assignmentRepository.save(assignment);
        return "redirect:/assignments";
    }

    @GetMapping("/assignments/delete/{id}")
    public String deleteAssignment(@PathVariable Long id) {
        assignmentRepository.deleteById(id);
        return "redirect:/assignments";
    }
}