## Introduction
In modern Spring Boot applications, managing configurations through externalized properties is essential for flexibility and environment-specific customizations.

One powerful feature in Spring Boot that facilitates this is the @EnableConfigurationProperties annotation. In this article, we’ll explore how it works, why it’s useful, and how to use it effectively in a real-world application.

As usual, this article begins by addressing three key questions: what, why, and how, followed by an example. It explores the purpose, usage, and benefits of utilizing @EnableConfigurationProperties for managing application configurations. So, let’s dive in!

## What is @EnableConfigurationProperties?
@EnableConfigurationProperties is an annotation in Spring Boot that is used to enable support for configuration properties. It works hand-in-hand with another annotation called @ConfigurationProperties, which is used to bind external configuration (such as values from a properties or YAML file) to a POJO (Plain Old Java Object).

Refere the below link if you want to learn more about the ConfigurationProperties annotation: https://medium.com/@midhunbalan.dev/understanding-configurationproperties-annotation-in-springboot-674550f51f9a

This feature provides a type-safe way to manage configurations and allows you to cleanly group related properties into separate classes, promoting modularity in your application configuration.

## How Does It Work?
@ConfigurationProperties: This annotation defines a class where external configuration properties will be mapped. You can specify a prefix that matches the keys in your application.properties or application.yml file.
@EnableConfigurationProperties: This annotation is applied to a Spring configuration class and specifies which configuration properties classes should be bound from external configuration sources.

## Why Use @EnableConfigurationProperties?
Configuration properties are mapped directly to strongly-typed Java objects. This helps avoid common mistakes like typos in property keys and incorrect data types.
You can separate different sets of properties (e.g., database, mail server, API settings) into their own POJOs, which improves the maintainability of your code.
Spring profiles (e.g., dev, prod) are often used in applications. By externalizing configuration using @EnableConfigurationProperties, you can easily load different configurations depending on the active profile.
Spring Boot automatically maps properties from the environment or configuration files into your Java objects, requiring minimal boilerplate code.
Example: Using @EnableConfigurationProperties in a Real Project
Let’s walk through a real-world example of how to use @EnableConfigurationProperties to load database configuration properties in a Spring Boot application.

So in this project, what we are going to achieve is to enable two configuration class in your project.

#### Step 1: Create the Configuration Properties Classes
We start by defining a class that will hold our database configuration properties. We annotate this class with @ConfigurationProperties and specify the prefix (database) that matches the keys in the properties file.

AppProperties.java
```
package com.configmanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
private String name;
private String version;
}
```
DatabaseProperties.java
```
package com.configmanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {
private String url;
private String username;
private String password;
}
```
#### Step 2: Enable Configuration Properties
Next, we need to tell Spring Boot to enable the configuration properties class we just created. This is where @EnableConfigurationProperties comes in.

We create a configuration class and annotate it with @EnableConfigurationProperties, passing DatabaseProperties.class and AppProperties.class as parameters to indicate which properties class to enable.

```
package com.configmanager.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableConfigurationProperties({AppProperties.class, DatabaseProperties.class})
public class AppConfig {
}
```

#### Step 3: Define External Configuration
The next step is to add the actual configuration values in application.properties (or application.yml). The property keys must match the prefix and fields in the DatabaseProperties class and AppProperties class.

```
spring.application.name=ConfigPropEnabler

# Application properties
app.name=My Application
app.version=1.0.0
# Database properties
database.url=jdbc:mysql://localhost:3306/mydb
database.username=root
database.password=secret
```

#### Step 4: Use the Properties in a Spring Rest Controller
To demonstrate how to access the bound configuration properties in your application, let’s inject DatabaseProperties and AppProperties into a controller and print the values.

```
package com.configmanager.api;

import com.configmanager.config.AppProperties;
import com.configmanager.config.DatabaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ConfigPropEnablerApiEndPoint {
private final AppProperties appProperties;
private final DatabaseProperties databaseProperties;
@Autowired
public ConfigPropEnablerApiEndPoint(AppProperties appProperties, DatabaseProperties databaseProperties) {
this.appProperties = appProperties;
this.databaseProperties = databaseProperties;
}
@GetMapping("/info")
public String getAppInfo() {
return "App Name: " + appProperties.getName() + ", Version: " + appProperties.getVersion();
}
@GetMapping("/dbinfo")
public String getDbInfo() {
return "DB URL: " + databaseProperties.getUrl() + ", Username: " + databaseProperties.getUsername();
}
}

```
When you run your Spring Boot application, you should see the following output in the console, while hitting the apis.

## Conclusion
@EnableConfigurationProperties is an essential tool in Spring Boot for managing externalized configuration. By binding external properties to strongly-typed Java objects, it promotes clean, maintainable, and modular configuration management. Whether you're dealing with database settings, API keys, or other environment-specific settings, @EnableConfigurationProperties can significantly simplify your code and make it more flexible.

To summarize:

Use @ConfigurationProperties to define classes for external configurations.
Use @EnableConfigurationProperties to enable the binding of those properties into Spring components.
You can easily manage multiple environments by combining this with Spring profiles.
By adopting this pattern, you’ll have a more robust, organized, and flexible configuration management system in your Spring Boot application!

Github URL: https://github.com/MyArticleHub/ConfigMaster

Thank you so much for reading! Feel free to leave a comment if you have any questions. Have a wonderful day!





