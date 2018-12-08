package com.drmmx.devmax.randomuserapp.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.drmmx.devmax.randomuserapp.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import static android.Manifest.permission.CALL_PHONE;
import static com.drmmx.devmax.randomuserapp.util.Utils.*;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private MaterialButton callButton;

    //Data variables
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private int userAge;
    private String phoneNumber;
    private String email;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        callButton = findViewById(R.id.callButton);

        //Add call permission
        Dexter.withActivity(this)
                .withPermission(CALL_PHONE)
        .withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DetailActivity.this, "Call function blocked", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            }
        }).check();

        CircleImageView circleImageView = findViewById(R.id.circleImageView);
        TextView nameLastName = findViewById(R.id.nameLastName);
        TextView textViewAgeGender = findViewById(R.id.textViewAgeGender);
        TextView textViewCellPhone = findViewById(R.id.textViewCellPhone);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewDob = findViewById(R.id.textViewDob);

        fetchData();

        Picasso.get().load(imageUrl).placeholder(R.drawable.loading).into(circleImageView);
        nameLastName.setText(new StringBuilder(firstUpperCase(firstName)).append(" ").append(firstUpperCase(lastName)));
        textViewAgeGender.setText(new StringBuilder(firstUpperCase(gender)).append(", ")
                .append(userAge).append(" years old"));
        textViewCellPhone.setText(phoneNumber);
        textViewEmail.setText(email);
        textViewDob.setText(viewSimpleDate(dateOfBirth));

    }

    private void fetchData() {
        firstName = getIntent().getStringExtra(FIRST_NAME);
        lastName = getIntent().getStringExtra(LAST_NAME);
        gender = getIntent().getStringExtra(GENDER);
        dateOfBirth = getIntent().getStringExtra(DATE_OF_BIRTH);
        userAge = getIntent().getIntExtra(USER_AGE, 99);
        phoneNumber = getIntent().getStringExtra(USER_PHONE_NUMBER);
        email = getIntent().getStringExtra(USER_EMAIL);
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
    }
}
