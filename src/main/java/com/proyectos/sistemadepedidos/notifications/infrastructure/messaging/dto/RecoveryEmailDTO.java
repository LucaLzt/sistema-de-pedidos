package com.proyectos.sistemadepedidos.notifications.infrastructure.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryEmailDTO {
    private String to;
    private String link;
}
