package renewal.ektour.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import renewal.ektour.dto.request.EstimateRequest;
import renewal.ektour.dto.response.EstimateDetailResponse;
import renewal.ektour.dto.response.EstimateSimpleResponse;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Estimate {

    @Id @GeneratedValue
    @Column(name = "estimate_id")
    private Long id;

    // 신청자 정보
    private String name;
    private String email;
    private String phone;
    private String password;

    // 필수 견적 요청
    private String travelType; // 일반여행, 관혼상제, 학교단체, 기타단체
    private String vehicleType; // 25인승 소형, 28인승 리무진, 45인승 대형
    private int vehicleNumber; // 차량대수
    private int memberCount; // 인원수
    private String departDate; // 출발일시
    private String arrivalDate; // 도착일시
    private String departPlace; // 출발지
    private String arrivalPlace; // 도착지
    private String memo; // 기타메모

    // 선택 견적 요청
    private String stopPlace; // 경유지
    private String wayType; // 왕복구분 : 왕복, 편도
    private String payment; // 결제방법 : 현금, 카드
    private String taxBill; // 세금계산서 : 발급, 발급안함

    // 견적 요청 삭제여부 (사용자에게 보여지는 여부)
    private boolean visibility;

    // 견적 요청일
    @CreatedDate
    private String createdDate;

    // 견적 유효일 (요청일로부터 +7일)
    private String validDate;

    private String ip;

    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.validDate = LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public EstimateSimpleResponse toSimpleResponse() {
        return new EstimateSimpleResponse(id, name, travelType, vehicleType, arrivalPlace, vehicleType, createdDate);
    }

    public EstimateDetailResponse toDetailResponse() {
        return EstimateDetailResponse.builder()
                .id(id)
                .name(name)
                .email(email)
                .phone(phone)
                .password(password)
                .travelType(travelType)
                .vehicleType(vehicleType)
                .vehicleNumber(vehicleNumber)
                .memberCount(memberCount)
                .departDate(departDate)
                .arrivalDate(arrivalDate)
                .departPlace(departPlace)
                .arrivalPlace(arrivalPlace)
                .memo(memo)
                .stopPlace(stopPlace)
                .wayType(wayType)
                .payment(payment)
                .taxBill(taxBill)
                .visibility(visibility)
                .createdDate(createdDate)
                .ip(ip)
                .build();
    }

    /**
     * 비즈니스 로직
     */
    // 견적 요청 삭제 (보여지는걸 숨기기)
    public void setInvisible() {
        this.visibility = false;
    }

    // 견적 요청 수정
    public void update(EstimateRequest form) {
        this.name = form.getName();
        this.email = form.getEmail();
        this.phone = form.getPhone();
        this.password = form.getPassword();
        this.travelType = form.getTravelType();
        this.vehicleType = form.getVehicleType();
        this.vehicleNumber = form.getVehicleNumber();
        this.memberCount = form.getMemberCount();
        this.departDate = form.getDepartDate();
        this.arrivalDate = form.getArrivalDate();
        this.departPlace = form.getDepartPlace();
        this.arrivalPlace = form.getArrivalPlace();
        this.memo = form.getMemo();
        this.stopPlace = form.getStopPlace();
        this.wayType = form.getWayType();
        this.payment = form.getPayment();
        this.taxBill = form.getTaxBill();
    }

    public void update(EstimateDetailResponse form) {
        this.name = form.getName();
        this.email = form.getEmail();
        this.phone = form.getPhone();
        this.password = form.getPassword();
        this.travelType = form.getTravelType();
        this.vehicleType = form.getVehicleType();
        this.vehicleNumber = form.getVehicleNumber();
        this.memberCount = form.getMemberCount();
        this.departDate = form.getDepartDate();
        this.arrivalDate = form.getArrivalDate();
        this.departPlace = form.getDepartPlace();
        this.arrivalPlace = form.getArrivalPlace();
        this.memo = form.getMemo();
        this.stopPlace = form.getStopPlace();
        this.wayType = form.getWayType();
        this.payment = form.getPayment();
        this.taxBill = form.getTaxBill();
        this.visibility = form.isVisibility();
    }
}
