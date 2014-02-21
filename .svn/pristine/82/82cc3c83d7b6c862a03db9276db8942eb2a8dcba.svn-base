package org.sicdlab.microlecture.common.favorite.impl;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.sicdlab.microlecture.common.baseService.impl.BaseServiceImpl;
import org.sicdlab.microlecture.common.bean.Favorite;
import org.sicdlab.microlecture.common.bean.User;
import org.sicdlab.microlecture.common.favorite.FavoriteHandle;
import org.sicdlab.microlecture.util.UUIDGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class FavoriteHandleImpl extends BaseServiceImpl implements FavoriteHandle{

	@Override
	public void AddFavorite(User user, String url, String title, String objectId) {

		Favorite favorite=new Favorite();
		favorite.setUser(user);
		favorite.setFavoriteId(UUIDGenerator.randomUUID());
		favorite.setFavoriteDate(new Date());
		favorite.setUrl(url);
		favorite.setFavoriteName(title);
		favorite.setObjectId(objectId);
		getCurrentSession().save(favorite);
	}

	@Override
	public void DeleteFavorite(String favoriteId) {
		Favorite favorite=(Favorite) getCurrentSession().get(Favorite.class, favoriteId);
		getCurrentSession().delete(favorite);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean CheckFavorite(String objectId, User user) {
		List<Favorite> favorites = (List<Favorite>) getCurrentSession().createCriteria(Favorite.class)
				.add(Restrictions.eq("objectId", objectId)).add(Restrictions.eq("user.userId", user.getUserId()));
		if(favorites.size()>0)
			return true;
		else 
			return false;
	}
}
