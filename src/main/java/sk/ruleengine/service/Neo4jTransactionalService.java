package sk.ruleengine.service;

import java.net.URI;
import java.util.List;

import sk.ruleengine.beans.CypherResponse;
import sk.ruleengine.beans.Transaction;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface Neo4jTransactionalService 
{
	public CypherResponse runQuery(Transaction transaction, String cypherQuery) throws JsonProcessingException;
	
	public CypherResponse runQuery(Transaction transaction, List<String> cypherQueries) throws JsonProcessingException;
	
	public Transaction startTransaction() throws JsonProcessingException;
	
	public void commitTransaction(Transaction transaction) throws JsonProcessingException;
	
	public void rollbackTransaction(Transaction transaction);
}
