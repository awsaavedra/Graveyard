package com.company.generics;

import java.util.ArrayList;

/*
    For more information on generics see: https://docs.oracle.com/javase/tutorial/java/generics/

    Key words and ideas:
        - keywords: generics, raw types, diamond notation, parameterized type
            autoboxing and unboxing, bounded type parameters
        - Why use generics at all instead of raw types?
            - What problems do generics solve?
        -
 */

public class Main {
    public static void main(String[] args) {
        FootballPlayer jak = new FootballPlayer("jak");
        FootballPlayer jim = new FootballPlayer("jim");
        FootballPlayer joe = new FootballPlayer("joe");

        BaseballPlayer one = new BaseballPlayer("one");
        BaseballPlayer two = new BaseballPlayer("two");
        BaseballPlayer three = new BaseballPlayer("three");

        BaseballPlayer four = new BaseballPlayer("four");
        BaseballPlayer five = new BaseballPlayer("five");
        BaseballPlayer six = new BaseballPlayer("six");

        Team<FootballPlayer> denverBroncos = new Team<>("Denver Broncos");
        denverBroncos.addPlayer(jak);
        denverBroncos.addPlayer(jim);
        denverBroncos.addPlayer(joe);

        System.out.println(denverBroncos.numPlayers());

        Team<BaseballPlayer> detroitTigers = new Team<>("Detroit Tigers");
        Team<BaseballPlayer> miamiMarlins = new Team<>("Miami Marlins");

        detroitTigers.addPlayer(four);
        detroitTigers.addPlayer(five);
        detroitTigers.addPlayer(six);

        detroitTigers.matchResult(detroitTigers, 1, 0);
        miamiMarlins.matchResult(miamiMarlins, 1, 0);

        System.out.println("Rankings");
        System.out.println(detroitTigers.getName() + ": " + detroitTigers.ranking());
        System.out.println(miamiMarlins.getName() + ": " + miamiMarlins.ranking());
        System.out.println(detroitTigers.compareTo(miamiMarlins));

        // Collections.sort, how could you use it here?
    }

    private static void printAddOne(ArrayList<Integer> n){
        // autoboxing and unboxing Integer and int
        for(int i : n){
            System.out.println((Integer) i + 1);
        }
    }
}
