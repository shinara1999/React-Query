package com.sist.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.MapoCultureDAO;
import com.sist.web.dao.MapoNatureDAO;
import com.sist.web.entity.Mapoculture;
import com.sist.web.entity.Maponature;

@RestController
@CrossOrigin(origins = "*")
public class MapoCultureRestController {
	@Autowired
	private MapoCultureDAO dao;
	
	@GetMapping("/culture/list/{page}")
	public ResponseEntity<Map> cultureListData(@PathVariable("page") int page)
	{
		
		Map map=new HashMap();
		try
		{
			int rowSize=12;
			int start=(rowSize*page)-rowSize;
			List<Mapoculture> list=dao.cultureListData(start);
			int count=(int)dao.count();
			map.put("list", list);
			map.put("count", count);
			map.put("curpage", page);
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	

	// 상세보기
	@GetMapping("/culture/detail/{cno}")
	public ResponseEntity<Mapoculture> culture_detail(@PathVariable("cno") int cno)
	{
		Mapoculture culture=new Mapoculture();
		try
		{
			culture=dao.findByCno(cno);
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(culture, HttpStatus.OK);
	}
}	
