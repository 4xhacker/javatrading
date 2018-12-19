package com.compica.javatrading.guppy;

public enum RulesEnum {
	ALIGN_MOVING_AVERAGE("alignMovingAverage"),
	MOVING_AVERAGE_EXPANSION("movingAverageExpansion"),
	STOCHASTIC_OVERBOUGHT("stochasticOverbought");


	private String rule;
	
	RulesEnum(String ruleEnum){
		this.rule = ruleEnum;
		
	}
	
	public String getRule() {
		return rule;
	}
}
