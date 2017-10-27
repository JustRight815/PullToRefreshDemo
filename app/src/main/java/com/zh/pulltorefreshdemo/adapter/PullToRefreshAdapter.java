package com.zh.pulltorefreshdemo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.pulltorefreshdemo.R;
import com.zh.pulltorefreshdemo.model.Model;

import java.util.List;

/**
 * 列表适配器
 */
public class PullToRefreshAdapter extends BaseMultiItemQuickAdapter<Model, BaseViewHolder> implements BaseQuickAdapter.SpanSizeLookup{
    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;

    public PullToRefreshAdapter(List<Model> data, Context context){
        super(data);
        setSpanSizeLookup(this);
        mContext = context;
        addItemType(0, R.layout.list_home_text_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Model item) {
            bindTextData(helper, item);
    }

    private void bindTextData(BaseViewHolder helper, Model item) {
        TextView textView = helper.getView(R.id.tv_home_text);
        textView.setText(item.getType());
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return 0;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
