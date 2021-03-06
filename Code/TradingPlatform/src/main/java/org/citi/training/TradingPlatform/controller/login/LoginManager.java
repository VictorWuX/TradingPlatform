package org.citi.training.TradingPlatform.controller.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.citi.training.TradingPlatform.module.trader.SelectSpecifiedTrader;
import org.citi.training.TradingPlatform.module.trader.Trader;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class LoginManager implements Login{
	
	private SelectSpecifiedTrader selectSpecifiedTraderService;

	public void setSelectSpecifiedTraderService(SelectSpecifiedTrader selectSpecifiedTraderService) {
		this.selectSpecifiedTraderService = selectSpecifiedTraderService;
	}

	public boolean login(String traderName, String password) {
		Trader trader = selectSpecifiedTraderService.getTrader(traderName);
		if(trader == null) {
			return false;
		}
		String passwordEncoderByMd5 = encoderByMd5(password);
		boolean result = trader.getPassword().equals(passwordEncoderByMd5);
		return result;
	}
	
	private String encoderByMd5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		BASE64Encoder base64en = new BASE64Encoder();
		String newstr = null;
		try {
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}
}
