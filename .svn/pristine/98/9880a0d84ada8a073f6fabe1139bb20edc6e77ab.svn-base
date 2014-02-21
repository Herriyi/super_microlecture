package org.sicdlab.microlecture.common.baseService.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.tag.pageTag.PageHelper;
//import org.sicdlab.entrepreneur.common.bean.DataDic;
//import org.sicdlab.entrepreneur.common.tag.pageTag.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseServiceImpl implements BaseService {
    @Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional()
	public void delete(Object object) {
		getCurrentSession().delete(object);

	}

	@Transactional()
	public void saveOrUpdate(Object object) {
		getCurrentSession().saveOrUpdate(object);

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> queryAll(Class<T> clazz) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		List<T> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> T findById(Class<T> clazz, String id) {

		return (T) getCurrentSession().get(clazz, id);
	}

	@Transactional
	public void deleteById(Class<?> clazz, String id) {
		delete(findById(clazz, id));
	}

	@Transactional
	public void save(Object object) {
		getCurrentSession().save(object);

	}

	@Transactional
	public void update(Object object) {
		getCurrentSession().update(object);

	}

	@Transactional(readOnly = true)
	public int countTotalPage(Class<?> clazz, int pageSize) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		int totalRecord = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		int totalPage = (int) Math.ceil(((double) totalRecord / pageSize));
		return totalPage;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> getByPage(Class<T> clazz,int pageSize) {
		int curPage=PageHelper.getCurPage();
		if (curPage < 1)
			curPage = 1;
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.setFirstResult((curPage - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		List<T> list = criteria.list();
		return list;
	}
    @Transactional(readOnly=true)
	public int countTotalPage(DetachedCriteria dCriteria,int pageSize) {
		Criteria criteria = dCriteria
				.getExecutableCriteria(getCurrentSession());
		int totalRecord = ((Long) criteria
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		criteria.setProjection(null);
		int totalPage = (int) Math.ceil(((double) totalRecord / pageSize));
		return totalPage;
	}
    
    @Transactional(readOnly=true)
    public int countNum(DetachedCriteria dCriteria) {
    	Criteria criteria = dCriteria
				.getExecutableCriteria(getCurrentSession());
    	int num=((Long) criteria
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		return  num;
	}
    
    
    
    
    
    @Transactional(readOnly=true)
	public List<?> getByPage(DetachedCriteria dCriteria,int pageSize) {
    	
		int curPage=PageHelper.getCurPage();
//		System.out.println(curPage);
		Criteria criteria = dCriteria
				.getExecutableCriteria(getCurrentSession());
		criteria.setFirstResult((curPage - 1) * pageSize);
		criteria.setMaxResults(pageSize);
//		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		List<?> list = criteria.list();
		return list;
	}
   
    @SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public <T> List<T> queryAllOfCondition(Class<T> clazz,
			DetachedCriteria dCriteria) {
		Criteria criteria=dCriteria.getExecutableCriteria(getCurrentSession());
		List<T> list=criteria.list();
		return list;
	}
    @Transactional
	public<T> void deleteAll(List<T> list) {
		for(T object:list){
			getCurrentSession().delete(object);
		}		
	}
	@Transactional
	public<T> void saveAll(List<T> list) {
		for(T object:list){
			getCurrentSession().save(object);
		}		
	}
	@Transactional
	public <T>void updateAll(List<T> objList){
		for(T object:objList){
			getCurrentSession().update(object);
		}
	}
	
//	 @Transactional(readOnly=true)
//		public List<DataDic> getDataDicByDescription(String description) {
//			Criteria criteria=getCurrentSession().createCriteria(DataDic.class);
//			criteria.add(Restrictions.eq("dataDicDescription", description));
//			List<DataDic> list=criteria.list();
//			return list;
//		}
//	    @Transactional(readOnly=true)
//		public List<DataDic> getDataDicByKey(String key) {
//			Criteria criteria=getCurrentSession().createCriteria(DataDic.class);
//			criteria.add(Restrictions.eq("dataDicKey", key));
//			List<DataDic> list=criteria.list();
//			return list;
//		}

	    @SuppressWarnings("unchecked")
		@Transactional(readOnly=true)
		public <T> List<T> queryMaxNumOfCondition(Class<T> clazz,DetachedCriteria dCriteria, int num) {
	    	Criteria criteria=dCriteria.getExecutableCriteria(getCurrentSession());
			List<T> list=criteria.setMaxResults(num).list();
			return list;
		}

	   
}
