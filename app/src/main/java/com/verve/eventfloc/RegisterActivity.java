package com.verve.eventfloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * Created by Vanessa on 8/05/2015.
 */
public class RegisterActivity extends ActionBarActivity {
    public static final String EXTRA_REGISTER_CLICKED = "com.example.raymond.eventfloc.registration";

    EditText registerFirstName;
    EditText registerLastName;
    EditText registerEmail;
    EditText registerPassword;
    EditText registerConfirmPassword;
    RadioButton registerSocietyType;
    RadioButton registerStudentType;
    Button registerButton;
    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerSetButtons();
        final DatabaseQueries dq = new DatabaseQueries(this);


        //This doesn't work for some reason. Also the buttons are messed up (can't unselect)
        registerSocietyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if society radio button is checked, uncheck the student raadio button
                registerStudentType.setChecked(false);


                registerFirstName.setHint("Society Name");
                registerLastName.setHint("Faculty");
            }
        });

        registerStudentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uncheck the society radio button if student button is checked
                registerSocietyType.setChecked(false);


                registerFirstName.setHint("James Name");
                registerLastName.setHint("Last Name");

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstName = registerFirstName.getText().toString();
                String lastName = registerLastName.getText().toString();
                String password1 = registerPassword.getText().toString();
                String password2 = registerConfirmPassword.getText().toString();
                String email = registerEmail.getText().toString();

                //Checks if there are any empty fields
                if (!firstName.trim().equals("") || !lastName.trim().equals("") || !password1.trim().equals("") || !password2.trim().equals("")) {
                    //Check if email is valid
                    if (email.matches(".*@*.") && email.matches(".*.com*.")) {
                        //Checks if entered passwords are equal to each other
                        if (password1.equals(password2)) {


                            boolean exists = false;


                            //if the password length is greater than 8
                            if (password1.length() > 8) {

                                //Checks to see if the email has already been registered
                                exists = dq.doesEmailExist(email);
                                if (exists) {
                                    //the email has been registered already, show toast
                                    Toast.makeText(RegisterActivity.this, R.string.email_registered, Toast.LENGTH_LONG).show();
                                } else {
                                    //email has not been registered, insert the user into DB
                                    if (registerStudentType.isChecked()) {
                                        //if student radio button is selected
                                        Student s = null;
                                        Log.d("ADDING STUDENT", s.toString());
                                        dq.insertStudent(s);
                                        //show successful registration toast
                                        passwordToast(true);
                                    } else if (registerSocietyType.isChecked()) {
                                        //if society radio button is selected
                                        Society s = null;
                                        Log.d("ADDING SOCIETY", s.toString());
                                        dq.insertSociety(s);
                                        //show successful registration toast
                                        passwordToast(true);
                                    } else {
                                        //if no radio button selected, assume default of student
                                        Student s = null;
                                        Log.d("ADDING STUDENT", s.toString());
                                        dq.insertStudent(s);
                                        //show successful registration toast
                                        passwordToast(true);
                                    }
                                }
                            } else {
                                //show password too short toast
                                Toast.makeText(RegisterActivity.this, R.string.password_length, Toast.LENGTH_LONG).show();
                            }


                        } else {
                            //Show the non-matching password toast if passwords not equal
                            passwordToast(false);
                        }
                    } else {
                        //show invalid email toast if email is not valid
                        Toast.makeText(RegisterActivity.this, R.string.invalid_email, Toast.LENGTH_LONG).show();
                    }
                } else {
                    //if one or more fields empty show toast
                    Toast.makeText(RegisterActivity.this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                }

            }

        });


            backButton.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                startLoginActivity();
            }
            }

            );

        }


                /**
                 * Change the toast if some fields are correct/incorrect
                 * @param goodPassword
                 */

    public void passwordToast(boolean goodPassword) {
        int toastMessage = 0;


        if (goodPassword) {
            toastMessage = R.string.matching_password;

        } else {
            toastMessage = R.string.nonmatching_password;
        }

        Toast.makeText(RegisterActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

    }

    ////


    /**
     * Set the buttons
     */
    public void registerSetButtons() {
        registerFirstName = (EditText) findViewById(R.id.editFirstname);
        registerLastName = (EditText) findViewById(R.id.editLastname);
        registerEmail = (EditText) findViewById(R.id.email);
        registerPassword = (EditText) findViewById(R.id.password);
        registerConfirmPassword = (EditText) findViewById(R.id.password_confirm);
        registerStudentType = (RadioButton) findViewById(R.id.radioButton2);
        registerSocietyType = (RadioButton) findViewById(R.id.radioButton2);
        registerButton = (Button) findViewById(R.id.registerButton);
        backButton = (Button) findViewById(R.id.backButtonRegister);
    }


    /**
     * Creates a student with the filled in registration form
     *
     * @return
     */
    public Student fillStudent() throws InvalidKeySpecException, NoSuchAlgorithmException {
        DatabaseQueries dq = new DatabaseQueries(this);
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();


        String firstName = registerFirstName.getText().toString();
        String lastName = registerLastName.getText().toString();
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        String confirmPassword = registerConfirmPassword.getText().toString();
        int userType = 0;
        if (registerStudentType.isChecked()) {
            userType = 1;
        } else if (registerSocietyType.isChecked()) {
            userType = 2;
        } else {
            userType = 1;
        }


        Student s = new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setUserEmail(email);
        if (password.equals(confirmPassword)) {
            String encryptedPassword = passwordEncryptor.encryptPassword(password);
            s.setPassword(encryptedPassword);
        } else


            s.setUserType(userType);

        return s;

    }

    /**
     * When Radio button selected, changes fields
     *
     * @param v

    public void radioUserType(View v) {

        if (v == registerSocietyType) {
            registerFirstName.setHint("Society Name");
            registerLastName.setHint("Faculty");

        } else if (v == registerStudentType) {
            registerFirstName.setHint("First Name");
            registerLastName.setHint("Last Name");
        }
    }*/

    /**
     * Start the activity
     */
    public void startLoginActivity() {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(i);
    }

    /**
     * Gets societ details from filled out registration form
     *
     * @param v
     * @return
     */
    public Society fillSociety(View v) {
        DatabaseQueries dq = new DatabaseQueries(this);

        String societyName = registerFirstName.getText().toString();
        String societyEmail = registerEmail.getText().toString();

        //HOW DO WE DO APPROVED DATE?
        Date societyDate = new Date();
        String societyFaculty = registerLastName.getText().toString();

        String password = registerPassword.getText().toString();
        String confirmPassword = registerConfirmPassword.getText().toString();
        int userType = 0;
        if (registerStudentType.isSelected()) {
            userType = 1;
        } else if (registerSocietyType.isSelected()) {
            userType = 2;
        }

        Society s = new Society();
        s.setSocietyName(societyName);
        s.setUserEmail(societyEmail);
        s.setApprovalDate(societyDate);
        s.setPassword(password);
        s.setSocietyFaculty(societyFaculty);

        return s;
    }


}
