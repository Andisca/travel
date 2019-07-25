package com.itheima.service;

import java.util.List;
import java.util.Map;
import com.itheima.constant.Constant;
import com.itheima.dao.FavoriteDao;
import com.itheima.domain.Favorite;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;

public class FavoriteService {
    private FavoriteDao dao = new FavoriteDao();

    public boolean isFavorited(Integer rid, int uid) {
        Favorite favorite = dao.findFavorited(rid, uid);

        boolean flag = false;

        if (favorite != null) {
            flag = true;
        }

        return flag;
    }

    public void addFavorite(Integer rid, int uid) throws Exception {
        dao.addFavorite(rid, uid);
        dao.updateCount(rid, 1);
    }

    public void remFavorite(Integer rid, int uid) throws Exception {
        dao.remFavorite(rid, uid);
        dao.updateCount(rid, -1);
    }

    public PageBean showMyFav(int uid, Integer curPage) {
        PageBean pageBean = new PageBean<>();
        pageBean.setCurPage(curPage);

        Integer pageSize = Constant.MYFAV_PAGE_SIZE;
        pageBean.setPageSize(pageSize);

        Integer totalCount = dao.findMyFavCount(uid);
        pageBean.setTotalCount(totalCount);

        Integer totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);

        List<Map<String, Object>> list = dao.findMyFavList(uid, (curPage - 1) * pageSize, pageSize);
        pageBean.setList(list);

        return pageBean;

    }

    public PageBean showFavRank(Integer curPage, String favName, String lowPrice, String highPrice) {
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurPage(curPage);

        Integer pageSize = Constant.FAVRANK_PAGE_SIZE;
        pageBean.setPageSize(pageSize);

        Integer totalCount = dao.findRankCount(favName, lowPrice, highPrice);
        pageBean.setTotalCount(totalCount);

        Integer totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);

        List<Route> list = dao.findRankList(favName, lowPrice, highPrice, (curPage - 1) * pageSize, pageSize);
        pageBean.setList(list);

        return pageBean;
    }
}
