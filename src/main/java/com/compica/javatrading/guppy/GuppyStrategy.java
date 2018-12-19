package com.compica.javatrading.guppy;

import java.io.IOException;
import java.util.Date;

import com.compica.javatrading.common.TemplateStrategy;
import com.jfx.AppliedPrice;
import com.jfx.ErrAccountDisabled;
import com.jfx.ErrCommonError;
import com.jfx.ErrCustomIndicatorError;
import com.jfx.ErrHistoryWillUpdated;
import com.jfx.ErrIntegerParameterExpected;
import com.jfx.ErrInvalidAccount;
import com.jfx.ErrInvalidFunctionParamvalue;
import com.jfx.ErrInvalidPrice;
import com.jfx.ErrInvalidPriceParam;
import com.jfx.ErrInvalidStops;
import com.jfx.ErrInvalidTradeParameters;
import com.jfx.ErrInvalidTradeVolume;
import com.jfx.ErrLongPositionsOnlyAllowed;
import com.jfx.ErrLongsNotAllowed;
import com.jfx.ErrMarketClosed;
import com.jfx.ErrNoConnection;
import com.jfx.ErrNoOrderSelected;
import com.jfx.ErrNotEnoughMoney;
import com.jfx.ErrOffQuotes;
import com.jfx.ErrOldVersion;
import com.jfx.ErrOrderLocked;
import com.jfx.ErrPriceChanged;
import com.jfx.ErrRequote;
import com.jfx.ErrServerBusy;
import com.jfx.ErrShortsNotAllowed;
import com.jfx.ErrStringParameterExpected;
import com.jfx.ErrTooFrequentRequests;
import com.jfx.ErrTooManyRequests;
import com.jfx.ErrTradeContextBusy;
import com.jfx.ErrTradeDisabled;
import com.jfx.ErrTradeExpirationDenied;
import com.jfx.ErrTradeModifyDenied;
import com.jfx.ErrTradeNotAllowed;
import com.jfx.ErrTradeTimeout;
import com.jfx.ErrTradeTimeout2;
import com.jfx.ErrTradeTimeout3;
import com.jfx.ErrTradeTimeout4;
import com.jfx.ErrTradeTooManyOrders;
import com.jfx.ErrUnknownSymbol;
import com.jfx.MarketInfo;
import com.jfx.MovingAverageMethod;
import com.jfx.SelectionPool;
import com.jfx.SelectionType;
import com.jfx.Timeframe;
import com.jfx.TradeOperation;
import com.jfx.strategy.StrategyRunner;

public class GuppyStrategy extends TemplateStrategy {
	private static final double LOT_SIZE = 1.0;
	Guppy guppy = new Guppy();
	private Date lastBarTimeStamp;

	double param1;

