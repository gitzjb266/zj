package com.more.transformation.util;

/**
 * oss录音文件识别 json转javabean
 * @author zhangjb
 */
public class Words {


    private String Word;
    private int EndTime;
    private int BeginTime;
    private int ChannelId;
    public void setWord(String Word) {
        this.Word = Word;
    }
    public String getWord() {
        return Word;
    }

    public void setEndTime(int EndTime) {
        this.EndTime = EndTime;
    }
    public int getEndTime() {
        return EndTime;
    }

    public void setBeginTime(int BeginTime) {
        this.BeginTime = BeginTime;
    }
    public int getBeginTime() {
        return BeginTime;
    }

    public void setChannelId(int ChannelId) {
        this.ChannelId = ChannelId;
    }
    public int getChannelId() {
        return ChannelId;
    }


}
