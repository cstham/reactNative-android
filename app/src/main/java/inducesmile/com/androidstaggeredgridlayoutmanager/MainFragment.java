package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.error.ANError;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.API.PublicPostListing;
import okhttp3.Response;

import static inducesmile.com.androidstaggeredgridlayoutmanager.MainActivity.currentObj;




@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements AsyncResponse {
    //=========================================================================================
    //to determine time required for this operation
    long startnow;
    long endnow;
    //=========================================================================================
    PostOperation postOperation = new PostOperation();

    private RecyclerView recyclerView;

    //ListMultimap<String, String> publicPostListing;
    private ListMultimap<Integer, String> publicPostListing;
    private ArrayList<String> listing_array;

    public static List<FrameObjects> gaggeredList;

    private String mTitle;

    Context mContext;

    public static MainFragment getInstance(String title) {
        MainFragment sf = new MainFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.host_page, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);

        recyclerView.setNestedScrollingEnabled(true);  //pls check this!!!

        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemAnimator(null);
        //==========================================================================================
        //post request to server to get home/host page information
        mContext = getContext();
        AndroidNetworking.initialize(mContext);

        postOperation.delegate = this;
        postOperation.execute();

        //setHomeInfo(mContext);
        //POST REQUEST
        //new PostOperation().execute();












        //==========================================================================================2
/*
        //gaggeredList = getListItemData();
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(currentObj, numberOfColumns));

        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(currentObj, gaggeredList);
        recyclerView.setAdapter(rcAdapter);
*/

        return v;
    }

    @Override
    public void processFinish(ListMultimap<Integer, String> output){
        System.out.println("lolxxxxxxxx output:    " + output.get(0).get(2));

        List<FrameObjects> listViewItems = new ArrayList<FrameObjects>();

        for (int i = 0; i < output.keySet().size(); i++) {
            System.out.println("LOLZAAA");
            listViewItems.add(new FrameObjects(output.get(i).get(2),
                    output.get(i).get(1),
                    output.get(i).get(0)));         //5 for type
        }

        gaggeredList = listViewItems;

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(currentObj, numberOfColumns));

        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(currentObj, gaggeredList);
        recyclerView.setAdapter(rcAdapter);


        System.out.println("gaggeredList lol:   "+gaggeredList);
        //listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/8a/4b/f0/8a4bf04b2eb7c0fde87533f2a3041abb.jpg", "stream01","100,000"));
        endnow = android.os.SystemClock.uptimeMillis();
        Log.d("END", "Time Required: " + (endnow - startnow) + " ms");

    }

    private List<FrameObjects> getListItemData(){
        List<FrameObjects> listViewItems = new ArrayList<FrameObjects>();
        /*
        //DESERIALIZATION
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(currentObj);
        String publicPostListing_gson = prefs.getString("publicPostListing", "");
        Gson gson = new Gson();
        //publicPostListing = gson.fromJson(publicPostListing_gson, ListMultimap.class);
*/

        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/8a/4b/f0/8a4bf04b2eb7c0fde87533f2a3041abb.jpg", "stream01","100,000"));
        listViewItems.add(new FrameObjects("http://cdn.cavemancircus.com//wp-content/uploads/images/2018/feb/betsy/betsy_alvarez_1.jpg", "stream02","125,000"));
        //System.out.println("home_page lol:   "+publicPostListing);
        //listViewItems.add(new FrameObjects(home_page.get("1").get(2),home_page.get("1").get(1), home_page.get("1").get(5)));
        return listViewItems;
    }

    private class PostOperation extends AsyncTask<Void, Void, PublicPostListing> {
        public AsyncResponse delegate = null;
        @Override
        protected PublicPostListing doInBackground(Void... params) {
            startnow = android.os.SystemClock.uptimeMillis();

            //coinMarketCapList = new ArrayList<>();
            listing_array = new ArrayList<>();
            publicPostListing = LinkedListMultimap.create();  //equivalent to publicPostListing.clear()


            System.out.println("SENDING POST REQUESTS LOL.................");
            ANRequest request = AndroidNetworking.post("http://14.102.151.252:8080/projv3/ajax/public/list_public_post.shtml")
                    //indication of content type is important to ensure that results do not show up in error section instead
                    .setContentType("application/x-www-form-urlencoded")
                    .build();

            ANResponse<JSONArray> response = request.executeForJSONArray();

            if (response.isSuccess()) {
                //==================================================================================
                //TO CHECK HEADER RESPONSE
                Response okHttpResponse = response.getOkHttpResponse();
                System.out.println("HEADER response lol:    " + okHttpResponse);
                //Log.d(TAG, "headers : " + okHttpResponse.headers().toString());
                //==================================================================================
                JSONArray jsonArray_response = response.getResult();

                try {
                    for (int i = 0; i < jsonArray_response.length(); i++) {
                        for (int x = 0; x < jsonArray_response.getJSONArray(x).length(); x++) {
                            listing_array.add(jsonArray_response.getJSONArray(i).get(x).toString());
                            //System.out.println("response total length lolz:    " +response.getJSONArray(i).get(x).toString() );
                        }
                        publicPostListing.putAll(i, listing_array);
                        listing_array.clear();
                    }

                    //to return home_page out of this method
                    System.out.println("lolxxxxxxxx:    " + publicPostListing);
                    System.out.println("check server response lol:    " + publicPostListing.get(1).get(2));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //Log.d(TAG, "response : " + jsonObject.toString());

            } else {
                ANError error = response.getError();
                System.out.println("result error lol: "+error);
            }
            //======================================================================================
            System.out.println("result onPostExecute lolxtra: "+publicPostListing);

            PublicPostListing wrapper = new PublicPostListing();
            wrapper.publicPostListing = publicPostListing;
            System.out.println("wrapper lolxtra: "+wrapper);
            return wrapper;
        }

        @Override
        protected void onPostExecute(PublicPostListing wrapper) {

            System.out.println("LOLZ");
            System.out.println("result onPostExecute lolz: "+wrapper.publicPostListing.keySet().size());

            //String food = "food lol";
            delegate.processFinish(wrapper.publicPostListing);

/*
            List<FrameObjects> listViewItems = new ArrayList<FrameObjects>();

            for (int i = 0; i < wrapper.publicPostListing.keySet().size(); i++) {
                System.out.println("LOLZAAA");
                listViewItems.add(new FrameObjects(wrapper.publicPostListing.get(String.valueOf(i)).get(2),
                wrapper.publicPostListing.get(String.valueOf(i)).get(1),
                wrapper.publicPostListing.get(String.valueOf(i)).get(5)));
            }

            gaggeredList = listViewItems;
            System.out.println("gaggeredList lol:   "+gaggeredList);
            //listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/8a/4b/f0/8a4bf04b2eb7c0fde87533f2a3041abb.jpg", "stream01","100,000"));
            endnow = android.os.SystemClock.uptimeMillis();
            Log.d("END", "Time Required: " + (endnow - startnow) + " ms");
            */
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}



//to remove listview divider
//int[] colors = { Color.parseColor("#D3D3D3"), Color.parseColor("#D3D3D3"), Color.parseColor("#D3D3D3") };
//lv.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors))

//TextView stations_subtext = (TextView) view.findViewById(R.id.textView2);
//((CommonTabActivity)getActivity()).onPlaceSelected(stations_subtext.getText().toString());
//String  itemValue    = (String) lv.getItemAtPosition(position);
//Toast.makeText(getContext(), "You Clicked on: " + position + "||  Subtext: " + stations_subtext.getText(), Toast.LENGTH_SHORT).show();

/*
listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/8a/4b/f0/8a4bf04b2eb7c0fde87533f2a3041abb.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/3d/d4/0b/3dd40b427071091570ad1d1572f3d8ac.jpg"));
        listViewItems.add(new FrameObjects("http://cdn.cavemancircus.com//wp-content/uploads/images/2018/feb/betsy/betsy_alvarez_1.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/bf/45/4f/bf454f06a8adc81f6808bcbcbf6ffdb5.jpg"));
        listViewItems.add(new FrameObjects("http://cdn.cavemancircus.com//wp-content/uploads/images/2015/august/galina/galina_1.jpg"));

        listViewItems.add(new FrameObjects("http://cdn.cavemancircus.com//wp-content/uploads/images/2018/feb/betsy/betsy_alvarez_1.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/3d/d4/0b/3dd40b427071091570ad1d1572f3d8ac.jpg"));
        listViewItems.add(new FrameObjects("https://data.whicdn.com/images/56794279/original.jpg"));
        listViewItems.add(new FrameObjects("https://78.media.tumblr.com/ebc30dfe681db8bf8a966bf9a464cdcb/tumblr_ozxf53W9O71uzbd5io1_1280.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/3d/d4/0b/3dd40b427071091570ad1d1572f3d8ac.jpg"));

        listViewItems.add(new FrameObjects("http://cdn.cavemancircus.com//wp-content/uploads/images/2015/august/galina/galina_1.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/8a/4b/f0/8a4bf04b2eb7c0fde87533f2a3041abb.jpg"));
        listViewItems.add(new FrameObjects("https://78.media.tumblr.com/ebc30dfe681db8bf8a966bf9a464cdcb/tumblr_ozxf53W9O71uzbd5io1_1280.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/bf/45/4f/bf454f06a8adc81f6808bcbcbf6ffdb5.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/8a/4b/f0/8a4bf04b2eb7c0fde87533f2a3041abb.jpg"));

        listViewItems.add(new FrameObjects("https://78.media.tumblr.com/ebc30dfe681db8bf8a966bf9a464cdcb/tumblr_ozxf53W9O71uzbd5io1_1280.jpg"));
        listViewItems.add(new FrameObjects("http://cdn.cavemancircus.com//wp-content/uploads/images/2015/august/galina/galina_1.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/8a/4b/f0/8a4bf04b2eb7c0fde87533f2a3041abb.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/bf/45/4f/bf454f06a8adc81f6808bcbcbf6ffdb5.jpg"));
        listViewItems.add(new FrameObjects("https://i.pinimg.com/originals/3d/d4/0b/3dd40b427071091570ad1d1572f3d8ac.jpg"));
 */