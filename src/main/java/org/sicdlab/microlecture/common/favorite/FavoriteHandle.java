package org.sicdlab.microlecture.common.favorite;

import org.sicdlab.microlecture.common.baseService.BaseService;
import org.sicdlab.microlecture.common.bean.User;

public interface FavoriteHandle extends BaseService {

	void AddFavorite(User user, String url, String title, String objectId);
	void DeleteFavorite(String favoriteId);
	boolean CheckFavorite(String objectId, User user);
}
