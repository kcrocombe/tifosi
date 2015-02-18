package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import uk.co.pegortech.tifosi.database.Persister;
import uk.co.pegortech.tifosi.database.RideServerContract;

/**
 * Created by kevin on 13/02/2015.
 */
public class PaceGroupAdapter extends BaseAdapter {

    PaceGroup[] pgs = new PaceGroup[100];
    LayoutInflater mInflator;
    int layoutId;

    public PaceGroupAdapter(Context ctx, int layoutResourceId) {

        Persister pageGrps = new Persister(ctx, RideServerContract.Tables.PaceGroups.TABLENAME);

        mInflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutId=layoutResourceId;

        pgs[0] = new PaceGroup(pageGrps, "pg1","Pace Group1", 10, "Red");
        pgs[1] = new PaceGroup(pageGrps, "pg2","Pace Group1", 20, "Blue");
        pgs[2] = new PaceGroup(pageGrps, "pg3","Pace Group1", 30, "Green");
        pgs[3] = new PaceGroup(pageGrps, "pg4","Pace Group1", 40, "Black");

        for(int i=4;i<100;i++){
            pgs[i] = new PaceGroup(pageGrps, "pg5","Pace Group1", 40, "YEllow");

        }


    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return pgs.length;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return pgs[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return (long) position;

    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vw = mInflator.inflate(layoutId,null);

        TextView nameTv = (TextView) vw.findViewById(R.id.name);
        TextView descTv = (TextView) vw.findViewById(R.id.Description);
        TextView typPaceTv = (TextView) vw.findViewById(R.id.typicalPace);
        TextView displayColTv = (TextView) vw.findViewById(R.id.displayColour);

        nameTv.setText(pgs[position].getName());
        descTv.setText(pgs[position].getDescription());
        typPaceTv.setText(String.valueOf(pgs[position].getTypicalPace()));
        displayColTv.setText(pgs[position].getDisplayColour());

        return vw;
    }
}
