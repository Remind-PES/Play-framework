package models;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario extends Model {
    @Required
    @MaxSize(15)
    @MinSize(4)
    public String nombre;
    @Required
    @MaxSize(15)
    @MinSize(4)
    public String contrasena;



    @OneToMany(mappedBy="user")
    public List<Tarea> tareas= new ArrayList<Tarea>();

    public Usuario(String nombre, String contrasena){
        this.nombre=nombre;
        this.contrasena=contrasena;
    }

}
