package sample.cafekiosk.spring.api.service.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
	// @Spy
	@Mock
	private MailSendClient mailSendClient;

	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;

	@InjectMocks
	private MailService mailService;

	@DisplayName("메일 전송 테스트")
	@Test
	void sendMail() {
	    // given
		// MailSendClient mailSendClient = mock(MailSendClient.class);
		// MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);

		// MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

		// Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
		// 	.thenReturn(true);
		BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
			.willReturn(true);

		// doReturn(true)
		// 	.when(mailSendClient)
		// 	.sendEmail(anyString(), anyString(), anyString(), anyString());

		// when
		boolean result = mailService.sendMail("", "", "", "");

		// then
		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
		assertThat(result).isTrue();
	}
}