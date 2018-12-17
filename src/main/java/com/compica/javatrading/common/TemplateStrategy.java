package com.compica.javatrading.common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfx.ErrUnknownSymbol;
import com.jfx.MarketInfo;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;

public abstract class TemplateStrategy extends Strategy implements TradingSignal{

	private double pointValue;
	public TimeSerie timeSerie;
	public TradeManagement trademanagement;
	
	public TemplateStrategy() {
		timeSerie = new TimeSerie();
		trademanagement = new TradeManagement();
	}

	@Override
	public synchronized void init(String symbol, int period, StrategyRunner strategyRunner)
			throws ErrUnknownSymbol, IOException {
		super.init(symbol, period, strategyRunner);
		
		double price_resolution;
		try {
			price_resolution = marketInfo(getSymbol(),MarketInfo.MODE_DIGITS);
			double point;
			if(price_resolution == 5 || price_resolution == 3) {
				pointValue = 10; 
			}
			else {
				pointValue = 1;
			}
		} catch (ErrUnknownSymbol e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getPointValue() {
		return pointValue;
	}

}
