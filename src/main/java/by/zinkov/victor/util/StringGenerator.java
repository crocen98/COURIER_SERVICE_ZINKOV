package by.zinkov.victor.util;

import java.util.UUID;

public final class StringGenerator {
    public String generate(){
        String randomString = UUID.randomUUID().toString();
        return randomString.replace("-","");
    }
}
