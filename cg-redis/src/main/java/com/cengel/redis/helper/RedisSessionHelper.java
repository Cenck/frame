package com.cengel.redis.helper;

import com.cengel.starbucks.model.user.OnlineUser;

import java.io.Serializable;

public interface RedisSessionHelper {

	void putUser(OnlineUser user);
	OnlineUser getUser(String account, String platform);

	Serializable getMemberDetail(Integer memberId);
	void putMemberDetail(Integer memberId, Serializable memberDetailDo);
}
