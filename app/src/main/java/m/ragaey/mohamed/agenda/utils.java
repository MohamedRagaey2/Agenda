package m.ragaey.mohamed.agenda;

import android.nfc.NfcAdapter;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

class Utils {

    static String  bookname,bookauthor,bookpublisher,publishdate,description,imageurl;

    public static ArrayList<Dataclass> fetchBooksData(String Url)
    {
        // Create URL object
        URL url = null;
        try {
            url = createURL(Url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String jsonresponse = null;
        try {
            jsonresponse = MakeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Dataclass> booksarraylist = null;
        try {
            booksarraylist = extractFromJson(jsonresponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return booksarraylist;
    }

    public static URL createURL (String stringurl)throws MalformedURLException
    {
        // create a new object from URL
        URL url = null;

        // try and catch exception

            // initialize a new object
            url = new URL(stringurl);

        // return url
        return url;
    }
    public static String MakeHttpRequest (URL url)throws IOException
    {
        String jsonresponse = "";

        if (url == null)
        {
            return jsonresponse;
        }
        HttpURLConnection urlConnection = null;

        InputStream inputStream = null;


            // open url connection
            urlConnection = (HttpURLConnection)url.openConnection();

            // set request method
            urlConnection.setRequestMethod("GET");

            // connect to server
            urlConnection.connect();

            // check if the request successful (response code = 200)
            // then read data from stream
            if (urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                jsonresponse = readFromStream(inputStream);
            }
        // if urlConnection return with data
        if (urlConnection != null)
        {
            // then disconnect (disconnect don't mean delete data from urlConnection)
            urlConnection.disconnect();
        }
        // if inputStream return with data
        if (inputStream != null)
        {
            // then close (close don't mean delete data from inputStream)
            inputStream.close();
        }
        return jsonresponse;
    }

    public static String readFromStream (InputStream inputStream) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream
            ,Charset.defaultCharset());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                String line = bufferedReader.readLine();

                while (line != null)
                {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
        }
        return stringBuilder.toString();
    }
    public static ArrayList<Dataclass> extractFromJson(String jsonresponse) throws JSONException
    {
        if (TextUtils.isEmpty(jsonresponse) && jsonresponse == null) {
            return null;
        }
        ArrayList<Dataclass> booksdata = new ArrayList<>();

        JSONObject baseobject = new JSONObject(jsonresponse);
        JSONArray itemsarray = baseobject.getJSONArray("items");

        for (int i = 0; i < itemsarray.length(); i ++)
        {
            JSONObject item = itemsarray.getJSONObject(i);

            JSONObject info = item.getJSONObject("volumeInfo");

            if (info.has("title"))
            {
                bookname = info.getString("title");
            }else
            {
                bookname = "Title not found";
            }

            if (info.has("authors"))
            {
                bookauthor = info.getJSONArray("authors").getString(0);
            }else
            {
                bookauthor = "Authors not found";
            }

            if (info.has("publisher"))
            {
                bookpublisher = info.getString("publisher");
            }else
            {
                bookpublisher = "publisher not found";
            }

            if (info.has("publishedDate"))
            {
                publishdate = info.getString("publishedDate");
            }else
            {
                publishdate = "publishedDate not found";
            }

            if (info.has("description"))
            {
                description = info.getString("description");
            }else
            {
                description = "description not found";
            }

            if (info.has("imageLinks"))
            {
                imageurl = info.getJSONObject("imageLinks").getString("thumbnail");
            }else
            {
                imageurl = "" ;
            }
            booksdata.add(new Dataclass(bookname,bookauthor,bookpublisher,publishdate,description,imageurl));
        }
        return booksdata;
    }
}