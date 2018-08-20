package inducesmile.com.androidstaggeredgridlayoutmanager;

import com.google.common.collect.ListMultimap;

public interface AsyncResponse {
    void processFinish(ListMultimap<Integer, String> output);
}
