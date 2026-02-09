package com.JAVA.DAO.Factory;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DAOFactoryTest {

    @Test
    void getInstanceProvidesConnection() throws Exception {
        DAOFactory factory = DAOFactory.getInstance();
        try (Connection conn = factory.getConnection()) {
            assertNotNull(conn);
        }
    }
}
