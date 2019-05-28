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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        //whether login
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        UserModel sessionUserModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN");

        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        UserModel userModel = userService.getUserBySid(sid, sessionUserModel.getGid());
        UserVO userVO = convertFromModel(userModel);
        System.out.println("get user required from " + sid);
        return CommonReturnType.create(userVO);
    }

    //http://127.0.0.1:8090/user/login?sid=11611803&password=654321
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType logIn(@RequestParam(name = "sid") String sid,
                                  @RequestParam(name = "password") String password,
                                  @RequestParam(name = "gid") String gid) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (sid == null || password == null || sid.isEmpty() || password.isEmpty()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserModel userModel = userService.validateLogin(sid, encodeByMD5(password), gid);

        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN", userModel);
        System.out.println("login required from " + sid);
        return CommonReturnType.create(userModel);
    }

    //http://127.0.0.1:8090/user/logout
    @RequestMapping(value = "/logout", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType logOut() throws BusinessException {
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        // whether login
        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", false);
        this.httpServletRequest.getSession().setAttribute("LOGIN", null);
        System.out.println("logout required ");
        return CommonReturnType.create(null);
    }

    //http://127.0.0.1:8090/user/cgpass?oldpassword=123456&newpassword=654321
    @RequestMapping(value = "/cgpass", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    @Transactional
    public CommonReturnType changePassword(@RequestParam(name = "oldpassword") String oldpassword,
                                           @RequestParam(name = "newpassword") String newpassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //whether empty
        if (oldpassword == null || newpassword == null || oldpassword.isEmpty() || newpassword.isEmpty()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "password is empty");
        }
        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN");
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        // whether login
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "null session model");
        }
        // whether login
        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }

        userService.validateOldPassword(encodeByMD5(oldpassword), userModel.getEncryptPassword());

        userModel.setEncryptPassword(encodeByMD5(newpassword));
////        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
//        this.httpServletRequest.getSession().setAttribute("LOGIN", userModel);
        userService.changePassword(userModel);
        System.out.println("changePassword required from " + userModel.getSid());
        return CommonReturnType.create(null);
    }

    //http://127.0.0.1:8090/user/adduser?sid=11611804&password=123456&phonenumber=12345678987&name=peng&gender=1&role=1
    @RequestMapping(value = "/adduser", method = {RequestMethod.POST}, consumes = {CONTNET_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addUser(@RequestParam(name = "sid") String sid,
                                    @RequestParam(name = "name") String name,
                                    @RequestParam(name = "password") String password,
                                    @RequestParam(name = "phonenumber") String phonenumber,
                                    @RequestParam(name = "role") String role,
                                    @RequestParam(name = "gender") String gender
                                    ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //whether login
        Boolean bool = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        UserModel sessionUserModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN");
        if (bool == null || !bool) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL, "is not login");
        }

        //whether privileged
        if (((UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN")).getRole() != 0) {
            throw new BusinessException(EmBusinessError.USER_PRIVILEGE_ERROR, "only ADMIN can add user");
        }
        UserModel userModel = new UserModel();
        userModel.setSid(sid);
        userModel.setName(name);
        userModel.setEncryptPassword(encodeByMD5(password));
        userModel.setPhonenumber(phonenumber);
        userModel.setRole(Integer.parseInt(role));
        userModel.setGender(Integer.parseInt(gender));
        userModel.setGid(sessionUserModel.getGid());

        userService.register(userModel);
        System.out.println("addUser required ");
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

    private String encodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
