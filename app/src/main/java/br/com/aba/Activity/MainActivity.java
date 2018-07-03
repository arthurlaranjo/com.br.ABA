package br.com.aba.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.aba.DAO.ConfiguracaoFirebase;
import br.com.aba.Entidades.Users;
import br.com.aba.R;

public class MainActivity extends AppCompatActivity {

    private EditText edtemail;
    private EditText edtsenha;
    private Button btnlogar;
    private FirebaseAuth autenticacao;
    private Users users;
    private TextView abreCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtemail= (EditText)findViewById(R.id.edtemail);
        edtsenha= (EditText)findViewById(R.id.edtsenha);
        btnlogar= (Button)findViewById(R.id.btnlogin);
        abreCadastro=(TextView)findViewById(R.id.txtabrecadastro);
        btnlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtemail.getText().toString().equals("")&&!edtsenha.getText().toString().equals("")){
                    users = new Users();
                    users.setUsuario(edtemail.getText().toString());
                    users.setSenha(edtsenha.getText().toString());
                    validarLogin();
                }else{
                    Toast.makeText(MainActivity.this,"Preencha os campos de e-mail e senha.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        abreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });
    }
    public void validarLogin(){
        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(users.getUsuario(),users.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario ou senha invalidos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void abrirTelaPrincipal(){

        Intent it =new Intent(MainActivity.this,MapsActivity.class);
        startActivity(it);
    }
    public void abreCadastroUsuario(){
        Intent it =new Intent (MainActivity.this,Cadastrar.class);
        startActivity(it);
    }
}
