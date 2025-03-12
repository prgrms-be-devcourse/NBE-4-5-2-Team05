package com.NBE_4_5_2.Team5.domain.user.service;

import com.NBE_4_5_2.Team5.global.config.Pop3Properties;
import com.NBE_4_5_2.Team5.global.exception.ServiceException;
import jakarta.mail.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class BouncedEmailService {

    private final Pop3Properties pop3Properties;
    private static final String FAILED_RECIPIENTS_HEADER = "X-Failed-Recipients";

    public boolean checkBouncedEmail(String email) {
        try {
            Thread.sleep(10000); // 메일이 반송되기까지 기다릴 시간
            Folder emailFolder = getEmailFolder();
            Instant untilTime = Instant.now().minusSeconds(pop3Properties.getUntilTime());
            for (Message message : emailFolder.getMessages()) {
                // 지정한 시간(limitedTime) 안에 수신한 메일만 확인
                if (untilTime.isBefore(message.getSentDate().toInstant())) {

                    Optional.ofNullable(message.getHeader(FAILED_RECIPIENTS_HEADER))
                            .ifPresent(recipients -> {
                                if (recipients[0].equals(email)) {
                                    try {
                                        // 반송된 이메일 삭제
                                        message.setFlag(Flags.Flag.DELETED, true);
                                        emailFolder.close(true);
                                    } catch (MessagingException e) {
                                        throw new ServiceException("500", "반송 메일 확인 중 오류가 발생했습니다.");
                                    }
                                }
                            });
                    if (!emailFolder.isOpen()) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException("500", "반송 메일 확인 중 오류가 발생했습니다.");
        }
        return true;
    }

    private Folder getEmailFolder() {
        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", pop3Properties.getHost());
            properties.put("mail.pop3.port", pop3Properties.getPort());
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore(pop3Properties.getProtocol());
            store.connect(pop3Properties.getHost(), pop3Properties.getUsername(), pop3Properties.getPassword());

            Folder emailFolder = store.getFolder(pop3Properties.getFolder());
            emailFolder.open(Folder.READ_WRITE);
            return emailFolder;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("404-1", "반송 메일을 찾기 못했습니다.");
        }
    }
}
