package com.JAVA.DAO;

import java.math.BigDecimal;
import java.util.List;

import com.JAVA.Beans.BookingBean;
import com.JAVA.DAO.Exception.DAOException;

public interface BookingDAO {

	 List<BookingBean> findByTripId(int tripId) throws DAOException;
	 public boolean updateStatus(int bookingId, String status) throws DAOException;
	 public int countConfirmedBookingsByTripId(int tripId) throws DAOException;
	 public List<BookingBean> findByPassengerId(int passengerId) throws DAOException;
	 public boolean create(BookingBean booking) throws DAOException;
	 public BigDecimal calculateTotalEarningsByDriver(int driverId) throws DAOException;
	 public void markTripAsCompleted(int tripId) throws DAOException;
	 public boolean tripIsCompleted(int tripId) throws DAOException;
	 public BookingBean findById(int id) throws DAOException;
	  
}