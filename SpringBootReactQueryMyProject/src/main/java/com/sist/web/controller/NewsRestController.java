package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.entity.NewsVO;
import com.sist.web.manager.*;
@RestController
@CrossOrigin(origins = "*")
public class NewsRestController {
	@Autowired
	private NewsManager mgr;
	
	// 뉴스검색
	@GetMapping("/news/list/{fd}")
	public ResponseEntity<List<NewsVO>> news_list(@PathVariable("fd") String fd)
	{
		List<NewsVO> list=new ArrayList<NewsVO>();
		try
		{
			list=mgr.newsFind(fd);
			
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
