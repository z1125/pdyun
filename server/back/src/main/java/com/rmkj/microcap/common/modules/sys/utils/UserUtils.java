package com.rmkj.microcap.common.modules.sys.utils;

import com.rmkj.microcap.common.handler.SpringContextHolder;
import com.rmkj.microcap.common.modules.shiro.SystemAuthorizingRealm;
import com.rmkj.microcap.common.modules.sys.bean.SysMenuBean;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysMenuDao;
import com.rmkj.microcap.common.modules.sys.dao.ISysRoleDao;
import com.rmkj.microcap.common.modules.sys.dao.ISysUserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 用户工具类
 *
 * @author
 * @version 2013-12-05
 */
public class UserUtils {

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    private static ISysUserDao userDao = SpringContextHolder.getBean(ISysUserDao.class);
    private static ISysRoleDao roleDao = SpringContextHolder.getBean(ISysRoleDao.class);
    private static ISysMenuDao menuDao = SpringContextHolder.getBean(ISysMenuDao.class);

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static SysUserBean get(String id) {
        SysUserBean user = (SysUserBean) LocalCacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userDao.get(id);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new SysRoleBean(user)));
            LocalCacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            LocalCacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return 取不到返回null
     */
    public static SysUserBean getByLoginName(String loginName, boolean useCash) {
        SysUserBean user = useCash ? (SysUserBean) LocalCacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName) : null;
        if (user == null) {
            user = userDao.getByLoginName(loginName);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new SysRoleBean(user)));
            LocalCacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            LocalCacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        UserUtils.clearCache(getUser());
    }

    /**
     * 清除指定用户缓存
     *
     * @param user
     */
    public static void clearCache(SysUserBean user) {
        LocalCacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        LocalCacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        LocalCacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static SysUserBean getUser() {
        SystemAuthorizingRealm.Principal principal = getPrincipal();
        if (principal != null) {
            SysUserBean user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new SysUserBean();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new SysUserBean();
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<SysRoleBean> getRoleList() {
        List<SysRoleBean> roleList = (List<SysRoleBean>) getCache(CACHE_ROLE_LIST);
        if (roleList == null) {
            roleList = roleDao.findList(new SysRoleBean());
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public static List<SysMenuBean> getMenuList() {
        List<SysMenuBean> menuList = (List<SysMenuBean>) getCache(CACHE_MENU_LIST);
        if (menuList == null) {
            SysMenuBean m = new SysMenuBean();
            SysUserBean user = getUser();
            if (user.getRoleIdList().contains("1")) {
                menuList = menuDao.queryList(m);
            } else {
                m.setUserId(user.getId());
                menuList = menuDao.findByUserId(m);
            }

            putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static SystemAuthorizingRealm.Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }


    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 判断当前用户是否拥有超级管理员权限
     *
     * @return
     */
    public static boolean curUserHasSuperRole() {
        boolean superRole = false;
        SysUserBean sysUserBean = UserUtils.getUser();
        List<SysRoleBean> roleList = sysUserBean.getRoleList();
        for (SysRoleBean role : roleList) {
            if ("1".equals(role.getId())) {
                superRole = true;
                break;
            }
        }
        return superRole;
    }

}
