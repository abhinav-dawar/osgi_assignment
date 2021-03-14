package com.mysite.core.services.impl;

import com.mysite.core.services.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import java.util.ArrayList;
import java.util.List;

@Component(service= {StudentClassService.class}, immediate = true)
@Designate(ocd= Configuration2.class)
class StudentClassServiceImpl implements StudentClassService{

    private List<Student> studentList=new ArrayList<Student>();

    @Reference
    private ConfigurationService configurationService;

    @Override
    public void addStudent(Student student) {

        if(! configurationService.isClassLimitReached(studentList))
            studentList.add(student);
        else
            System.out.println("Student limit reached");
    }


    @Override
    public void deleteStudent(int id) {
        if(studentList.size() > 0){
            int i = 0;
            while(i < studentList.size()){
                if(studentList.get(i).id == id){
                    studentList.remove(i);
                    break;
                }
                i++;
            }
        }

    }

    @Override
    public boolean isStudentPassed(int id) {
        int i = 0;
        while(i < studentList.size()){
            if(studentList.get(i).id == id){
                return studentList.get(i).marks_obtained > configurationService.getPassingMarks();
            }
        }
        return false;
    }

    @Override
    public Student getStudent(int id) {
        int i = 0;
        while(i < studentList.size()){
            if(studentList.get(i).id == id){
                return studentList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentList;
    }

}
