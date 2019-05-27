package com.EduAdminSysProject.service.Impl;

import com.EduAdminSysProject.dao.GradeDOMapper;
import com.EduAdminSysProject.dataobject.GradeDO;
import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.service.GradeService;
import com.EduAdminSysProject.service.model.GradeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GradeServiceImpl implements GradeService {

    @Resource
    GradeDOMapper gradeDOMapper;

    @Override
    public void selectCourse(GradeModel gradeModel) throws BusinessException {
        if (gradeModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        GradeDO gradeDO = converFromModel(gradeModel);
        gradeDOMapper.insertSelective(gradeDO);
        return;
    }

    private GradeDO converFromModel(GradeModel gradeModel) throws BusinessException {
        if (gradeModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        GradeDO gradeDO = new GradeDO();
        BeanUtils.copyProperties(gradeModel, gradeDO);
        return gradeDO;
    }
}
