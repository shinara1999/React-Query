package com.sist.web.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity//(name="jpaboard") // 이름 설정
@DynamicUpdate // updatable = false 줄 때 사용
@Data
public class Board {
	
	@Id
	private int no;
	
	// 인서트 업데이트 모두 가능
	private String name;
	private String subject;
	private String content;
	
	@Column(insertable = true, updatable = false) // 인서트 가능, 업데이터 불가능
	private String pwd;
	
	@Column(insertable = true, updatable = false)
	private String regdate;
	
	// 인서트 업데이트 모두 가능 (증가되야 한다.)
	private int hit;
	
	@PrePersist
	public void regdate()
	{
		this.regdate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
