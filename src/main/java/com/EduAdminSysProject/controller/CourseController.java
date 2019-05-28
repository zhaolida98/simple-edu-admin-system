package com.EduAdminSysProject.controller;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.response.CommonReturnType;
import com.EduAdminSysProject.service.CourseService;
import com.EduAdminSysProject.service.model.CourseModel;
import com.EduAdminSysProject.service.model.UserModel;
import com.alibaba.druid.util.StringUtils;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("course")
@RequestMapping("/course")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class CourseController extends BaseController {

    @Resource
    CourseService courseService;

    @Resource
    private HttpServletRequest httpServletRequest;

    //127.0.0.1:8090/course/addcourse?cid=CS103&description=JAVA1
    @RequestMapping(value = "/addcourse", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addCourse(@RequestParam(name = "cid") String cid,
                                      @RequestParam(name = "description") String description) throws BusinessException {
        //whether null
        if (StringUtils.isEmpty(cid) || StringUtils.isEmpty(description)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "cid or description empty");
        }
        //whether login
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        UserModel userModel = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN");
        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        //whether privileged
        if (userModel.getRole() != 0) {
            throw new BusinessException(EmBusinessError.USER_PRIVILEGE_ERROR, "only ADMIN can add user");
        }
        CourseModel courseModel = new CourseModel();
        courseModel.setCid(cid);
        courseModel.setDescription(description);
        courseModel.setGid(userModel.getGid());
        courseService.addCourse(courseModel);
        System.out.println("addCourse required");
        return CommonReturnType.create(null);
    }
//http://127.0.0.1:8090/course/getallcourse
    @RequestMapping(value = "/getallcourse", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getAllCourse() throws BusinessException {
        //whether login
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        UserModel userModel = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN");
        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        List<CourseModel> courseModelList = courseService.getAllCourse(userModel.getGid());
        System.out.println("get all course required");
        return CommonReturnType.create(courseModelList);
    }
}
