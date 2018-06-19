package com.skcc.cloudz.zdb.config;

public class ApiTemplate {

	public static String MATCHER_PATTERN = "\\{\\{([a-zA-Z]+)\\}\\}";

	public static String getCreateDeployment(String serviceType) {
    	StringBuffer sbfTemplate = new StringBuffer();
    	
    	if(CommonConstants.SERVICE_TYPE_REDIS.equals(serviceType)) {
        	sbfTemplate.append("{                                                        ");
        	sbfTemplate.append("  \"version\": {{version}},                              ");
        	sbfTemplate.append("  \"serviceType\": {{serviceType}},                      ");
        	sbfTemplate.append("  \"serviceName\": {{serviceName}},                      ");
        	sbfTemplate.append("  \"namespace\": {{namespace}},                          ");
        	sbfTemplate.append("  \"podSpec\": [                                         ");
        	sbfTemplate.append("    {                                                    ");
        	sbfTemplate.append("      \"podType\": \"master\",                           ");
        	sbfTemplate.append("      \"resourceSpec\": [                                ");
        	sbfTemplate.append("        {                                                ");
        	sbfTemplate.append("          \"resourceType\": \"requests\",                ");
        	sbfTemplate.append("          \"cpu\": {{cpu}},                          ");
        	sbfTemplate.append("          \"memory\": {{memory}}                     ");
        	sbfTemplate.append("        },                                               ");
        	sbfTemplate.append("        {                                                ");
        	sbfTemplate.append("          \"resourceType\": \"limits\",                  ");
        	sbfTemplate.append("          \"cpu\": {{cpu}},                          ");
        	sbfTemplate.append("          \"memory\": {{memory}}                     ");
        	sbfTemplate.append("        }                                                ");
        	sbfTemplate.append("      ]                                                  ");
        	sbfTemplate.append("    }                                                    ");
        	sbfTemplate.append("  ],                                                     ");
        	sbfTemplate.append("  \"serviceSpec\": [                                     ");
        	sbfTemplate.append("    {                                                    ");
        	sbfTemplate.append("      \"podType\": \"master\",                           ");
        	sbfTemplate.append("      \"loadBalancerType\": {{exposeType}}               ");
        	sbfTemplate.append("    }                                                    ");
        	sbfTemplate.append("  ],                                                     ");
        	sbfTemplate.append("  \"purpose\": {{purpose}}                               ");    		
        	sbfTemplate.append("}                                                        ");    		
    	}else if(CommonConstants.SERVICE_TYPE_MARIA.equals(serviceType)) {
    		sbfTemplate.append(" {                                                       ");
    		sbfTemplate.append("   \"serviceType\": {{serviceType}} ,     			     ");                       
    		sbfTemplate.append("   \"serviceName\": {{serviceName}} ,				     ");                                          
    		sbfTemplate.append("   \"namespace\": {{namespace}},                         ");                           
    		sbfTemplate.append("   \"clusterEnabled\": true,                             ");                     
    		sbfTemplate.append("   \"podSpec\": [                                        ");          
    		sbfTemplate.append("     {                                                   "); 
    		sbfTemplate.append("       \"podType\": \"master\",                          ");                      
    		sbfTemplate.append("       \"resourceSpec\": [                               ");                   
    		sbfTemplate.append("         {                                               ");     
    		sbfTemplate.append("           \"resourceType\": \"requests\",               ");                                 
    		sbfTemplate.append("           \"cpu\": {{cpu}},                             ");                    
    		sbfTemplate.append("           \"memory\": {{memory}}                        ");                       
    		sbfTemplate.append("         }                                               ");     
    		sbfTemplate.append("       ]                                                 ");   
    		sbfTemplate.append("     },                                                  ");  
    		sbfTemplate.append("     {                                                   "); 
    		sbfTemplate.append("       \"podType\": \"slave\",                           ");                     
    		sbfTemplate.append("       \"resourceSpec\": [                               ");                   
    		sbfTemplate.append("         {                                               ");     
    		sbfTemplate.append("           \"resourceType\": \"requests\",               ");                                 
    		sbfTemplate.append("           \"cpu\": {{cpu}},	                         ");                    
    		sbfTemplate.append("           \"memory\": {{memory}} 	                     ");                       
    		sbfTemplate.append("         }                                               ");     
    		sbfTemplate.append("       ]                                                 ");   
    		sbfTemplate.append("     }                                                   "); 
    		sbfTemplate.append("   ],                                                    ");
        	sbfTemplate.append("  \"serviceSpec\": [                                     ");
        	sbfTemplate.append("    {                                                    ");
        	sbfTemplate.append("      \"loadBalancerType\": {{exposeType}}               ");
        	sbfTemplate.append("    }                                                    ");
        	sbfTemplate.append("  ],                                                     ");  
    		sbfTemplate.append("   \"persistenceSpec\": [                                ");                  
    		sbfTemplate.append("     {                                                   "); 
    		sbfTemplate.append("       \"podType\": \"master\",                          ");                      
    		sbfTemplate.append("       \"size\": {{disk}}                                ");                
    		sbfTemplate.append("     },                                                  ");  
    		sbfTemplate.append("     {                                                   "); 
    		sbfTemplate.append("       \"podType\": \"slave\",                           ");                     
    		sbfTemplate.append("       \"size\": {{disk}}                                ");                
    		sbfTemplate.append("     }                                                   "); 
    		sbfTemplate.append("   ],                                                    ");
    		sbfTemplate.append("   \"mariaDBConfig\":                                    ");              
    		sbfTemplate.append("     {                                                   "); 
    		sbfTemplate.append("       \"mariadbDatabase\": {{dbName}}                   ");                             
    		sbfTemplate.append("     }                                                   "); 
    		sbfTemplate.append(" }                                                       ");
    	}                                                                                
    	return sbfTemplate.toString();
	}
	
