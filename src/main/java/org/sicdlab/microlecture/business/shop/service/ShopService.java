package org.sicdlab.microlecture.business.shop.service;

import java.util.List;

import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.bean.Goods;

public interface ShopService extends BaseService{
	public List<Goods> getprop(String type);
	public List<Goods> getnote(String type);
}
