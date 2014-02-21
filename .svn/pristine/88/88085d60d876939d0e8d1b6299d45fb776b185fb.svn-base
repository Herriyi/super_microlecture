package org.sicdlab.microlecture.business.shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.shop.service.ShopService;
import org.sicdlab.microlecture.business.user.service.UserControllerService;
import org.sicdlab.microlecture.common.bean.Goods;
import org.sicdlab.microlecture.common.bean.Level;
import org.sicdlab.microlecture.common.bean.Note;
import org.sicdlab.microlecture.common.bean.Prop;
import org.sicdlab.microlecture.common.bean.Rule;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.bean.UserProp;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShopController {

	@Autowired
	private UserControllerService  uservice;
	@Autowired
	private ShopService  shopservice;
	
	@RequestMapping("goshop.htm")
	public ModelAndView goshop(){
		
		return new ModelAndView("/shop/shop");
		
	}
	@RequestMapping("gobuyprop.htm")
	public ModelAndView gobuyprop(){
		
		return new ModelAndView("/shop/buyprop");
		
	}
	@RequestMapping("gobuynote.htm")
	public ModelAndView gobuynote(){
		
		return new ModelAndView("/shop/gobuynote");
		
	}
	
	
	//公共方法
	//得到等级
	public Level getUserInfo(HttpServletRequest request,HttpServletResponse response){
		String userid=request.getSession().getAttribute("userId").toString();
		User user=new User();
		user=(User)uservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", userid)).uniqueResult();
		int credit=user.getCredit();
		System.out.println("credit ::"+credit);
		Level level= uservice.getUserLevel(credit);
		
		request.setAttribute("user", user);
		request.setAttribute("level", level);
		return level;
	}
	
	@RequestMapping("shop.htm")
	public ModelAndView shop(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String message=null;
		HttpSession hs=request.getSession();
		User user = (User) hs.getAttribute("user");
		//获取商品列表，显示在页面
		List<Goods> shopprop;
		shopprop=shopservice.getprop("prop");
		List<Goods> shopnote;
		shopnote=shopservice.getnote("note");
		System.out.println("-------------"+shopprop+"========"+shopnote);
		//更新用户等级
		getUserInfo(request,response);
		hs.removeAttribute("user");
		hs.setAttribute("user", user);
		request.setAttribute("shopprop", shopprop);
		request.setAttribute("shopnote", shopnote);
		System.out.println("this is shoppropthis is shoppropthis is shopprop"+shopprop.size());
		return new ModelAndView("forward:goshop.htm","message",message);
		
	}
	@RequestMapping("buyprop.htm")
	public ModelAndView buyprop(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String message=null;
		message="请仔细校验交易数额";
		HttpSession hs=request.getSession();
		User user = (User) hs.getAttribute("user");
		String id=request.getParameter("id");
		Goods goods=(Goods) shopservice.getCurrentSession().createCriteria(Goods.class).add(Restrictions.eq("id", id)).uniqueResult();
		//提示用户金币数量是否足够
		String buymessage;
		//判断道具数量是否足够
		int number=Integer.parseInt(request.getParameter("buynumber"));
		if(number>goods.getNumber()){
			message="购买数量大于剩余数量，请修改数量";
			return new ModelAndView("forward:goshop.htm","message",message);
		}
		//判断用户是否有购买权限
	    //获取购买页面应付金币数额和用户现有金币数额
		int golds=number*goods.getPrice();
		Rule rule=(Rule) shopservice.getCurrentSession().createCriteria(Rule.class).add(Restrictions.eq("action", "购买道具")).uniqueResult();
		if(golds>user.getGold()){
			buymessage="金币不足，还需"+(golds-user.getGold())+"个";
		}else{
			buymessage="可以支付";}
		
		//将获得的积分
		int credits=number*rule.getCredit();
		request.setAttribute("credits", credits);
		request.setAttribute("golds", golds);
		//获取道具信息并传递
		Prop prop=(Prop)shopservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propId", goods.getGoodsObject())).uniqueResult();
        request.setAttribute("prop", prop);
        request.setAttribute("type", "道具");
		//传递商品信息
        request.setAttribute("number", number);  //购买数量
        request.setAttribute("buymessage", buymessage);
        request.setAttribute("goods",goods);
		getUserInfo(request,response);
		hs.removeAttribute("user");
		hs.setAttribute("user", user);
		return new ModelAndView("forward:gobuyprop.htm","message",message);
		
	}
	@RequestMapping("buypropconfig.htm")
	public ModelAndView buypropconfig(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String message=null;
		HttpSession hs=request.getSession();
		User user = (User) hs.getAttribute("user");
		if(user==null){
			user=(User) shopservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("id", "xox")).uniqueResult();
			
		}
		String id=request.getParameter("goods");
		Goods goods=(Goods) shopservice.getCurrentSession().createCriteria(Goods.class).add(Restrictions.eq("id", id)).uniqueResult();
		
		int number=Integer.parseInt(request.getParameter("number"));
		//交易成功,修改用户积分和金币信息
		int golds=number*goods.getPrice();
		Rule rule=(Rule) shopservice.getCurrentSession().createCriteria(Rule.class).add(Restrictions.eq("action", "购买道具")).uniqueResult();
		int credit=user.getCredit()+number*rule.getCredit();
		user.setCredit(credit);
		user.setGold(user.getGold()-golds);
		shopservice.update(user);
		//修改商品信息
		goods.setNumber(goods.getNumber()-number);
		goods.setUserByCustomerId(user);
		shopservice.update(goods);
		//修改用户道具表
		String propId=goods.getGoodsObject();
		Prop prophehe=(Prop) shopservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propId", propId)).uniqueResult();
		UserProp up=(UserProp) shopservice.getCurrentSession().createCriteria(UserProp.class).add(Restrictions.eq("user", user)).add(Restrictions.eq("prop", prophehe)).uniqueResult();
		if(up==null){
			UserProp userprop=new UserProp();
			userprop.setUserPropId(UUIDGenerator.randomUUID());
			userprop.setNumber(number);
			Prop prop=(Prop)shopservice.getCurrentSession().createCriteria(Prop.class).add(Restrictions.eq("propId", goods.getGoodsObject())).uniqueResult();
			userprop.setProp(prop);
			userprop.setUser(user);
			shopservice.save(userprop);
		}else{
			int nu=up.getNumber();
			up.setNumber(nu+number);
			shopservice.update(up);
		}
		
		//获取商品列表，显示在页面
		List<Goods> shopprop;
		shopprop=shopservice.getprop("prop");
		List<Goods> shopnote;
		shopnote=shopservice.getnote("note");
		request.setAttribute("shopprop", shopprop);
		request.setAttribute("shopnote", shopnote);
		
		message="恭喜您，购买成功";//购买成功提示信息
		getUserInfo(request,response);
		hs.removeAttribute("user");
		hs.setAttribute("user", user);
		request.setAttribute("message", message);
		return new ModelAndView("forward:goshop.htm","message",message);
		
	}
	@RequestMapping("buynote.htm")
	public ModelAndView buynote(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String message=null;
		HttpSession hs=request.getSession();
		User user = (User) hs.getAttribute("user");
		if(user==null){
			user=(User) shopservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("id", "xox")).uniqueResult();
			
		}
		String id=request.getParameter("id");
		Goods goods=(Goods) shopservice.getCurrentSession().createCriteria(Goods.class).add(Restrictions.eq("id", id)).uniqueResult();
		
		
		//道具
		//判断用户是否有购买权限
	    //获取购买页面应付金币数额和用户现有金币数额
		int golds=goods.getPrice();
		Rule rule=(Rule) shopservice.getCurrentSession().createCriteria(Rule.class).add(Restrictions.eq("action", "购买笔记"));
		//将获得的积分
		int credits=rule.getCredit();
		String seller=request.getParameter("seller");
		getUserInfo(request,response);
		request.setAttribute("seller", seller);
		request.setAttribute("credits", credits);
		request.setAttribute("golds", golds);
		//获取道具信息
				Note note=(Note)shopservice.getCurrentSession().createCriteria(Note.class).add(Restrictions.eq("NoteId", goods.getGoodsObject())).uniqueResult();
		        request.setAttribute("Note", note);
		        request.setAttribute("type", "笔记");
		message="请仔细校验交易数额";	
		
		return new ModelAndView("forward:gobuynote.htm","message",message);
		
	}
	@RequestMapping("buynoteconfig.htm")
	public ModelAndView buynoteconfig(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String message=null;
		HttpSession hs=request.getSession();
		User user = (User) hs.getAttribute("user");
		if(user==null){
			user=(User) shopservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("id", "xox")).uniqueResult();
			
		}
		String id=request.getParameter("goods");
		Goods goods=(Goods) shopservice.getCurrentSession().createCriteria(Goods.class).add(Restrictions.eq("id", id)).uniqueResult();
		
		
		//交易成功,修改买家用户积分和金币信息
		int golds=goods.getPrice();
		Rule rule=(Rule) shopservice.getCurrentSession().createCriteria(Rule.class).add(Restrictions.eq("action", "购买道具"));
		int credit=user.getCredit()+rule.getCredit();
		user.setCredit(credit);
		user.setGold(user.getGold()-golds);
		shopservice.update(user);
		//交易成功,修改卖家用户积分和金币信息
		User seller=(User)shopservice.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userId", goods.getUserBySallerId())).uniqueResult();
		rule=(Rule) shopservice.getCurrentSession().createCriteria(Rule.class).add(Restrictions.eq("action", "出售笔记"));     //触发卖笔记规则
		credit=credit+rule.getCredit();
		seller.setCredit(credit);
		int sellergold=seller.getGold();
		seller.setGold(sellergold+golds);
		//修改商品信
		int zero=0;
		goods.setNumber(zero);
		goods.setUserByCustomerId(user);
		shopservice.update(goods);
		message="购买成功";
		
		getUserInfo(request,response);
		return new ModelAndView("forward:goshop.htm","message",message);
		
	}
	
}
