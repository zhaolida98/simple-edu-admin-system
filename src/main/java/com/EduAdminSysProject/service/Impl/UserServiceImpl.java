package com.EduAdminSysProject.service.Impl;

import com.EduAdminSysProject.dao.UserDOMapper;
import com.EduAdminSysProject.dao.UserpasswordDOMapper;
import com.EduAdminSysProject.dataobject.UserDO;
import com.EduAdminSysProject.dataobject.UserpasswordDO;
import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.service.UserService;
import com.EduAdminSysProject.service.model.UserModel;
import com.alibaba.druid.util.StringUtils;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;

    @Resource
    private UserpasswordDOMapper userpasswordDOMapper;

    @Override
    public UserModel getUserBySid(String sId) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(sId);
        if (userDO == null) {
            return null;
        }
        UserpasswordDO userpasswordDO = userpasswordDOMapper.selectByPrimaryKey(userDO.getSid());
        return convertFromDataObject(userDO, userpasswordDO);
    }

    @Override
    public UserModel validateLogin(String sid, String encryptPassword) throws BusinessException {
        UserDO userDO = userDOMapper.selectByPrimaryKey(sid);
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserpasswordDO userpasswordDO = userpasswordDOMapper.selectByPrimaryKey(userDO.getSid());
        UserModel userModel = convertFromDataObject(userDO, userpasswordDO);

        if (!StringUtils.equals(encryptPassword, userModel.getEncryptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        return userModel;
    }

    @Override
    public void validateOldPassword(String inputPassword, String oldPassword) throws BusinessException {
        if (StringUtils.isEmpty(inputPassword)) {
            throw new BusinessException(EmBusinessError.USER_OLDPASS_WRONG);
        }

        if (!StringUtils.equals(oldPassword, inputPassword)) {
            throw new BusinessException(EmBusinessError.USER_OLDPASS_WRONG);
        }
        return;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        if (StringUtils.isEmpty(userModel.getSid())
                || StringUtils.isEmpty(userModel.getName())
                || StringUtils.isEmpty(userModel.getPhonenumber())
                || userModel.getRole() == null
        ) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserDO userDO = convertFromModel(userModel);
        userDOMapper.insertSelective(userDO);

        UserpasswordDO userpasswordDO = convertPasswordFromModel(userModel);
        userpasswordDOMapper.insertSelective(userpasswordDO);

        return;
    }
    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    private UserpasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserpasswordDO userPasswordDO = new UserpasswordDO();
        userPasswordDO.setEncryptpassword(userModel.getEncryptPassword());
        userPasswordDO.setSid(userModel.getSid());
        return userPasswordDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserpasswordDO userpasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userpasswordDO != null) {
            userModel.setEncryptPassword(userpasswordDO.getEncryptpassword());
        }
        return userModel;
    }
}
