package org.sicdlab.microlecture.business.shop.service.impl;

import java.util.List;

import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.business.shop.service.ShopService;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Goods;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Component
public class ShopServiceImpl extends BaseServiceImpl implements ShopService{

	@Override
	public List<Goods> getprop(String type) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Goods> prop=getCurrentSession().createCriteria(Goods.class).add(Restrictions.and(Restrictions.eq("type", type), Restrictions.gt("number", 0))).list();
		
		return prop;
	}

	@Override
	public List<Goods> getnote(String type) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Goods> note=getCurrentSession().createCriteria(Goods.class)	
		.add(Restrictions.and(Restrictions.eq("type", type), Restrictions.eq("goodsId", "5"))).list();

		return note;
	}

	
}
