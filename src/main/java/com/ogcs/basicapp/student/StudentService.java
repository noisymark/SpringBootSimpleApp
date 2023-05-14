package com.ogcs.basicapp.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

        if(studentByEmail.isPresent()){
            throw new IllegalStateException("E-mail already taken!");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("Student with ID " + studentId + " does not exist!");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentID, String studentName, String studentEmail){
        Student student = studentRepository.findById(studentID).orElseThrow(() -> new IllegalStateException("Student with id " + studentID + " does not exist!"));
        if(studentName != null && studentName.length() > 0 && !Objects.equals(student.getName(), studentName)) {
            student.setName(studentName);
        }
        if(studentEmail != null && studentEmail.length() > 0 && !Objects.equals(student.getEmail(), studentEmail)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentEmail);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("E-mail is already taken!");
            }
            student.setEmail(studentEmail);
        }
    }
}
