package com.example.simpletodo;

import org.apache.commons.io.FileUtils;

//import android.os.FileUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;



public class MainActivity extends AppCompatActivity {

     public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
     public static final int EDIT_TEXT_CODE = 20;

     ArrayList items;

     Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;

     ItemAdapter itemsAdapter;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

         loadItems();



        ItemAdapter.OnLongClickListener onLongClickListener = position -> {
            items.remove(position);
            itemsAdapter.notifyItemRemoved(position);
            Toast.makeText(getApplicationContext(), "Item removed!", Toast.LENGTH_LONG).show();
            saveItems();
        };


        itemsAdapter = new ItemAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(v -> {
            String item = etItem.getText().toString();
            items.add(item);
            itemsAdapter.notifyItemInserted(items.size() - 1);
            etItem.setText("");
            saveItems();

            Toast.makeText(getApplicationContext(), "Added todo item!", Toast.LENGTH_SHORT).show();
        });
    }


    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}