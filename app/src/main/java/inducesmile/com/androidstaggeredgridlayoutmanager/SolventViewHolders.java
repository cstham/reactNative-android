package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView itemName, originalPrice, streamName, postUniqueID;
    public ImageView itemPhoto;

    public SolventViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        //itemName = (TextView) itemView.findViewById(R.id.itemName);
        itemPhoto = itemView.findViewById(R.id.itemPhoto);

        //originalPrice = (TextView) itemView.findViewById(R.id.originalPrice);
        streamName = itemView.findViewById(R.id.host_name);
        postUniqueID = itemView.findViewById(R.id.points);



    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();


        //the following must be changed to display hosts according to position
        Intent intent = new Intent(view.getContext(), Subscribe.class);
        view.getContext().startActivity(intent);


    }
}
