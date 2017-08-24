package com.whu.bingo.bingoidea

import android.app.Application
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.RemoteViews
import android.widget.Toast

import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.MsgConstant
import com.umeng.message.PushAgent
import com.umeng.message.UTrack
import com.umeng.message.UmengMessageHandler
import com.umeng.message.UmengNotificationClickHandler
import com.umeng.message.common.UmLog
import com.umeng.message.entity.UMessage
import com.whu.bingo.bingoidea.utils.SharedPreferenceUtil

class MyApplication : Application() {
    private var handler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        val mPushAgent = PushAgent.getInstance(this)
        mPushAgent.setDebugMode(true)
        handler = Handler()

        //sdk开启通知声音
        mPushAgent.notificationPlaySound = MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE
        // sdk关闭通知声音
        //		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
        //		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

        //		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        //		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);


        val messageHandler = object : UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             */
            override fun dealWithCustomMessage(context: Context?, msg: UMessage?) {

                handler!!.post {
                    // TODO Auto-generated method stub
                    // 对自定义消息的处理方式，点击或者忽略
                    val isClickOrDismissed = true
                    if (isClickOrDismissed) {
                        //自定义消息的点击统计
                        UTrack.getInstance(applicationContext).trackMsgClick(msg)
                    } else {
                        //自定义消息的忽略统计
                        UTrack.getInstance(applicationContext).trackMsgDismissed(msg)
                    }
                    Toast.makeText(context, msg!!.custom, Toast.LENGTH_LONG).show()
                }
            }

            /**
             * 自定义通知栏样式的回调方法
             */
            override fun getNotification(context: Context?, msg: UMessage?): Notification {
                when (msg!!.builder_id) {
                    1 -> {
                        val builder = Notification.Builder(context)
                        val myNotificationView = RemoteViews(context!!.packageName, R.layout.notification_view)
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title)
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text)
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg))
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg))
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true)
                        var i =0
                        msg.extra.values.forEach {
                            SharedPreferenceUtil.setExtraInfo("key"+i,it,applicationContext)
                            i = i+1}
                        return builder.getNotification()
                    }
                    else ->
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg)
                }
            }
        }
        mPushAgent.messageHandler = messageHandler

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         */
        val notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun dealWithCustomAction(context: Context?, msg: UMessage?) {
                Toast.makeText(context, msg!!.custom, Toast.LENGTH_LONG).show()
            }
        }
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.notificationClickHandler = notificationClickHandler


        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                UmLog.e(TAG, "device token: " + deviceToken)
                sendBroadcast(Intent(UPDATE_STATUS_ACTION))
            }

            override fun onFailure(s: String, s1: String) {
                UmLog.e(TAG, "register failed: $s $s1")
                sendBroadcast(Intent(UPDATE_STATUS_ACTION))
            }
        })

        //此处是完全自定义处理设置，两个例子，任选一种即可
        //        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        //        mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);
    }

    companion object {

        private val TAG = "MyApplicationname"
        val UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS"
    }
}
