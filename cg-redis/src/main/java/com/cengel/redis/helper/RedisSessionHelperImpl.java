package com.cengel.redis.helper;

import com.cengel.starbucks.model.user.OnlineUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;

import static com.cengel.redis.constant.RedisKeys.MemberRedisKeys.MEMBER_NAME_DETAIL_CACHE;
import static com.cengel.redis.constant.RedisKeys.MemberRedisKeys.ONLINE_USER;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/22 - 12:46
 * @Version V1.0
 **/
public class RedisSessionHelperImpl implements RedisSessionHelper {

	@Autowired
	private RedisHelper redisHelper;

	@Override
	public void putUser(OnlineUser user) {
		Assert.notNull(user,"onlineuser为空");
		Assert.notNull(user.getAccount(),"user用户名为空");
		redisHelper.set(ONLINE_USER.concat(user.getUsrKey()),user);
	}

	@Override
	public OnlineUser getUser(String account,String platform) {
		Assert.notNull(account,"用户key不能为空");
		Assert.notNull(platform,"平台不能为空");
		return redisHelper.get(ONLINE_USER.concat(OnlineUser.getUsrKey(account,platform)));
	}

	@Override
	public Serializable getMemberDetail(Integer memberId) {
		Assert.notNull(memberId,"用户id不能为空");
		return redisHelper.get(MEMBER_NAME_DETAIL_CACHE+memberId);
	}

	@Override
	public void putMemberDetail(Integer memberId,Serializable memberDetailDo) {
		Assert.notNull(memberId,"用户id不能为空");
		Assert.notNull(memberDetailDo,"memberDetail为空");
		redisHelper.set(MEMBER_NAME_DETAIL_CACHE+memberId,memberDetailDo);
	}
}
