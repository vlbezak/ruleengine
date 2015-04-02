package sk.ruleengine;

import java.util.Random;

public class NEO4JObjectGenerator {
	public static void main(String[] args){
		
		Random random = new Random();
		
		int randInt = random.nextInt(1000);
		
		String queryAll = "";
		
		
		for (int j = 0; j < 20; j++){
		
			String node1 = "node1_" + randInt + "_" + j;
			String node2 = "node2_" + randInt + "_" + j;
		    String node3 = "node3_" + randInt + "_" + j;
		    String node4 = "node4_" + randInt + "_" + j;
		    String node5 = "node5_" + randInt + "_" + j;
		    String node6 = "node6_" + randInt + "_" + j;
		    String node7 = "node7_" + randInt + "_" + j;
		    
		    String nodeLabel1 = "TestNode1";
		    String nodeLabel2 = "TestNode2";
		    String nodeLabel3 = "TestNode3";
		    String nodeLabel4 = "TestNode4";
		    String nodeLabel5 = "TestNode5";
		    String nodeLabel6 = "TestNode6";
		    String nodeLabel7 = "TestNode7";
		    
		    String name1 = "name1_" + randInt + "_" + j;
		    String name2 = "name2_" + randInt + "_" + j;
		    String name3 = "name3_" + randInt + "_" + j;
		    String name4 = "name4_" + randInt + "_" + j;
		    String name5 = "name5_" + randInt + "_" + j;
		    String name6 = "name6_" + randInt + "_" + j;
		    String name7 = "name7_" + randInt + "_" + j;
		    
		    String query1 = "create (" + node1 + ":" + nodeLabel1 + " {name: '" +  name1 +"', type: 'testnode', time: '" + System.currentTimeMillis() +"'}) ";
		    String query2 = "create (" + node2 + ":" + nodeLabel2 + " {name: '" +  name2 +"', type: 'testnode', time: '" + System.currentTimeMillis() +"'}) ";
		    String query3 = "create (" + node3 + ":" + nodeLabel3 + " {name: '" +  name3 +"', type: 'testnode', time: '" + System.currentTimeMillis() +"'}) ";
		    String query4 = "create (" + node4 + ":" + nodeLabel4 + " {name: '" +  name4 +"', type: 'testnode', time: '" + System.currentTimeMillis() +"'}) ";
		    String query5 = "create (" + node5 + ":" + nodeLabel5 + " {name: '" +  name5 +"', type: 'testnode', time: '" + System.currentTimeMillis() +"'}) ";
		    String query6 = "create (" + node6 + ":" + nodeLabel6 + " {name: '" +  name6 +"', type: 'testnode', time: '" + System.currentTimeMillis() +"'}) ";
		    String query7 = "create (" + node7 + ":" + nodeLabel7 + " {name: '" +  name7 +"', type: 'testnode', time: '" + System.currentTimeMillis() +"'}) ";
		    
		    queryAll = query1 + "\n" + query2 + "\n" + query3 + "\n" + query4 + "\n" +  query5 + "\n" + query6 + "\n" + query7 + "\n";
		    
		    String rel1 = "create (" + node1 + ")-[:REL_TYPE1]->(" + node2 + ")";
		    String rel2 = "create (" + node1 + ")-[:REL_TYPE1]->(" + node3 + ")";
		    String rel3 = "create (" + node3 + ")-[:REL_TYPE2]->(" + node4 + ")";
		    String rel4 = "create (" + node2 + ")-[:REL_TYPE3]->(" + node4 + ")";
		    String rel5 = "create (" + node2 + ")-[:REL_TYPE3]->(" + node5 + ")";
		    String rel6 = "create (" + node2 + ")-[:REL_TYPE3]->(" + node6 + ")";
		    String rel7 = "create (" + node6 + ")-[:REL_TYPE3]->(" + node7 + ")";
		    
		    queryAll += rel1 + "\n" + rel2 + "\n" + rel3 + "\n" + rel4 + "\n" + rel5 + "\n" + rel6 + "\n" + rel7 + "\n";
		    
		    System.out.println(queryAll);
		}
		
	}
}
