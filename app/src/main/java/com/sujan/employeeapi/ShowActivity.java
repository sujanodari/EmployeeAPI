package com.sujan.employeeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sujan.employeeapi.api.EmployeeApi;
import com.sujan.employeeapi.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowActivity extends AppCompatActivity {
    private TextView tvData;
    private  String base_Url="http://dummy.restapiexample.com/api/v1/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        tvData=findViewById(R.id.tvData);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//Interface instance
        EmployeeApi employeeApi=retrofit.create(EmployeeApi.class);
        Call<List<Employee>>listCall=employeeApi.getAllEmployees();
        //Asyncrinous call
        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {

                if(!response.isSuccessful()){
                    tvData.setText("Code :"+response.code());
                    Toast.makeText(ShowActivity.this, "Data fetch Unsucessfull"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Employee>employeeList=response.body();
                for(Employee employee:employeeList){
                    String emp="";

                    emp+="ID :"+employee.getId() +"\n";
                    emp+="Employee Name :"+employee.getEmployee_name() +"\n";
                    emp+="............................. \n";

                    tvData.append(emp);
                }


            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                tvData.setText("Error"+t.getMessage());
                Toast.makeText(ShowActivity.this, "Internal server error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("My message", "onFailure: "+t.getLocalizedMessage());
            }
        });


    }
}
