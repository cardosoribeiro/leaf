# leaf/pom.xml
```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.7.18</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>br.com.leaf</groupId>
	<artifactId>leaf</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>
	<name>leaf</name>
	<description>Leaf - A Diagnostic Decision Support System</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.mariadb.jdbc</groupId>
			<artifactId>mariadb-java-client</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<image>
						<builder>paketobuildpacks/builder-jammy-base:latest</builder>
					</image>
				</configuration>
			</plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-pmd-plugin</artifactId>
        <version>3.23.0</version> <!-- or use version from pluginManagement -->
        <configuration>
            <!-- failOnViolation is actually true by default, but can be disabled -->
            <failOnViolation>true</failOnViolation>
            <!-- printFailingErrors is pretty useful -->
            <printFailingErrors>true</printFailingErrors>
        </configuration>
        <executions>
          <execution>
            <goals>
              <goal>check</goal>
            </goals>
          </execution>
        </executions>
      </plugin>


		</plugins>
	</build>

</project>
```

# leaf/src/main/java/br/com/leaf/App.java

```java

package br.com.leaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}

```

# leaf/src/main/java/br/com/leaf/ServletInitializer.java
```java

package br.com.leaf;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

}

```

# leaf/src/main/java/br/com/leaf/WebConfig.java
```java

package br.com.leaf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

```

# leaf/src/main/java/br/com/leaf/controller/AssignmentController.java
```java
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
```

# leaf/src/main/java/br/com/leaf/controller/CategoryController.java
```java
package br.com.leaf.controller;

import br.com.leaf.entity.Category;
import br.com.leaf.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String listCategories(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories/list"; // Logical view name
    }

    @GetMapping("/categories/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category()); // Important for form binding
        return "categories/add"; // Logical view name
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/categories"; // Redirect after saving
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable Integer id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category Id:" + id));
        model.addAttribute("category", category); // For pre-populating the form
        return "categories/edit"; // Logical view name
    }

    @PostMapping("/categories/update")
    public String updateCategory(@ModelAttribute Category category) {
        categoryRepository.save(category); // Saves (updates) the category
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }
}
```

# leaf/src/main/java/br/com/leaf/controller/DiseaseController.java
```java
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
```

# leaf/src/main/java/br/com/leaf/controller/RootController.java
```java
package br.com.leaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/") // Maps to the root URL ("/")
    public String home() {
        return "index"; // Logical view name (Thymeleaf will look for index.html)
    }

    @GetMapping("/about") // Example: Maps to the /about URL
    public String about() {
        return "about"; // Logical view name (Thymeleaf will look for about.html)
    }

    // Add other mappings for your root-level pages (e.g., /contact, etc.)

}
```

# leaf/src/main/java/br/com/leaf/controller/UserController.java
```java
package br.com.leaf.controller;

import br.com.leaf.entity.User;
import br.com.leaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
        userRepository.save(user); // Save (update) the user
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
```

# leaf/src/main/java/br/com/leaf/entity/Assignment.java
```java
package br.com.leaf.entity;

import javax.persistence.*;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Could also be a composite key of diseaseId and categoryId

    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Getters and setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Disease getDisease() { return disease; }
    public void setDisease(Disease disease) { this.disease = disease; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
```

# leaf/src/main/java/br/com/leaf/entity/Category.java
```java
package br.com.leaf.entity;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(nullable = false)
    private String categoryName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Disease> diseases = new ArrayList<>();


    // Getters and setters for all fields
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Disease> getDiseases() { return diseases; }
    public void setDiseases(List<Disease> diseases) { this.diseases = diseases; }
}
```

# leaf/src/main/java/br/com/leaf/entity/Disease.java
```java
package br.com.leaf.entity;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diseaseId;

    @Column(nullable = false)
    private String diseaseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String causes;

    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String treatments;

    @Column(columnDefinition = "TEXT")
    private String pathogenesis;

    @Column(columnDefinition = "TEXT")
    private String prevention;

    @Column(columnDefinition = "TEXT")
    private String epidemiology;

    // Add relationship with Category (Many-to-Many)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "disease_category_assignments",
            joinColumns = @JoinColumn(name = "disease_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();


    // Getters and setters for all fields (IMPORTANT!)
    public Integer getDiseaseId() { return diseaseId; }
    public void setDiseaseId(Integer diseaseId) { this.diseaseId = diseaseId; }

    public String getDiseaseName() { return diseaseName; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCauses() { return causes; }
    public void setCauses(String causes) { this.causes = causes; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getTreatments() { return treatments; }
    public void setTreatments(String treatments) { this.treatments = treatments; }

    public String getPathogenesis() { return pathogenesis; }
    public void setPathogenesis(String pathogenesis) { this.pathogenesis = pathogenesis; }

    public String getPrevention() { return prevention; }
    public void setPrevention(String prevention) { this.prevention = prevention; }

    public String getEpidemiology() { return epidemiology; }
    public void setEpidemiology(String epidemiology) { this.epidemiology = epidemiology; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
}
```

# leaf/src/main/java/br/com/leaf/entity/User.java
```java
package br.com.leaf.entity;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // In real application, hash this password

    @Column(nullable = false)
    private String fullName;

    private String role; // e.g., "administrator", "doctor"

    // Getters and setters for all fields
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
```

# leaf/src/main/java/br/com/leaf/repository/AssignmentRepository.java
```java
package br.com.leaf.repository;

import br.com.leaf.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    // Add custom queries if needed
}
```

# leaf/src/main/java/br/com/leaf/repository/CategoryRepository.java
```java
package br.com.leaf.repository;

import br.com.leaf.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Add custom queries if needed
}
```

# leaf/src/main/java/br/com/leaf/repository/DiseaseRepository.java
```java
package br.com.leaf.repository;

import br.com.leaf.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
    // Add custom queries if needed
}
```

# leaf/src/main/java/br/com/leaf/repository/UserRepository.java
```java
package br.com.leaf.repository;

import br.com.leaf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Add custom queries if needed
}
```

# leaf/src/main/resources/static
```html

```
# leaf/src/main/resources/templates

## leaf/src/main/resources/templates/index.html
```html

```
## leaf/src/main/resources/templates/about.html
```html

```
## leaf/src/main/resources/templates/assignments/add.html
```html

```
## leaf/src/main/resources/templates/assignments/edit.html
```html

```
## leaf/src/main/resources/templates/assignments/list.html
```html

```
## leaf/src/main/resources/templates/categories/add.html
```html

```
## leaf/src/main/resources/templates/categories/edit.html
```html

```
## leaf/src/main/resources/templates/categories/list.html
```html

```
## leaf/src/main/resources/templates/diseases/add.html
```html

```
## leaf/src/main/resources/templates/diseases/edit.html
```html

```
## leaf/src/main/resources/templates/diseases/list.html
```html

```
## leaf/src/main/resources/templates/users/add.html
```html

```
## leaf/src/main/resources/templates/users/edit.html
```html

```
## leaf/src/main/resources/templates/users/list.html
```html

```

# leaf/src/main/resources/application.properties
```txt
spring.application.name=leaf
spring.datasource.url=jdbc:mariadb://localhost:3306/leaf
spring.datasource.username=root
spring.datasource.password=devdatabase
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
```