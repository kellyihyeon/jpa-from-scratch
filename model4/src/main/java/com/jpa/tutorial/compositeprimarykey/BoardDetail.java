package com.jpa.tutorial.compositeprimarykey;

import javax.persistence.*;

@Entity
public class BoardDetail {

    @Id     // pk
    private Long boardId;

    @MapsId     // BoardDetail.boardId
    @OneToOne   // fk
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String content;


    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
