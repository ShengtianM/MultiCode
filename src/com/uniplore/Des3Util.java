/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 */
package com.uniplore;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/** 
 * 3DES加密工具类  返回的加密字符串的长度为 [n/3]*4, n/3向上取整
 */
public class Des3Util {
	// 密钥
	private final static String secretKey = "xUnsd1HB4A1dgfd4ads23asW";
	// 向量
	private final static String iv = "********";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	/** 
	 * 3DES加密 
	 *  
	 * @param plainText 普通文本 
	 * @return 
	 * @throws Exception  
	 */
	public synchronized static String encode(String plainText) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return Base64.getEncoder().encodeToString(encryptData);
	}

	/** 
	 * 3DES解密 
	 *  
	 * @param encryptText 加密文本 
	 * @return 
	 * @throws Exception 
	 */
	public synchronized static String decode(String encryptText) throws Exception {
		
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText));
		return new String(decryptData, encoding);
	}

} 
