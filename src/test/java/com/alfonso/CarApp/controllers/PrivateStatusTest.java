package com.alfonso.CarApp.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PrivateStatusTest {
    @InjectMocks
    PrivateController privateStatus;

    @Test
    void whenPrivateStatusCalled_returnOK() {
        String response = privateStatus.getPrivateStatus();
        Assertions.assertEquals("OK", response);
    }
}
