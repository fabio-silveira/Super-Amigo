package fabio.silveira.superamigo.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import fabio.silveira.superamigo.R;
import fabio.silveira.superamigo.adapter.DogAdapter;
import fabio.silveira.superamigo.model.Dog;

public class MainActivity extends AppCompatActivity {




    //Attributes + adapter
    private RecyclerView rvDogs;
    private ArrayList<Dog> dogs;
    private DogAdapter adapter;



    //MENU
    private Drawer result = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initialize();


        //Firebase
        FirebaseApp.initializeApp(MainActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("dogs");


        //DRAWER MENU HEADER
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this).withHeaderBackground(R.mipmap.ic_launcher)
                .addProfiles(new ProfileDrawerItem().withName("Fábio Silveira").withEmail("fabiost3@live.com").
                        withIcon(getResources().getDrawable(R.mipmap.ic_launcher))).withOnAccountHeaderListener
                        (new AccountHeader.OnAccountHeaderListener() {
                            @Override
                            public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                                return false;
                            }
                        }).build();
        //MENU
        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIdentifier(0).withIcon(GoogleMaterial.Icon.gmd_home),
                        new DividerDrawerItem(),
                        new SectionDrawerItem().withName("Ações"),
                        new SecondaryDrawerItem().withName("Adicionar Animal").withIdentifier(1).withIcon(FontAwesome.Icon.faw_plus),
                        new SecondaryDrawerItem().withName("Facebook").withIdentifier(2).withIcon(FontAwesome.Icon.faw_facebook))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Intent it;

                        switch ((int)drawerItem.getIdentifier()){
                            case 0:
                                it = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(it);
                                break;

                            case 1:
                                it = new Intent(MainActivity.this,Upload.class);
                                startActivity(it);
                                break;

                            case 2:
                                Toast.makeText(getBaseContext(),"Pagina do FB",Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                }).build();

        adapter.setOnItemClickListener(new DogAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Empty
            }

            //ItemClick
            @Override
            public void onItemLongClick(View v, final int position) {
                AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                msg.setTitle("Confirmação");
                msg.setMessage("Você tem certeza que deseja excluir este cadastro?");
                msg.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dog d = dogs.get(position);
                        banco.child(d.getKey()).removeValue();
                        toast("Registro Excluido");
                    }
                });
                msg.setNegativeButton("Não", null);
                msg.show();
            }
        });

        //Database Select
        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogs.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Dog d = data.getValue(Dog.class);
                    d.setKey(data.getKey());
                    dogs.add(d);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




    }//oncreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }

    private void initialize(){

        toolbar = findViewById(R.id.toolbar);
        rvDogs = findViewById(R.id.ma_rv_dogs);

        dogs = new ArrayList<>();
        adapter = new DogAdapter(MainActivity.this,dogs);

        rvDogs.setAdapter(adapter);
        rvDogs.setHasFixedSize(true);
        rvDogs.setLayoutManager(new LinearLayoutManager(this));
    }

}
