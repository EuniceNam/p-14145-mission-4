package com.back.domain.wiseSaying.controller;

import com.back.domain.constant.GuideMsg;
import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class WiseSayingController {
    private final Scanner sc;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner sc, WiseSayingService wiseSayingService) {
        this.sc = sc;
        this.wiseSayingService = wiseSayingService;
    }

    public void execute(SystemController.CmdMsg cmd,int qid) {
        switch (cmd) {
            case REGISTER -> register(sc);
            case VIEW -> view();
            case DELETE -> delete(qid);
            case EDIT -> edit(sc, qid);
        }
    }
    private void register(Scanner sc) {
        String quote, author;
        System.out.print(GuideMsg.QUOTE.getValue());
        quote = sc.nextLine();
        System.out.print(GuideMsg.AUTHOR.getValue());
        author = sc.nextLine();

        int lastQuoteNo = wiseSayingService.register(quote, author);
        if (lastQuoteNo != -1) {
            System.out.printf(GuideMsg.REGISTER.getValue(), lastQuoteNo);
        } else {
            System.out.print(GuideMsg.EMPTY.getValue());
        }
    }
    private void view() {
        System.out.print(GuideMsg.VIEW.getValue());
        System.out.print(wiseSayingService.readAll());
    }
    private void delete(int qid) {
        boolean isDeleted = wiseSayingService.delete(qid);
        if (isDeleted) {
            System.out.printf(GuideMsg.DELETE.getValue(), qid);
        } else {
            System.out.printf(GuideMsg.ABSENT.getValue(), qid);
        }
    }
    private void edit(Scanner sc, int qid) {
        String[] tmpQ = wiseSayingService.read(qid);
        if (tmpQ == null) { System.out.printf(GuideMsg.ABSENT.getValue(), qid); return;}

        System.out.print(GuideMsg.QUOTEOLD.getValue()+tmpQ[0]+"\n"+GuideMsg.QUOTE.getValue());
        String newQuote = sc.nextLine();
        if (newQuote.isEmpty()){ System.out.print(GuideMsg.EMPTY.getValue()); return;}
        System.out.print(GuideMsg.AUTHOROLD.getValue()+tmpQ[1]+"\n"+GuideMsg.AUTHOR.getValue());
        String newAuthor = sc.nextLine();
        wiseSayingService.edit(qid, newQuote, newAuthor);
    }
}
