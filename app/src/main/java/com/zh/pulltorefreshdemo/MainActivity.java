package com.zh.pulltorefreshdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zh.pulltorefreshdemo.adapter.PullToRefreshAdapter;
import com.zh.pulltorefreshdemo.model.Model;
import com.zh.pulltorefreshdemo.view.LoadMoreLayout;
import com.zh.pulltorefreshdemo.view.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 经典下拉刷新，上拉加载示例
 */
public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private PullToRefreshAdapter mOnlineVideoAdapter;
    private List<Model> mVideoList;
    private PullToRefreshLayout mPtrFrame;//下拉刷新
    private LoadMoreLayout loadMoreLayout;

    private static final int PAGE_SIZE = 20;
    private static final int TOTAL_COUNT = 60;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mPtrFrame = (PullToRefreshLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                }, 1500);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        mVideoList = new ArrayList();
        for (int i = 1; i <= PAGE_SIZE; i++) {
            Model model = new Model();
            model.setType(i + "");
            mVideoList.add(model);
        }
        mOnlineVideoAdapter = new PullToRefreshAdapter(mVideoList, this);
        mRecyclerView.setAdapter(mOnlineVideoAdapter);


        loadMoreLayout = new LoadMoreLayout(this, mRecyclerView, mOnlineVideoAdapter, new LoadMoreLayout.OnLoadMoreListener() {
            boolean flag = false;

            @Override
            public void onLoadMore() {
                Log.e("zh", "加载更多");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mVideoList.size() < TOTAL_COUNT) {
                            if (mVideoList.size() == 40 && flag == false) {
                                flag = true;
                                loadMoreLayout.loadMoreFail();
                            } else {
                                for (int i = 1; i <= PAGE_SIZE; i++) {
                                    Model model = new Model();
                                    model.setType(i + "");
                                    mVideoList.add(model);
                                }
                                mOnlineVideoAdapter.notifyDataSetChanged();
                                loadMoreLayout.loadMoreComplete();
                            }
                        } else {
                            loadMoreLayout.loadMoreEnd();
                        }
                    }
                }, 1000);
            }
        });
        loadMoreLayout.loadMoreEnable(true);
    }
}
