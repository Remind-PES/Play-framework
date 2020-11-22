package controllers;

import org.h2.engine.User;
import play.*;
import play.db.jpa.JPA;
import play.mvc.*;
import java.util.Date;
import java.util.*;
import play.data.validation.*;
import models.*;
import play.mvc.results.RenderText;

import javax.persistence.Query;

public class Application extends Controller {
    @Before
    static void addUser(){
        Usuario user=connected();
        if (user!=null){
            renderArgs.put("user",user);
        }
    }
    static Usuario connected(){
        if(renderArgs.get("user")!=null) {
            return renderArgs.get("user", Usuario.class);
        }
        String username=session.get("user");
        if(username!=null){
            return Usuario.find("byNombre",username).first();
        }
        return null;
    }

    public static void index() {
       if(connected()!=null){
          Tareas.index();
       }
       render();
    }
    public static void register(){
        render();
    }
    public static void saveUser(@Valid Usuario user, String verifypassword){
        validation.required(verifypassword);
        validation.equals(verifypassword, user.contrasena).message("La contraseña no coincide");
        if(validation.hasErrors()){
            render("@register", user,verifypassword);
        }
        user.create();
        session.put("user",user.nombre);
        flash.success("Bienvenido, " +user.nombre);
        Tareas.index();
    }
    public void inicializarBDD(){
        Usuario f = new Usuario("marc", "123");
        f.save();
        Tarea tar = new Tarea( "deberes","13-3-2020", "pendientes","urgente");
        tar.save();
        f.tareas.add(tar);
        f.save();
        tar.user=f;
        tar.save();
        }

    public static void signUp(String nombre, String contra){
        Usuario u=Usuario.find("byNombre", nombre).first();
        if (u==null)
        {
            Usuario f = new Usuario (nombre, contra);
            f.save();
            renderText("Registrado correctamente");
            session.put("user", u.nombre);
            flash.success("Registrado correctamente");
        }
        else {
            flash.error("Error, el usuario ya existe");
            index();
        }
    }
    public void logIn(String nombre, String contra) {
        Usuario u = Usuario.find("byNombreAndContrasena", nombre, contra).first();
        if (u!=null) {
            session.put("user", u.nombre);
            flash.success("Bienvenido, "+u.nombre);
            Tareas.index();
        }
        flash.put("nombre",nombre);
        flash.error("Error al iniciar sesión");
        index();
    }
    public void deleteUser(String user){
        Usuario u = Usuario.find("byNombre",user).first();
        if(u!=null){
            if(!u.tareas.isEmpty()){
                for(Tarea t: u.tareas){
                    t.delete();
                }
            }
            u.delete();
            renderText("Usuario "+user+" borrado correctamente");
        }
        else renderText("Usuario "+user+" no encontrado");
    }
    public static void logout(){
        session.clear();
        index();
    }



}