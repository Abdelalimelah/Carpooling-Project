package com.JAVA.DAO;

import com.JAVA.Beans.*;
import java.util.List;

public interface AdminDAO {
    int getTotalUsers() throws Exception;
    int getTotalDrivers() throws Exception;
    int getTotalPassengers() throws Exception;
    int getTotalFeedbacks() throws Exception;
    
    List<DriverBean> getAllDrivers() throws Exception;
    DriverBean getDriverById(int id) throws Exception;
    boolean addDriver(DriverBean driver, UserBean user) throws Exception;
    boolean updateDriver(DriverBean driver, UserBean user) throws Exception;
    boolean deleteDriver(int driverId) throws Exception;
    
    List<PassengerBean> getAllPassengers() throws Exception;
    PassengerBean getPassengerById(int id) throws Exception;
    boolean addPassenger(PassengerBean passenger, UserBean user) throws Exception;
    boolean updatePassenger(PassengerBean passenger, UserBean user) throws Exception;
    boolean deletePassenger(int passengerId) throws Exception;
    
    List<FeedbackBean> getAllFeedbacks() throws Exception;
    FeedbackBean getFeedbackById(int id) throws Exception;
    boolean deleteFeedback(int feedbackId) throws Exception;
}