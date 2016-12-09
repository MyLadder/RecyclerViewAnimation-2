package co.songlcy.recyclerviewanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import co.songlcy.recyclerviewanimation.adapter.RecyclerViewAdapter;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv;
    private ImageButton btnChange;
    //切换标示,默认显示线性布局
    private boolean isLinearLayout = true;
    private RecyclerViewAdapter recyclerViewAdapter;
    //data
    private int[] images;
    private String[] strDescs;
    private String[] strItems;
    //LineLayout
    private GridLayoutManager mGirdLayoutManger;
    private LinearLayoutManager mLinearLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private int lastPosition = 0;
    private int lastOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        btnChange = (ImageButton) findViewById(R.id.btn_change);
        rv.setItemAnimator(new DefaultItemAnimator());
        mGirdLayoutManger = new GridLayoutManager(this, 2);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, VERTICAL);
    }

    private void initData() {
        strItems = getResources().getStringArray(R.array.str_item);
        strDescs = getResources().getStringArray(R.array.str_desc);
        images = new int[]{
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo,
                R.drawable.ic_photo
        };

        recyclerViewAdapter = new RecyclerViewAdapter(this, strItems, strDescs);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.setAdapter(recyclerViewAdapter);


//        startAnimation(R.anim.scale);

    }

    private void setListener() {
        btnChange.setOnClickListener(this);
    }


    private void calculateRecyclePos() {
        if (isLinearLayout) {
            lastPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            lastOffset = mLinearLayoutManager.findViewByPosition(lastPosition).getTop();
        } else {
            //获取与该view的顶部的偏移量
            lastPosition = (mStaggeredGridLayoutManager.findFirstVisibleItemPositions(null))[0];
            lastOffset = mStaggeredGridLayoutManager.findViewByPosition(lastPosition).getTop();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_change:
                calculateRecyclePos();
                //切换x
                if (isLinearLayout) {
                    //切换成网格布局
//                    recyclerViewAdapter.setType(1);
////                    rv.setLayoutManager(new GridLayoutManager(this, 2));
//                    rv.setLayoutManager(mGirdLayoutManger);
//                    recyclerViewAdapter.notifyDataSetChanged();
//                    mGirdLayoutManger.scrollToPosition(lastPosition);
////                    startAnimation(R.anim.zoom_in);
//                    isLinearLayout = false;


                    recyclerViewAdapter.setType(5);     //瀑布流
                    rv.setLayoutManager(mStaggeredGridLayoutManager);
                    if (lastPosition % 2 == 1) {
                        lastPosition--;
                    }
//                    mStaggeredGridLayoutManager.scrollToPosition(lastPosition);
                    mStaggeredGridLayoutManager.scrollToPositionWithOffset(5,-150);

                    recyclerViewAdapter.notifyDataSetChanged();
//                    startAnimation(R.anim.zoom_in);
                    isLinearLayout = false;
                } else {
                    //切换成垂直线性布局
                    recyclerViewAdapter.setType(0);
//                    rv.setLayoutManager(new LinearLayoutManager(this));
                    rv.setLayoutManager(mLinearLayoutManager);
                    mLinearLayoutManager.scrollToPositionWithOffset(lastPosition,lastOffset);
                    recyclerViewAdapter.notifyDataSetChanged();
//                    mLinearLayoutManager.scrollToPosition(lastPosition);
//                    startAnimation(R.anim.zoom_in);
                    isLinearLayout = true;

                }
                break;
            default:
                break;
        }
    }

    /**
     * 开启动画
     */
    private void startAnimation(int anim) {

        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this, anim));
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
        rv.setLayoutAnimation(lac);
        rv.startLayoutAnimation();
    }
}
