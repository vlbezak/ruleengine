package sk.ruleengine.config;  

import javax.annotation.Resource;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@PropertySource("classpath:/ruleengine.properties")
@Configuration
@EnableNeo4jRepositories(basePackages = "sk.ruleengine")
public class Neo4JConfig extends Neo4jConfiguration  {
	
	@Resource
	Environment environment;
	
	public Neo4JConfig() {
        setBasePackage("sk.ruleengine.config");
    }
	
	@Autowired
    GraphDatabase graphDatabase;
	
	@Bean
    GraphDatabaseService graphDatabaseService() {
		//SpringCypherRestGraphDatabase
		System.out.println("Resource Environment:" + environment);
		
        return new SpringRestGraphDatabase(environment.getProperty("neo4j.url"));
    }
}  
  