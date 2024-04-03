package com.sist.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.web.entity.Board;

public interface BoardDAO extends JpaRepository<Board, Integer>{
	// 목록
	@Query(value = "SELECT * FROM board "
				 +"ORDER BY no DESC "
			     +"LIMIT :start, 10", nativeQuery = true)
	public List<Board> boardListData(@Param("start") int start);
	
	// count  => 총페이지
	// update => save
	// delete => delete
	//public Board deleteByNo(int no);
	// insert => save
	
	// 상세보기
	public Board findByNo(int no);
}
