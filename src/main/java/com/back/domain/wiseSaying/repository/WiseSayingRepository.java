package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public class WiseSayingRepository {
    private final LinkedList<WiseSaying> wiseSayings = new LinkedList<>();

    public WiseSayingRepository() {}
    public int create(String quote, String author) {
        wiseSayings.addFirst(new WiseSaying(quote, author));
        return WiseSaying.getLastQuoteNo();
    }
    public Iterator<WiseSaying> getIterator() {
        return wiseSayings.iterator();
    }
    public String[] read(int qid) {
        return wiseSayings.stream().filter(q -> q.compareNo(qid)).findFirst()
                .map(q -> new String[] {q.getQuote(), q.getAuthor()}).orElse(null);
    }
    public Stream<WiseSaying> filter(String keywordType, String keyword) {
        switch (keywordType) {
            case "author" -> {
                return wiseSayings.stream().filter(q -> q.getAuthor().contains(keyword));
            }
            case "content" -> {
                return wiseSayings.stream().filter(q -> q.getQuote().contains(keyword));
            }
            default -> { return Stream.empty(); }
        }
    }
    public void update(int qid, String quote, String author) {
        wiseSayings.stream().filter(q -> q.compareNo(qid)).findFirst()
                .ifPresentOrElse(q -> { q.setQuote(quote); q.setAuthor(author);}, () -> {});
    }
    public boolean delete(int qid) {
        return wiseSayings.removeIf(q -> q.compareNo(qid));
    }
}
