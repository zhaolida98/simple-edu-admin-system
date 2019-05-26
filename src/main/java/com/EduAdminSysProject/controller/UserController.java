package com.EduAdminSysProject.controller;

import com.EduAdminSysProject.controller.viewobject.UserVO;
import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.response.CommonReturnType;
import com.EduAdminSysProject.service.UserService;
import com.EduAdminSysProject.service.model.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/get_user")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "sid") String sid) throws BusinessException {
        UserModel userModel = userService.getUserBySid(sid);
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    @RequestMapping(value = "/login"/*, method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED}*/)
    @ResponseBody
    public CommonReturnType logIn(@RequestParam(name = "sid") String sid,
                                  @RequestParam(name = "password") String password) throws BusinessException {
        if (sid == null || password == null || sid.isEmpty() || password.isEmpty()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserModel userModel = userService.validateLogin(sid, password);

        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN", userModel);

        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/cgpass"/*, method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED}*/)
    @ResponseBody
    public CommonReturnType changePassword(@RequestParam(name = "oldpassword") String oldpassword,
                                           @RequestParam(name = "newpassword") String newpassword) throws BusinessException {
//        if (oldpassword == null || newpassword == null || oldpassword.isEmpty() || newpassword.isEmpty()) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
//        UserModel userModel = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN");
//
//        userService.validateOldPassword(oldpassword, userModel.getEncryptPassword());
//
//        userModel.setEncryptPassword(newpassword);
////        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
//        this.httpServletRequest.getSession().setAttribute("LOGIN", userModel);
//
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/adduser"/*, method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED}*/)
    @ResponseBody
    public CommonReturnType addUser(@RequestParam(name = "sid") String sid,
                                    @RequestParam(name = "name") String name,
                                    @RequestParam(name = "password") String password,
                                    @RequestParam(name = "phonenumber") String phonenumber,
                                    @RequestParam(name = "role") Integer role,
                                    @RequestParam(name = "gender") Integer gender) throws BusinessException {
//        if (((UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN")).getRole() != 0) {
//            throw new BusinessException(EmBusinessError.USER_PRIVILEGE_ERROR, "ONLY ADMIN can add user");
//        }
        UserModel userModel = new UserModel();
        userModel.setSid(sid);
        userModel.setName(name);
        userModel.setEncryptPassword(password);
        userModel.setPhonenumber(phonenumber);
        userModel.setRole(role);
        userModel.setGender(gender);

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    private UserVO convertFromModel(UserModel userModel) throws BusinessException {
        if (userModel == null) {
//            userModel.setEncryptPassword("123");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }


}
