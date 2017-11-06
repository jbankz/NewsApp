package jbankz.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import jbankz.com.R;
import jbankz.com.adapter.myAdapter;
import jbankz.com.model.Response;
import jbankz.com.model.pojo;
import jbankz.com.util.APiService;
import jbankz.com.util.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by King Jaycee on 29/10/2017.
 */

public class SportFragment extends Fragment {

    private static final String TAG = CnnFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private APiService aPiService;
    private String API_KEY;  // = YOUR [API_KEY HERE]
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.news_rv);
        layoutManager = new LinearLayoutManager(getContext());
        progressBar = (ProgressBar) view.findViewById(R.id.pb);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        testForNetwork();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            testForNetwork();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void testForNetwork() {
        if (RetrofitUtil.isConnected(getContext())) {
            if (API_KEY != null) {
                progressBar.setVisibility(View.VISIBLE);
                getSports();
            } else {
                Toast.makeText(getContext(), "Please obtain your api key at www.news.org", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Unable to Connect", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSports() {
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext());
        aPiService = retrofitUtil.provideRetrofit().create(APiService.class);
        aPiService.getSports("latest", API_KEY).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    ArrayList<pojo> pojoArayList = response.body().getArticles();
                    mRecyclerView.setAdapter(new myAdapter(pojoArayList));
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
