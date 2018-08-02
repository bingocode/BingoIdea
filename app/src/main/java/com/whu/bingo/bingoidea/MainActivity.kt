package com.whu.bingo.bingoidea

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.whu.bingo.bingoidea.utils.ImageUtil
import com.whu.zengbin.mutiview.VStereoView
import com.whu.zengbin.mutiview.HStereoView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val imgarray = arrayOf(
            "http://outjw9m6x.bkt.clouddn.com/p00.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p01.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p02.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p031.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p10.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p11.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p121.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p13.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p20.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p21.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p221.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p231.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p30.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p311.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p321.jpg",
            "http://outjw9m6x.bkt.clouddn.com/p331.jpg"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ImageUtil.loadurlimage(this,imgarray[0],img00)
        ImageUtil.loadurlimage(this,imgarray[1],img01)
        ImageUtil.loadurlimage(this,imgarray[2],img02)
        ImageUtil.loadurlimage(this,imgarray[3],img03)
        ImageUtil.loadurlimage(this,imgarray[4],img10)
        ImageUtil.loadurlimage(this,imgarray[5],img11)
        ImageUtil.loadurlimage(this,imgarray[6],img12)
        ImageUtil.loadurlimage(this,imgarray[7],img13)
        ImageUtil.loadurlimage(this,imgarray[8],img20)
        ImageUtil.loadurlimage(this,imgarray[9],img21)
        ImageUtil.loadurlimage(this,imgarray[10],img22)
        ImageUtil.loadurlimage(this,imgarray[11],img23)
        ImageUtil.loadurlimage(this,imgarray[12],img30)
        ImageUtil.loadurlimage(this,imgarray[13],img31)
        ImageUtil.loadurlimage(this,imgarray[14],img32)
        ImageUtil.loadurlimage(this,imgarray[15],img33)

        backorigion.setOnClickListener {
            stereoView.setItem(VStereoView.mCurStartScreen)
            stereoView22.setItem(HStereoView.curStartScreen)
        }
    }
}
