package com.JAVA.DAO.Exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOConfigurationExceptionTest {

    @Test
    void constructors_setMessageAndCause() {
        DAOConfigurationException e1 = new DAOConfigurationException("msg");
        assertEquals("msg", e1.getMessage());

        Throwable cause = new RuntimeException("root");
        DAOConfigurationException e2 = new DAOConfigurationException("wrap", cause);
        assertEquals("wrap", e2.getMessage());
        assertSame(cause, e2.getCause());

        DAOConfigurationException e3 = new DAOConfigurationException(cause);
        assertSame(cause, e3.getCause());
    }
}
