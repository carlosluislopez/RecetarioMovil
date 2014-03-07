package recetariomovil.app;

/**
 * Created by Carlos Luis on 3/6/14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryAdapter extends BaseAdapter
{
    Context context;
    Integer[] imagenes;

    //guardamos las imágenes reescaladas para mejorar el rendimiento ya que estas operaciones son costosas
    //se usa SparseArray siguiendo la recomendación de Android Lint
    static SparseArray<Bitmap> imagenesEscaladas = new SparseArray<Bitmap>(7);

    public GalleryAdapter(Context context, Integer[] imagenes)
    {
        super();
        this.imagenes = imagenes;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return imagenes.length;
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

            holder.setTextView((TextView) convertView.findViewById(R.id.textView1));
            holder.setImage((ImageView) convertView.findViewById(R.id.imageView1));

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        if (imagenesEscaladas.get(position) == null)
        {
            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(context.getResources(), imagenes[position], 180, 180);
            imagenesEscaladas.put(position, bitmap);

            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(5, 5, 5, 5);
        }

        holder.getImage().setImageBitmap(imagenesEscaladas.get(position));
        holder.getImage().setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.getImage().setPadding(5, 5, 5, 5);
        holder.getTextView().setText("Item: " + position);

        return convertView;
    }

    class Holder
    {
        ImageView image;

        TextView textView;

        public ImageView getImage()
        {
            return image;
        }

        public void setImage(ImageView image)
        {
            this.image = image;
        }

        public TextView getTextView()
        {
            return textView;
        }

        public void setTextView(TextView textView)
        {
            this.textView = textView;
        }

    }
}
