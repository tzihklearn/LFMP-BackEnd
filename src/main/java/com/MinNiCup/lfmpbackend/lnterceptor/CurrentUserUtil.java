package com.MinNiCup.lfmpbackend.lnterceptor;


/**
 * @author tzih
 * @date 2022.10.13
 */
public class CurrentUserUtil {

    private static final ThreadLocal<CurrentUser> CURRENT_USER = new ThreadLocal<>();

    public static void setCurrentUser(CurrentUser currentUser) {
        CURRENT_USER.set(currentUser);
    }

    public static CurrentUser getCurrentUser() {
        return CURRENT_USER.get();
    }

    public static void remove() {
        CURRENT_USER.remove();
    }

}
