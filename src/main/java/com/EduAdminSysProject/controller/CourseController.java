package com.EduAdminSysProject.controller;

import com.EduAdminSysProject.dataobject.CourseDO;
import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.response.CommonReturnType;
import com.EduAdminSysProject.service.CourseService;
import com.EduAdminSysProject.service.model.CourseModel;
import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller("course")
@RequestMapping("/course")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class CourseController extends BaseController {

    @Resource
    CourseService courseService;

    //127.0.0.1:8090/course/addcourse?cid=CS101&description=JAVA1
    @RequestMapping("/addcourse")
    @ResponseBody
    public CommonReturnType addCourse(@RequestParam(name = "cid") String cid,
                                      @RequestParam(name = "description") String description) throws BusinessException {
        if (StringUtils.isEmpty(cid) || StringUtils.isEmpty(description)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "cid or description empty");
        }
        CourseModel courseModel = new CourseModel();
        courseModel.setCid(cid);
        courseModel.setDescription(description);
        courseService.addCourse(courseModel);

        return CommonReturnType.create(null);

    }

}
