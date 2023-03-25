package kkwo.JAM.container;

import kkwo.JAM.dao.ArticleDao;
import kkwo.JAM.dao.MemberDao;
import kkwo.JAM.service.ArticleService;
import kkwo.JAM.service.MemberService;

public class Container {
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	
	public static ArticleService articleService;
	public static MemberService memberService;
	
	static {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		
		articleService = new ArticleService();
		memberService = new MemberService();
	}
}
