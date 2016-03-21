package com.vishnu.notificationmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vishnu.notificationmanager.ItemFragment.OnListFragmentInteractionListener;
import com.vishnu.notificationmanager.dummy.DummyContent.DummyItem;

import org.json.JSONObject;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>  {

    private final List<DummyItem> mValues;
    private SharedPreferences sharedPreferences;
    private final OnListFragmentInteractionListener mListener;
private Context context;
    public MyItemRecyclerViewAdapter(Context context,List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("notification_list",0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);
        try {
            final JSONObject jsonObject = new JSONObject(sharedPreferences.getString(String.valueOf(position), ""));
            holder.title.setText(jsonObject.optString("title"));
            holder.summary.setText(jsonObject.optString("message"));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (null != mListener) {
                            Intent intent = Intent.parseUri(jsonObject.optString("intentUri"), 0);
                            v.getContext().startActivity(intent);
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            //  mListener.onListFragmentInteraction(holder.mItem);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(position);
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return sharedPreferences.getAll().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView summary;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            summary = (TextView) view.findViewById(R.id.summary);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + summary.getText() + "'";
        }
    }
}
