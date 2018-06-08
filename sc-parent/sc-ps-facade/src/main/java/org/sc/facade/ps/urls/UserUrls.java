package org.sc.facade.ps.urls;

/**
 * URL
 * Created by Webb Dong on 2017/4/11.
 */
public interface UserUrls {

    String LOGIN = "/login";

    String CHECK_BY_USER = "/checkByUser";

    String GET_BY_ID = "/getById";

    String ADD_USER = "/addUser";

    String UPDATE_USER_WALLET = "/updateUserWallet";

    String UPDATE_USER_ATTRIBUTE = "/updateUserAttribute";

    String GET_BY_ENTITY = "/getByEntity";

    String GET_LIST_BY_ENTITY = "/getListByEntity";

    String GET_USER_NAMES = "/getUserNames";

    String GET_SHOP_USER_NAMES = "/getShopUserNames";

    String GET_BY_USER_NAME_LIKE = "/getByUserNameLike";

    String GET_EXTEND_ATTRIBUTE = "/getExtendAttribute";

    String GET_ALL_USERS = "/getAllUsers";

    String CHECK_USER_ATTRIBUTE = "/checkUserAttribute";

    String GET_ENABLE_DEMOTE_USER = "/getEnableDemoteUser";

    String GET_NO_LEVEL_USERS = "/getNoLevelUsers";

    String GET_MONEY_USERS = "/getMoneyUsers";

    String CLEAN_WXOPENID = "/cleanWxOpenId";

    String CHECK_LAST_UPDATE_USER = "/checkLastUpdateUser";

}
