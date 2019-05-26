package com.EduAdminSysProject.service.Impl;

import com.EduAdminSysProject.dao.CourseDOMapper;
import com.EduAdminSysProject.dataobject.CourseDO;
import com.EduAdminSysProject.error.BusinessException;
import com.EduAdminSysProject.error.EmBusinessError;
import com.EduAdminSysProject.service.CourseService;
import com.EduAdminSysProject.service.model.CourseModel;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    CourseDOMapper courseDOMapper;

    @Override
    public void addCourse(CourseModel courseModel) throws BusinessException {
        if (courseModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "course model is empty");
        }
        if (StringUtils.isEmpty(courseModel.getCid())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "course model is empty");
        }

        CourseDO courseDO = convertFromModel(courseModel);
        courseDOMapper.insertSelective(courseDO);
        return;
    }

    private CourseDO convertFromModel(CourseModel courseModel) throws BusinessException {
        if (courseModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "course model is empty");
        }
        CourseDO courseDO = new CourseDO();
        BeanUtils.copyProperties(courseModel, courseDO);
        return courseDO;

    }
}
