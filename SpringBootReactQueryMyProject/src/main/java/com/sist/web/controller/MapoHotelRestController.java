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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.MapoHotelDAO;
import com.sist.web.entity.Mapoculture;
import com.sist.web.entity.Mapohotel;
import com.sist.web.entity.Maponature;

@RestController
@CrossOrigin(origins = "*")
public class MapoHotelRestController {
	@Autowired
	private MapoHotelDAO dao;
	
	@GetMapping("/hotel/list/{page}")
	public ResponseEntity<Map> hotelListData(@PathVariable("page") int page)
	{
		
		Map map=new HashMap();
		try
		{
			int rowSize=12;
			int start=(rowSize*page)-rowSize;
			List<Mapohotel> list=dao.hotelListData(start);
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
	@GetMapping("/hotel/detail/{hno}")
	public ResponseEntity<Mapohotel> hotel_detail(@PathVariable("hno") int hno)
	{
		Mapohotel hotel=new Mapohotel();
		try
		{
			hotel=dao.findByHno(hno);
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(hotel, HttpStatus.OK);
	}
	
	// 찾기
	@GetMapping("/hotel/find/{page}/{address}")
	public ResponseEntity<Map> hotel_find(@PathVariable("page") int page, @PathVariable("address") String address)
	{
		Map map=new HashMap();
		try
		{
			int rowSize=9;
			int start=(rowSize*page)-rowSize; // 0
			List<Mapohotel> hList=dao.hotelFindData(page, address);
			int count=(int)dao.count();
			map.put("hList", hList);
			map.put("count", count);
			map.put("curpage", page);
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
