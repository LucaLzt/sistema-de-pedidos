package com.proyectos.sistemadepedidos.mail.infrastructure.messaging;

import com.proyectos.sistemadepedidos.mail.application.port.in.SendMailCommand;
import com.proyectos.sistemadepedidos.mail.application.port.in.SendMailUseCase;
import com.proyectos.sistemadepedidos.notifications.infrastructure.messaging.dto.OrderEmailDTO;
import com.proyectos.sistemadepedidos.notifications.infrastructure.messaging.dto.RecoveryEmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final SendMailUseCase sendMailUseCase;

    @RabbitListener(queues = "${rabbitmq.email.recovery-password.queue}")
    public void consumeRecoveryEmail(RecoveryEmailDTO dto) {
        SendMailCommand command = new SendMailCommand(
                dto.getTo(),
                "Password Recovery",
                """
                        Hello,
                        
                        To reset your password, click the following link:
                        %s
                        
                        If you did not request this change, you can ignore this message.
                        """.formatted(dto.getLink()),
                false
        );

        sendMailUseCase.send(command);
    }

    @RabbitListener(queues = "${rabbitmq.email.order.queue}")
    public void consumeOrderEmail(OrderEmailDTO dto) {
        SendMailCommand command = new SendMailCommand(
                dto.getTo(),
                "Update to your order  #" + dto.getOrderId(),
                """
                        Hello,
                        
                        Your order #%d has changed to status: %s.
                        
                        You can see more details here:
                        %s
                        """.formatted(dto.getOrderId(), dto.getStatus(), dto.getDetailUrl()),
                false
        );
        sendMailUseCase.send(command);
    }

}
