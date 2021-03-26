package cn.wolfcode.spring.test.config;

import cn.wolfcode.spring.test.domain.User;
import cn.wolfcode.spring.test.event.PayFailedEvent;
import cn.wolfcode.spring.test.event.PaySuccessEvent;
import cn.wolfcode.spring.test.listener.PaySuccessListener;
import cn.wolfcode.spring.test.service.IUserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.math.BigDecimal;

/**
 * @author Leon
 * @date 2021/3/16
 */
@Configuration
@ComponentScan(basePackages = "cn.wolfcode.spring.test")
public class AppConfig {
//
//	@Bean(initMethod = "init", destroyMethod = "destroy")
//	public User user() {
//		return new User("test", "123456");
//	}
//
//	@Bean
//	public IUserService userService() {
//		UserServiceImpl userService = new UserServiceImpl();
//		userService.setUser(user());
//		return userService;
//	}

	@Bean
	public ApplicationEventMulticaster applicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
		// 设置异步事件线程池：开启异步事件
		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return eventMulticaster;
	}

	public static void main(String[] args) {
		System.out.println("-----------准备启动容器-----------");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		System.out.println("-----------容器初始化完成，开始使用容器-----------");
		User user = ctx.getBean(User.class);
		System.out.println(user);
		System.out.println("-----------获取 Service Bean-----------");

		String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}

		IUserService bean = ctx.getBean(IUserService.class);
		IUserService bean1 = ctx.getBean(IUserService.class);
		System.out.println("=================>>>>>>" + (bean == bean1));
		bean.test();
		bean1.test();

		// 自己添加的注册器
//		ctx.addApplicationListener(new PaySuccessListener());

		System.out.println("------------测试spring事件机制：Begin--------------");
		// 发布支付成功事件
		ctx.publishEvent(new PaySuccessEvent(ctx, "1000248720181204", "295898209213825983250", new BigDecimal("10000.00")));
		// 发布支付失败事件
		ctx.publishEvent(new PayFailedEvent(ctx, "234085972304958093", "985302921094982983", "20090", "支付失败，您的余额已不足！"));
		System.out.println("------------测试spring事件机制：End--------------");

		// 关闭容器
		System.out.println("-----------准备关闭容器-----------");
		ctx.close();
	}
}
