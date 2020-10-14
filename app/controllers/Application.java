package controllers;

import play.*;
import play.mvc.*;
import java.util.Date;
import java.util.*;

import models.*;
import play.mvc.results.RenderText;

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

}