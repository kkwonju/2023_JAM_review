package kkwo.JAM.controller;

import kkwo.JAM.dto.Member;

public abstract class Controller {

	public static Member loginedMember = null;

	public static boolean isLoginCheck() {
		if (loginedMember == null) {
			return false;
		}
		return true;
	}

	public abstract void action(String actionMethodName, String command);

}
