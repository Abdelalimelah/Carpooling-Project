package com.JAVA.Beans;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarBeanTest {
    @Test
    void testCarBeanGettersAndSetters() {
        CarBean bean = new CarBean();
        bean.setId(1);
        bean.setDriverId(2);
        bean.setModel("Toyota");
        bean.setBrand("Supra");
        assertEquals(1, bean.getId());
        assertEquals(2, bean.getDriverId());
        assertEquals("Toyota", bean.getModel());
        assertEquals("Supra", bean.getBrand());
    }
}
