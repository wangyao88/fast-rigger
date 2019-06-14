package com.sxkl.fastrigger.commoner.utils;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统公用模块 rsa公私钥管理类
 * @author wy
 * @date 2019-06-10 11:14:52
 */
@Service
public class RsaKeyManager {
	
	private static Map<String,String> key = new HashMap<String,String>();
	private static final String PUBLIC_KEY = "publicKey";
	private static final String PRIVATE_KEY = "privateKey";
	
	@PostConstruct
	public void initialization() throws Exception{
		 Map<String, Key> keyMap = RSACoder.initKey();
	     String publicKey = RSACoder.getPublicKey(keyMap);
	     String privateKey = RSACoder.getPrivateKey(keyMap);
	     key.put(PUBLIC_KEY, publicKey);
	     key.put(PRIVATE_KEY, privateKey);
	}
	
	public static String getPublickey(){
		return key.get(PUBLIC_KEY);
	}
	
	public static String getPrivateKey(){
		return key.get(PRIVATE_KEY);
	}
}
