package com.model;
import java.util.*;

public class Part3 extends OnePart{
	
	public Part3() {
		this.setId("defaultPart3");
		this.setName("ENVIRONMENT");
		this.setDescription("Make up the physical,social and attitudinal environment in which people live and conduct their lives");
		this.setScalarName(new String[]{"Complete barrier", "Severe barrier", "Moderate barrier", "Mild barrier", 
				"No barrier/facilitator", "Mild facilitator", "Moderate facilitator", "Substancial facilitator", "Complete facilitator",
                "Not applicable"}); 
		this.setScalarValue(new String[][]{{"-4","-3","-2","-1","0","1","2","3","4","9"}});
		
		SubSet subSet;
		
		subSet = new SubSet("e1","PRODUCTS AND TECHNOLOGY");
		subSet.addDomain(new Domain("e110", "For personal consumption(food,medicines)"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e115", "For personal use in daily living"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e120", "For personal  indoor and outdoor mobility and transportation"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e125", "Products for communication"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e150", "Design, construction and building products and technology of buildings for public use"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e155", "Design, construction and building products and technology of buildings for private use"));
		this.addSubSet(subSet);
		//e2
		subSet = new SubSet("e2","NATURAL ENVIRONMENT AND HUMAN MADE CHAGES TO ENVIRONMENT");
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e225", "Climate"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e240", "Light"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("e250", "Sound"));
		this.addSubSet(subSet);
		//e3
		subSet = new SubSet("e3","SUPPORT AND RELATIONSHIPS");
		this.addSubSet(subSet);
		//e4
		subSet = new SubSet("e4","ATTITUDES");
		this.addSubSet(subSet);
		//e5
		subSet = new SubSet("e5","SERVICES, SYSTEMS AND POLICIES");
		this.addSubSet(subSet);		
	}
}
