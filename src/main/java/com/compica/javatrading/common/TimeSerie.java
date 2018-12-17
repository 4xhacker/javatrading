package com.compica.javatrading.common;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.jfx.ErrHistoryWillUpdated;
import com.jfx.ErrUnknownSymbol;
import com.jfx.Timeframe;
import com.jfx.strategy.Strategy;

public class TimeSerie {

	private Date lastBarTimeStamp;

	public boolean isNewBar(Strategy strategy) {
		Date iTime;
		try {
			iTime = strategy.iTime(strategy.getSymbol(), Timeframe.PERIOD_DEFAULT, 0);
			if (lastBarTimeStamp == null) {
				lastBarTimeStamp = iTime;
			}
			
			if (iTime.after(lastBarTimeStamp)) {
				System.out.println(iTime);
				lastBarTimeStamp = iTime;
				return true;
			} else {
				return false;

			}
		} catch (ErrHistoryWillUpdated e) {

			e.printStackTrace();
		} catch (ErrUnknownSymbol e) {
			e.printStackTrace();
		}
		return false;
	}

}
