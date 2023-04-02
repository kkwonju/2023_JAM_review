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
		if (memberMap.isEmpty()) {
			return null;
		}
		Member member = new Member(memberMap);

		return member;
	}

	/** 회원정보 삭제 */
	public void doDelete(int loginedMemberId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM `member`");
		sql.append("WHERE id = ?", loginedMemberId);

		DBUtil.delete(Container.conn, sql);
	}

	/** 회원정보 수정 */
	public void doModify(int loginedMemberId, String newLoginPw, String newName) {
		SecSql sql = new SecSql();

		sql.append("UPDATE `member`");
		if (newLoginPw != null || newName != null) {
			sql.append("SET");
			if (newLoginPw != null) {
				sql.append(" loginPw = ?", newLoginPw);
				if (newName != null) {
					sql.append(", `name` = ?", newName);
				}
			} else if (newName != null) {
				sql.append("`name` = ?", newName);			
			}
		}
		sql.append(", updateDate = NOW()");
		sql.append("WHERE id = ?", loginedMemberId);
		
		DBUtil.update(Container.conn, sql);
	}
}
