package egat.kampeepan.kampeepanservice;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

    }   //Main Method

    //Create Inner Class : select ข้อมูลจาก db โดยเขียนconnect กับ php
    private class SynchronizeUser extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;    //โยน data ระหว่าง class ต้องต่อท่อ context
        private String myUserString, myPasswordString,
                truePasswordString, nameString;  //รับค่า string จาก main class
        private static final String urlJson = "http://swiftcodingthai.com/6aug/get_user_bow.php";
        private boolean statusABoolean = true;

        public SynchronizeUser(Context context, String myUserString, String myPasswordString) {
            this.context = context;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;
        }   //Alt+Insert -> Constructor

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJson).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
                //Success
            } catch (Exception e) {
                e.printStackTrace();
                return null;
                //Unsuccess
            }

            //return null;
        }   //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("7AugV1", "JSON ==> " + s);
            //ข้อมูลจาก DB มาเป็น JsonArray
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i+=1) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i); //ชี้เป้า
                    if (myUserString.equals(jsonObject.getString("User"))) {
                        truePasswordString = jsonObject.getString("Password");
                        nameString = jsonObject.getString("Name");
                        statusABoolean = false;
                    }   //if
                }   //for
                if (statusABoolean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, 4, "ไม่มี User", "ไม่มี " + myUserString + "ในระบบ");
                } else if (passwordString.equals(truePasswordString)) {
                    //Password True
                    //Toast = text Alert แล้วหายไป
                    Toast.makeText(context, "Welcome " + nameString, Toast.LENGTH_SHORT).show();

                } else {
                    //Password False
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, 4,"Wrong Password", "Please fill in correct password");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }   //onPost
    }   //Sync User Class


    public void clickSignIn(View view){
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, 3, "Have Space", "Please Complete your Username and Password");
        } else {
            //Call Sync User
            SynchronizeUser synchronizeUser = new SynchronizeUser(this, userString, passwordString);
            synchronizeUser.execute();
        }

    } //Click Sign In

    public void clickSignUpMain(View view) {

        startActivity(new Intent(MainActivity.this, SignUpActivity.class));

    }

}   //Main Class
