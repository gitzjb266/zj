package com.more.transformation.util;

import java.util.List;

/**
 * 阿里云 录音文件识别返回值 转javabean、
 * @author zhangjb
 */
public class SoundBean {
    private List<Words> Words;
    private List<Sentences> Sentences;
    public void setWords(List<Words> Words) {
        this.Words = Words;
    }
    public List<Words> getWords() {
        return Words;
    }

    public void setSentences(List<Sentences> Sentences) {
        this.Sentences = Sentences;
    }
    public List<Sentences> getSentences() {
        return Sentences;
    }
}
