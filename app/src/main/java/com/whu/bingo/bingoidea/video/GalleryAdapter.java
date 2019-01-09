package com.whu.bingo.bingoidea.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.whu.bingo.bingoidea.R;
import com.whu.bingo.bingoidea.bean.FileBean;
import com.whu.bingo.bingoidea.utils.ImageUtil;
import com.whu.zengbin.mutiview.util.LogUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.util.List;

/**
 * 创建时间: 2019/01/05 13:01 <br>
 * 作者: zengbin <br>
 * 描述: 手机文件浏览适配器
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>
    implements Observer<List<FileBean>> {

  private static final String TAG = GalleryAdapter.class.getSimpleName();

  private Context mContext;
  private List<FileBean> mData;

  private OnItemClickListener<FileBean> mItemClickListener;

  public GalleryAdapter(Context context) {
    this.mContext = context;
  }

  public void setOnItemClickListener(OnItemClickListener<FileBean> onItemClickListener) {
    mItemClickListener = onItemClickListener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    bindValue(holder, position, mData.get(position));
  }

  private void bindValue(ViewHolder holder, int position, FileBean entity) {
    holder.mContainer.setTag(position);
    if (entity.getType() == FileType.VIDEO) {
      holder.mVideoIcon.setVisibility(View.VISIBLE);
    } else {
      holder.mVideoIcon.setVisibility(View.GONE);
    }
    String thumbUrl = entity.getFilePath();
    if (thumbUrl != null) {
      ImageUtil.loadCenterCrop(mContext, new File(thumbUrl),
          R.mipmap.gridview_picture_normal,
          R.mipmap.gridview_picture_normal, holder.mPic);
    }
  }

  @Override public int getItemCount() {
    return mData != null ? mData.size() : 0;
  }

  @Override public void onError(Throwable e) {
    LogUtil.e(TAG, e.toString());
  }

  @Override public void onComplete() {
    notifyDataSetChanged();
  }

  @Override public void onSubscribe(Disposable d) {

  }

  @Override public void onNext(List<FileBean> fileItems) {
    this.mData = fileItems;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout mContainer;
    public ImageView mPic;
    public ImageView mVideoIcon;

    public ViewHolder(View itemView) {
      super(itemView);
      mContainer = itemView.findViewById(R.id.sl_container);
      mPic = itemView.findViewById(R.id.iv_pic);
      mVideoIcon = itemView.findViewById(R.id.iv_video_icon);
      mContainer.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          int position = (int) mContainer.getTag();
          if (mItemClickListener != null) {
            mItemClickListener.onItemClick(position, mData.get(position), v);
          }
        }
      });
    }
  }
}