	public GuppyStrategy(double param1) {
		this.param1 = param1;
		System.out.println("is licence limited ? " + isLimitedFunctionality());
		System.out.println();
	}
	@Override
	public void init(String symbol, int period, StrategyRunner strategyRunner) {
		try {
			super.init(symbol, period, strategyRunner);
			tradingSignal.buySignalMap.put(RulesEnum.ALIGN_MOVING_AVERAGE.getRule(), false);
			tradingSignal.buySignalMap.put("stochaticOverSold",false);
			
		} catch (ErrUnknownSymbol e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		//
		// load existing orders, recover itself from the previous shutdown
		//
	}
	@Override
	public void deinit() {
	}

	boolean isNewBar() throws ErrHistoryWillUpdated, ErrUnknownSymbol {
		Date iTime = iTime(getSymbol(), Timeframe.PERIOD_DEFAULT, 0);

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
				Guppy guppyMovingAverage = getGuppyMovingAverage();
				
				if (isAllowedToTrade()) {
					if (isBuyingTime(guppyMovingAverage)==true) {
						System.out.println("Sending a buy order");
						buy();
					}
					
					if (isSellingTime(guppyMovingAverage)==true) {
						System.out.println("Sending a sell order");
						sell();
					}
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isBuyingTime(Guppy guppyMovingAverage) {
		boolean orderedUp = guppyMovingAverage.isOrderedUp();
		boolean consolidation = guppyMovingAverage.isConsolidation();
		if(orderedUp && !consolidation && !isSellTradeAlreadyOpen()) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean isSellTradeAlreadyOpen() {
		int ordersTotal = ordersTotal();
		for(int i=0;i<ordersTotal;i++) {
			if(orderSelect(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
				try {
					long orderTicketNumber = orderTicketNumber();
					boolean orderSelect = orderSelect(orderTicketNumber, SelectionType.SELECT_BY_TICKET, SelectionPool.MODE_TRADES);
					if(orderType()==1) {
						return true;
					}
				} catch (ErrNoOrderSelected e) {
					System.out.println("that order no longer exist");
				}
			}
		}
		return false;
	}

	private boolean isSellingTime(Guppy guppyMovingAverage) {
		boolean orderedDown = guppyMovingAverage.isOrderededDown();
		if(orderedDown) {
			if(!guppyMovingAverage.isConsolidation()&&!isSellTradeAlreadyOpen()) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	
	private void buy() throws ErrUnknownSymbol, ErrInvalidFunctionParamvalue, ErrCustomIndicatorError,
			ErrStringParameterExpected, ErrIntegerParameterExpected, ErrInvalidPriceParam, ErrTradeNotAllowed,
			ErrLongsNotAllowed, ErrShortsNotAllowed, ErrCommonError, ErrInvalidTradeParameters, ErrServerBusy,
			ErrOldVersion, ErrNoConnection, ErrTooFrequentRequests, ErrAccountDisabled, ErrInvalidAccount,
			ErrTradeTimeout, ErrInvalidPrice, ErrInvalidStops, ErrInvalidTradeVolume, ErrMarketClosed, ErrTradeDisabled,
			ErrNotEnoughMoney, ErrPriceChanged, ErrOffQuotes, ErrRequote, ErrOrderLocked, ErrLongPositionsOnlyAllowed,
			ErrTooManyRequests, ErrTradeTimeout2, ErrTradeTimeout3, ErrTradeTimeout4, ErrTradeModifyDenied,
			ErrTradeContextBusy, ErrTradeExpirationDenied, ErrTradeTooManyOrders {
		double point = marketInfo(getSymbol(), MarketInfo.MODE_POINT);
		double price = marketInfo(getSymbol(), MarketInfo.MODE_ASK);
		double buyPrice = price;
		long ticket = orderSend(getSymbol(), TradeOperation.OP_BUY, LOT_SIZE, buyPrice, 2, price - 400 * point,
				price + 400 * point, "" + System.currentTimeMillis(), 0,
				new Date(System.currentTimeMillis() + 60 * 60 * 1000));
	}

	private void sell() throws ErrUnknownSymbol, ErrInvalidFunctionParamvalue, ErrCustomIndicatorError,
			ErrStringParameterExpected, ErrIntegerParameterExpected, ErrInvalidPriceParam, ErrTradeNotAllowed,
			ErrLongsNotAllowed, ErrShortsNotAllowed, ErrCommonError, ErrInvalidTradeParameters, ErrServerBusy,
			ErrOldVersion, ErrNoConnection, ErrTooFrequentRequests, ErrAccountDisabled, ErrInvalidAccount,
			ErrTradeTimeout, ErrInvalidPrice, ErrInvalidStops, ErrInvalidTradeVolume, ErrMarketClosed, ErrTradeDisabled,
			ErrNotEnoughMoney, ErrPriceChanged, ErrOffQuotes, ErrRequote, ErrOrderLocked, ErrLongPositionsOnlyAllowed,
			ErrTooManyRequests, ErrTradeTimeout2, ErrTradeTimeout3, ErrTradeTimeout4, ErrTradeModifyDenied,
			ErrTradeContextBusy, ErrTradeExpirationDenied, ErrTradeTooManyOrders {
		double point = marketInfo(getSymbol(), MarketInfo.MODE_POINT);
		double price = marketInfo(getSymbol(), MarketInfo.MODE_BID);
		double sellPrice = price;
		long ticket = orderSend(getSymbol(), TradeOperation.OP_SELL, LOT_SIZE, sellPrice, 2, price + 400 * point,
				price - 400 * point, "" + System.currentTimeMillis(), 0,
				new Date(System.currentTimeMillis() + 60 * 60 * 1000));
	}

	private boolean isAllowedToTrade() {
		if (ordersTotal() > 0) {
			return false;
		}
		return true;
	}

	private Guppy getGuppyMovingAverage() {
		try {
			guppy.setIma3(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 3, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma5(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 5, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma8(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 8, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma10(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 10, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma12(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 12, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma30(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 30, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma35(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 35, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma40(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 40, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma45(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 45, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma50(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 50, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));
			guppy.setIma200(iMA(getSymbol(), Timeframe.PERIOD_DEFAULT, 200, 0, MovingAverageMethod.MODE_SMA,
					AppliedPrice.PRICE_CLOSE, 1));

		} catch (ErrUnknownSymbol e) {
			e.printStackTrace();
		}
		return guppy;

	}
}