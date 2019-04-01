package co.netguru.android.umeshandroidtest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private static final String TAG = "LISTFRAG";
    private View view;
    private RecyclerView rcvList;
    private Context context;


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        rcvList = (RecyclerView) view.findViewById(R.id.rcv_list);
        rcvList.setLayoutManager(new LinearLayoutManager(context));
        rcvList.setAdapter(new MoviesAdapter((ClickItemPosition) context));

        callAPi();

    }

    private void callAPi() {
        ApiClient.get().getMovieList().enqueue(new RestCallBack<List<MovieModel>>() {
            @Override
            public void onResponseSuccess(Response<List<MovieModel>> response) {
                Log.d(TAG, "onResponseSuccess: ");
            }

            @Override
            public void onResponseFailure(int errorCode, String msg) {
                Log.d(TAG, "onResponseFailure: ");
            }
        });
    }

}
