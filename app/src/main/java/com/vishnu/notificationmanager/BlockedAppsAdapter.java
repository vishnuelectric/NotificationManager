package com.vishnu.notificationmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vishnu.notificationmanager.BlockAppsFragment.OnListFragmentInteractionListener;
import com.vishnu.notificationmanager.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BlockedAppsAdapter extends RecyclerView.Adapter<BlockedAppsAdapter.ViewHolder> {

    private final List<String> mValues;
    private final OnListFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private Context context;

    public BlockedAppsAdapter(Context context,List<String> items, OnListFragmentInteractionListener listener) {
        mValues = items;
  sharedPreferences = context.getSharedPreferences("blocked_apps",0);
        mListener = listener;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blocked_apps_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.appName.setText(sharedPreferences.getString(mValues.get(position),""));
        holder.packageName.setText(mValues.get(position));
        try {
            holder.icon.setImageDrawable(context.getPackageManager().getApplicationIcon(mValues.get(position)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView appName;
        public final TextView packageName;
        public  final ImageView icon;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            icon = (ImageView)view.findViewById(R.id.icon);
            appName = (TextView) view.findViewById(R.id.appname);
            packageName = (TextView) view.findViewById(R.id.packagename);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + packageName.getText() + "'";
        }
    }
}
