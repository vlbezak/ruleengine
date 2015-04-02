package sk.ruleengine.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sk.ruleengine.beans.CypherRequest;
import sk.ruleengine.beans.CypherResponse;
import sk.ruleengine.beans.CypherStatement;
import sk.ruleengine.beans.Transaction;
import sk.ruleengine.exceptions.Neo4jErrorException;
import sk.ruleengine.service.Neo4jTransactionalService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Neo4jTransactionalServiceImpl implements Neo4jTransactionalService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jTransactionalServiceImpl.class);
	
	@Autowired
	String neo4jTransactionRestUrl;
	
	@Autowired
	@Qualifier("neo4jRestTemplate")
	RestTemplate restTemplate;
	
	

	@Override
	public CypherResponse runQuery(Transaction transaction, String cypherQuery) throws JsonProcessingException 
	{
		CypherRequest request = new CypherRequest();

		request.getStatements().add(new CypherStatement(cypherQuery));
		
		LOGGER.debug("Request to neo4j from runQuery:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request));
		
		ResponseEntity<CypherResponse> response = restTemplate.postForEntity(transaction.getLocationUri(), request , CypherResponse.class);
		
		LOGGER.debug("Response from neo4j from runQuery:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody()));
		
		checkResponse(response);
		
		return response.getBody();
	}
	
	@Override
	public CypherResponse runQuery(Transaction transaction, List<String> cypherQueries) throws JsonProcessingException 
	{
		CypherRequest request = new CypherRequest();

		for(String cypherQuery: cypherQueries)
		{
			request.getStatements().add(new CypherStatement(cypherQuery));
		}
		
		LOGGER.debug("Request to neo4j from runQuery:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request));
		
		ResponseEntity<CypherResponse> response = restTemplate.postForEntity(transaction.getLocationUri(), request , CypherResponse.class);
		
		LOGGER.debug("Response from neo4j from runQuery:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody()));
		
		checkResponse(response);
		
		return response.getBody();
	}
	
	@Override
	public Transaction startTransaction() throws JsonProcessingException 
	{
		CypherRequest request = new CypherRequest();
		
		LOGGER.debug("Request to neo4j from startTransaction:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request));
		
		URI locationUri = restTemplate.postForLocation(neo4jTransactionRestUrl, request, new HashMap<String, String>());
		
		LOGGER.debug("Response from neo4j from startTransaction: locationUri='" + locationUri + "'");
		
		ResponseEntity<CypherResponse> response = restTemplate.postForEntity(locationUri, request , CypherResponse.class);
		
		LOGGER.debug("Response from neo4j from startTransaction:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody()));
		
		return new Transaction(locationUri, response.getBody().getCommit());
	}

	@Override
	public void commitTransaction(Transaction transaction) throws JsonProcessingException 
	{
		CypherRequest request = new CypherRequest();
		
		LOGGER.debug("Request to neo4j from commitTransaction:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request));
		
		ResponseEntity<CypherResponse> response = restTemplate.postForEntity(transaction.getCommitUrl(), request , CypherResponse.class);
		
		LOGGER.debug("Request to neo4j from commitTransaction:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody()));
		
		checkResponse(response);
	}

	@Override
	public void rollbackTransaction(Transaction transaction)
	{
		restTemplate.delete(transaction.getLocationUri());
	}
	
	private void checkResponse(ResponseEntity<CypherResponse> response)
	{
		if(response.getBody().getErrors() != null && !response.getBody().getErrors().isEmpty())
		{
			throw new Neo4jErrorException("Response has errors", response.getBody().getErrors());
		}
	}
}
