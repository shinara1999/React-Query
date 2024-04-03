package com.sist.web.controller;
import com.sist.web.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.*;
@RestController
@CrossOrigin(origins = "*")
public class BoardRestController {
	// DAO객체 => Spring에서 관리
	@Autowired
	private BoardDAO bDao;
	
	// 목록 @GetMapping => axios.get
	@GetMapping("/board/list/{page}")
	public ResponseEntity<Map> boardListData(@PathVariable("page") int page)
	{
		Map map=new HashMap();
		try
		{
			int rowSize=10;
			int start=(rowSize*page)-rowSize;
			List<Board> list=bDao.boardListData(start);
			int totalpage=(int)(Math.ceil(bDao.count()/10.0));
			map.put("bList", list);
			map.put("totalpage", totalpage);
			map.put("curpage", page);
			
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	 
	// 상세보기 @GetMapping => axios.get
	@GetMapping("/board/detail/{no}")
	public ResponseEntity<Board> boardDetail(@PathVariable("no") int no)
	{
		Board board=new Board();
		try
		{
			board=bDao.findByNo(no);
			// 조회수 증가
			board.setHit(board.getHit()+1);
			bDao.save(board);
			board=bDao.findByNo(no);
			
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(board, HttpStatus.OK);
	}
	
	// 추가 @PostMapping => axios.post
	@PostMapping("/board/insert")
	public ResponseEntity<Map> boardInsert(@RequestBody Board board) // @RequestBody : 객체 단위로 받을 때 사용
	{
		Map map=new HashMap();
		try
		{
			Board _board=bDao.save(board);
			map.put("board", _board);
			map.put("msg", "yes");
			
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.CREATED); // CREATED : 데이터 정상 추가
	}
	
	// 수정 @PutMapping => axios.put
		@GetMapping("/board/update/{no}")
		public ResponseEntity<Board> boardUpdateData(@PathVariable("no") int no)
		{
			Board board=new Board();
			
			try
			{
				board=bDao.findByNo(no);
				
			}catch(Exception e)
			{
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(board, HttpStatus.OK);
		}
		@PutMapping("/board/update_ok/{no}")
		public ResponseEntity<Map> boardUpdateOk(@PathVariable("no") int no ,@RequestBody Board board) // @RequestBody : 객체 단위로 받을 때 사용
		{
			Map map=new HashMap();
			try
			{
				Board dbBoard=bDao.findByNo(no);
				if(dbBoard.getPwd().equals(board.getPwd())) // db의 pwd와 update의 pwd가 일치하는지
				{
					board.setNo(no); // 이거 안하면 수정된 글이 새글로 들어간다.
					board.setHit(dbBoard.getHit());
					Board b=bDao.save(board);
					map.put("board", b); // 수정된 내용이 들어간다.
					map.put("msg", "yes");
				}
				else
				{
					map.put("msg", "no");
				}	
			}catch(Exception e)
			{
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		
		// 삭제 @DeleteMapping => axios.delete
		@DeleteMapping("/board/delete/{no}/{pwd}")
		public ResponseEntity<Map> boardDelete(@PathVariable("no") int no, @PathVariable("pwd") String pwd)
		{
			Map map=new HashMap();
			try
			{
				Board board=bDao.findByNo(no);
				if(pwd.equals(board.getPwd())) // delete ok
				{
					bDao.delete(board);
					map.put("msg", "yes");
				}
				else // delete fail
				{
					map.put("msg", "no");
				}
			}catch(Exception e)
			{
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	
	
	// @PathVariable /{} => url 주소 뒤에 값을 넣어 보내주는 것
}
