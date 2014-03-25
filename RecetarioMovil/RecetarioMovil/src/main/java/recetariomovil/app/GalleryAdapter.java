package recetariomovil.app;

/**
 * Created by Carlos Luis on 3/6/14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class GalleryAdapter extends BaseAdapter
{
    Context context;
    List<Recipe> Recetas;
    //Integer[] imagenes;

    //guardamos las imágenes reescaladas para mejorar el rendimiento ya que estas operaciones son costosas
    //se usa SparseArray siguiendo la recomendación de Android Lint
    static SparseArray<Bitmap> imagenesEscaladas = new SparseArray<Bitmap>(7);

    public GalleryAdapter(Context context, List<Recipe> recetas)
    {
        super();
        //this.imagenes = imagenes;
        this.context = context;
        this.Recetas = recetas;
    }

    @Override
    public int getCount()
    {
        if(this.Recetas != null){
            return this.Recetas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder = null;

        if (convertView == null)
        {
            holder = new Holder();
            LayoutInflater ltInflate = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = ltInflate.inflate(R.layout.gallery_item, null);

            holder.setCategoria((TextView) convertView.findViewById(R.id.txtCategoria));
            holder.setReceta((TextView) convertView.findViewById(R.id.txtReceta));
            holder.setUsuario((TextView) convertView.findViewById(R.id.txtUsuario));
            holder.setImage((ImageView) convertView.findViewById(R.id.imgReceta));

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        if(Recetas != null){
            if(Recetas.get(position) != null)
            {
                holder.getCategoria().setText(Recetas.get(position).Categoria);
                holder.getReceta().setText(Recetas.get(position).Nombre);
                holder.getUsuario().setText(Recetas.get(position).Usuario);
                try{

                    //Bitmap bmap = BitmapFactory.decodeByteArray(Recetas.get(position).Foto, 0, Recetas.get(position).Foto.length);
                    holder.getImage().setImageBitmap(Recetas.get(position).Foto);
                    holder.getImage().setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.getImage().setPadding(5, 5, 5, 5);

                }catch (Exception ex){ }
            }
        }

        /*
        if (imagenesEscaladas.get(position) == null)
        {
            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(context.getResources(), imagenes[position], 120, 120);
            imagenesEscaladas.put(position, bitmap);

            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(5, 5, 5, 5);
        }

        holder.getImage().setImageBitmap(imagenesEscaladas.get(position));
        holder.getImage().setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.getImage().setPadding(5, 5, 5, 5);
        holder.getCategoria().setText("Categoria: " + position);
        holder.getReceta().setText("Receta: " + position);
        holder.getUsuario().setText("Usuario: " + position);
        */

        return convertView;
    }

    class Holder
    {
        ImageView image;

        TextView Categoria;
        TextView Receta;
        TextView Usuario;

        public ImageView getImage()
        {
            return image;
        }

        public void setImage(ImageView image)
        {
            this.image = image;
        }

        public TextView getCategoria()
        {
            return Categoria;
        }

        public void setCategoria(TextView textView)
        {
            this.Categoria = textView;
        }

        public TextView getReceta()
        {
            return Receta;
        }

        public void setReceta(TextView textView)
        {
            this.Receta = textView;
        }

        public TextView getUsuario()
        {
            return Usuario;
        }

        public void setUsuario(TextView textView)
        {
            this.Usuario = textView;
        }

    }
}
