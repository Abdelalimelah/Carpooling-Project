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
	 int countAll() throws DAOException;
	 int countByStatus(String status) throws DAOException;
	 java.math.BigDecimal sumCompletedEarnings() throws DAOException;
	 int countFiltered(Integer tripId, Integer passengerId, String status) throws DAOException;
	 java.util.List<com.JAVA.Beans.BookingBean> findPagedFiltered(
	         Integer tripId,
	         Integer passengerId,
	         String status,
	         int offset,
	         int limit
	 ) throws DAOException;
	 boolean deleteById(int id) throws DAOException;
}