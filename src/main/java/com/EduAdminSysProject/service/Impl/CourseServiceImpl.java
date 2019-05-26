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
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<CourseModel> getAllCourse() {
        List<CourseDO> courseDOList = courseDOMapper.selectAllCourses();
        List<CourseModel> courseModelList = courseDOList.stream().map(courseDO ->{
            CourseModel courseModel = convertToModel(courseDO);
            return courseModel;
        }).collect(Collectors.toList());
        return courseModelList;
    }

    private CourseModel convertToModel(CourseDO courseDO) {
        if (courseDO == null) {
            return null;
        }
        CourseModel courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDO, courseModel);
        return courseModel;
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
