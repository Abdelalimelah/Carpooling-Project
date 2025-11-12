package com.JAVA.DAO;

import java.util.List;

import com.JAVA.Beans.CarBean;
import com.JAVA.DAO.Exception.DAOException;

public interface CarDAO {
	
	 public boolean create(CarBean car) throws DAOException;
	 public List<CarBean> findByDriverId(int driverId) throws DAOException;
	 public CarBean findById(int id) throws DAOException;
	 public boolean update(CarBean car) throws DAOException;
	 public boolean delete(int id) throws DAOException;
	
}
