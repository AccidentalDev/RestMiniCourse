package com.example.restminicourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonTest1;
    Button buttonTest2;
    Button buttonTest3;
    Button buttonTest4;
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

        buttonTest1 = findViewById(R.id.Button1);
        buttonTest2 = findViewById(R.id.Button2);
        buttonTest3 = findViewById(R.id.Button3);
        buttonTest4 = findViewById(R.id.Button4);

        buttonTest1.setOnClickListener(this);
        buttonTest2.setOnClickListener(this);
        buttonTest3.setOnClickListener(this);
        buttonTest4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        responseText.setText("Loading...");
        if(view == buttonTest1){
            Toast.makeText(getApplicationContext(), "GET list resources TEST", Toast.LENGTH_LONG).show();
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
        }else if(view == buttonTest2){
            Toast.makeText(getApplicationContext(), "Create new user TEST", Toast.LENGTH_LONG).show();
            //Create new user
            User user = new User("Morpheus", "Leader");
            Call<User> userCall = apiInterface.createUser(user);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User tmpUser = response.body();
                    String newResponse = tmpUser.name+", "+tmpUser.job+", "+tmpUser.id+", "+tmpUser.createdAt;
                    responseText.setText(newResponse);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    call.cancel();
                }
            });
        }else if(view == buttonTest3){
            Toast.makeText(getApplicationContext(), "GET list users TEST", Toast.LENGTH_LONG).show();
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

                    String resultText = text+" Page\n"+total+" Total\n"+totalPages+" Total Pages\n";
                    for(UserList.Datum datum : datumList){
                        resultText = resultText+"id: "+datum.id+" Name: "+datum.first_name+" "+datum.last_name+" Avatar: "+datum.avatar+"\n";
                    }

                    responseText.setText(resultText);
                }

                @Override
                public void onFailure(Call<UserList> call, Throwable t) {
                    call.cancel();
                }
            });
        } else if (view == buttonTest4) {
            Toast.makeText(getApplicationContext(), "POST name and job URL encoded TEST", Toast.LENGTH_LONG).show();
            //POST name and job URL encoded
            Call<User> callUsers2 = apiInterface.doCreateUserWithField("Morpheus", "Leader");
            callUsers2.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User userResponse = response.body();

                    String newResponse = userResponse.name+", "+userResponse.job+", "+userResponse.id+", "+userResponse.createdAt;
                    responseText.setText(newResponse);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    call.cancel();
                }
            });
        }
    }
}