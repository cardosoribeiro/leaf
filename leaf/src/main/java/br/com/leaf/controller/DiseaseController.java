package br.com.leaf.controller;

import br.com.leaf.entity.Disease;
import br.com.leaf.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiseaseController {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @GetMapping("/diseases")
    public String listDiseases(Model model) {
        Iterable<Disease> diseases = diseaseRepository.findAll();
        model.addAttribute("diseases", diseases);
        return "diseases/list"; // Logical view name (Thymeleaf template)
    }

    @GetMapping("/diseases/add")
    public String addDiseaseForm(Model model) {
        model.addAttribute("disease", new Disease()); // Empty Disease object for the form
        return "diseases/add"; // Logical view name
    }

    @PostMapping("/diseases/save")
    public String saveDisease(@ModelAttribute Disease disease) {
        diseaseRepository.save(disease);
        return "redirect:/diseases"; // Redirect to the disease list
    }

    @GetMapping("/diseases/edit/{id}")
    public String editDiseaseForm(@PathVariable Integer id, Model model) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Disease Id:" + id));
        model.addAttribute("disease", disease); // Disease object for pre-populating the form
        return "diseases/edit";
    }

    @PostMapping("/diseases/update")
    public String updateDisease(@ModelAttribute Disease disease) {
        diseaseRepository.save(disease); // Saves (updates) the disease
        return "redirect:/diseases";
    }


    @GetMapping("/diseases/delete/{id}")
    public String deleteDisease(@PathVariable Integer id) {
        diseaseRepository.deleteById(id);
        return "redirect:/diseases";
    }
}