package aunyamane.mynavigator;
        import java.io.IOException;
        import java.io.InputStream;
        import java.util.ArrayList;

        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.protocol.BasicHttpContext;
        import org.apache.http.protocol.HttpContext;
        import org.w3c.dom.Document;
        import org.w3c.dom.Node;
        import org.w3c.dom.NodeList;

        import android.content.ContentValues;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Log;

public class GooglePlaceSearch {
    private String API_KEY;

    public final static String STATUS_OK = "OK";
    public final static String STATUS_ZERO_RESULTS = "ZERO_RESULTS";
    public final static String STATUS_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    public final static String RSTATUS_EQUEST_DENIED = "REQUEST_DENIED";
    public final static String STATUS_INVALID_REQUEST = "INVALID_REQUEST ";

    public final static String NAME = "Name";
    public final static String ADDRESS = "Address";
    public final static String LATITUDE = "Latitude";
    public final static String LONGITUDE = "Longitude";
    public final static String ICON = "Icon";
    public final static String OPENNOW = "OpenNow";
    public final static String PHOTO = "Photo";
    public final static String PHONENUMBER = "PhoneNumber";

    private String status = "";

    public GooglePlaceSearch(String api_key) {
        API_KEY = "AIzaSyC5BfF90liA0hv4uzXZab1paN0U9m6P2j0";
    }

    public String getApiKey() {
        return API_KEY;
    }

    public ArrayList<ContentValues> getNearBy(Double latitude, Double longitude
            , int radius, String type, String language, String keyword) {
        return getNearByData(getNearByDocument(latitude, longitude
                , radius, type, language, keyword));
    }

    public ArrayList<ContentValues> getNearBy(Double latitude, Double longitude
            , int radius, String type, String language) {
        return getNearByData(getNearByDocument(latitude, longitude
                , radius, type, language, ""));
    }

    public ArrayList<ContentValues> getNearBy(Double latitude, Double longitude
            , int radius, String type) {
        return getNearByData(getNearByDocument(latitude, longitude
                , radius, type, "", ""));
    }

    public ArrayList<ContentValues> getNearBy(Double latitude, Double longitude
            , int radius) {
        return getNearByData(getNearByDocument(latitude, longitude
                , radius, "", "", ""));
    }

    private ArrayList<ContentValues> getNearByData(Document doc) {
        ArrayList<ContentValues> arr_cv = new ArrayList<ContentValues>();

        NodeList nl1 = doc.getElementsByTagName("PlaceSearchResponse");
        NodeList nl2 = nl1.item(0).getChildNodes();
        Node node = nl2.item(getNodeIndex(nl2, "status"));
        status = node.getTextContent();
        Log.d("DataStatus", status);

        if(node.getTextContent().equals(STATUS_OK)) {
            nl1 = doc.getElementsByTagName("result");
            for (int i = 0; i < nl1.getLength(); i++) {
                ContentValues cv = new ContentValues();
                node = nl1.item(i);
                nl2 = node.getChildNodes();
                node = nl2.item(getNodeIndex(nl2, "reference"));
                cv.put("reference", node.getTextContent());
                cv = getReferenceData(cv, getReferenceDocument(node.getTextContent()));

                arr_cv.add(cv);
            }
            return arr_cv;
        }
        return null;
    }

