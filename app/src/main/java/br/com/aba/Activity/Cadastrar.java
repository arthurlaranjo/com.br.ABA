package br.com.aba.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.aba.DAO.ConfiguracaoFirebase;
import br.com.aba.Entidades.Users;
import br.com.aba.Helper.Base64Custom;
import br.com.aba.Helper.Preferencias;
import br.com.aba.R;

public class Cadastrar extends AppCompatActivity {
    private EditText edtCadUsuario;
    private EditText edtCadSenha;
    private EditText edtCadNome;
    private EditText edtCadCPF;
    private Button btnSalvar;
    private EditText edtCadSenhaconf;
    private Users user;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        edtCadNome=(EditText)findViewById(R.id.edtcadnome);
        edtCadSenha=(EditText)findViewById(R.id.edtcadsenha);
        edtCadCPF=(EditText)findViewById(R.id.edtcadcpf);
        edtCadUsuario=(EditText)findViewById(R.id.edtcademail);
        edtCadSenhaconf=(EditText)findViewById(R.id.edtcadsenhaconf);
        btnSalvar=(Button) findViewById(R.id.btngravar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCadSenha.getText().toString().equals(edtCadSenhaconf.getText().toString())){
                    user=new Users();
                    user.setNome(edtCadNome.getText().toString());
                    user.setCPF(edtCadCPF.getText().toString());
                    user.setSenha(edtCadSenha.getText().toString());
                    user.setUsuario(edtCadUsuario.getText().toString());
                    user.setTipo("cliente");
                    cadastrarUsuario();
                }else{
                    Toast.makeText(Cadastrar.this,"A senhas não são iguais!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void cadastrarUsuario(){
    autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
    autenticacao.createUserWithEmailAndPassword(
            user.getUsuario(),
            user.getSenha()

    ).addOnCompleteListener(Cadastrar.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Toast.makeText(Cadastrar.this,"Usuario cadastrado com sucesso!!",Toast.LENGTH_SHORT).show();

                String identificadorUsuario = Base64Custom.codificarBase64(user.getUsuario());

                FirebaseUser usuarioFirebase = task.getResult().getUser();
                user.setUsuarioId(identificadorUsuario);
                user.salvar();
                Preferencias preferencias =new Preferencias(Cadastrar.this);
                preferencias.salvarUsuarioPreferencias(identificadorUsuario,user.getNome());
                abrirLoginUsuario();
            }else{
                String erroExecao = "";
                try{
                    throw task.getException();
                }catch (FirebaseAuthWeakPasswordException e){
                    erroExecao="Digite uma senha mias forte contendo no mínimo 8 caracteres de letras e numeros";
                }catch (FirebaseAuthInvalidCredentialsException e){
                    erroExecao = "O e-mail digitado é invalido, digite um novo e-mail";
                }catch(FirebaseAuthUserCollisionException e){
                    erroExecao="Esse e-mail já está cadastrado no sistema";
                }catch (Exception e){
                    erroExecao="Erro ao efetuar o cadastro!";
                    e.printStackTrace();
                }
                Toast.makeText(Cadastrar.this,"Erro"+erroExecao,Toast.LENGTH_LONG).show();
            }
        }
    });
    }
    public void abrirLoginUsuario()
    {
        Intent intent =new Intent(Cadastrar.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
