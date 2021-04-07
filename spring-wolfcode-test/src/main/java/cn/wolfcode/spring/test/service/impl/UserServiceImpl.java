package cn.wolfcode.spring.test.service.impl;

import cn.wolfcode.spring.test.domain.User;
import cn.wolfcode.spring.test.service.IUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Leon
 * @date 2021/3/16
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private User user;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void test() {
		System.out.println("获取到的user：" + user);
		try {
			IUserService currentProxy = (IUserService) AopContext.currentProxy();
			currentProxy.login("xxxx", "admin");
		} catch (IllegalStateException e) {
			login("xxxx", "admin");
		}
		System.out.println("Ioc加载流程测试...");
	}

	@Override
	public void login(String username, String password) {
		System.out.println("用户登录：username=" + username + ", password=" + password);
//		if (username != "xxxx") {
//			// 即使是 jdk，也可以进行内部动态代理方法的调用
//			((IUserService) AopContext.currentProxy()).register(username);
//		}
	}

	@Override
	public void register(String mobile) {
		System.out.println("用户注册：mobile=" + mobile);
	}

}
