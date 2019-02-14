package changhua.com.myflickclient.ui.main;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import changhua.com.myflickclient.PhotoActivity;
import changhua.com.myflickclient.R;
import changhua.com.myflickclient.model.PhotoData;

import changhua.com.myflickclient.databinding.MainFragmentBinding;
import changhua.com.myflickclient.model.PhotoItem;

public class MainFragment extends Fragment implements MyAdapter.ItemClickListener {

    private MainViewModel mViewModel;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    PhotoData phd = new PhotoData();

    private MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = MainFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(getActivity());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mLayoutManager = new GridLayoutManager(getActivity(), 3);

        recyclerView.setLayoutManager(mLayoutManager);


        //phd.loadPhotoFromJson( getActivity() );

        // define an adapter
        mAdapter = new MyAdapter(phd.items);

        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);


        mViewModel = new MainViewModel(phd);

        binding.setViewModel(mViewModel);

        mViewModel.getPhotoItems().observe(this, new Observer<List<PhotoItem>>() {
            @Override
            public void onChanged(@Nullable List<PhotoItem> repos) {
                //mAdapter.swapItems(repos);
                //mViewModel.isLoading.set(false);
                mAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(View view, int position) {
        if (phd.items != null && position < phd.items.size()) {
            String url = phd.items.get(position).imageUrl;

            Intent i = new Intent(this.getActivity(), PhotoActivity.class);

            Bundle b = new Bundle();
            b.putString("imageUrl", url);
            i.putExtras(b);
            startActivity(i);
        }
    }

}
