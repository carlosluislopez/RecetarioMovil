package recetariomovil.app;

/**
 * Created by Carlos Luis on 3/8/14.
 */

import android.graphics.Bitmap;

import java.lang.String;
import java.util.Date;

public class Recipe
{
    public int Id;
    public String Categoria;
    public String Nombre;
    public String Descripcion;
    public Date Fecha;
    public String Usuario;
    public String UsuarioNombre;
    public float Clasificacion;
    public Bitmap Foto;
    public int Longitud;

    public Recipe()
    {
    }

    public Recipe(int id, String categoria, String nombre, String descripcion, Date fecha, String usuario, String usuarioNombre, float clasificacion, Bitmap foto, int longitud) {
        Id = id;
        Categoria = categoria;
        Nombre = nombre;
        Descripcion = descripcion;
        Fecha = fecha;
        Usuario = usuario;
        UsuarioNombre = usuarioNombre;
        Clasificacion = clasificacion;
        Foto = foto;
        Longitud = longitud;
    }
}
