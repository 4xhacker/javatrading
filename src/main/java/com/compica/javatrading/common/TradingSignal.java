package com.compica.javatrading.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TradingSignal {
	public Map<String, Boolean> buySignalMap = new TreeMap<String, Boolean>();
	public Map<String, Boolean> sellSignalMap = new TreeMap<String, Boolean>();
	
	public boolean buySignal() {
		for (Entry<String, Boolean> signalRule : buySignalMap.entrySet()) {
			if (signalRule.getValue() == false) {
				return false;
			}
			else {
				return true;
			}
		}
		
		return false;
	}

}
