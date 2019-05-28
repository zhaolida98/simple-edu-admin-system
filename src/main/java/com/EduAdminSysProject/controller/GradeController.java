package com.EduAdminSysProject.controller;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.response.CommonReturnType;
import com.EduAdminSysProject.service.GradeService;
import com.EduAdminSysProject.service.model.GradeModel;
import com.EduAdminSysProject.service.model.UserModel;
import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("grade")
@RequestMapping("/grade")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class GradeController extends BaseController{

    @Resource
    private GradeService gradeService;

    @Resource
    private HttpServletRequest httpServletRequest;
//    http://127.0.0.1:8090/grade/selectcourse?sid=11611803&cid=CS101
    @RequestMapping(value = "/selectcourse", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType selectCourse(@RequestParam(name = "cid") String cid) throws BusinessException {
        //whether empty
        if (StringUtils.isEmpty(cid)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //whether login
        UserModel userModel = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN");
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        //whether privileged
        if (userModel.getRole() != 1) {
            throw new BusinessException(EmBusinessError.USER_PRIVILEGE_ERROR, "only STUDENT can select course");
        }
        GradeModel gradeModel = new GradeModel();
        gradeModel.setSid(userModel.getSid());
        gradeModel.setCid(cid);
        gradeModel.setGrade(0);
        gradeModel.setGid(userModel.getGid());
        gradeService.selectCourse(gradeModel);
        return CommonReturnType.create(null);
    }
//http://127.0.0.1:8090/grade/getselected
    @RequestMapping(value = "/getselected", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getAllSelectedCourse() throws BusinessException {
        //whether login
        UserModel userModel = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN");
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        //whether privileged
        if (userModel.getRole() != 1) {
            throw new BusinessException(EmBusinessError.USER_PRIVILEGE_ERROR, "only STUDENT can select course");
        }
        List<GradeModel> gradeModelList = gradeService.getSelectedCourse(userModel.getSid(), userModel.getGid());

        return CommonReturnType.create(gradeModelList);
    }
}
