package sopt._39sopkathonserverandroid1.domain.mission.service;

public interface MissionImageVerifier {

    boolean verify(String title, String body, byte[] image, String contentType);
}
