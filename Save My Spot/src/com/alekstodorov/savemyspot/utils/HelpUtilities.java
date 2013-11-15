package com.alekstodorov.savemyspot.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelpUtilities  {
	
	public static final String TAG = "SaveMySpotTag";
	public static final String SPOT_ID = "com.alekstodorov.savemyspot.spotId";
	public static final int CREATOR_ACTIVITY_REQUEST = 1001;
	public static final int MENU_DELETE_ID = 1002;
	private static final String USERNAME_AVAILABLE_SYMBOLS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890_.";
	private static final String PASSWORD_AVAILABLE_SYMBOLS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890_.!@$";
	private static final String TITLE_AVAILABLE_SYMBOLS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890_-.!@$? ";

	private static String convertToHex(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (byte b : data) {
			int halfbyte = (b >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte)
						: (char) ('a' + (halfbyte - 10)));
				halfbyte = b & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String encodePassword(String text)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		byte[] sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	public static void validateUsername(String username)
			throws SecurityException {

		if (username.length() < 5 || username.length() > 15) {
 
			throw new SecurityException(
					"Username must be between 5 and 15 symbols!");
		}

		for (int i = 0; i < username.length(); i++) {

			String ch = Character.toString(username.charAt(i));

			if (!USERNAME_AVAILABLE_SYMBOLS.contains(ch)) {

				throw new SecurityException(
						"Username contains invalid symbols!");
			}
		}
	}

	public static void validatePassword(String password) {

		if (password.length() < 5 || password.length() > 20) {

			throw new SecurityException(
					"Password must be between 5 and 20 symbols!");
		}

		for (int i = 0; i < password.length(); i++) {

			String ch = Character.toString(password.charAt(i));

			if (!PASSWORD_AVAILABLE_SYMBOLS.contains(ch)) {

				throw new SecurityException(
						"Password contains invalid symbols!");
			}
		} 
	}
	
	public static void validateTitle(String title)
			throws SecurityException {

		if (title.length() < 5 || title.length() > 35) {
 
			throw new SecurityException(
					"Title must be between 5 and 35 symbols!");
		}

		for (int i = 0; i < title.length(); i++) {

			String ch = Character.toString(title.charAt(i));

			if (!TITLE_AVAILABLE_SYMBOLS.contains(ch)) {

				throw new SecurityException(
						"Title contains invalid symbols!");
			}
		}
	}
}
