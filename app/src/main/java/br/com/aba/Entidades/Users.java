package br.com.aba.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import br.com.aba.DAO.ConfiguracaoFirebase;

public class Users {
    private String nome;
    private String CPF;
    private String usuarioId;
    private String usuario;
    private String senha;
    private String tipo;

    public Users() {
    }
    public void salvar(){
        DatabaseReference referenciaFirebase= ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuario").child(String.valueOf(getUsuarioId())).setValue(this);
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object>hashMapUsuario=new HashMap<>();
        hashMapUsuario.put("id",getUsuarioId());
        hashMapUsuario.put("email",getUsuario());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("cpf",getCPF());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("tipo",getTipo());
        return hashMapUsuario;
    }
    public Users(String id,String senha,String usuario,String tipo){
        this.usuarioId=id;
        this.senha=senha;
        this.usuario=usuario;
        this.tipo=tipo;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

}
