package com.zust.common.bean;

import java.io.Serializable;

/**
 * Created by Zzzz on 2018/1/12.
 */
public class DeleteFriendRequestBean implements Serializable
{
	private static final long serialVersionUID = 1L;

    private User asker;

    private User bedeleteder;

    public User getAsker() {
        return asker;
    }

    public void setAsker(User asker) {
        this.asker = asker;
    }

    public User getBedeleteder() {
        return bedeleteder;
    }

    public void setBedeleteder(User bedeleteder) {
        this.bedeleteder = bedeleteder;
    }
}
