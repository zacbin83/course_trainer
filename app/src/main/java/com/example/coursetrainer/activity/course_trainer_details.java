package com.example.coursetrainer.activity;



import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursetrainer.R;
import com.example.coursetrainer.model.Course;
import com.example.coursetrainer.retrofit.ApiService;
import com.example.coursetrainer.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class course_trainer_details extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_trainer_details);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                onBackPressed(); // Navigate back to the previous screen
            }
        });
        int courseId = getIntent().getIntExtra("id", -1);
        Log.d("CourseId", String.valueOf(courseId));
        Button deleteCourse = findViewById(R.id.deleteCourse);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), course_delete_trainer.class);
                intent.putExtra("id", courseId);
                view.getContext().startActivity(intent);
            }
        });
        Button courseScheduleList = findViewById(R.id.courseScheduleList);
        courseScheduleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), course_schedule_trainer_get.class);
                intent.putExtra("id", courseId);
                view.getContext().startActivity(intent);
            }
        });
        Button updateCourse = findViewById(R.id.updateCourse);
        updateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), update_course_trainer.class);
                intent.putExtra("id", courseId);
                view.getContext().startActivity(intent);
            }
        });

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<Course> call = apiService.getCourseById(courseId);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if (response.isSuccessful()) {
                    Course course = response.body();
                    String jsonString = new Gson().toJson(course);
                    Log.d("RES", jsonString);
                    Log.d("API", "Success");
                    displayCourse(course);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void displayCourse(Course course){

//        ImageView imageViewAva = findViewById(R.id.imageViewAva);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewRole = findViewById(R.id.textViewRole);
        TextView textViewStatus = findViewById(R.id.textViewStatus);
        TextView textViewCapacity = findViewById(R.id.textViewCapacity);
        TextView textViewLocation = findViewById(R.id.textViewLocation);
        TextView textViewFeeInfo = findViewById(R.id.textViewFeeInfo);
        TextView textViewDateStart = findViewById(R.id.textViewDateStart);
        TextView textViewDateEnd = findViewById(R.id.textViewDateEnd);
        TextView textViewDescInfo = findViewById(R.id.textViewDescInfo);



        Button deleteCourse = findViewById(R.id.deleteCourse);

//        imageViewAva.setImageResource(R.drawable.course_logo_img);
        textViewName.setText(Html.fromHtml("<b>Khóa học:</b> " + course.getTitle()));
        textViewRole.setText(String.valueOf("Loại: " + course.getServiceTypeString()));
        textViewStatus.setText(String.valueOf(course.getTrainerId()));
        textViewFeeInfo.setText(String.valueOf("Học phí: " + course.getFee()) + " VNĐ");
        textViewCapacity.setText(String.valueOf("Sĩ số: " + course.getCapacity() + " học viên"));
        textViewStatus.setText(Html.fromHtml("Trạng thái: " + course.getStringStatus() + "</font>"));
        Date dateS = new Date(course.getStartDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateStart = dateFormat.format(dateS);
        Date dateE = new Date(course.getEndDate());
        SimpleDateFormat dateEnd = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateEnd = dateEnd.format(dateE);
        textViewLocation.setText("Địa điểm: " + course.getLocation());
        textViewDateStart.setText("Ngày bắt đầu: " + formattedDateStart);
        textViewDateEnd.setText("Ngày kết thúc: " + formattedDateEnd);
        textViewDescInfo.setText(course.getDescription());
    }
}