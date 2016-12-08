package co.songlcy.recyclerviewanimation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import co.songlcy.recyclerviewanimation.R;

/**
 * Created by Song on 2016/7/19.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder>{

    private int type = 0;
    private String[] strDescs;
    private String[] strItems;
    private Context mContext;
    private LayoutInflater inflater;
    public RecyclerViewAdapter(Context context, String[] strItems, String[] descs) {

        this.strItems = strItems;
        this.strDescs = descs;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView;
        if(viewType == 0) {
            rootView = inflater.inflate(R.layout.layout_linear,null,false);
            LinearViewHolder linearViewHolder = new LinearViewHolder(rootView);
            return linearViewHolder;
        } else {

            rootView = inflater.inflate(R.layout.layout_grid,null,false);
            GridViewHolder gridViewHolder = new GridViewHolder(rootView);
            return gridViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if(type == 0) {
            LinearViewHolder linearViewHolder = (LinearViewHolder)holder;
            linearViewHolder.tvName.setText(strItems[position]);
            linearViewHolder.tvDesc.setText(strDescs[position]);
        } else {
            GridViewHolder gridViewHolder = (GridViewHolder)holder;
            gridViewHolder.tvName.setText(strItems[position]);
        }
    }

    @Override
    public int getItemCount() {
        return strItems.length;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class LinearViewHolder extends BaseViewHolder {

        private TextView tvName;
        private TextView tvDesc;

        public LinearViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

    public static class GridViewHolder extends BaseViewHolder {

        private TextView tvName;

        public GridViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
