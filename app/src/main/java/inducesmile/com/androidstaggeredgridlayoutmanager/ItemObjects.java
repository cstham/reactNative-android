package inducesmile.com.androidstaggeredgridlayoutmanager;

public class ItemObjects {
    private String name, photo, originalPrice;
    //private int photo;

    public ItemObjects(String name, String photo, String originalPrice) {
        this.name = name;
        this.photo = photo;
        this.originalPrice = originalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    //----------------------------------------------------------------------------------------------
    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }




}
