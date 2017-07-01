package com.example.hindahmed.anti_cancer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Public_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String body_of_post ;
    String Uid;
    String Author;
    String Email;
    String Name ;
    ListView listView;
    List post_s = new ArrayList();
    TextView name_text ;
    TextView H_Text_Name ;
    AlertDialog.Builder alertDialogBuilder ;
    static FirebaseAuth mAuth;
    static FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mFirebaseDatabase_Users;
    private DatabaseReference mFirebaseDatabase_Posts;
    private FirebaseDatabase mFirebaseInstance;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.List_of_all_posts);
        prefManager = new PrefManager(this);
        update_posts();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase_Users = mFirebaseInstance.getReference("Users");
        // get reference to 'posts' node
        mFirebaseDatabase_Posts = mFirebaseInstance.getReference("Posts");
        //get userid and email
        Uid = prefManager.getuid();
        Email = prefManager.getemail();
        Name = prefManager.getname() ;

        mFirebaseDatabase_Users.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user_details = dataSnapshot.getValue(User.class);
                Author = user_details.getUsername();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Public_Activity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        mFirebaseDatabase_Posts.child("posts").addValueEventListener(new ValueEventListener() {
            long childrenCount;
            Object value;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Post postss = noteDataSnapshot.getValue(Post.class);
//                    childrenCount = noteDataSnapshot.getChildrenCount();
//                    value = noteDataSnapshot.getValue();
                    post_s.add(postss);
                }
                int size = post_s.size();
                update_posts();
                Toast.makeText(Public_Activity.this, childrenCount + "//" + value + "", Toast.LENGTH_LONG).show();
                Toast.makeText(Public_Activity.this, size + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add your story :)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Write_new_post();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // Nav Header
        View H_View =navigationView.getHeaderView(0);
         H_Text_Name = (TextView) H_View.findViewById(R.id.H_name);
        //set user name in nav_header
        H_Text_Name.setText(Name);
    }

    private void update_posts() {

        listView.setAdapter(new m_Adapter(this, post_s));
    }


    private void Write_new_post() {

        // get prompts.xml view
        LayoutInflater add_design_Xml = LayoutInflater.from(this);
        View promptsView = add_design_Xml.inflate(R.layout.add_post_design, null);

        alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Share your story")
                .setIcon(R.drawable.lol)
                .setPositiveButton("Post",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                body_of_post = userInput.getText().toString();
                                writeNewPost(Uid, Author, "new post", body_of_post);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mFirebaseDatabase_Posts.push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mFirebaseDatabase_Posts.updateChildren(childUpdates);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {

            Toast.makeText(Public_Activity.this,"slideshow",Toast.LENGTH_LONG).show();

            Intent i = new Intent(Public_Activity.this, slide_show_activity.class);
            startActivity(i);

        } else if (id == R.id.nav_filter) {

            // set dialog message

            Toast.makeText(Public_Activity.this, "Filter", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_notification) {
            Toast.makeText(Public_Activity.this, "notification", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_share) {
            Toast.makeText(Public_Activity.this, "share", Toast.LENGTH_LONG).show();
            //whatsapp
            String shareBody = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } else if (id == R.id.nav_send) {

            Toast.makeText(Public_Activity.this, "send", Toast.LENGTH_LONG).show();
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"hind_ahmed31@hotmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));

        }else if(id==R.id.logout) {
            mAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
