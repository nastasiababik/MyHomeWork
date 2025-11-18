package com.example;

public class StudentRepositoryMock implements StudentRepo{
    public int getRatingForGradeSum(int sum){
        if(sum>50) return 9;
        return 10;
    }
    public long count(){return 0;}
    public void delete(Student entity){}
    public void deleteAll(Iterable<Student> entities){}
    public Iterable<Student> findAll(){return null;}
    public Student save(Student entity){return null;}
    public Iterable<Student> saveAll(Iterable<Student> entities){return null;}
}
