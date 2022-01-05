package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_jpa");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // business logic
            
            save(em);
            
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }

    public static void save(EntityManager em) {
        final Board board = new Board();
        board.setTitle("게시판 제목입니다.");
        em.persist(board);

        final BoardDetail boardDetail = new BoardDetail();
        boardDetail.setContent("게시판 내용입니다.");
        boardDetail.setBoard(board);
        em.persist(boardDetail);

        System.out.println("boardDetail.getBoard().getTitle() = " + boardDetail.getBoard().getTitle());
        System.out.println("board.getTitle() = " + board.getTitle());

        System.out.println("board = " + board.getId());
        System.out.println("boardDetail.getBoardId() = " + boardDetail.getBoardId());
    }
}
