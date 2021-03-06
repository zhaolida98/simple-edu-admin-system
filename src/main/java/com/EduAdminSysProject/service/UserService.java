package com.EduAdminSysProject.service;

import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.service.model.UserModel;

public interface UserService {

    UserModel getUserBySid(String sid, String gid);

    UserModel validateLogin(String sid, String encryptPassword, String gid) throws BusinessException;

    void validateOldPassword(String sid, String encryptPassword) throws BusinessException;

    void register(UserModel userModel) throws BusinessException;

    void changePassword(UserModel userModel) throws BusinessException;

}
