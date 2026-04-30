package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class AppContext {
    public static Scanner scanner = new Scanner(System.in);
    public static WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    public static WiseSayingService wiseSayingService = new WiseSayingService(wiseSayingRepository);
    public static WiseSayingController wiseSayingController = new WiseSayingController(scanner, wiseSayingService);
    public static SystemController systemController = new SystemController(scanner, wiseSayingController);

    public static void renew(Scanner sc) {
        scanner = sc;
        wiseSayingRepository = new WiseSayingRepository();
        wiseSayingService = new WiseSayingService(wiseSayingRepository);
        wiseSayingController = new WiseSayingController(scanner, wiseSayingService);
        systemController = new SystemController(scanner, wiseSayingController);
    }

    public static void renew() {
        renew(new Scanner(System.in));
    }
}
