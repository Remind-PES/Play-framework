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
public class Tareas extends Application{
    @Before
    static void checkUser(){
        if (connected()==null){
            flash.error("Por favor inicia sesión");
            Application.index();
        }
    }
    public static void index() {
        Usuario u= connected();
        String nombre= u.nombre;
        Usuario user= Usuario.find("byNombre",u.nombre).first();
        render(user.tareas);
    }
    public void deleteTarea(String descripcion, Usuario user){
        Tarea t= Tarea.find("byDescripcionAndUser",descripcion, user).first();
        if(t!=null){
            user.tareas.remove(t);
            t.delete();
            renderText("Tarea borrada correctamente");
        }
        else renderText("No existe esa tarea");
    }
    public void anadirTarea(String descripcion, String fecha, String lista, String etiqueta, String user){
        Tarea t= new Tarea(descripcion, fecha, lista, etiqueta);
        Usuario u =Usuario.find("byNombre",user).first();
        if (u!=null){
            u.tareas.add(t);
            t.user=u;
            t.save();
            renderText("Tarea añadida correctamente");
        }
        else renderText("Usuario no encontrado");
    }
    public void editarDescripcion(Tarea t, String user, String nuevaDescripcion){
        Usuario u = Usuario.find("byNombre", user).first();
        if(u!=null){
            for(Tarea tar:u.tareas){
                if (tar==t){
                    tar.descripcion=nuevaDescripcion;
                    tar.save();
                }
                renderText("Tarea editada");
            }

        }
        else renderText("Tarea no encontrada");
    }
    public void editarEtiqueta(Tarea t, String user, String nuevaEtiqueta){
        Usuario u = Usuario.find("byNombre", user).first();
        if(u!=null){
            for(Tarea tar:u.tareas){
                if (tar==t){
                    tar.etiqueta=nuevaEtiqueta;
                    tar.save();
                }
                renderText("Etiqueta editada");
            }

        }
        else renderText("Tarea no encontrada");
    }
    public void editarContrasena(String nombre,String nuevaContrasena){
        Usuario u= Usuario.find("byNombre",nombre).first();
        if(u!=null){
            u.contrasena=nuevaContrasena;
            u.save();
            renderText("Contraseña editada");

        }
        else renderText("No se encontró el usuario");
    }
}
