package com.etaTech;

import Model.DataSource;


import java.util.List;

/****************************************************
 *** Created by Fady Fouad on 6/25/2019 at 22:10.***
 ***************************************************/
public class Main {
    public static void main(String[] args) {
        System.out.println("------------------------- Music Database------------------------\n");

        DataSource dataSource = new DataSource();
        if (!dataSource.open()){
            System.out.println("Cant open");
            return;
        }

//        List<Artist> artists = dataSource.getArtists(DataSource.ORDERBY_ASC);
//        for (Artist artist :
//                artists) {
//            System.out.println(artist.getId()+" "+artist.getName());
//        }
        List<String >albums = dataSource.albums4Artists("Iron Maiden",DataSource.ORDERBY_NONE);
        for (String s :
                albums) {
            System.out.println(s);
        }

        List<String>albumJoinArtist = dataSource.artist4Songs("Mahogany Rush",DataSource.ORDERBY_NONE);
        for (String s:
             albumJoinArtist) {
            System.out.println(s);
        }

        int count = dataSource.getCount("artists");
        System.out.println(count);

        dataSource.close();
    }
}

