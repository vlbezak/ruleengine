package sk.ruleengine.config;  

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;


@Configuration
@EnableNeo4jRepositories(basePackages = "sk.ruleengine")
public class Neo4JConfig extends Neo4jConfiguration  {
	public Neo4JConfig() {
        setBasePackage("com.concretepage.neo4j");
    }
	@Autowired
    GraphDatabase graphDatabase;
	
	@Bean
    GraphDatabaseService graphDatabaseService() {
		//SpringCypherRestGraphDatabase
        return new SpringRestGraphDatabase("http://localhost:7474/db/data");
    }
}  
  