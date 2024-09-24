package com.example.restminicourse;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restminicourse.pojo.MultipleResource;
import com.example.restminicourse.pojo.User;
import com.example.restminicourse.pojo.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView responseText;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        responseText = findViewById(R.id.responseText);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        //GET list resources
        Call<MultipleResource> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {
                Log.d("RESTUT", response.code()+"");

                String displayResponse = "";
                MultipleResource resource = response.body();
                Integer text = resource.page;
                Integer total = resource.total;
                Integer totalPages = resource.totalPages;
                List<MultipleResource.Datum> datumList = resource.data;

                displayResponse += text+" Page\n"+total+" Total\n"+totalPages+" Total Pages\n";
                for(MultipleResource.Datum datum : datumList){
                    displayResponse += datum.id+" "+datum.name+" "+datum.pantoneValue+" "+ datum.year+"\n";
                }
                responseText.setText(displayResponse);
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });

        //Create new user
        User user = new User("Morpheus", "Leader");
        Call<User> userCall = apiInterface.createUser(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User tmpUser = response.body();
                Toast.makeText(getApplicationContext(), tmpUser.name+" "+tmpUser.job+" "+tmpUser.id+" "+tmpUser.createdAt, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });

        //GET list users
        Call<UserList> callUsers = apiInterface.doGetUserList("2");
        callUsers.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                UserList userList = response.body();
                Integer text = userList.page;
                Integer total = userList.total;
                Integer totalPages = userList.totalPages;
                List<UserList.Datum> datumList = userList.data;

                Toast.makeText(getApplicationContext(), text+" Page\n"+total+" Total\n"+totalPages+" Total Pages\n", Toast.LENGTH_SHORT).show();
                for(UserList.Datum datum : datumList){
                    Toast.makeText(getApplicationContext(), "id: "+datum.id+" Name: "+datum.first_name+" "+datum.last_name+" Avatar: "+ datum.avatar, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
            }
        });

        //POST name and job URL encoded
        Call<UserList> callUsers2 = apiInterface.doCreateUserWithField("Morpheus", "Leader");
        callUsers2.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                UserList userList = response.body();
                Integer text = userList.page;
                Integer total = userList.total;
                Integer totalPages = userList.totalPages;
                List<UserList.Datum> datumList = userList.data;

                Toast.makeText(getApplicationContext(), text+" Page\n"+total+" Total\n"+totalPages+" Total Pages\n", Toast.LENGTH_SHORT).show();
                for(UserList.Datum datum : datumList){
                    Toast.makeText(getApplicationContext(), "id: "+datum.id+" Name: "+datum.first_name+" "+datum.last_name+" Avatar: "+ datum.avatar, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
            }
        });

    }
}