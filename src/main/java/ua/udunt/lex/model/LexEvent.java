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

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.udunt.lex.util.ObjectUtil;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class LexEvent implements Serializable {

    private String messageVersion;
    private String invocationSource;
    private String inputMode;
    private String responseContentType;
    private String sessionId;
    private String inputTranscript;
    private Bot bot;
    private Interpretation[] interpretations;
    private ProposedNextState proposedNextState;
    private Map<String, String> requestAttributes;
    private SessionState sessionState;
    private Transcription[] transcriptions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class Bot implements Serializable {

        private String id;
        private String name;
        private String aliasId;
        private String aliasName;
        private String localeId;
        private String version;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class Interpretation implements Serializable {

        private Intent intent;
        private Double nluConfidence;
        private SentimentResponse sentimentResponse;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class Intent implements Serializable {

        private String confirmationState;
        private String name;
        private Map<String, Slot> slots;
        private String state;
        private KendraResponse kendraResponse;

        @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
        public Map<String, Slot> getSlots() {
            return this.slots;
        }

        @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
        public void setSlots(Map<String, Slot> slots) {
            this.slots = slots;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class Slot implements Serializable {

        private String shape;
        private SlotValue value;
        private Slot[] values;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class SlotValue implements Serializable {

        private String interpretedValue;
        private String originalValue;
        private String[] resolvedValues;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class KendraResponse implements Serializable {

        private String queryId;
        private KendraResponseResultItem[] resultItems;
        private Object[] facetResults;
        private Integer totalNumberOfResults;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class KendraResponseResultItem implements Serializable {

        private String id;
        private String type;
        private Object[] additionalAttributes;
        private String documentId;
        private KendraResponseDocumentInfo documentTitle;
        private KendraResponseDocumentInfo documentExcerpt;
        private String documentURI;
        private KendraResponseDocumentAttribute[] documentAttributes;
        private KendraResponseScoreAttributes scoreAttributes;
        private String feedbackToken;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class KendraResponseDocumentInfo implements Serializable {

        private String text;
        private KendraResponseDocumentHighlights[] highlights;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class KendraResponseDocumentHighlights implements Serializable {

        private Integer beginOffset;
        private Integer endOffset;
        private boolean topAnswer;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class KendraResponseDocumentAttribute implements Serializable {

        private String key;
        private Map<String, String> value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class KendraResponseScoreAttributes implements Serializable {

        private String scoreConfidence;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class NluConfidence implements Serializable {

        private Double score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class SentimentResponse implements Serializable {

        private String sentiment;
        private SentimentScore sentimentScore;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class SentimentScore implements Serializable {

        private Double mixed;
        private Double negative;
        private Double neutral;
        private Double positive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class ProposedNextState implements Serializable {

        private DialogAction dialogAction;
        private Intent intent;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class DialogAction implements Serializable {

        private String slotToElicit;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class SessionState implements Serializable {

        private ActiveContext[] activeContexts;
        private Map<String, String> sessionAttributes;
        private RuntimeHints runtimeHints;
        private DialogAction dialogAction;
        private Intent intent;
        private String originatingRequestId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class ActiveContext implements Serializable {

        private String name;
        private Map<String, String> contextAttributes;
        private TimeToLive timeToLive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class TimeToLive implements Serializable {

        private Integer timeToLiveInSeconds;
        private Integer turnsToLive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class RuntimeHints implements Serializable {

        private Map<String, Map<String, Hint>> slotHints;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class Hint implements Serializable {

        private RuntimeHintValue[] runtimeHintValues;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class RuntimeHintValue implements Serializable {

        private String phrase;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class Transcription implements Serializable {

        private String transcription;
        private Double transcriptionConfidence;
        private ResolvedContext resolvedContext;
        private Map<String, Slot> resolvedSlots;

        @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
        public Map<String, Slot> getResolvedSlots() {
            return this.resolvedSlots;
        }

        @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
        public void setResolvedSlots(Map<String, Slot> resolvedSlots) {
            this.resolvedSlots = resolvedSlots;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class ResolvedContext implements Serializable {

        private String intent;
    }

    @Override
    public String toString() {
        return ObjectUtil.toJSON(this);
    }

}
