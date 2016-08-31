package com.example.owner.movieapp;

/**
 * Created by dom on 31/08/16.
 */
public final class PicassoHelper {
    public static final String POSTER_SIZE_XS ="w92";
    public static final String POSTER_SIZE_S ="w154";
    public static final String POSTER_SIZE_M ="w185";
    public static final String POSTER_SIZE_L ="w342";
    public static final String POSTER_SIZE_XL ="w500";
    public static final String POSTER_SIZE_XXL ="w780";
    public static final String POSTER_SIZE_original ="original";


    public static String buildUrl(String size, String path){
        String baseUrl = "http://image.tmdb.org/t/p/";
        return baseUrl + size + path;
    }
}
