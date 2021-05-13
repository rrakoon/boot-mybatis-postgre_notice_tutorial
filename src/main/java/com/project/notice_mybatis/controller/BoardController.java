package com.project.notice_mybatis.controller;

import com.project.notice_mybatis.domain.BoardDTO;
import com.project.notice_mybatis.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/write")
    //@RequestParam : view에서 전달받은 파라미터 처리하는데 사용.
    //value="값" = > view에서 받을 파라미터 값.
    //required = false => 지정 키값이 없을때 Bad Request Error발생 방지. default값은 true며 반드시 false로 고친다.
    //defaultValue = "값" => 지정 키값이 존재 안하면 기본값 할당.
    public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = boardService.getBoardDetail(idx);
            if (board == null) {
                return "redirect:/board/list";
            }
            model.addAttribute("board", board);
        }

        return "board/write";
    }

    @PostMapping(value = "/register")
    public String registerBoard(final BoardDTO params) {
        try {
            boolean isRegistered = boardService.registerBoard(params);
            if (isRegistered == false) {
                //TODO => 게시글 등록에 실패하였다는 메시지를 전달
            }
        } catch (DataAccessException e) {
            //TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

        } catch (Exception e) {
            //TODO => 시스템에 문제가 발생하였다는 메시지를 전달
        }

        return "redirect:/board/list";
    }

}