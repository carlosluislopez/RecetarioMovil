package recetariomovil.app;

/**
 * Created by Carlos Luis on 3/8/14.
 */

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CallSoap
{
    public final String SOAP_ACTION = "http://tempuri.org/SearchRecipiesString";

    public  final String OPERATION_NAME = "SearchRecipiesString";

    public  final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public  final String SOAP_ADDRESS = "http://recetario.apphb.com/wsChefChar.asmx";

    public CallSoap()
    {
    }

    public List<Recipe> Call(String search)
    {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("search");
        pi.setValue(search);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        HttpTransportSE v = new HttpTransportSE(SOAP_ADDRESS, 5000);

        Object response = null;
        StackOverflowXmlParser Parser = new StackOverflowXmlParser();
        try
        {
            httpTransport.call(SOAP_ACTION, envelope);
            //SoapObject so = (SoapObject)envelope.bodyIn;
            //String xml = so.toString();

            //response = envelope.getResponse();

            //String xml = "<Recipies>9</Recipies>";
            InputStream in = new ByteArrayInputStream(envelope.getResponse().toString().getBytes("UTF-8"));
            //InputStream in = new ByteArrayInputStream(response.toString().getBytes("UTF-8"));

            return Parser.parse(in);
        }
        catch (Exception exception)
        {
            response = exception.toString();
        }
        return null;
    }
}