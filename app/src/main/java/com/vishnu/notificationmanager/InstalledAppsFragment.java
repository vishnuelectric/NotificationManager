package com.vishnu.notificationmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vishnu.notificationmanager.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class InstalledAppsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private InstalledAppsAdapter installedAppsAdapter;
    private ItemTouchHelper itemTouchHelper;
    private SharedPreferences sharedPreferences;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InstalledAppsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static InstalledAppsFragment newInstance(int columnCount) {
        InstalledAppsFragment fragment = new InstalledAppsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
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
        final View view = inflater.inflate(R.layout.installed_apps_list, container, false);

        final List<ApplicationInfo> applist =  getActivity().getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
       final List<ApplicationInfo>applist1 =new ArrayList<>();
        for (int i=0; i < applist.size(); i++)
        {

            if ((applist.get(i).flags & ApplicationInfo.FLAG_SYSTEM) == 1)
            {
              //  applist.remove(i);
           continue;
                //System app
            }else if(sharedPreferences.contains(applist.get(i).packageName))
            {
                continue;
             //   applist.remove(i);
            }
            applist1.add(applist.get(i));

        }
        System.out.println(applist.size());
        installedAppsAdapter= new InstalledAppsAdapter(getActivity().getPackageManager(),applist1, mListener);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(installedAppsAdapter);
            recyclerView.setItemAnimator(new Animate());
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                   String p = ((InstalledAppsAdapter.InstalledAppsViewHolder)viewHolder).packageName.getText().toString();
                    if(p.equalsIgnoreCase("com.vishnu.notificationmanager"))
                        return;
                String name = ((InstalledAppsAdapter.InstalledAppsViewHolder)viewHolder).appName.getText().toString();
                    sharedPreferences.edit().putString(p,name).apply();
                    applist1.remove(viewHolder.getAdapterPosition());
                    installedAppsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                }
            };
            itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(),"Swipe the apps left or right to block these apps from posting notifications",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPreferences =context.getSharedPreferences("blocked_apps",0);
       /* if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    public static class Animate extends DefaultItemAnimator
    {
        @Override
        public void onAnimationStarted(RecyclerView.ViewHolder viewHolder) {
            super.onAnimationStarted(viewHolder);
        }

        @Override
        public void setRemoveDuration(long removeDuration) {
            super.setRemoveDuration(removeDuration);
            setRemoveDuration(2000);
        }

        @Override
        public void onRemoveStarting(RecyclerView.ViewHolder item) {
            super.onRemoveStarting(item);
        }
    }
}
