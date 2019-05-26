package com.EduAdminSysProject.controller;

import com.EduAdminSysProject.controller.viewobject.CourseVO;
import com.EduAdminSysProject.dataobject.CourseDO;
import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.response.CommonReturnType;
import com.EduAdminSysProject.service.CourseService;
import com.EduAdminSysProject.service.model.CourseModel;
import com.EduAdminSysProject.service.model.UserModel;
import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
//http://127.0.0.1:8090/course/getallcourse
    @RequestMapping("/getallcourse")
    @ResponseBody
    public CommonReturnType getAllCourse() throws BusinessException {
//        UserModel inSessionUserModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN");
//        if (inSessionUserModel == null) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "user login error");
//        }
        List<CourseModel> courseModelList = courseService.getAllCourse();
//        List<CourseVO> courseVOList = courseModelList.stream().map(courseModel -> {
//            return convertToVO(courseModel);
//        }).collect...
        return CommonReturnType.create(courseModelList);
    }
}
