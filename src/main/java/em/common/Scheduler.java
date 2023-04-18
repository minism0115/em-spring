package em.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class Scheduler {

    /**
     * 매년 1월 1일 00시 00분 00초
     */
    @Scheduled(cron = "0 0 0 1 1 *", zone = "Asia/Seoul")
    @Transactional
    public void check(){

    }

    /**
     * 매월 마지막 날 오후 11시 59분 59초
     */
    @Scheduled(cron = "59 59 23 L * *", zone = "Asia/Seoul")
    @Transactional
    public void saveMonthlyReport(){

    }
}
