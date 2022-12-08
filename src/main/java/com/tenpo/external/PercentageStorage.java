package com.tenpo.external;

import com.google.common.cache.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.concurrent.*;

@Component
@Scope(value = "singleton")
public class PercentageStorage {

    CacheLoader<String, String> loader = new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return key.toUpperCase();
        }
    };;

    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                                                     .expireAfterWrite(30, TimeUnit.MINUTES)
                                                     .build(loader);

    public void savePercentage(String value){
        cache.put("PERCENTAGE", value);
    }

    public BigDecimal getPercentage() throws ExecutionException {
        return new BigDecimal(cache.get("PERCENTAGE"));
    }


}
