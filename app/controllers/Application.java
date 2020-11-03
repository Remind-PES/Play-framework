package controllers;

import org.h2.engine.User;
import play.*;
import play.db.jpa.JPA;
import play.mvc.*;
import java.util.Date;
import java.util.*;

import models.*;
import play.mvc.results.RenderText;

import javax.persistence.Query;

public class Application extends Controller {

    public static void index() {
        render();
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

    public void signUp(String nombre, String contra){
        Usuario u=Usuario.find("Nombre", nombre).first();
        if (u==null)
        {
            Usuario f = new Usuario (nombre, contra);
            f.save();
            renderText("Registrado correctamente");
        }
        else renderText("El nombre ya existe");
    }
    public void logIn(String nombre, String contra) {
        Usuario u = Usuario.find("byNombreAndContrasena", nombre, contra).first();
        if (u==null) {
            renderText("Algo salió mal");
        }
        else {
            renderText("Has iniciado sesión");
        }

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