package jpa.jpashop.service;

import jpa.jpashop.domain.Board;
import jpa.jpashop.repositoty.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;


    public void save(Board board) {
        boardRepository.save(board);
        System.out.println("board 저장= " + board.getId());
    }

    public void flush() {
        boardRepository.flush();
    }

}
