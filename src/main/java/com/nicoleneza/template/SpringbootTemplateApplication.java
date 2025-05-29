package com.nicoleneza.template;

import com.nicoleneza.template.v1.dtos.AdminProperties;
import com.nicoleneza.template.v1.enums.ERole;
import com.nicoleneza.template.v1.services.IRoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.Set;

@EnableConfigurationProperties(AdminProperties.class)
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories("com.nicoleneza.template.v1.repositories")
@EntityScan("com.nicoleneza.template.v1.models")
public class SpringbootTemplateApplication {



     private final IRoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTemplateApplication.class, args);
    }

    @PostConstruct
    public void seedData() {
        Set<ERole> userRoleSet = new HashSet<>();
        userRoleSet.add(ERole.ADMIN);
        userRoleSet.add(ERole.MANAGER);
        userRoleSet.add(ERole.EMPLOYEE);
        for (ERole role : userRoleSet) {
            if (!this.roleService.isRolePresent(role)) {
                this.roleService.createRole(role);
            }
        }
    }

}
