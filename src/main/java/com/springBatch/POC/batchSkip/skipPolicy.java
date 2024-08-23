package com.springBatch.POC.batchSkip;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class skipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) {
        if (t instanceof JsonParseException || t instanceof JsonMappingException || t instanceof IllegalArgumentException) {
            return true;
        }
        return false;
    }
}


