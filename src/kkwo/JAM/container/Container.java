package kkwo.JAM.container;

import java.sql.Connection;
import java.util.Scanner;

import kkwo.JAM.controller.ArticleController;
import kkwo.JAM.controller.MemberController;
import kkwo.JAM.dao.ArticleDao;
import kkwo.JAM.dao.CommentDao;
import kkwo.JAM.dao.MemberDao;
import kkwo.JAM.dto.Comment;
import kkwo.JAM.service.ArticleService;
import kkwo.JAM.service.CommentService;
import kkwo.JAM.service.MemberService;

public class Container {
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
	public static CommentDao commentDao;
	
	public static ArticleService articleService;
	public static MemberService memberService;
	public static CommentService commentService;
	
	public static ArticleController articleController;
	public static MemberController memberController;
	
	public static Connection conn;
	
	public static Scanner sc;
	
	public static void init() {
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		commentDao = new CommentDao();
		
		articleService = new ArticleService();
		memberService = new MemberService();
		commentService = new CommentService();
		
		articleController = new ArticleController();
		memberController = new MemberController();
	}
}
