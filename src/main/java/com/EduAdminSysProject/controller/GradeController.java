package com.EduAdminSysProject.controller;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.response.CommonReturnType;
import com.EduAdminSysProject.service.GradeService;
import com.EduAdminSysProject.service.model.GradeModel;
import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("grade")
@RequestMapping("/grade")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class GradeController {

    @Resource
    private GradeService gradeService;

    @Resource
    private HttpServletRequest httpServletRequest;
//    http://127.0.0.1:8090/grade/selectcourse?sid=11611803&cid=CS101
    @RequestMapping("/selectcourse")
    @ResponseBody
    public CommonReturnType selectCourse(@RequestParam(name = "sid") String sid,
                                         @RequestParam(name = "cid") String cid) throws BusinessException {
//        UserModel userModel = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN");

        if (StringUtils.isEmpty(sid) || StringUtils.isEmpty(cid)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        GradeModel gradeModel = new GradeModel();
        gradeModel.setSid(sid);
        gradeModel.setCid(cid);
        gradeModel.setGrade(0);
        gradeService.selectCourse(gradeModel);
        return CommonReturnType.create(null);

    }
//http://127.0.0.1:8090/grade/getselected?sid=11611803
    @RequestMapping("/getselected")
    @ResponseBody
    public CommonReturnType getAllSelectedCourse(@RequestParam(name = "sid") String sid) throws BusinessException {
        if (StringUtils.isEmpty(sid)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<GradeModel> gradeModelList = gradeService.getSelectedCourse(sid);

        return CommonReturnType.create(gradeModelList);
    }
}
