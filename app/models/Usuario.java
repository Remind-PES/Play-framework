package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario extends Model {
    public String nombre;
    public String contrasena;
    static int B=10;


    @OneToMany(mappedBy="user")
    public List<Tarea> tareas= new ArrayList<Tarea>();

    public Usuario(String nombre, String contrasena){
        this.nombre=nombre;
        this.contrasena=contrasena;
    }

}
