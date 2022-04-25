package com.sphincs.cryptohelper.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "com.sphincs.cryptohelper" })
@EntityScan(basePackages = { "com.sphincs.cryptohelper" })
@EnableJpaRepositories(basePackages = { "com.sphincs.cryptohelper" })
public class JpaConfig {
}
