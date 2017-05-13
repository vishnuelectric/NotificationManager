package com.vishnu.notificationmanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vishnu.notificationmanager.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class InstalledAppsAdapter extends RecyclerView.Adapter<InstalledAppsAdapter.InstalledAppsViewHolder> {

    private  List<ApplicationInfo> mValues;
    private final OnListFragmentInteractionListener mListener;
    private PackageManager packageManager;

    public InstalledAppsAdapter(PackageManager packageManager,List<ApplicationInfo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.packageManager = packageManager;
    }
    public void setUpdatedSource(List<ApplicationInfo> items)
    {
        mValues = items;
    }
    @Override
    public InstalledAppsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.installed_app_item, parent, false);
        return new InstalledAppsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstalledAppsViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(final InstalledAppsViewHolder holder, int position) {

        holder.appName.setText(mValues.get(position).loadLabel(packageManager));
        holder.packageName.setText(mValues.get(position).packageName);
holder.icon.setImageDrawable(mValues.get(position).loadIcon(packageManager));
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

    public class InstalledAppsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView appName;
        public final TextView packageName;
        public final ImageView icon;
        public DummyItem mItem;

        public InstalledAppsViewHolder(View view) {
            super(view);
            mView = view;
            icon =(ImageView)view.findViewById(R.id.icon);
            appName = (TextView) view.findViewById(R.id.appname);
            packageName = (TextView) view.findViewById(R.id.packagename);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + packageName.getText() + "'";
        }
    }
}
