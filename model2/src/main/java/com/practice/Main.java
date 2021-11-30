package com.practice;

public class Main {

    public static void main(String[] args) {
        final Member member1 = new Member("member1", "회원1");
        final Member member2 = new Member("member2", "회원2");
        final Team team1 = new Team("team1", "팀1");

        member1.setTeam(team1);
        member2.setTeam(team1);

        final Team findTeam = member1.getTeam();
        System.out.println("findTeam = " + findTeam);

    }
}
