package com.example.coursetrainer.retrofit;

import com.example.coursetrainer.activity.course_schedule_trainer_get;
import com.example.coursetrainer.model.Course;
import com.example.coursetrainer.model.CourseSchedule;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
//    @GET("v1/courses")
//    Call<List<Course>> getAllCourses();
    @GET("v1/courses/{id}")
    Call<List<Course>> getCoursesByTrainerID(@Path("id") int id);
    @GET("v1/courses/dtl/{id}")
    Call<Course> getCourseById(@Path("id") int id);

    @GET("/v1/courseSchedule")
    Call<course_schedule_trainer_get.GetCourseSchedule> getCourseScheduleByCourseId(@Query("courseId") int courseId);
    @DELETE("v1/courses/{id}")
    Call<Course> deleteCourseByID(@Path("id") int id);
    @POST("v1/courses")
    Call<Course> createCourse(@Body Course course);
    @PUT("v1/courses/{id}")
    Call<Course> updateCourse(@Body Course course, @Path("id") int id);

}