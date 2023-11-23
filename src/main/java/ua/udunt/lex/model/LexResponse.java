/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ua.udunt.lex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.udunt.lex.util.ObjectUtil;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class LexResponse implements Serializable {

    private LexEvent.SessionState sessionState;
    private Message[] messages;
    private Map<String, String> requestAttributes;

    @Data
    @Builder(setterPrefix = "with")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message implements Serializable {

        private String contentType;
        private String content;
        private ImageResponseCard imageResponseCard;
    }

    @Data
    @Builder(setterPrefix = "with")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageResponseCard implements Serializable {

        private String title;
        private String subtitle;
        private String imageUrl;
        private Button[] buttons;
    }

    @Data
    @Builder(setterPrefix = "with")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Button implements Serializable {

        private String text;
        private String value;
    }

    @Override
    public String toString() {
        return ObjectUtil.toJSON(this);
    }

}
