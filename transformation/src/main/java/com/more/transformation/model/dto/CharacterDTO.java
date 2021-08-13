package com.more.transformation.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lzy
 * @date 2021/7/7 17:12
 */
@Data
public class CharacterDTO {
    private List<CommonText> results;
    private String base64;
    public static class CommonText{
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
