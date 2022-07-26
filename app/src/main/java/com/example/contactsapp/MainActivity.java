package com.example.contactsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ContactsModel> contactsModalArrayList;
    private RecyclerView contactRV;
    public ContactRVAdapter contactRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                1);
        contactsModalArrayList = new ArrayList<>();
        contactRV = findViewById(R.id.recycler);
        prepareContactRV();
        getContacts();
    }

    private void prepareContactRV() {
        // in this method we are preparing our recycler view with adapter.
        contactRVAdapter = new ContactRVAdapter(this, contactsModalArrayList);
        // on below line we are setting layout manager.
        contactRV.setLayoutManager(new LinearLayoutManager(this));
        // on below line we are setting adapter to our recycler view.
        contactRV.setAdapter(contactRVAdapter);
    }

    private void filter(String text) {
        ArrayList<ContactsModel> filteredlist = new ArrayList<>();
        for (ContactsModel item : contactsModalArrayList) {
            if (item.getUserName().toLowerCase().contains(text.toLowerCase()) || item.getCompany().toLowerCase().contains(text.toLowerCase()) || item.getContactNumber().toLowerCase().contains(text.toLowerCase()) || item.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Log.e("checkme", "filter: empty");
        } else {
            contactRVAdapter.filterList(filteredlist);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final android.widget.SearchView searchView = (android.widget.SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s.toLowerCase());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("Range")
    private void getContacts() {
        contactsModalArrayList= ContactUtil.INSTANCE.getAllContactsoldie(this);
        TextView textView=findViewById(R.id.textView);
        textView.setText("Total Contacts: "+contactsModalArrayList.size());
        Log.e("checkme", "getContacts: "+contactsModalArrayList.size() );
        contactRVAdapter.setData(contactsModalArrayList);
        contactRVAdapter.notifyDataSetChanged();
    }
}