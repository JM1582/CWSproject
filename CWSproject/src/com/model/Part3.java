package com.model;
import java.util.*;

public class Part3 extends OnePart{
	
	public Part3() {
		this.setPartId("defaultPart3");
		this.setPartName("ENVIRONMENT");
		this.setPartDescription("Make up the physical,social and attitudinal environment in which people live and conduct their lives");
		this.setScalarName(new String[]{"Complete barrier", "Severe barrier", "Moderate barrier", "Mild barrier", 
				"No barrier/facilitator", "Mild facilitator", "Moderate facilitator", "Substancial facilitator", "Complete facilitator",
                "Not applicable"}); 
		this.setScalarValue(new String[][]{{"-4","-3","-2","-1","0","1","2","3","4","9"}});
		
		SubSet subSet;
		
		subSet = new SubSet("e1","PRODUCTS AND TECHNOLOGY");
		subSet.addDomain(new Domain("e110", "For personal consumption(food,medicines)"));
		this.addSubSet(subSet);
		//e2
		subSet = new SubSet("e2","NATURAL ENVIRONMENT AND HUMAN MADE CHAGES TO ENVIRONMENT");
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
