package com.leohart.buildwhisperer.repositories.json.generic;

public interface JsonTraverser {
	
	Object traverse(Object json, String traversalPath);

}
