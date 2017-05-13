package com.vishnu.notificationmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BlockAppsFragment extends Fragment{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ItemTouchHelper itemTouchHelper;
    public BlockedAppsAdapter blockedAppsAdapter;
   private SharedPreferences sharedPreferences;
    public SwipeRefreshLayout swipeRefreshLayout;
    static BlockAppsFragment fragment;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BlockAppsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BlockAppsFragment newInstance(int columnCount) {
        if(fragment == null)
       fragment = new BlockAppsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    public static BlockAppsFragment getfragment(){
        if(fragment == null)
            return null;
        else
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blocked_apps_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.block_refresh);
        swipeRefreshLayout.setColorSchemeColors(new int[]{android.R.color.holo_red_dark, android.R.color.holo_green_dark});

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        final List<String> list = new ArrayList<>();
        list.addAll(sharedPreferences.getAll().keySet());
blockedAppsAdapter = new BlockedAppsAdapter(getActivity(),list, mListener);
        // Set the adapter
        if (true) {
            Context context = view.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(blockedAppsAdapter);
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               sharedPreferences.edit().remove(((BlockedAppsAdapter.ViewHolder)viewHolder).packageName.getText().toString()).apply();
                   list.clear();
                    list.addAll(sharedPreferences.getAll().keySet());
                    if(list.size()>0)
                    list.remove(viewHolder.getAdapterPosition());
                    blockedAppsAdapter.setUpdatedSource(list);
                    blockedAppsAdapter.notifyDataSetChanged();
                }
            };
            itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    blockedAppsAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPreferences = context.getSharedPreferences("blocked_apps",0);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




}
