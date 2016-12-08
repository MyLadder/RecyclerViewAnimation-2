package co.songlcy.recyclerviewanimation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.songlcy.recyclerviewanimation.R;

/**
 * Created by Song on 2016/7/19.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder> {

    private int type = 0;
    private String[] strDescs;
    private String[] strItems;
    private Context mContext;
    private LayoutInflater inflater;
    private static List<Integer> mTipIds = new ArrayList<>(Arrays.asList(R.id.tip1, R.id.tip2, R.id.tip3, R.id.tip4, R.id.tip5, R.id.tip6, R.id.tip7, R.id.tip8));
    private static String[] tipDatas;

    public RecyclerViewAdapter(Context context, String[] strItems, String[] descs) {

        this.strItems = strItems;
        this.strDescs = descs;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        tipDatas = context.getResources().getStringArray(R.array.str_tips);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView;
        if (viewType == 0) {
            rootView = inflater.inflate(R.layout.layout_linear, null, false);
            LinearViewHolder linearViewHolder = new LinearViewHolder(rootView);
            return linearViewHolder;
        } else if (viewType == 1) {
            rootView = inflater.inflate(R.layout.layout_grid, null, false);
            GridViewHolder gridViewHolder = new GridViewHolder(rootView);
            return gridViewHolder;
        } else {
            rootView = inflater.inflate(R.layout.layout_grid_tips, null, false);
            LineLayoutTipsHolder lineLayoutTipsHolder = new LineLayoutTipsHolder(rootView);
            return lineLayoutTipsHolder;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (type == 0&&position!=8) {
            LinearViewHolder linearViewHolder = (LinearViewHolder) holder;
            linearViewHolder.tvName.setText(strItems[position]);
            linearViewHolder.tvDesc.setText(strDescs[position]);
        } else if (type == 1&&position!=8) {
            GridViewHolder gridViewHolder = (GridViewHolder) holder;
            gridViewHolder.tvName.setText(strItems[position]);
        } else if (type == 8) {
            LineLayoutTipsHolder tipsHolder = (LineLayoutTipsHolder) holder;
            for (int i = 0; i < 8; i++) {
                tipsHolder.tvNames.get(i).setText(tipDatas[i]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return strItems.length;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 8){
            return 2;
        }else {
            return type;
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

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

    public static class LineLayoutTipsHolder extends BaseViewHolder {

        private List<TextView> tvNames = new ArrayList<TextView>();
        private TextView tvName;

        public LineLayoutTipsHolder(View itemView) {
            super(itemView);
            for (int i = 0; i < tipDatas.length; i++) {
                tvName = (TextView) itemView.findViewById(mTipIds.get(i));
                tvNames.add(tvName);
            }
        }
    }
}
