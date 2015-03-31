package sk.ruleengine.web.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import scala.util.Random;
import sk.ruleengine.service.RuleModuleClientService;

@Controller
@RequestMapping("/ruleprocessor")
public class RuleModuleRestController {

	@Autowired
	RuleModuleClientService ruleModuleClientService;

	@Autowired
	GraphDatabase graphDatabase;

	@RequestMapping(value = "/cmdb", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String storeObjects(
			@RequestBody String cypherStatement, HttpServletResponse response) {
		System.out.println("RuleEngine: REST: received cypherStatement:"
				+ cypherStatement);

		String toReturn = "OK";

		long transStartTime = System.currentTimeMillis();
		long transFinishTime;
		int ruleCount = 100;

		Transaction tx = graphDatabase.beginTx();
		
		
		
		
		try {

			// :TODO: Setting created for now
			// :TODO try to do it other way (advice ?)

			for (int i = 0; i < ruleCount; i++) {
				
				String cypherQuery = ruleModuleClientService
						.getRuleToCheck("dummy");
				System.out
						.println("RuleEngine: REST: called ruleModule. returned rule:"
								+ cypherQuery);

				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("param1", "ParamValue1");

				Result result = graphDatabase.queryEngine().query(cypherQuery,
						paramMap);

				Iterator it = result.iterator();

				while (it.hasNext()) {
					Object o = it.next();
					// System.out.println("Iterating: " + o.getClass().getName()
					// +
					// " : " + o.toString());
					System.out.println("********************");
					if (o instanceof HashMap) {

						System.out.println("It is hashMap:");
						System.out.println("==============");
						HashMap<String, Object> map = (HashMap<String, Object>) o;
						Set<String> keySet = map.keySet();

						Iterator keySetIt = keySet.iterator();
						while (keySetIt.hasNext()) {
							String key = (String) keySetIt.next();

							Object value = map.get(key);
							if (value instanceof String) {
								System.out.println(key + ":" + value);
							} else if ( value != null ) {
								System.out.println("!!!!!" + key
										+ " - not String:"
										+ value.getClass().getName());
							}
							else{
								System.out.println(" value is null");
							}
						}

					}
				}
				result.finish();

				System.out.println("after query========:" + i);

			}
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			tx.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			toReturn = "FAILURE";
			tx.failure();
		} finally {
			tx.close();
		}
		
		transFinishTime = System.currentTimeMillis();
		
		System.out.println("Transaction time:" + (transFinishTime - transStartTime) + " ms");

		// :TODO: only temporary
		return toReturn;
	}

	@RequestMapping(value = "/cmdb", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String test() {
		return "OK";
	}

}
