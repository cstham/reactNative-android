package inducesmile.com.androidstaggeredgridlayoutmanager;


//==================================================================================================

//refer to ItemObjects for more parameters reference

//==================================================================================================

public class FrameObjects {
    private String photo, streamName, postUniqueID;
    //private int photo;

    public FrameObjects(String photo, String streamName, String postUniqueID) {
        this.photo = photo;
        this.streamName = streamName;
        this.postUniqueID = postUniqueID;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }


    public String getPostUniqueID() {
        return postUniqueID;
    }

    public void setPostUniqueID(String postUniqueID) {
        this.postUniqueID = postUniqueID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    //----------------------------------------------------------------------------------------------


}
