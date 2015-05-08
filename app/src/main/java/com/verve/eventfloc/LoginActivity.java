package com.verve.eventfloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Vanessa on 8/05/2015.
 */
public class LoginActivity extends ActionBarActivity {

    private Button landingLoginButton;
    private Button landingRegisterButton;
    private EditText userName;
    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final DatabaseQueries dq = new DatabaseQueries(LoginActivity.this);
        landingSetButtons();




        landingRegisterButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(i, 0);
            }
        });

        landingLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean successfulLogin = false;
                String mUsername = userName.getText().toString();
                String mPassword = password.getText().toString();

                try {
                    successfulLogin = dq.requestLogin(mUsername, mPassword);
                    Log.d("BEFORE","" + successfulLogin);
                    if(successfulLogin){
                        Log.d("LOGIN STATUS", "Successful login");
                        loginToast(true);
                    }
                    else {
                        Log.d("LOGIN STATUS", "Unsuccessful login");
                        loginToast(false);
                    }

                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                /**
                try {
                    successfulLogin =  dq.requestLogin(mUsername, mPassword);
                    if(successfulLogin = true){

                        Log.d("LOGIN STATUS", "Successful Login");
                    }
                    // Log.d("LOGIN ACCOUNT", dq.getUserEmail(mUsername).toString());

                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                    Log.d("LOGIN STATUS", "Unsuccessful Login");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Log.d("LOGIN STATUS", "Unsuccessful Login");
                }

                if(successfulLogin = true){
                    //WHAT HAPPENS IF USER SUCCESSFULLY LOGS IN?

                    //login activity goes here
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    loginToast(true);
                }
                else if(successfulLogin = false){
                    //WHAT HAPPENS IF UNSUCCESSFUL LOGIN?
                    loginToast(false);
                }*/

            }

        });



    }


    /**
     * Set the buttons and fields on landing page
     */
    public void landingSetButtons(){
        landingLoginButton = (Button)findViewById(R.id.loginButton);
        landingRegisterButton = (Button)findViewById(R.id.registerButton);
        userName = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPassword);

    }


    /**
     * Set the toast message for successful or unsuccessful login
     * @param loginStatus
     */
    public void loginToast(boolean loginStatus){
        int toastMessage = 0;

        if(loginStatus){
            toastMessage = R.string.successful_login;
        }
        else{
            toastMessage = R.string.unsuccessful_login;
        }
        Toast.makeText(LoginActivity.this, toastMessage, Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
     EventType a = new EventType(4563, "Party");
     EventType b = new EventType(4652, "Free Food");

     String party = "Event ID " + a.getEventTypeID() + " is a " + a.getEventType();
     String freeFood = "Event ID " + b.getEventTypeID() + " is a " + b.getEventType();

     System.out.println(party);
     System.out.println(freeFood);

     Student s = new Student("s@google.com", "hello", 76543, "Bob", "Bob");


     System.out.println(s.toString());

     Student d = new Student("bob@gmail.com", "oijasd", 12345, "James", "James");
     System.out.println(d.toString());
     }**/


}
