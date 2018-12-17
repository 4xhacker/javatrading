package com.compica.javatrading.martingale;

import com.compica.javatrading.common.TemplateStrategy;
import com.jfx.ErrUnknownSymbol;
import com.jfx.MarketInfo;

public class MartingaleStrategy extends TemplateStrategy {
	
	private int magicNumber = 20181213;
	
	
	@Override
	public void deinit() {
		// TODO Auto-generated method stub
		super.deinit();
	}

	@Override
	public void init(String symbol, int period) {
		// TODO Auto-generated method stub
		super.init(symbol, period);
	}

	@Override
	public void coordinate() {
		System.out.println("got a tic");
		boolean newBar = timeSerie.isNewBar(this);
		System.out.println(getPointValue());
		trademanagement.openOrdersTotal(this, magicNumber, 1);
		
		try {
			double point = marketInfo(getSymbol(), MarketInfo.MODE_POINT);
			double digits = marketInfo(getSymbol(),MarketInfo.MODE_DIGITS);
			System.out.println();
		} catch (ErrUnknownSymbol e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean buySignal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sellSignal() {
		// TODO Auto-generated method stub
		return false;
	}
}
