package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.repository.MemberJpaRepository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class CommonConfig {
	
    private final EntityManager em;

    @Autowired
    public CommonConfig(EntityManager em) {
    	
        this.em = em;
    }

    @Bean
    public MemberJpaRepository jpaMemberRepository(){
    	
        return new MemberJpaRepository(em);
    }
}
