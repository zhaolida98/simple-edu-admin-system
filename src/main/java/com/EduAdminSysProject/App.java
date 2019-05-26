package com.EduAdminSysProject;

import com.EduAdminSysProject.dao.UserDOMapper;
import com.EduAdminSysProject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"com.EduAdminSysProject"})
@RestController
@MapperScan("com.EduAdminSysProject.dao")
public class App {

    @Resource
    private UserDOMapper userDOMapper;
    @RequestMapping("/")
    public String home() {
        UserDO userDo = userDOMapper.selectByPrimaryKey("11611803");
        if (userDo == null) {
            return "用户对象不存在";
        } else {
            return "找到该对象";
        }
    }
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class, args);

    }
}
