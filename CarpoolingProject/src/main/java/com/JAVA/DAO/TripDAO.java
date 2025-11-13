package com.JAVA.DAO;

import java.util.Date;
import java.util.List;

import com.JAVA.Beans.TripBean;
import com.JAVA.DAO.Exception.DAOException;

public interface TripDAO {

	 public List<TripBean> findByDriverId(int driverId) throws DAOException;
	 public boolean create(TripBean trip) throws DAOException;
	 public TripBean findById(int id) throws DAOException;
	 public boolean update(TripBean trip) throws DAOException;
	 public boolean delete(int id) throws DAOException;
	 public List<TripBean> findAvailableTripsForPassenger(
		        int passengerId, 
		        String departureCity, 
		        String arrivalCity, 
		        String dateStr) throws DAOException;
	 
}
