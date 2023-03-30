package kkwo.JAM.dao;

import java.util.Map;

import kkwo.JAM.container.Container;
import kkwo.JAM.dto.Member;
import kkwo.JAM.util.DBUtil;
import kkwo.JAM.util.SecSql;

public class MemberDao extends Dao {

	/** 회원가입 */
	public int doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", name);
		sql.append(", regDate = NOW()");
		sql.append(", updateDate = NOW()");

		int memberId = DBUtil.insert(Container.conn, sql);
		return memberId;
	}
	/** 아이디 중복 검사 */
	public boolean isExistingLoginId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		boolean isExisting = DBUtil.selectRowBooleanValue(Container.conn, sql);
		return isExisting;
	}
	/** loginId 일치하는 회원 찾기 */
	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);
		Member member = new Member(memberMap);
		
		return member;
	}
}