	public static String getUpdateScaleTemplate(String serviceType) {
		StringBuffer sbfTemplate = new StringBuffer();
		
		if(CommonConstants.SERVICE_TYPE_REDIS.equals(serviceType)) {
			sbfTemplate.append("{                                                        ");
		}else if(CommonConstants.SERVICE_TYPE_MARIA.equals(serviceType)) {
			sbfTemplate.append("{                                                        ");
			sbfTemplate.append("  \"serviceType\": {{serviceType}},                      ");
			sbfTemplate.append("  \"serviceName\": {{serviceName}},                      ");
			sbfTemplate.append("  \"namespace\": {{namespace}},                          ");
			sbfTemplate.append("  \"podSpec\": [                                         ");
			sbfTemplate.append("    {                                                    ");
			sbfTemplate.append("      \"podType\": \"master\",                           ");
			sbfTemplate.append("      \"resourceSpec\": [                                ");
			sbfTemplate.append("        {                                                ");
			sbfTemplate.append("          \"resourceType\": \"requests\",                ");
			sbfTemplate.append("          \"cpu\": {{cpu}},                              ");
			sbfTemplate.append("          \"memory\": {{memory}}                         ");
			sbfTemplate.append("        },                                               ");
			sbfTemplate.append("        {                                                ");
			sbfTemplate.append("          \"resourceType\": \"limit\",                   ");
			sbfTemplate.append("          \"cpu\": {{cpu}},                              ");
			sbfTemplate.append("          \"memory\":{{memory}}                          ");
			sbfTemplate.append("        }                                                ");
			sbfTemplate.append("      ]                                                  ");
			sbfTemplate.append("    },                                                   ");
			sbfTemplate.append("    {                                                    ");
			sbfTemplate.append("      \"podType\": \"slave\",                            ");
			sbfTemplate.append("      \"resourceSpec\": [                                ");
			sbfTemplate.append("        {                                                ");
			sbfTemplate.append("          \"resourceType\": \"requests\",                ");
			sbfTemplate.append("          \"cpu\": {{cpu}},                              ");
			sbfTemplate.append("          \"memory\":{{memory}}                          ");
			sbfTemplate.append("        },                                               ");
			sbfTemplate.append("        {                                                ");
			sbfTemplate.append("          \"resourceType\": \"limit\",                   ");
			sbfTemplate.append("          \"cpu\": {{cpu}},                              ");
			sbfTemplate.append("          \"memory\":{{memory}}                          ");
			sbfTemplate.append("        }                                                ");
			sbfTemplate.append("      ]                                                  ");
			sbfTemplate.append("    }                                                    ");
			sbfTemplate.append("  ]                                                      ");
			sbfTemplate.append(" }                                                       ");
		}                                                                                
		return sbfTemplate.toString();
		
	}
}
