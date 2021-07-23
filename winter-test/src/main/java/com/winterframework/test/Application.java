/*
 * Copyright (C), 1987-2099, Winter All Rights Reserved.
 */
package com.winterframework.test;

import com.winterframework.context.support.ClassPathXmlApplicationContext;
import com.winterframework.context.weaving.ApplicationContext;

public class Application {

    public static void main(String[] args) {
        // 用我们的配置文件来启动一个 ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");

        System.out.println("context 启动成功");

        // 从 context 中取出我们的 Bean，而不是用 new MessageServiceImpl() 这种方式
        MessageService messageService = context.getBean(MessageService.class);
        // 这句将输出: hello world
        System.out.println(messageService.getMessage());

    }

}
