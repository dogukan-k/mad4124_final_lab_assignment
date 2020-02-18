package com.dku.dogukankarayilanoglu_mad4124_final_lab_assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonDetailController extends AppCompatActivity {

    int incomingPersonId;
    Person person;
    final PersonDatabase personDatabase = new PersonDatabase(this);

    EditText name;
    EditText lastName;
    EditText phoneNumber;
    EditText address;

    Button updateButton;
    Button removeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detailcontroller);

        incomingPersonId = getIntent().getExtras().getInt("incomingPersonId");
        person = personDatabase.getPerson(incomingPersonId);

        name= findViewById(R.id.txt_nameD);
        lastName = findViewById(R.id.txt_last_nameD);
        phoneNumber = findViewById(R.id.txt_phone_numberD);
        address=findViewById(R.id.txt_addressD);

        updateButton = findViewById(R.id.btn_update_person);
        removeButton = findViewById(R.id.btn_remove_person);


        name.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        phoneNumber.setText(person.getPhoneNumber());
        address.setText(person.getAddress());


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().equals("") && !lastName.getText().toString().equals("") && !phoneNumber.getText().toString().equals("") && !address.getText().toString().equals("")){


                    person.setFirstName(name.getText().toString());
                    person.setLastName(lastName.getText().toString());
                    person.setPhoneNumber(phoneNumber.getText().toString());
                    person.setAddress(address.getText().toString());

                    personDatabase.updatePerson(person);
                    Toast.makeText(PersonDetailController.this,"Person updated",Toast.LENGTH_SHORT).show();
                    finish();

                }

                else {
                    Toast.makeText(PersonDetailController.this,"Please fill all fields to update",Toast.LENGTH_SHORT).show();
                }
            }
        });


        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonDetailController.this);
                builder.setTitle(person.toString() + " will be deleted \nAre you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete person and finish;
                        personDatabase.deletePerson(person.getPersonId());
                        Toast.makeText(PersonDetailController.this,person.toString()+" is deleted",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }

        });




    }
}
