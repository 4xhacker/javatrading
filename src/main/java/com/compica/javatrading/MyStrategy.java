package com.compica.javatrading;

import java.io.IOException;
import java.util.Date;

import com.jfx.AppliedPrice;
import com.jfx.ErrHistoryWillUpdated;
import com.jfx.ErrUnknownSymbol;
import com.jfx.MarketInfo;
import com.jfx.MovingAverageMethod;
import com.jfx.Timeframe;
import com.jfx.TradeOperation;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;

public class MyStrategy extends Strategy {
	static { System.setProperty("nj4x_activation_key", "473514972"); }
	private Date lastBarTimeStamp;
	double param1;

	public MyStrategy(double param1) {
		this.param1 = param1;
	}

	public void init(String symbol, int period, StrategyRunner strategyRunner) {
		try {
			super.init(symbol, period, strategyRunner);
		} catch (ErrUnknownSymbol e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		//
		// load existing orders, recover itself from the previous shutdown
		//
	}

	public void deinit() {
		// release resources on EA exit
	}

	boolean isNewBar() throws ErrHistoryWillUpdated, ErrUnknownSymbol {
		Date iTime = iTime(getSymbol(), Timeframe.PERIOD_DEFAULT, 0);

		if (lastBarTimeStamp == null) {
			lastBarTimeStamp = iTime;
		}
		if (iTime.after(lastBarTimeStamp)) {
			lastBarTimeStamp = iTime;
			System.out.println("new bar" + iTime);
			return true;
		} else {
			return false;

		}

	}

	@Override
	public void coordinate() {
		// trading logic goes here
		/*
		 * make use of all API methods: accountBalance, accountCompany, accountCredit,
		 * accountCurrency, accountEquity, accountFreeMargin, accountMargin,
		 * accountName, accountNumber, accountProfit, comment, day, dayOfWeek,
		 * dayOfYear, getLastError, getTickCount, hour, iAC, iAD, iADX, iAlligator, iAO,
		 * iATR, iBands, iBars, iBarShift, iBearsPower, iBullsPower, iBWMFI, iCCI,
		 * iClose, iCustom, iDeMarker, iEnvelopes, iForce, iFractals, iGator, iHigh,
		 * iHighest, iLow, iLowest, iMA, iMACD, iMFI, iMomentum, iOBV, iOpen, iOsMA,
		 * iRSI, iRVI, iSAR, isConnected, isDemo, iStdDev, isTesting, iStochastic,
		 * isTradeContextBusy, isVisualMode, iTime, iVolume, iWPR, marketInfo, minute,
		 * month, objectCreate, objectCreate, objectCreate, objectDelete, objectGet,
		 * objectGetFiboDescription, objectSet, objectSetFiboDescription, objectSetText,
		 * objectsTotal, objectType, orderClose, orderCloseBy, orderClosePrice,
		 * orderCloseTime, orderComment, orderCommission, orderDelete, orderExpiration,
		 * orderLots, orderMagicNumber, orderModify, orderOpenPrice, orderOpenTime,
		 * orderPrint, orderProfit, orderSelect, orderSend,ordersHistoryTotal,
		 * orderStopLoss, ordersTotal, orderSwap, orderSymbol, orderTakeProfit,
		 * orderTicket, orderType, print, refreshRates, seconds, timeCurrent, year
		 */try {
			if (isNewBar()) {
				double iMA = iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 21, 0, MovingAverageMethod.MODE_SMA,
						AppliedPrice.PRICE_CLOSE, 0);
				print(Double.toString(iMA));

				double point = marketInfo("EURUSD", MarketInfo.MODE_POINT);
				double price = marketInfo(getSymbol(), MarketInfo.MODE_ASK);

				double buyPrice = price;
				int ticket = orderSend("EURUSD", TradeOperation.OP_BUY, 0.01, buyPrice, 2, price - 100 * point,
						price + 100 * point, "" + System.currentTimeMillis(), 0,
						new Date(System.currentTimeMillis() + 60 * 60 * 1000), -1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}