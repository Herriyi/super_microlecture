package org.sicdlab.microlecture.business.favorite.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import org.sicdlab.microlecture.business.favorite.service.FavoriteService;
import org.sicdlab.microlecture.common.bean.Favorite;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.tag.pageTag.PageHelper;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.marswork.core.exceptions.messaging.MarsException;

@Controller
public class FavoriteController {
	@Autowired
	private FavoriteService service;

	@RequestMapping("addFavorite.htm")
	public ModelAndView addFavorite(HttpServletRequest req,
			HttpServletResponse res) throws MarsException {

		String url = ServletRequestUtils.getStringParameter(req, "url", "");
		String title = ServletRequestUtils.getStringParameter(req, "title", "");
		String objectId=ServletRequestUtils.getStringParameter(req, "objectId", "");
		ModelMap map = new ModelMap();
		map.addAttribute("url", url);
		map.addAttribute("title", title);
		map.addAttribute("objectId", objectId);
		return new ModelAndView("/favorite/addFavorite", map);
	}
	
	@RequestMapping("createFavorite.htm")
	public ModelAndView createFavorite(HttpServletRequest req,
			HttpServletResponse res) throws MarsException {
		User user=(User)req.getSession().getAttribute("user");
		String url = ServletRequestUtils.getStringParameter(req, "url","");
		String title = ServletRequestUtils.getStringParameter(req, "title","");
		String objectId=ServletRequestUtils.getStringParameter(req, "objectId", "");
		Date favoriteDate = new Date();

		Favorite favorite=new Favorite();
		favorite.setUser(user);
		favorite.setFavoriteId(UUIDGenerator.randomUUID());
		favorite.setFavoriteDate(favoriteDate);
		favorite.setUrl(url);
		favorite.setFavoriteName(title);
		favorite.setObjectId(objectId);
		service.save(favorite);
		
		return new ModelAndView("/common/outSuccess");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("myFavotite.htm")
	public ModelAndView myFavotite(HttpServletRequest req,HttpServletResponse res) throws MarsException{
		
		User user=(User)req.getSession().getAttribute("user");
		DetachedCriteria dCriteria=DetachedCriteria.forClass(Favorite.class)
				.add(Restrictions.eq("user",user));
				
		int pageSize=5;
		int totalPage=service.countTotalPage(dCriteria, pageSize);
		PageHelper.forPage(totalPage, pageSize);			
		List<Favorite> favorites=(List<Favorite>)service.getByPage(dCriteria, pageSize);
		req.setAttribute("favorites", favorites);
		return new ModelAndView("/favorite/myFavorite");
	}
	
	@RequestMapping("deleteFavotite.htm")
	public ModelAndView ddeleteFavotite(HttpServletRequest req,HttpServletResponse res) throws MarsException {
		
		String favoriteId=ServletRequestUtils.getStringParameter(req, "favoriteId", "");
		Favorite favorite=service.findById(Favorite.class,favoriteId);
		
		service.delete(favorite);
		
		return new ModelAndView("redirect:myFavotite.htm");
	}
}
