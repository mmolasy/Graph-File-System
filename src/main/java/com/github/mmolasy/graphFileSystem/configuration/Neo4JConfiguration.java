package com.github.mmolasy.graphFileSystem.configuration;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.github.mmolasy.graphFileSystem")
@EnableNeo4jRepositories("com.github.mmolasy.graphFileSystem.repository")
public class Neo4JConfiguration {

    @Bean
    public SessionFactory sessionFactory()
    {
        org.neo4j.ogm.config.Configuration neoCfg = new org.neo4j.ogm.config.Configuration();
        neoCfg.driverConfiguration().setURI("http://neo4j:password@localhost:7474");
        return new SessionFactory(neoCfg, "com.github.mmolasy.graphFileSystem.graph");
    }


    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
}
