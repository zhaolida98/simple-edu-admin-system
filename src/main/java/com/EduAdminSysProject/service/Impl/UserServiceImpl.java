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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserModel getUserBySid(String sid, String gid) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(sid, gid);
        if (userDO == null) {
            return null;
        }
        UserpasswordDO userpasswordDO = userpasswordDOMapper.selectByPrimaryKey(userDO.getSid(), userDO.getGid());
        return convertFromDataObject(userDO, userpasswordDO);
    }

    @Override
    public UserModel validateLogin(String sid, String encryptPassword, String gid) throws BusinessException {
        UserDO userDO = userDOMapper.selectByPrimaryKey(sid, gid);
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserpasswordDO userpasswordDO = userpasswordDOMapper.selectByPrimaryKey(userDO.getSid(), userDO.getGid());
        UserModel userModel = convertFromDataObject(userDO, userpasswordDO);

        if (!StringUtils.equals(encryptPassword, userModel.getEncryptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        return userModel;
    }

    @Override
    public void validateOldPassword(String inputPassword, String oldPassword) throws BusinessException {
        if (!StringUtils.equals(oldPassword, inputPassword)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "old password is wrong");
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

    @Override
    public void changePassword(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserpasswordDO userpasswordDO = convertPasswordFromModel(userModel);
        userpasswordDOMapper.updateByPrimaryKeySelective(userpasswordDO);

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

    private UserpasswordDO convertPasswordFromModel(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "course model is empty");
        }
        UserpasswordDO userPasswordDO = new UserpasswordDO();
        userPasswordDO.setEncryptpassword(userModel.getEncryptPassword());
        userPasswordDO.setSid(userModel.getSid());
        userPasswordDO.setGid(userModel.getGid());
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
