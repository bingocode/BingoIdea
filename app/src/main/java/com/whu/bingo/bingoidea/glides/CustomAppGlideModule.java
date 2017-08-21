package com.whu.bingo.bingoidea.glides;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by zengbin on 2017/8/21.
 */
@GlideModule
public class CustomAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(20*1024*1024));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
