package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;

import java.util.List;

public class SolventRecyclerViewAdapter  extends RecyclerView.Adapter<SolventViewHolders> {

    private List<FrameObjects> itemList;
    private List<FrameObjects> itemListFiltered;
    private Context context;

    public SolventRecyclerViewAdapter(Context context, List<FrameObjects> itemList) {
        this.itemList = itemList;
        this.context = context;
        this.itemListFiltered = itemList;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.host_frame, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {
        //holder.itemName.setText(itemListFiltered.get(position).getName());

        loadImage(itemListFiltered.get(position).getPhoto(), holder);
        holder.streamName.setText(itemListFiltered.get(position).getStreamName());
        holder.postUniqueID.setText(itemListFiltered.get(position).getPostUniqueID());
        //holder.originalPrice.setText(itemListFiltered.get(position).getOriginalPrice());
        //holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {
        return this.itemListFiltered.size();
    }


    private void loadImage(String imageUrl, final SolventViewHolders holder){
        AndroidNetworking.get(imageUrl)
                .setTag("imageRequestTag")
                .setPriority(Priority.HIGH)
                .setBitmapMaxHeight(400) //this value must be larger than height and width in layout
                .setBitmapMaxWidth(400)  //otherwise, bitmap quality will be poor
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // do anything with bitmap
                        //logo.setDefaultImageResId(R.drawable.ic_filter_24dp);
                        //logo.setErrorImageResId(R.drawable.circle_black_transparent);
                        holder.itemPhoto.setImageBitmap(response);

                        //holder.streamName.setText();


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        System.out.println("url error loln!!");

                        holder.itemPhoto.setImageResource(R.drawable.circle_black_transparent);
                    }
                });
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
/*
 //holder.itemPhoto.setImageResource(itemList.get(position).getPhoto());
        Resources res = holder.itemView.getContext().getResources();
        holder.itemPhoto.setImageBitmap(
                decodeSampledBitmapFromResource(res, itemList.get(position).getPhoto(), 200, 200));
 */