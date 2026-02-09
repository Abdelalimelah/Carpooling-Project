package com.JAVA.DAO.Exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOExceptionTest {

    @Test
    void constructors_setMessageAndCause() {
        DAOException e1 = new DAOException("msg");
        assertEquals("msg", e1.getMessage());

        Throwable cause = new RuntimeException("root");
        DAOException e2 = new DAOException("wrap", cause);
        assertEquals("wrap", e2.getMessage());
        assertSame(cause, e2.getCause());

        DAOException e3 = new DAOException(cause);
        assertSame(cause, e3.getCause());
    }
}
