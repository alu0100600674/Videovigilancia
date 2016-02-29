package com.tfg.jonay.videovigilancia;

import android.app.Activity;
import android.media.tv.TvInputService;
import android.view.SurfaceHolder;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspClient;
import net.majorkernelpanic.streaming.video.VideoQuality;

/**
 * Created by jonay on 29/02/16.
 */
public class Servidor extends Activity implements RtspClient.Callback,
        Session.Callback, SurfaceHolder.Callback{

    private String stream_url;
    private String publisher_user;
    private String publisher_pass;

    private RtspClient client;
    private Session session;

    public Servidor(){

    }

    public void iniciar(SurfaceView surfaceView){
//        session = SessionBuilder.getInstance()
//                .setCallback(this)
//                .setSurfaceView(surfaceView)
//                .setPreviewOrientation(90)
//                .setContext(getApplicationContext())
//                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
//                .setAudioQuality(new AudioQuality(16000, 32000))
//                .setVideoEncoder(SessionBuilder.VIDEO_H264)
//                .setVideoQuality(new VideoQuality(320,240,20,500000))
//                .build();

//        surfaceView.getHolder().addCallback(this);
    }

    public String getURL(){
        return stream_url;
    }

    public void setURL(String url){
        stream_url = url;
    }

    public String getUsername(){
        return publisher_user;
    }

    public void setUsername(String user){
        publisher_user = user;
    }

    public String getPassword(){
        return publisher_pass;
    }

    public void setPassword(String pass){
        publisher_pass = pass;
    }

    @Override
    public void onBitrateUpdate(long bitrate) {

    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {

    }

    @Override
    public void onPreviewStarted() {

    }

    @Override
    public void onSessionConfigured() {

    }

    @Override
    public void onSessionStarted() {

    }

    @Override
    public void onSessionStopped() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onRtspUpdate(int message, Exception exception) {

    }
}
