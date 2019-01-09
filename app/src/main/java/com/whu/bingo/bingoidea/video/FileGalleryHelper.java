package com.whu.bingo.bingoidea.video;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import com.whu.bingo.bingoidea.bean.FileBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2019/01/07 22:47 <br>
 * 作者: zengbin <br>
 * 描述: 浏览手机文件的帮助类
 */
public class FileGalleryHelper {
  private static final String[] GALLERY_PROJECTION = {
      MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME,
      MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID
  };
  private static final String[] GALLERY_VIDEO_PROJECTION = {
      MediaStore.Video.Media.DATA,
      MediaStore.Video.Media.DISPLAY_NAME,
      MediaStore.Video.Media.DATE_MODIFIED,
      MediaStore.Video.Media._ID
  };
  private Context mContext;
  private String mFolderTitle = "视频和图片";

  private final CursorLoader mVideoCursorLoader;
  private final CursorLoader mImageCursorLoader;
  private List<FolderEntity> mItems = new ArrayList<>();

  private FolderEntity mFolderEntity = new FolderEntity();
  private FileBean mfileBean = new FileBean();
  private Disposable mDisposable;

  private FileGalleryHelper(Context context) {
    mContext = context;
    this.mImageCursorLoader =
        new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, GALLERY_PROJECTION,
            null, null, GALLERY_PROJECTION[2] + " DESC");
    this.mVideoCursorLoader =
        new CursorLoader(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            GALLERY_VIDEO_PROJECTION,
            null, null, GALLERY_VIDEO_PROJECTION[2] + " DESC");
  }

  public static FileGalleryHelper createFileGallery(Context context) {
    return new FileGalleryHelper(context);
  }

  public void subscribe(Consumer<List<FolderEntity>> onSuccess, Consumer<Throwable> onError) {
    if (mDisposable != null) {
      mDisposable.dispose();
    }
    mDisposable = interactor().subscribe(onSuccess, onError);
  }

  public void unsubscribe() {
    if (mDisposable != null) {
      mDisposable.dispose();
      mDisposable = null;
    }
  }

  private Single<List<FileBean>> getLoaderObservable(final int loadType,
      final CursorLoader cursorLoader) {
    return Observable.create(new ObservableOnSubscribe<Cursor>() {
      @Override public void subscribe(ObservableEmitter<Cursor> emitter) throws Exception {
        final Cursor cursor = cursorLoader.loadInBackground();
        if (cursorLoader.isStarted() && cursor != null && !cursor.isClosed()) {
          cursorLoader.cancelLoad();
          cursor.close();
        }
        if (cursor == null) {
          Observable.error(new NullPointerException("cursor must not be null"));
        } else {
          while (cursor.moveToNext()) {
            emitter.onNext(cursor);
          }
          cursor.close();
          emitter.onComplete();
        }
      }
    }).map(new Function<Cursor, FileBean>() {
      @Override public FileBean apply(Cursor cursor) throws Exception {
        String imagePath = null;
        String imageName = null;
        long addDate = 0;
        if (loadType == FileType.IMAGE) {
          imagePath = cursor.getString(cursor.getColumnIndexOrThrow(GALLERY_PROJECTION[0]));
          imageName = cursor.getString(cursor.getColumnIndexOrThrow(GALLERY_PROJECTION[1]));
          addDate = cursor.getLong(cursor.getColumnIndexOrThrow(GALLERY_PROJECTION[2]));
        } else if (loadType == FileType.VIDEO) {
          imagePath = cursor.getString(cursor.getColumnIndexOrThrow(GALLERY_VIDEO_PROJECTION[0]));
          imageName = cursor.getString(cursor.getColumnIndexOrThrow(GALLERY_VIDEO_PROJECTION[1]));
          addDate = cursor.getLong(cursor.getColumnIndexOrThrow(GALLERY_VIDEO_PROJECTION[2]));
        }
        FileBean clone = mfileBean.newInstance();
        clone.setFilePath(imagePath);
        clone.setFileName(imageName);
        clone.setDate(addDate);
        clone.setType(loadType);
        return clone;
      }
    }).filter(new Predicate<FileBean>() {
      @Override public boolean test(FileBean fileBean) throws Exception {
        File file = new File(fileBean.getFilePath());
        File parentFile = file.getParentFile();
        return file.exists() && parentFile != null;
      }
    }).doOnNext(new Consumer<FileBean>() {
      @Override public void accept(FileBean fileBean) throws Exception {
        File folderFile = new File(fileBean.getFilePath()).getParentFile();
        FolderEntity clone = mFolderEntity.newInstance();
        if (fileBean.getType() == FileType.VIDEO) {
          clone.setFolderName("全部视频");
        } else {
          clone.setFolderName(folderFile.getName());
        }
        clone.setFolderPath(folderFile.getAbsolutePath());
        if (!mItems.contains(clone)) {
          clone.setThumbPath(fileBean.getFilePath());
          clone.addImage(fileBean);
          mItems.add(clone);
        } else {
          mItems.get(mItems.indexOf(clone)).addImage(fileBean);
        }
      }
    }).toList();
  }

  private Single<List<FolderEntity>> interactor() {
    return getLoaderObservable(FileType.VIDEO, mVideoCursorLoader)
        .zipWith(getLoaderObservable(FileType.IMAGE, mImageCursorLoader), new GalleryFunction())
        .map(new Function<List<FileBean>, FolderEntity>() {
          @Override public FolderEntity apply(List<FileBean> fileBeans) throws Exception {
            FolderEntity clone = mFolderEntity.newInstance();
            clone.setFolderName(mFolderTitle);
            clone.setFolderPath("");
            clone.setChecked(true);
            if (fileBeans.size() > 0) {
              clone.setThumbPath(fileBeans.get(0).getFilePath());
            }
            clone.setFileEntities(fileBeans);
            return clone;
          }
        })
        .map(new Function<FolderEntity, List<FolderEntity>>() {
          @Override public List<FolderEntity> apply(FolderEntity folderEntity) throws Exception {
            List<FolderEntity> galleryFolderEntities = new ArrayList<>(mItems.size() + 1);
            galleryFolderEntities.add(folderEntity);
            List<FileBean> videoEntities = new ArrayList<>();
            FolderEntity videoFolder = mFolderEntity.newInstance();
            for (int i = 0; i < mItems.size(); i++) {
              FolderEntity folder = mItems.get(i);
              if (TextUtils.equals(folder.getFolderName(), "全部视频")) {
                videoEntities.addAll(folder.getFileEntities());
              }
            }
            if (videoEntities.size() > 0) {
              videoFolder.setFolderName("全部视频");
              videoFolder.setFolderPath("");
              videoFolder.setChecked(false);
              videoFolder.setThumbPath(videoEntities.get(0).getFilePath());
              videoFolder.setFileEntities(videoEntities);
              galleryFolderEntities.add(1, videoFolder);
            }
            return galleryFolderEntities;
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
