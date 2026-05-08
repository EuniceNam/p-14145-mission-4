package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public String readPage(int pageNo) {
        List<WiseSaying> page = wiseSayingRepository.fetchPage(pageNo);
        if (page.isEmpty()) { return "";}
        Collections.reverse(page);
        return page.stream().map(WiseSaying::toString)
                .collect(Collectors.joining("\n", "", "\n"));
    }
    // - TODO: 다른 클래스로 분리하기
    public String getPageList(int pageNo) {
        int lastPageNo = wiseSayingRepository.getPageCount();
        int range = 1;
        if (lastPageNo <= 2 * range + 1) { // 슬라이딩 윈도우 불필요
            return pageListString(pageNo, 1, lastPageNo) +"\n";
        }
        if (pageNo <= range+2) {
            return pageListString(pageNo, 1, pageNo+range) + " ... " + lastPageNo;
        } else if (pageNo > lastPageNo - range) {
            return "1 ... " + pageListString(pageNo, lastPageNo - range, lastPageNo) +"\n";
        } else {
            return "1 ... " + pageListString(pageNo, pageNo - range, pageNo + range) + " ... " + lastPageNo +"\n";
        }
    }
    public String pageListString(int pageNo, int startPageNo, int lastPageNo) {
        StringBuilder sb = new StringBuilder();
        for (int i = startPageNo; i <= lastPageNo; i++) {
            if (i == pageNo) {
                sb.append("[").append(i).append("] / ");
            } else {
                sb.append(i).append(" / ");
            }
        }
        sb.delete(sb.length() - 3, sb.length());
        return sb.toString();
    }
    public String filter(String keywordType, String keyword) {
        List<WiseSaying> filteredPage = wiseSayingRepository.filter(keywordType, keyword);
        Collections.reverse(filteredPage);
        return filteredPage.stream().map(WiseSaying::toString)
                .collect(Collectors.joining("\n", "", "\n"));
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
