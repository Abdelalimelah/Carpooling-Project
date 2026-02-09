package com.JAVA.DAO.Implement;

import com.JAVA.TestUtils.TestDb;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseDaoTest {
    @BeforeEach
    void resetDb() {
        TestDb.reset();
    }
}
