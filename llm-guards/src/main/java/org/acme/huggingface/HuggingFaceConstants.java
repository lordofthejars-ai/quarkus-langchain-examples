package org.acme.huggingface;

public class HuggingFaceConstants {

    public static final String URL = "https://huggingface.co";
    public static final String BRANCH = "main";

    public static String resolveHost(String org, String model) {
        return URL + "/" + org + "/" + model;
    }

}