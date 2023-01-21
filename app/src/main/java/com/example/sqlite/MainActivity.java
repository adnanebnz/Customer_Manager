package com.example.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText et1,et2;
    Switch s1;
    Button btn1,btn2;
    ListView list;
    Data dataBaseHelper;
    ArrayAdapter customerArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        btn1 = findViewById(R.id.btn1);
        s1 = findViewById(R.id.s1);
        list = findViewById(R.id.list);
        dataBaseHelper = new Data(MainActivity.this);
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getEveryone());
        list.setAdapter(customerArrayAdapter);

        //Button listeners

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1,et1.getText().toString(),Integer.parseInt(et2.getText().toString()),s1.isChecked());
                    Toast.makeText(MainActivity.this,"Customer added",Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this,"Error creating customer",Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1,"null",0,false);

                };
                Data data = new Data(MainActivity.this);
                boolean success = data.addOne(customerModel);

                customerArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
                list.setAdapter(customerArrayAdapter);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getEveryone());
                list.setAdapter(customerArrayAdapter);
                Toast.makeText(MainActivity.this,"Customer deleted",Toast.LENGTH_SHORT).show();
            }
        });
    }
}