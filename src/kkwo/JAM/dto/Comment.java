package kkwo.JAM.dto;

public class Comment extends Dto {
	public int memberId;
	public int articleId;
	public String body;

	
	
	
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", memberId=" + memberId + ", articleId=" + articleId + ", body=" + body
				+ ", regDate=" + regDate + ", updateDate=" + updateDate + "]";
	}
}
