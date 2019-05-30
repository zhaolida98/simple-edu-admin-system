package com.EduAdminSysProject;

import com.EduAdminSysProject.dao.UserDOMapper;
import com.EduAdminSysProject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"com.EduAdminSysProject"})
@Controller
@MapperScan("com.EduAdminSysProject.dao")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class App {

    @Resource
    private UserDOMapper userDOMapper;
    @GetMapping("/index")
    public String home() {
        return "login";
    }
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class, args);

    }
}
