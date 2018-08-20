package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

//import static inducesmile.com.androidstaggeredgridlayoutmanager.MainActivity.gaggeredList;
import static inducesmile.com.androidstaggeredgridlayoutmanager.MainFragment.gaggeredList;

public class SearchBarFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, TextWatcher {

    private ImageView mIvBack, mIvClear;
    private EditText mEtSearchText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_box, container, false);

        initializeView(view);

        //initListeners();
        mIvBack.setOnClickListener(this);
        mIvClear.setOnClickListener(this);
        mEtSearchText.setOnTouchListener(this);
        mEtSearchText.addTextChangedListener(this);

        return view;
    }


    private void initializeView(View view) {
        mIvBack = (ImageView) view.findViewById(R.id.iv_back);
        mIvClear = (ImageView) view.findViewById(R.id.iv_clear_search_box);
        mEtSearchText = (EditText) view.findViewById(R.id.et_search_text);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.iv_back) {
            resetSearchBox();
            performBackAction();
        }
        else if(id == R.id.iv_clear_search_box){
            resetSearchBox();
        }
    }

    private void enableSearchField() {
        mIvBack.setImageResource(R.drawable.ic_arrow_back);
    }

    private void resetSearchBox() {
        mEtSearchText.setText("");
    }

    protected void performBackAction() {
        mIvBack.setImageResource(R.drawable.ic_search);
        mEtSearchText.clearFocus();
        Utils.hideKeyboardFromDialog(getActivity(), mEtSearchText);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP){
            //startActivity( new Intent(getActivity(), CommonTabActivity.class));
            enableSearchField();
        }
        return false;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //String searchText = query.toString();
        //System.out.println("searchText lol: "+searchText);

        final String query = s.toString().toLowerCase().trim();
        final List<FrameObjects> filteredList = new ArrayList<>();

        //List<FrameObjects> originalList = getListItemData();
/*
        for (int i = 0; i < FrameObjects.size(); i++) {

            final String text = chapterLists.get(i).getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(chapterLists.get(i));
            }
        }
*/
        for (FrameObjects item: gaggeredList){
            //To filter by stream name
            if (item.getStreamName().toLowerCase().contains(query)){
                filteredList.add(item);
            }

        }



        //recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        //adapter = new SearchListAdapter(SearchActivity.this, filteredList);
        //recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();  // data set changed

        //==========================================================================================
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //List<FrameObjects> gaggeredList = getListItemData();

        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(getActivity(), filteredList);
        recyclerView.setAdapter(rcAdapter);

        rcAdapter.notifyDataSetChanged();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



}
