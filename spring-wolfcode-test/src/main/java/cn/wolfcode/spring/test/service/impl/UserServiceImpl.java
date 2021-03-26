package cn.wolfcode.spring.test.service.impl;

import cn.wolfcode.spring.test.domain.User;
import cn.wolfcode.spring.test.service.IUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leon
 * @date 2021/3/16
 */
@Service
public class UserServiceImpl implements IUserService, InitializingBean {

	@Autowired
	private User user;

	@Override
	public void test() {
		System.out.println("获取到的user：" + user);
		System.out.println("Ioc加载流程测试...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("-----");
	}
}
