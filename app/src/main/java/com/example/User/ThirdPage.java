package com.example.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ThirdPage extends AppCompatActivity {

    private ImageView qr_code;
    private TextView view_details;
    private TextView cus_id_text;
    private EditText edit_id;
    private Button btn_id;
    private Button btn_back;
    private Button btn_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_page_design);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        qr_code = (ImageView) findViewById(R.id.idIVQrcode);
        //view_details = (TextView) findViewById(R.id.view_details);
        cus_id_text = (TextView) findViewById(R.id.cus_id_text);
        //edit_id = (EditText) findViewById(R.id.edit_id);
        //btn_id = (Button) findViewById(R.id.btn_id);

        final DatabaseReference dbParcel = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Parcel.class.getSimpleName());
        final DatabaseReference dbCustomer = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Customer.class.getSimpleName());

        btn_display = (Button) findViewById(R.id.btn_display);
        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDisplay("1");
            }
        });


        //Create QR
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        new CreateQR().create(manager, qr_code, "1");
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openDisplay(String cusId) {
        Intent intent = new Intent(this, ThirdPageView.class);
        intent.putExtra("key","1");
        startActivity(intent);
    }
}

