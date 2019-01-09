package com.whu.bingo.bingoidea.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.whu.bingo.bingoidea.R;
import com.whu.bingo.bingoidea.bean.FileBean;
import com.whu.bingo.bingoidea.ui.MarginDecoration;
import com.whu.zengbin.mutiview.util.LogUtil;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * 创建时间: 2019/01/07 22:47 <br>
 * 作者: zengbin <br>
 * 描述: 浏览手机图片和视频文件的页面
 */
public class PickVideoActivity extends AppCompatActivity {
  private static final String TAG = "PickVideoActivity";
  private RecyclerView mGallery;
  private GalleryAdapter mGalleryAdapter;
  private TextView mMore;
  private FileGalleryHelper mHelper;
  private List<FolderEntity> mFolders;
  private FolderEntity mCurrentFolderEntity;

  public static void start(Context context) {
    Intent intent = new Intent(context, PickVideoActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pick_video);
    mGallery = findViewById(R.id.rv_gallery);
    mMore = findViewById(R.id.tv_gallery_category);
    initGalleryView();
    initData();
  }

  private void initGalleryView() {
    mGalleryAdapter = new GalleryAdapter(this);
    mGalleryAdapter.setOnItemClickListener(new OnItemClickListener<FileBean>() {
      @Override public void onItemClick(int position, FileBean item, View v) {
        if (item.getType() == FileType.VIDEO) {
          VideoPlayActivity.start(PickVideoActivity.this, item.getFilePath());
        } else if (item.getType() == FileType.IMAGE) {
          //TODO 预览图片
        }
      }
    });
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
    gridLayoutManager.setSmoothScrollbarEnabled(true);
    mGallery.setLayoutManager(gridLayoutManager);
    mGallery.setHasFixedSize(true);
    mGallery.addItemDecoration(new MarginDecoration(this));
    mGallery.setAdapter(mGalleryAdapter);
    mMore.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //TODO 弹出更多文件列表事件
      }
    });
  }

  private void initData() {
    mHelper = FileGalleryHelper.createFileGallery(this);
    mHelper.subscribe(new Consumer<List<FolderEntity>>() {
      @Override public void accept(List<FolderEntity> folderEntities) {
        mFolders = folderEntities;
        mCurrentFolderEntity = folderEntities.get(0);
        mMore.setText(mCurrentFolderEntity.getFolderName());
        Observable.just(mCurrentFolderEntity.getFileEntities()).subscribe(mGalleryAdapter);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) {
        LogUtil.e(TAG, throwable.toString());
      }
    });
  }

  @Override protected void onDestroy() {
    mHelper.unsubscribe();
    super.onDestroy();
  }
}
