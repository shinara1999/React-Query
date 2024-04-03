package com.sist.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.web.entity.Member;

public interface MemberDAO extends JpaRepository<Member, Integer>{
	// 아이디 존재 여부 확인
	@Query(value = "SELECT COUNT(*) FROM member "
			+"WHERE id=:id", nativeQuery = true)
	public int idCount(@Param("id") String id);
	
	// 아이디에 해당하는 비밀번호가 맞는지 확인
	public Member findById(String id); 
}
