package fabio.silveira.superamigo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import fabio.silveira.superamigo.R;
import fabio.silveira.superamigo.adapter.DogAdapter;
import fabio.silveira.superamigo.model.Dog;

public class Upload extends AppCompatActivity {

    private EditText etName;
    private Button btRegister;
    private Spinner spGender;
    private Spinner spSize;
    private Spinner spAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initialize();

        //Firebase
        FirebaseApp.initializeApp(Upload.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("dogs");


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dog d = new Dog();

                //Validations

                if (etName.getText().toString().isEmpty()){
                    etName.setError("Digite um nome");
                    toast("Nome inválido!");
                    return;
                }
                if (spGender.getSelectedItemPosition() == 0){
                    toast("Selecione um Gênero");
                    return;
                }

                if (spSize.getSelectedItemPosition() == 0){
                    toast("Selecione um Porte");
                    return;
                }

                if (spAge.getSelectedItemPosition() == 0){
                    toast("Selecione uma Idade");
                    return;
                }

                d.setName(etName.getText().toString());
                d.setGender(spGender.getSelectedItem().toString());
                d.setAge(spAge.getSelectedItem().toString());
                d.setSize(spSize.getSelectedItem().toString());

                banco.push().setValue(d);

                toast("Animal Cadastrado!");
                limpar();

            }
        });



    }//oncreate

    private void initialize(){
        etName = findViewById(R.id.up_et_name);
        btRegister = findViewById(R.id.up_bt_register);
        spGender = findViewById(R.id.up_sp_gender);
        spSize = findViewById(R.id.up_sp_size);
        spAge = findViewById(R.id.up_sp_age);

    }

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }

    private void limpar(){
        etName.setText("");
        spGender.setSelection(0);
        spAge.setSelection(0);
        spSize.setSelection(0);
    }


}//class
