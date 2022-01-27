package com.example.User;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidmads.library.qrgenearator.QRGEncoder;

public class FirstPage extends AppCompatActivity {

    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText edit_name;
    private EditText edit_phone;
    private EditText edit_addr;
    private Button btn_submitCus;
    private Button btn_back;
    private long maxid = 0;
    private String qrUrl = "";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        edit_name = findViewById(R.id.edit_name);
        edit_phone = findViewById(R.id.edit_phone);
        edit_addr = findViewById(R.id.edit_addr);
        btn_submitCus = findViewById(R.id.btn_submitCus);
        btn_back = findViewById(R.id.pageMain);
        final DatabaseReference dbCustomer = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Customer.class.getSimpleName());

        //back button
        btn_back = (Button) findViewById(R.id.pageMain);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });


        // initializing onclick listener for button.
        btn_submitCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if input blank
                if (edit_name.getText().toString().trim().isEmpty() || edit_phone.getText().toString().trim().isEmpty() || edit_addr.getText().toString().trim().isEmpty()) {
                    Toast.makeText(FirstPage.this, "Please input all details", Toast.LENGTH_SHORT).show();
                } else {

                    //Check if phone not digit
                    if (new Function().isNotStringInteger(edit_phone.getText().toString().trim())) {
                        Toast.makeText(FirstPage.this, "Phone number must be in digit", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        //Show loading
                        AlertDialog.Builder builder = new AlertDialog.Builder(FirstPage.this);
                        builder.setCancelable(false); // if you want user to wait for some process to finish,
                        builder.setView(R.layout.layout_dialog);
                        final AlertDialog dialog = builder.create();
                        dialog.show();

                        //Get number of data
                        dbCustomer.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot datasnapshot) {
                                maxid = (datasnapshot.getChildrenCount());

                                //Create QR
                                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                                new CreateQR().create(manager, qrCodeIV, String.valueOf(maxid));


                                if (qrCodeIV.getDrawable() != null) {
                                    //Push into database
                                    // Create a storage reference from our app
                                    StorageReference storageRef = FirebaseStorage.getInstance("gs://fir-f9b19.appspot.com/").getReference();

                                    //Get data and push
                                    dbCustomer.addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot datasnapshot) {
                                            Query que = dbCustomer.orderByChild("phone").equalTo(edit_phone.getText().toString().trim());
                                            que.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        Toast.makeText(FirstPage.this, "Number phone has been registered", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Customer customer = new Customer(String.valueOf(maxid + 1), edit_name.getText().toString().trim(), edit_phone.getText().toString().trim(), edit_addr.getText().toString().trim());
                                                        dbCustomer.child(String.valueOf(maxid + 1)).setValue(customer).addOnSuccessListener(suc -> {
                                                            Toast.makeText(FirstPage.this, "Data inserted. User ID: " + (maxid + 1), Toast.LENGTH_SHORT).show();
                                                        }).addOnFailureListener(er -> {
                                                            Toast.makeText(FirstPage.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }

                                    });
                                    dialog.dismiss();
                                    openMain();
                                } else {
                                    Toast.makeText(FirstPage.this, "Unsuccesful creating QR", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}