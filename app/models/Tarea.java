package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Tarea extends Model{
    public String descripcion;
    public String fecha;
    public String lista;
    public String etiqueta;
    @ManyToOne
    public Usuario user;
    public Tarea( String descripcion, String fecha, String lista, String etiqueta){
        this.descripcion=descripcion;
        this.fecha=fecha;
        this.lista=lista;
        this.etiqueta=etiqueta;
    }


}
