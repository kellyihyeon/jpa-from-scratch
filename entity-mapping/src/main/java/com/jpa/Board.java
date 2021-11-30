package com.jpa;

import javax.persistence.*;

/**
 * 기본 키 자동생성 전략
 * # @SequenceGenerator(
 *         name = "BOARD_SEQ_GENERATOR",
 *         sequenceName = "BOARD_SEQ",
 *         initialValue = 1, allocationSize = 1
 *  )
 *
 * # @TableGenerator(
 *         name = "BOARD_SEQ_GENERATOR",
 *         table = "MY_SEQUENCES",
 *         pkColumnValue = "BOARD_SEQ", allocationSize = 1
 *  )
 *
 *  default strategy: AUTO
 *  # @GeneratedValue(strategy = GenerationType.AUTO)
 *  # @GeneratedValue(strategy = GenerationType.IDENTITY)
 *  # @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
 *  # @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
 */

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
}
