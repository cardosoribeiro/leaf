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
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>
	<name>demo</name>
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
```

# leaf/src/main/java/br/com/leaf/controller/CategoryController.java
```java
```

# leaf/src/main/java/br/com/leaf/controller/DiseaseController.java
```java
```

# leaf/src/main/java/br/com/leaf/controller/RootController.java
```java
```

# leaf/src/main/java/br/com/leaf/controller/UserController.java
```java
```

# leaf/src/main/java/br/com/leaf/entity/Assignment.java
```java
```

# leaf/src/main/java/br/com/leaf/entity/Category.java
```java
```

# leaf/src/main/java/br/com/leaf/entity/Disease.java
```java
```

# leaf/src/main/java/br/com/leaf/entity/User.java
```java
```

# leaf/src/main/java/br/com/leaf/repository/AssignmentRepository.java
```java
```

# leaf/src/main/java/br/com/leaf/repository/CategoryRepository.java
```java
```

# leaf/src/main/java/br/com/leaf/repository/DiseaseRepository.java
```java
```

# leaf/src/main/java/br/com/leaf/repository/UserRepository.java
```java
```

# leaf/src/main/resources/static
```html
```

# leaf/src/main/resources/template
```html
```

# leaf/src/main/resources/application.properties
```txt
spring.application.name=demo
spring.datasource.url=jdbc:mariadb://localhost:3306/leaf
spring.datasource.username=root
spring.datasource.password=devdatabase
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
```