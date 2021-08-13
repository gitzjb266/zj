package com.more.transformation.util;
/**
 * oss录音文件识别 json转javabean
 * @author zhangjb
 */
public class Sentences {
    private int EndTime;
    private int SilenceDuration;
    private int BeginTime;
    private String Text;
    private int ChannelId;
    private int SpeechRate;
    private double EmotionValue;
    public void setEndTime(int EndTime) {
        this.EndTime = EndTime;
    }
    public int getEndTime() {
        return EndTime;
    }

    public void setSilenceDuration(int SilenceDuration) {
        this.SilenceDuration = SilenceDuration;
    }
    public int getSilenceDuration() {
        return SilenceDuration;
    }

    public void setBeginTime(int BeginTime) {
        this.BeginTime = BeginTime;
    }
    public int getBeginTime() {
        return BeginTime;
    }

    public void setText(String Text) {
        this.Text = Text;
    }
    public String getText() {
        return Text;
    }

    public void setChannelId(int ChannelId) {
        this.ChannelId = ChannelId;
    }
    public int getChannelId() {
        return ChannelId;
    }

    public void setSpeechRate(int SpeechRate) {
        this.SpeechRate = SpeechRate;
    }
    public int getSpeechRate() {
        return SpeechRate;
    }

    public void setEmotionValue(double EmotionValue) {
        this.EmotionValue = EmotionValue;
    }
    public double getEmotionValue() {
        return EmotionValue;
    }



}
