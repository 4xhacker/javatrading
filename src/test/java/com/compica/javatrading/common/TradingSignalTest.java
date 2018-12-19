package com.compica.javatrading.common;

import static org.junit.Assert.*;

import org.junit.Test;

import com.compica.javatrading.guppy.RulesEnum;

public class TradingSignalTest {

	@Test
	public void testAllRulesAreFalse() {
		TradingSignal ts = new TradingSignal();
		ts.buySignalMap.put(RulesEnum.ALIGN_MOVING_AVERAGE.getRule(), false);
		
		boolean buySignal = ts.buySignal();
		assertTrue("All rules are false so answer should also be false ", buySignal==false);

	}
	
	@Test
	public void testAllRulesAreTrue() {
		TradingSignal ts = new TradingSignal();
		ts.buySignalMap.put(RulesEnum.ALIGN_MOVING_AVERAGE.getRule(), true);
		
		boolean buySignal = ts.buySignal();
		assertTrue("All rules are true so answer should also be true ", buySignal==false);

	}
	
}
