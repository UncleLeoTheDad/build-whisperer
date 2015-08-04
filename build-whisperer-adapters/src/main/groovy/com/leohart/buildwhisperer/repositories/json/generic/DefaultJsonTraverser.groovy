package com.leohart.buildwhisperer.repositories.json.generic

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class DefaultJsonTraverser implements JsonTraverser {
	
	private static final Log LOG = LogFactory.getLog(DefaultJsonTraverser.class);

	@Override
	public Object traverse(Object json, String traversalPath) {
		LOG.info("Traversing ${json} with ${traversalPath}");
		
		def result = json;		
		
		List<String> jsonSteps = traversalPath.tokenize('.');
		
		for (String jsonStep: jsonSteps){
			result = result."${jsonStep}";
		}
		
		return result;
	}

}
