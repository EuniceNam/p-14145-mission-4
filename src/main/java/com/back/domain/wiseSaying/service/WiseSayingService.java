package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.Iterator;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public int register(String quote, String author) {
        if (quote.isEmpty()) { return -1;}
        int lastQuoteNo;
        lastQuoteNo = wiseSayingRepository.create(quote, author);
        return lastQuoteNo;
    }
    public String readAll() {
        Iterator<WiseSaying> it = wiseSayingRepository.getIterator();
        WiseSaying q;
        StringBuilder quoteListSB = new StringBuilder();
        while(it.hasNext()) {
            q = it.next();
            quoteListSB.append(q.getQuoteId()).append(" / ").append(q.getAuthor())
                    .append(" / ").append(q.getQuote()).append("\n");
        }
        return quoteListSB.toString();
    }
    public boolean delete(int qid) {
        return wiseSayingRepository.delete(qid); // 없으면 null 리턴
    }
    public String[] read(int qid) {
        return wiseSayingRepository.read(qid);
    }
    public void edit(int qid, String newQuote, String newAuthor) {
        if (newQuote.isEmpty()) { return;}
        wiseSayingRepository.update(qid, newQuote, newAuthor);
    }
}
