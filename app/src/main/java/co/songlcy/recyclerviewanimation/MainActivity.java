package co.songlcy.recyclerviewanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import co.songlcy.recyclerviewanimation.adapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
    }

    private void initData() {
        strItems  = getResources().getStringArray(R.array.str_item);
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

        recyclerViewAdapter = new RecyclerViewAdapter(this,strItems,strDescs);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(recyclerViewAdapter);
        startAnimation(R.anim.scale);

    }

    private void setListener() {

        btnChange.setOnClickListener(this);
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLinearLayout) {
                    View topView = mLinearLayoutManager.getChildAt(0);
                    if (topView != null) {
                        lastOffset = topView.getTop();                                   //获取与该view的顶部的偏移量
                        lastPosition = mLinearLayoutManager.getPosition(topView);
                    }
                } else {
                    View topView = mGirdLayoutManger.getChildAt(0);
                    if (topView != null) {
                        lastOffset = topView.getTop();                                   //获取与该view的顶部的偏移量
                        lastPosition = mGirdLayoutManger.getPosition(topView);
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_change:
                //切换
                if(isLinearLayout) {
                    //切换成网格布局
                    recyclerViewAdapter.setType(1);
//                    rv.setLayoutManager(new GridLayoutManager(this, 2));
                    rv.setLayoutManager(mGirdLayoutManger);
                    recyclerViewAdapter.notifyDataSetChanged();
                    mGirdLayoutManger.scrollToPosition(lastPosition);
//                    startAnimation(R.anim.zoom_in);
                    isLinearLayout = false;

                } else {
                    //切换成垂直线性布局
                    recyclerViewAdapter.setType(0);
//                    rv.setLayoutManager(new LinearLayoutManager(this));
                    rv.setLayoutManager(mLinearLayoutManager);
                    recyclerViewAdapter.notifyDataSetChanged();
                    mLinearLayoutManager.scrollToPosition(lastPosition);
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

        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this,anim));
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
        rv.setLayoutAnimation(lac);
        rv.startLayoutAnimation();
    }
}