    private Document getNearByDocument(double latitude, double longitude, int radius
            , String type, String language, String keyword) {
        String url = "https://maps.googleapis.com/maps/api/place/search/xml?";
        url += "location=" + latitude + "," + longitude + "&radius=" + radius;
        url += "&key=" + API_KEY + "&sensor=false";

        if(!type.equals(""))
            url += "&types=" + type.toLowerCase();
        if(!keyword.equals(""))
            url += "&keyword=" + keyword.replace(" ", "+");
        if(!language.equals(""))
            url += "&language=" + language.toLowerCase();

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ContentValues> getTextSearch(String keyword, String type, boolean opennow
            , String language, double latitude, double longitude, int radius) {
        return getTextSearchData(getTextSearchDocument(keyword, type
                , opennow, language, latitude, longitude, radius));
    }

    public ArrayList<ContentValues> getTextSearch(String keyword, String type, boolean opennow
            , String language) {
        return getTextSearchData(getTextSearchDocument(keyword, type
                , opennow, language, -1, -1, -1));
    }

    public ArrayList<ContentValues> getTextSearch(String keyword, String type, boolean opennow) {
        return getTextSearchData(getTextSearchDocument(keyword, type
                , opennow, "", -1, -1, -1));
    }

    public ArrayList<ContentValues> getTextSearch(String keyword, String type) {
        return getTextSearchData(getTextSearchDocument(keyword, type
                , false, "", -1, -1, -1));
    }

    public ArrayList<ContentValues> getTextSearch(String keyword) {
        return getTextSearchData(getTextSearchDocument(keyword, ""
                , false, "", -1, -1, -1));
    }

    private ArrayList<ContentValues> getTextSearchData(Document doc) {
        ArrayList<ContentValues> arr_cv = new ArrayList<ContentValues>();

        NodeList nl1 = doc.getElementsByTagName("PlaceSearchResponse");
        NodeList nl2 = nl1.item(0).getChildNodes();
        Node node = nl2.item(getNodeIndex(nl2, "status"));
        status = node.getTextContent();
        Log.d("DataStatus", status);

        if(node.getTextContent().equals(STATUS_OK)) {
            nl1 = doc.getElementsByTagName("result");
            for (int i = 0; i < nl1.getLength(); i++) {
                ContentValues cv = new ContentValues();
                node = nl1.item(i);
                nl2 = node.getChildNodes();

                node = nl2.item(getNodeIndex(nl2, "reference"));
                cv.put("reference", node.getTextContent());
                cv = getReferenceData(cv, getReferenceDocument(node.getTextContent()));

                try {
                    node = nl2.item(getNodeIndex(nl2, "opening_hours"));
                    NodeList nl3 = node.getChildNodes();
                    node = nl3.item(getNodeIndex(nl3, "open_now"));;
                    cv.put(OPENNOW, node.getTextContent());
                } catch (ArrayIndexOutOfBoundsException e) {
                    cv.put(OPENNOW, "Unknown");
                }

                try {
                    node = nl2.item(getNodeIndex(nl2, "photo"));
                    NodeList nl3 = node.getChildNodes();
                    node = nl3.item(getNodeIndex(nl3, "photo_reference"));;
                    cv.put(PHOTO, node.getTextContent());
                } catch (ArrayIndexOutOfBoundsException e) {
                    cv.put(PHOTO, "");
                }
                arr_cv.add(cv);
            }
            return arr_cv;
        }
        return null;
    }

    private Document getTextSearchDocument(String keyword, String type, boolean opennow
            , String language, double latitude, double longitude, int radius) {
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/xml?";
        url += "query=" + keyword.replace(" ", "+") + "&key=" + API_KEY + "&sensor=false";

        if(latitude != -1 && longitude != -1 && radius != -1)
            url += "&location=" + latitude + "," + longitude + "&radius=" + radius;
        if(opennow)
            url += "&opennow";
        if(!language.equals(""))
            url += "&language=" + language.toLowerCase();

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ContentValues> getRadarSearch(double latitude, double longitude
            , int radius, String type, String language, boolean opennow, String keyword) {
        return getRadarSearchData(getRadarSearchDocument(latitude, longitude
                , radius, type, language, opennow, keyword));
    }

    public ArrayList<ContentValues> getRadarSearch(double latitude, double longitude
            , int radius, String type, String language, boolean opennow) {
        return getRadarSearchData(getRadarSearchDocument(latitude, longitude
                , radius, type, language, opennow, ""));
    }

    public ArrayList<ContentValues> getRadarSearch(double latitude, double longitude
            , int radius, String type, String language) {
        return getRadarSearchData(getRadarSearchDocument(latitude, longitude
                , radius, type, language, false, ""));
    }

    public ArrayList<ContentValues> getRadarSearch(double latitude, double longitude
            , int radius, String type) {
        return getRadarSearchData(getRadarSearchDocument(latitude, longitude
                , radius, type, "", false, ""));
    }

    public ArrayList<ContentValues> getRadarSearch(double latitude, double longitude
            , int radius ) {
        return getRadarSearchData(getRadarSearchDocument(latitude, longitude
                , radius, "", "", false, ""));
    }

    private ArrayList<ContentValues> getRadarSearchData(Document doc) {
        ArrayList<ContentValues> arr_cv = new ArrayList<ContentValues>();

        NodeList nl1 = doc.getElementsByTagName("PlaceSearchResponse");
        NodeList nl2 = nl1.item(0).getChildNodes();
        Node node = nl2.item(getNodeIndex(nl2, "status"));
        status = node.getTextContent();
        Log.d("DataStatus", status);

        if(node.getTextContent().equals(STATUS_OK)) {
            nl1 = doc.getElementsByTagName("result");
            for (int i = 0; i < nl1.getLength(); i++) {
                ContentValues cv = new ContentValues();
                node = nl1.item(i);
                nl2 = node.getChildNodes();
                node = nl2.item(getNodeIndex(nl2, "reference"));
                cv.put("reference", node.getTextContent());
                cv = getReferenceData(cv, getReferenceDocument(node.getTextContent()));
                arr_cv.add(cv);
            }
            return arr_cv;
        }
        return null;
    }

    private Document getRadarSearchDocument(double latitude, double longitude, int radius
            , String type, String language, boolean opennow, String keyword) {
        String url = "https://maps.googleapis.com/maps/api/place/search/xml?";
        url += "location=" + latitude + "," + longitude + "&radius=" + radius;
        url += "&key=" + API_KEY + "&sensor=false";

        if(!type.equals(""))
            url += "&types=" + type.toLowerCase();
        if(opennow)
            url += "&opennow";
        if(!keyword.equals(""))
            url += "&keyword=" + keyword.replace(" ", "+");
        if(!language.equals(""))
            url += "&language=" + language.toLowerCase();

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ContentValues getReferenceData(ContentValues cv, Document doc) {
        NodeList nl1 = doc.getElementsByTagName("result");
        nl1 = nl1.item(0).getChildNodes();

        Node node = nl1.item(getNodeIndex(nl1, "name"));
        cv.put(NAME, node.getTextContent());

        try {
            node = nl1.item(getNodeIndex(nl1, "formatted_phone_number"));
            cv.put(PHONENUMBER, node.getTextContent());
        } catch (ArrayIndexOutOfBoundsException e) {
            cv.put(PHONENUMBER, "Unknown");
        }

        node = nl1.item(getNodeIndex(nl1, "formatted_address"));
        cv.put(ADDRESS, node.getTextContent());

        node = nl1.item(getNodeIndex(nl1, "geometry"));
        NodeList nl2 = node.getChildNodes();
        node = nl2.item(getNodeIndex(nl2, "location"));
        nl2 = node.getChildNodes();
        node = nl2.item(getNodeIndex(nl2, "lat"));
        cv.put(LATITUDE, node.getTextContent());
        node = nl2.item(getNodeIndex(nl2, "lng"));
        cv.put(LONGITUDE, node.getTextContent());

        node = nl1.item(getNodeIndex(nl1, "icon"));
        cv.put(ICON, node.getTextContent());

        return cv;
    }

    private Document getReferenceDocument(String reference) {
        String url = "https://maps.googleapis.com/maps/api/place/details/xml?";
        url += "reference=" + reference + "&key=" + API_KEY + "&sensor=false";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap getPhotoBitmapByWidth(String reference, int maxWidth) {
        return getReferencePhoto(reference, maxWidth, 0);
    }

    public Bitmap getPhotoBitmapByHeight(String reference, int maxHeight) {
        return getReferencePhoto(reference, 0, maxHeight);
    }

    public Bitmap getPhotoBitmap(String reference, int maxWidth, int maxHeight) {
        return getReferencePhoto(reference, maxWidth, maxHeight);
    }

    private Bitmap getReferencePhoto(String reference, int maxWidth, int maxHeight) {
        String url = "https://maps.googleapis.com/maps/api/place/photo?";
        url += "photoreference=" + reference;
        url += "&sensor=false&key=" + API_KEY;
        if(maxWidth > 0)
            url += "&maxwidth=" + String.valueOf(maxWidth);
        if(maxHeight > 0)
            url += "&maxHeight=" + String.valueOf(maxHeight);

        Bitmap bmp = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        InputStream in = null;
        try {
            in = httpclient.execute(request).getEntity().getContent();
            bmp = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    public String getDataStatus() {
        return status;
    }

    private int getNodeIndex(NodeList nl, String nodename) {
        for(int i = 0 ; i < nl.getLength() ; i++) {
            if(nl.item(i).getNodeName().equals(nodename))
                return i;
        }
        return -1;
    }

    }
