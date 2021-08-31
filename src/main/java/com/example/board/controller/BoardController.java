package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model){
            log.info("list...................." + pageRequestDto);

        model.addAttribute("result" , boardService.getList(pageRequestDto));
    }

    @GetMapping("/register")
    public void register(){
        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto...." + dto);
        // 새로 추가된 엔티티의 번호
        Long bno = boardService.register(dto);
        // service 쪽에서 register 함수에서 dtoToEntity 를 하고 그러고 insert 하고난다음 그 insert 된 번호를 반환한다.

        log.info("BNO: " + bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";


//        redirectAttributes.addAttribute 는
//         redirectAttributes.addAttribute("pageNum" , cri.getPageNum());
//         redirectAttributes.addAttribute("amount" , cri.getAmount());

//        return "redirect:/board/list";

        // ------> localhost:8080/board/list?pageNum=4&amount=10 처럼
        // URL 에 붙어서 전달되어 값이 유지가된다. 즉, URL 에 파라미터 값들이 보인다.
        // 하지만

        //redirectAttributes.addFlashAttribute 는
        // 일회성으로 URL 에 붙지 않고 세션 후 재지정 요청이 들어오면 값은 사라지게 됩니다.
        // 즉 URL 에 묻어서 안온다. 즉, URL 에 파라미터 값을 보여주지 않게된다.
    }

    @GetMapping({"/read" , "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDto pageRequestDto, Long bno , Model model){

        log.info("bno : " + bno);

        BoardDTO boardDTO = boardService.get(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);

    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){

        log.info("bno : " + bno);

        boardService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg" , bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDto pageRequestDto, RedirectAttributes redirectAttributes ){
        log.info("post modify................................................................");
        log.info("dto : " + dto);

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", pageRequestDto.getPage());
        redirectAttributes.addAttribute("type", pageRequestDto.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDto.getKeyword());

        redirectAttributes.addAttribute("bno" , dto.getBno());

        return "redirect:/board/read";
    }
}
