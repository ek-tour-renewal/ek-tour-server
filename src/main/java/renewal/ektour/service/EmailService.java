package renewal.ektour.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import renewal.ektour.dto.request.EstimateRequest;
import renewal.ektour.exception.EmailException;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private static final String ADMIN_ADDRESS = "ektour0914@naver.com";

    @Async
    public void sendMail(EstimateRequest form) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.addRecipients(Message.RecipientType.TO, ADMIN_ADDRESS);
            message.setSubject("[이케이하나관광 견적요청]");
            String text = "";
            text += form.getName() + " " + form.getPhone() + "\n";
            text += form.getTravelType() + " " + form.getVehicleType() + " " + form.getVehicleNumber() + "대\n";
            text += form.getDepartPlace() + " ~ " + form.getArrivalPlace() + "\n";
            text += "경유지(" + form.getStopPlace() + ")\n";
            text += form.getDepartDate() + " ~ " + form.getArrivalDate() + "\n";
            message.setText(text, "utf-8");
            message.setFrom(new InternetAddress(ADMIN_ADDRESS, form.getName()));
            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("이메일 전송 관련 오류 발생");
        }
    }

}
