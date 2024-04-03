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

import com.sist.web.dao.MapoNatureDAO;
import com.sist.web.entity.Mapoculture;
import com.sist.web.entity.Mapohotel;
import com.sist.web.entity.Maponature;

@RestController
@CrossOrigin(origins = "*")
public class MapoNatureRestController {
	@Autowired
	private MapoNatureDAO dao;
	
	@GetMapping("/nature/list/{page}")
	public ResponseEntity<Map> natureListData(@PathVariable("page") int page)
	{
		
		Map map=new HashMap();
		try
		{
			int rowSize=12;
			int start=(rowSize*page)-rowSize;
			List<Maponature> list=dao.natureListData(start);
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
	@GetMapping("/nature/detail/{nno}")
	public ResponseEntity<Maponature> nature_detail(@PathVariable("nno") int nno)
	{
		Maponature nature=new Maponature();
		try
		{
			nature=dao.findByNno(nno);
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(nature, HttpStatus.OK);
	}
}
