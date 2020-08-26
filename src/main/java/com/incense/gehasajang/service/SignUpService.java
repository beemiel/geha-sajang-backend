package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.*;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.util.CommonString;
import com.incense.gehasajang.util.MailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SignUpService {

    private final HostRepository hostRepository;

    private final HostAuthKeyRepository hostAuthKeyRepository;

    private final JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder;

    public boolean checkEmail(String email) {
        return hostRepository.existsByEmailAndDeletedAtNull(email);
    }

    public boolean checkName(String nickname) {
        return hostRepository.existsByNicknameAndDeletedAtNull(nickname);
    }

    public void addHost(Host mainHost) {
        if(checkEmail(mainHost.getEmail())) {
            throw new DuplicateHostException();
        }

        if(checkName(mainHost.getNickname())) {
            throw new DuplicateHostException();
        }

        mainHost.hashPassword(passwordEncoder);
        Host savedHost = hostRepository.save(mainHost);
        HostAuthKey savedKey = hostAuthKeyRepository.save(createAuthKey(savedHost));

        sendMail(mailSender ,mainHost.getEmail(), savedKey.getAuthKey());
    }

    public void confirm(String email, String authkey) {
        //email 불일치
        MainHost host = hostRepository.findMainHostByEmail(email)
                .orElseThrow(NotFoundDataException::new);

        //이미 인증함
        if(host.isPassEmailAuth()) {
            throw new DuplicateAuthException();
        }

        //인증키 만료
        if(host.getAuthKey().getExpirationDate().isBefore(LocalDateTime.now())){
            throw new ExpirationException();
        }

        if(host.getAuthKey().getAuthKey().equals(authkey)){
            host.changeAuthPass();
            hostRepository.save(host);
            return;
        }

        //인증키 불일치
        throw new FailToAuthenticationException();
    }

    private HostAuthKey createAuthKey(Host savedHost) {
        HostAuthKey hostAuthKey = HostAuthKey.builder()
                .host(savedHost)
                .authKey(savedHost.getEmail())
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();
        hostAuthKey.hashAuthKey(passwordEncoder);

        return hostAuthKey;
    }

    private void sendMail(JavaMailSender mailSender, String hostEmail, String key) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            //받는 사람
            mailHandler.setTo(hostEmail);

            //제목
            mailHandler.setSubject(CommonString.MAIL_SUBJECT);

            //내용(HTML 레이아웃)
            StringBuffer sb = new StringBuffer()
                    .append("<img src='cid:logo'>")
                    .append("<h1>[게하사장 이메일 인증]</h1>")
                    .append("<h3>링크는 메일 전송 후 24시간 이내에만 유효합니다.</h3>")
                    .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
                    .append(CommonString.MAIL_AUTH_LINK)
                    .append(hostEmail)
                    .append("&authkey=")
                    .append(key)
                    .append("' target='_blenk'>이메일 인증 확인</a>");
            mailHandler.setText(sb.toString(), true);

            //이미지 추가
            mailHandler.setInline("logo", CommonString.LOGO_PATH);

            //전송
            mailHandler.send();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CannotSendMailException();
        }
    }

}
