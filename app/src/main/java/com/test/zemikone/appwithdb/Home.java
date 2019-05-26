package com.test.zemikone.appwithdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.test.zemikone.appwithdb.R;

import java.util.ArrayList;
//Here I have implemented the OnClickListener Interface.
public class Home extends AppCompatActivity implements View.OnClickListener {

    Button save;
    EditText name;
    ListView list;
    ArrayAdapter studentListAdapter;
    ArrayList<String> studentList;

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        name=(EditText) findViewById(R.id.txt_name);
        save=(Button) findViewById(R.id.btn_send);
        list =(ListView) findViewById(R.id.list_listView);

        database = openOrCreateDatabase("test.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        database.setVersion(1);

        createStudentTable();
        studentList =new ArrayList<>();

        save.setOnClickListener(this);
        setListViewValues();

    }

    @Override
    public void onClick(View v) {
        if (v== save){
            Toast.makeText(this, name.getText(), Toast.LENGTH_LONG).show();
            studentList.add(name.getText().toString());
            setListViewValues();
            name.setText("");
        }

    }

    public void setListViewValues(){
        studentListAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        //Assigning the name(value) to a string variable and put it into the adapter

        /*
        for(String studentName:studentList){
            studentListAdapter.add(studentName);
        }  */
        //Add that adapter into the List View

        /*
        list.setAdapter(studentListAdapter);
        */

        saveStudent(name.getText().toString());
        Cursor studentDetails= getStudentDetails();

        while (studentDetails.moveToNext())
        {
            studentListAdapter.add(studentDetails.getString(1));
        }

        list.setAdapter(studentListAdapter);

    }

    private  void createStudentTable()
    {
        database.execSQL("CREATE TABLE IF NOT EXISTS student(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)");
    }

    private void saveStudent(String name)
    {
        database.execSQL("INSERT INTO student(NAME) VALUES('"+name+"')");
    }

    private Cursor getStudentDetails()
    {
        Cursor c= database.rawQuery("SELECT * FROM student",null);
        return c;

    }
}
