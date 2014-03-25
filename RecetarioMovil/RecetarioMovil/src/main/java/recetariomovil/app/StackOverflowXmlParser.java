package recetariomovil.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Carlos Luis on 3/17/14.
 */
public class StackOverflowXmlParser
{
    private static final String ns = null;

    public List<Recipe> parse(InputStream in)
            throws XmlPullParserException, IOException
    {
        try
        {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        }
        finally
        {
            in.close();
        }
    }

    private List<Recipe> readFeed(XmlPullParser parser)
            throws XmlPullParserException, IOException
    {
        List<Recipe> entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "Recetas");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Receta")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Recipe readEntry(XmlPullParser parser)
            throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Receta");
        int Id = 0;
        String Categoria = null;
        String Nombre = null;
        String Descripcion = null;
        String Usuario = null;
        String UsuarioNombre = null;
        float Clasificacion = 0;
        Date Fecha = null;
        int Longitud = 0;
        Bitmap Foto = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("IdReceta")) {
                Id = readId(parser);
            }else if (name.equals("Categoria")) {
                Categoria = readCategoria(parser);
            } else if (name.equals("Nombre")) {
                Nombre = readNombre(parser);
            } else if (name.equals("Descripcion")) {
                Descripcion = readDescripcion(parser);
            } else if (name.equals("Fecha_Creo")) {
                Fecha = readFecha(parser);
            } else if (name.equals("Usuario")) {
                Usuario = readUsuario(parser);
            } else if (name.equals("NombreCompleto")) {
                UsuarioNombre = readUsuarioNombre(parser);
            } else if (name.equals("Rating")) {
                Clasificacion = readClasificacion(parser);
            } else if (name.equals("Foto")) {
                Foto = readFoto(parser);
            } else if (name.equals("Longitud")) {
                Longitud = readLongitud(parser);
            } else {
                skip(parser);
            }
        }
        //Recipe Receta = new Recipe(Nombre, Descripcion, Clasificacion, Usuario, Categoria, Fecha, Foto, Longitud);
        return new Recipe(Id, Categoria, Nombre, Descripcion, Fecha, Usuario, UsuarioNombre, Clasificacion, Foto, Longitud);

    }

    // Processes title tags in the feed.
    private int readId(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "IdReceta");
        String Id = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "IdReceta");
        return Integer.parseInt(Id);
    }

    private String readCategoria(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Categoria");
        String Categoria = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Categoria");
        return Categoria;
    }

    private String readNombre(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Nombre");
        String Nombre = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Nombre");
        return Nombre;
    }

    private String readDescripcion(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Descripcion");
        String Descripcion = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Descripcion");
        return Descripcion;
    }

    private String readUsuarioNombre(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "NombreCompleto");
        String Usuario = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "NombreCompleto");
        return Usuario;
    }

    private String readUsuario(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Usuario");
        String Usuario = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Usuario");
        return Usuario;
    }

    private Float readClasificacion(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Rating");
        String clasificacion = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Rating");
        return Float.parseFloat(clasificacion);
    }

    private Bitmap readFoto(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Foto");
        String Foto = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Foto");

        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream((InputStream) new URL(Foto).getContent());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return b;

        //Bitmap image = BitmapFactory.decodeStream(url.openStream());
        ///return image;
        //return Foto.getBytes("UTF-16");
        //return Foto.getBytes();
    }

    private int readLongitud(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Longitud");
        String Longitud = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Longitud");
        return Integer.parseInt(Longitud);
    }

    private Date readFecha(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Fecha_Creo");
        String strFecha = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Fecha_Creo");

        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {

            fecha = formatoDelTexto.parse(strFecha);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }
        return fecha;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")){
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    // Processes summary tags in the feed.
    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return summary;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }



}
